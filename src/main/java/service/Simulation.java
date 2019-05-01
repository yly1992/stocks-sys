package service;

import model.Quote;
import model.operations.Operator;
import model.simulator.PositionRecord;
import model.simulator.SimulationResults;
import model.simulator.SimulatorParameters;
import model.simulator.SimulatorRecord;
import model.simulator.SymbolPerformanceStatistics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simulation {
   
   private static final String INITIAL_INVESTMENT_ORDER_TYPE = "Initial Investment";
   private static final String CAPITAL_ORDER_TYPE = "Capital";
   private SimulatorParameters parameters;
   private IStockService stockService;
   private ExpressionService expressionService;
   private SimulationResults simulationResults = new SimulationResults();
   private StockSimulatorService stockSimulatorService = new StockSimulatorService();
   
   
   //simulation variables
   private String currentSymbol;
   private Calendar currentDate;
   private Quote currentLastQuote;
   private List<Quote> allTheQuotes = new ArrayList<Quote>(); //all The quotes between from and to years
   //This map is because not all the quotes begins at the same date
   private Map<String, Integer> indexPerSymbolMap  = new HashMap<String, Integer>();
   private Map<String, List<Quote>> symbolToQuotesMap  = new HashMap<String, List<Quote>>();
   private Map<String, PositionRecord> positionsMap = new HashMap<String, PositionRecord>();
   private Map<String, SymbolPerformanceStatistics> performanceBySymbolMap = new HashMap<String, SymbolPerformanceStatistics>();
   private SimulatorRecord lastSimulatorRecord;

   public Simulation( SimulatorParameters parameters, IStockService stockService, ExpressionService expressionService ){
      this.parameters = parameters;
      this.stockService = stockService;
      this.expressionService = expressionService;
   }
   
   public SimulationResults run(){
      
      try {
         initSimulationVariables();
         List<Quote> quotesAux;
         
         int index;
         for( Quote currentQuote: allTheQuotes){
            currentSymbol = currentQuote.getSymbol();
            
            quotesAux = symbolToQuotesMap.get( currentSymbol );
            index = indexPerSymbolMap.get( currentSymbol );
            if(index < 0){
               throw new RuntimeException( "There are no quotes for " + currentSymbol );
            }
            stockSimulatorService.setSimulationQuotes( quotesAux.subList( index, quotesAux.size() ));
            currentLastQuote = stockSimulatorService.getStock( currentSymbol ).getLastQuote();
            verifyChangeOfDate( currentLastQuote.getDate() );
            updateLastPriceOnPosition( currentLastQuote );
            if(!tryBuy()){
               if(!trySell()){
                  tryStopLoss();
               }
            }
            indexPerSymbolMap.put( currentSymbol, --index );
         }
         completeSimulationResults();
         return simulationResults;
      } catch (IOException e) {
         e.printStackTrace();
         throw new RuntimeException( "Error running simulation" );
      }
   }

   private void updateLastPriceOnPosition( Quote quote ) {
      PositionRecord positionRecord = positionsMap.get( quote.getSymbol() );
      if( positionRecord == null){
         return;
      }
      if( quote.getClose() != null && quote.getVolume() > 0){
         positionRecord.setLastPrice(  quote.getClose().doubleValue() );
      }
   }

   private void verifyChangeOfDate( Calendar currentQuoteDate ) {
      if( currentQuoteDate.equals( currentDate ) ){
         return;
      }
      //create capitalBalance at currentDate;
      SimulatorRecord capitalRecord = new SimulatorRecord();
      capitalRecord.setId( lastSimulatorRecord.getId() + 1 );
      capitalRecord.setOrderAmount( 0d );
      capitalRecord.setOrderPrice( 0d );
      capitalRecord.setOrderDate( currentDate );
      capitalRecord.setLiquity( lastSimulatorRecord.getLiquity() );
      capitalRecord.setOrderTotalCost( 0 );
      capitalRecord.setOrderType( CAPITAL_ORDER_TYPE );
      capitalRecord.setCapitalBalance( calculateCurrentCapitalBalance() );
      capitalRecord.setOperationPerformance( 0 );
      capitalRecord.setOperationDays( 0 );

      lastSimulatorRecord = capitalRecord;
      simulationResults.addRecord( lastSimulatorRecord );
      currentDate = currentQuoteDate;
   }

   private double calculateCurrentCapitalBalance() {
      double positionsSum = 0d;

      for( PositionRecord positionRecord : positionsMap.values()){
         positionsSum += (positionRecord.getLastPrice() * positionRecord.getOrderAmount());
      }
      return lastSimulatorRecord.getLiquity() + positionsSum;
   }

   private void completeSimulationResults() {
      simulationResults.setFinalLiquity( lastSimulatorRecord.getLiquity() );
      simulationResults.setFinalCapitalBalance( lastSimulatorRecord.getCapitalBalance() );
      simulationResults.setSymbolPerformances( performanceBySymbolMap.values() );
      //Extracts Capital records
      simulationResults.setQuantityOfOperations( calculateQuantityOfOperations()  );
      simulationResults.setTotalPerformance( lastSimulatorRecord.getCapitalBalance() - parameters.getInitialCapital() );
      simulationResults.setTotalPerformancePercentage( (lastSimulatorRecord.getCapitalBalance() - parameters.getInitialCapital())*100/parameters.getInitialCapital() );
      int sumOpp = 0;
      for(SymbolPerformanceStatistics sps : performanceBySymbolMap.values()){
         sumOpp += sps.getBuyingOpportunitiesMissed();
      }
      simulationResults.setTotalBuyingOpportunitiesMissed( sumOpp );
   }

   private int calculateQuantityOfOperations() {
      int count = 0;
      String orderType;
      for(SimulatorRecord record : simulationResults.getRecords()){
         orderType = record.getOrderType();
         if(!CAPITAL_ORDER_TYPE.equals( orderType ) && !INITIAL_INVESTMENT_ORDER_TYPE.equals( orderType )){
            count++;
         }
      }
      
      return count;
   }

   private void initSimulationVariables() throws IOException {
      lastSimulatorRecord = new SimulatorRecord();
      currentDate = new GregorianCalendar(parameters.getYearFrom(),0,1);
      lastSimulatorRecord.setOrderDate( currentDate );
      lastSimulatorRecord.setId( 0 );
      lastSimulatorRecord.setCapitalBalance( parameters.getInitialCapital() );
      lastSimulatorRecord.setLiquity( parameters.getInitialCapital() );
      lastSimulatorRecord.setOrderType( INITIAL_INVESTMENT_ORDER_TYPE );
      simulationResults.addRecord( lastSimulatorRecord );

      List<Quote> quotesAux;
      Calendar from = new GregorianCalendar(parameters.getYearFrom(),0,1);
      from.add( Calendar.DATE, parameters.getPreviousDaysOfAnalysis() * -1 ); 
      Calendar to = new GregorianCalendar(parameters.getYearTo()-1,11,31);
      int indexInFromYear = 0;
      Map<String, List<Quote>> map = stockService.getHistory( parameters.getSymbols(), from, to );
      List<String> unknownSymbols = new ArrayList<String>();
      for(String symbol : parameters.getSymbols()){
         quotesAux = map.get( symbol );
         if( quotesAux == null){
            unknownSymbols.add( symbol );
            continue;
         }
         indexInFromYear = extractIndexInFromYear( quotesAux );
         allTheQuotes.addAll( quotesAux.subList( 0, indexInFromYear + 1 ) );
         indexPerSymbolMap.put( symbol, indexInFromYear );
         symbolToQuotesMap.put( symbol, quotesAux );
      }
      //allTheQuotes must be sorted ASC
      Collections.sort( allTheQuotes, new Comparator<Quote>() {

         public int compare( Quote o1, Quote o2 ) {
            return o1.getDate().compareTo( o2.getDate() );
         }} );
      
      if( unknownSymbols.size() > 0 ){
         List<String> auxSymbolsList = new ArrayList<String>(Arrays.asList( parameters.getSymbols() ));
         auxSymbolsList.removeAll( unknownSymbols );
         parameters.setSymbols( auxSymbolsList.toArray( new String[ auxSymbolsList.size() ] ) );
      }
   }
   
   private int extractIndexInFromYear( List<Quote> quotes ){
      Quote quote;
      for(int i = quotes.size() - 1; i >= 0 ; i--){
         quote = quotes.get( i );
         if(quote.getDate().get( Calendar.YEAR ) == parameters.getYearFrom()){
            return i;
         }
      }
      return 0;
   }

   private boolean tryStopLoss() throws IOException {
      boolean sold = false;
      if( positionsMap.get( currentSymbol ) == null ) {
         return false; //There is NOT a position on this symbol
      }
      if(isVacationDay( currentSymbol )){
         return false;
      }
      
      PositionRecord positionRecord = positionsMap.get( currentSymbol );
      
      double maxCapitalToLoss = lastSimulatorRecord.getCapitalBalance() * parameters.getStopLossPercentage() / 100d;
      double currentValue = currentLastQuote.getClose().doubleValue() * positionRecord.getOrderAmount();
      if( (positionRecord.getOrderTotalCost() - currentValue) > maxCapitalToLoss ){
         SimulatorRecord sellRecord = sell();
         sellRecord.setOrderType( "Sell on StopLoss" );
         
         simulationResults.addRecord( sellRecord );
         positionsMap.remove( currentSymbol );
         lastSimulatorRecord = sellRecord;
         sold = true;
         updateStatistics();
      }
      
      return sold;
   }

   private boolean trySell() throws IOException {
      PositionRecord positionRecord = positionsMap.get( currentSymbol );
      if( positionRecord == null ) {
         return false; //There is NOT a position on this symbol
      }
      if(isVacationDay( currentSymbol )){
         return false;
      }
      
      boolean sold = false;
      Operator operator = expressionService.parseSimulatorExpression( parameters.getSellExpression(), this, stockSimulatorService );
      SimulatorRecord sellRecord = null;
      try{
         //Sell via SELL EXPRESSION
         if( operator.evaluate() ){
            sellRecord = sell();
           
         } else if( daysBetween(positionRecord.getOrderDate(), currentLastQuote.getDate()) > parameters.getPositionTimeoutDays()){
          //Sell via TIMEOUT
            sellRecord = sell();
            sellRecord.setOrderType( "Sell on TimeOut" );
         }
         if ( sellRecord != null ){
            simulationResults.addRecord( sellRecord );
            positionsMap.remove( currentSymbol );
            lastSimulatorRecord = sellRecord;
            sold = true;
            updateStatistics();
         }
      }catch(Exception e){
         
      }
      
      
      return sold;
   }

   private boolean isVacationDay( String symbol ) throws IOException {
      if(currentLastQuote.getVolume() == 0L){
         //you cannot operate on vacation day
         return true;
      }
      return false;
   }

   private SimulatorRecord sell() throws IOException {
      PositionRecord positionRecord = positionsMap.get( currentSymbol );
      double sellAux = currentLastQuote.getClose().doubleValue() * positionRecord.getOrderAmount();
      double commission = sellAux * parameters.getCommissionPercentage() / 100d;
      double totalEarned = sellAux - commission;
      
      SimulatorRecord sellRecord = new SimulatorRecord();
      sellRecord.setRelatedRecordId( positionRecord.getBuyRecord().getId() );
      sellRecord.setId( lastSimulatorRecord.getId() + 1 );
      sellRecord.setOrderAmount( positionRecord.getOrderAmount() );
      sellRecord.setOrderPrice( currentLastQuote.getClose().doubleValue() );
      sellRecord.setOrderDate( currentLastQuote.getDate() );
      sellRecord.setLiquity( lastSimulatorRecord.getLiquity() + totalEarned );
      sellRecord.setOrderTotalCost( totalEarned );
      sellRecord.setOrderSymbol( currentSymbol );
      sellRecord.setOrderType( "Sell" );
//      sellRecord.setCapitalBalance( lastSimulatorRecord.getCapitalBalance() + totalEarned - (positionRecord.getOrderAmount() * positionRecord.getOrderPrice()) );
      sellRecord.setCapitalBalance( lastSimulatorRecord.getCapitalBalance() - commission );
      sellRecord.setOperationPerformance( sellRecord.getOrderTotalCost() - positionRecord.getOrderTotalCost() );
      sellRecord.setOperationDays( daysBetween(positionRecord.getOrderDate(), sellRecord.getOrderDate()) );
      return sellRecord;
   }
   
   private int daysBetween(Calendar d1, Calendar d2) {
      long t1 = d1.getTime().getTime();
      long t2 = d2.getTime().getTime();
      
      return (int) ((t2 - t1) / (1000 * 60 * 60 * 24));
  } 

   private boolean tryBuy() throws IOException {
      if( positionsMap.get( currentSymbol ) != null ) {
         return false; //already has position on this symbol
      }
      
      if(isVacationDay( currentSymbol )){
         return false;
      }
      boolean bought = false;
      Operator operator = expressionService.parseSimulatorExpression( parameters.getBuyExpression(), this, stockSimulatorService );
      
      try{
         if( operator.evaluate() ){
            if(lastSimulatorRecord.getLiquity() >= parameters.getPositionMinimumValue()){
               double buyPrice = calculateAmountOfCapitalToInvest();
               double stockPrice = currentLastQuote.getClose().doubleValue();
               int amount = (int)(buyPrice / stockPrice);
               double stocksValue = amount * stockPrice;
               double commission =  stocksValue * parameters.getCommissionPercentage() / 100d;
               double orderValue = stocksValue + commission;
               //make the buy
               SimulatorRecord buyRecord = new SimulatorRecord();
               buyRecord.setId( lastSimulatorRecord.getId() + 1 );
               buyRecord.setLiquity( lastSimulatorRecord.getLiquity() - orderValue );
               buyRecord.setOrderAmount( amount );
               buyRecord.setOrderPrice( stockPrice );
               buyRecord.setOrderSymbol( currentSymbol );
               buyRecord.setOrderDate( currentLastQuote.getDate() );
               buyRecord.setOrderTotalCost( orderValue );
               buyRecord.setOrderType( "Buy" );
               buyRecord.setCapitalBalance( lastSimulatorRecord.getCapitalBalance() - commission );
               
               simulationResults.addRecord( buyRecord );
               positionsMap.put( currentSymbol, new PositionRecord( buyRecord ) );
               lastSimulatorRecord = buyRecord;
               bought = true;
            }else{
               getSymbolPerformanceStatistics().incBuyingOpportunitiesMissed();
            }
         }
      }catch(Exception e){
         
      }
      return bought;
   }

   /**
    * PRE: lastSimulatorRecord.getLiquity() >= parameters.getPositionMinimumValue()
    * @return
    */
   private double calculateAmountOfCapitalToInvest() {
      double percentage = parameters.getPositionPercentage();
      double aux = (percentage / 100d) * lastSimulatorRecord.getCapitalBalance();
      if(aux > parameters.getPositionMaximumValue()){
         aux = parameters.getPositionMaximumValue();
      }else if( aux < parameters.getPositionMinimumValue()){
         aux = parameters.getPositionMinimumValue();
      }
      // liquity restriction
      if(aux > lastSimulatorRecord.getLiquity()){
         aux = lastSimulatorRecord.getLiquity();
      }
      // if there is no chance to take another position
      if( lastSimulatorRecord.getLiquity() < parameters.getPositionMinimumValue()*2 
               || (lastSimulatorRecord.getLiquity() - aux) < parameters.getPositionMinimumValue()){
         aux = lastSimulatorRecord.getLiquity();
      }
      
      double estimatedComission = aux * parameters.getCommissionPercentage() /100d;
      
      return aux - estimatedComission;
   }
   
   private void updateStatistics() {
      SymbolPerformanceStatistics symbolPerformance = getSymbolPerformanceStatistics();
      
      if(lastSimulatorRecord.getOrderType().startsWith( "Sell" )){
         symbolPerformance.setPerformance( symbolPerformance.getPerformance() + lastSimulatorRecord.getOperationPerformance() );
         if(lastSimulatorRecord.getOperationPerformance()>0){
            symbolPerformance.incPositiveSales();
            simulationResults.incPositiveSales();
         }else{
            symbolPerformance.incNegativeSales();
            simulationResults.incNegativeSales();
         }
         symbolPerformance.addOperationDays(lastSimulatorRecord.getOperationDays());
      }
   }

   private SymbolPerformanceStatistics getSymbolPerformanceStatistics() {
      SymbolPerformanceStatistics symbolPerformance = performanceBySymbolMap.get( currentSymbol );
      if(symbolPerformance == null){
         symbolPerformance = new SymbolPerformanceStatistics();
         symbolPerformance.setSymbol( currentSymbol );
         performanceBySymbolMap.put( currentSymbol, symbolPerformance );
      }
      return symbolPerformance;
   }

   
   /**
    * @return the currentSymbol
    */
   public synchronized String getCurrentSymbol() {
      return currentSymbol;
   }

   
   /**
    * @return the currentLastQuote
    */
   public synchronized Quote getCurrentLastQuote() {
      return currentLastQuote;
   }

   
   /**
    * @return the positionsMap
    */
   public synchronized PositionRecord getPosition( String symbol ) {
      return positionsMap.get( symbol );
   }

   
   /**
    * @return the parameters
    */
   public synchronized SimulatorParameters getParameters() {
      return parameters;
   }
   

}
