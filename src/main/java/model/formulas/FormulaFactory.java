//package model.formulas;
//
//import model.Quote;
//import services.IStockService;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class FormulaFactory {
//   public static Formula getFormula(String expression, IStockService stockService){
//      Formula result;
//      List<Quote> quotes;
//      Quote quote;
//      int firstParenthesis = expression.indexOf( "(" );
//      int lastParenthesis = expression.lastIndexOf( ")" );
//      if (firstParenthesis < 0 || lastParenthesis < 0){
//         throw new RuntimeException( expression + " is not a valid formula expression" );
//      }
//      String formulaName = expression.substring( 0 , firstParenthesis );
//      String entireParams = expression.substring( firstParenthesis + 1 , lastParenthesis );
//      
//      String[] params = entireParams.split( ",(?![^()]*\\))" );
//      if(formulaName.equals( "EMA" )){
//         quotes = getQuotes( stockService, params[1] );
//         result = new ExponentialMovingAverage( Integer.parseInt( params[0] ), quotes );
//      }else if(formulaName.equals( "SMA" )){
//         quotes = getQuotes( stockService, params[1] );
//         result = new SimpleMovingAverage( Integer.parseInt( params[0] ), quotes );
//      }else if(formulaName.equals( "RSI" )){
//         quotes = getQuotes( stockService, params[1] );
//         result = new RelativeStrengthIndex( Integer.parseInt( params[0] ), quotes );
//      }else if(formulaName.equals( "PRICE" )){
//         try {
//            quote = stockService.getStock( params[0] ).getLastQuote();
//         } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException( "Could not calculate price from symbol: " + expression.substring( 6 ) );
//         }
//         result = new Price( quote );
//      }else if(formulaName.equals( "VOLUME" )){
//         try {
//            quote = stockService.getStock( params[0] ).getLastQuote();
//         } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException( "Could not calculate volume from symbol: " + expression.substring( 7 ) );
//         }
//         result = new Volume( quote );
//      }else if(formulaName.equals( "MACD_SIGNAL_LINE" )){
//         quotes = getQuotes( stockService, params[3] );
//         result = new MACDSignalLine( Integer.parseInt( params[0] ), Integer.parseInt( params[1] ), Integer.parseInt( params[2] ), quotes );
//      }else if(formulaName.equals( "MACD_HISTOGRAM" )){
//         quotes = getQuotes( stockService, params[3] );
//         result = new MACDHistogram( Integer.parseInt( params[0] ), Integer.parseInt( params[1] ), Integer.parseInt( params[2] ), quotes );
//      }else if(formulaName.equals( "MACD" )){
//         quotes = getQuotes( stockService, params[2] );
//         result = new MACD( Integer.parseInt( params[0] ), Integer.parseInt( params[1] ), quotes );
//      }else if(formulaName.equals( "STOCHASTIC_K" )){
//         quotes = getQuotes( stockService, params[1] );
//         result = new StochasticOscillatorK( Integer.parseInt( params[0] ), quotes );
//      }else if(formulaName.equals( "STOCHASTIC_D" )){
//         quotes = getQuotes( stockService, params[2] );
//         result = new StochasticOscillatorD( Integer.parseInt( params[0] ), Integer.parseInt( params[1] ) , quotes );
//      }else if(formulaName.equals( "AVERAGE" )){
//         quotes = getQuotes(stockService, params[1]);
//         result = new Average( Integer.parseInt( params[0] ), quotes );
//      }else if(formulaName.equals( "VARIANCE" )){
//         quotes = getQuotes(stockService, params[1]);
//         result = new Variance( Integer.parseInt( params[0] ), quotes );
//      }else if(formulaName.equals( "STANDARD_DEVIATION" )){
//         quotes = getQuotes(stockService, params[1]);
//         result = new StandardDeviation( Integer.parseInt( params[0] ), quotes );
//      }else if(formulaName.equals( "STANDARD_DEVIATION_PERCENTAGE" )){
//         quotes = getQuotes(stockService, params[1]);
//         result = new StandardDeviationPercentage( Integer.parseInt( params[0] ), quotes );
//      }else if(formulaName.equals( "BB_LOWER" )){
//         quotes = getQuotes(stockService, params[2]);
//         result = new BollingerBandLower( Integer.parseInt( params[0] ), Integer.parseInt( params[1] ), quotes );
//      }else if(formulaName.equals( "BB_UPPER" )){
//         quotes = getQuotes(stockService, params[2]);
//         result = new BollingerBandUpper( Integer.parseInt( params[0] ), Integer.parseInt( params[1] ), quotes );
//      }else if(formulaName.equals( "DIFF" )){
//         result = new Difference( getFormula(params[0], stockService), getFormula(params[1], stockService) );
//      }else if(formulaName.equals( "ATR" )){
//         quotes = getQuotes(stockService, params[1]);
//         result = new AverageTrueRange( Integer.parseInt( params[0] ), quotes );
//      }else if(formulaName.equals( "ATRP" )){
//         quotes = getQuotes(stockService, params[1]);
//         result = new AverageTrueRangePercentage( Integer.parseInt( params[0] ), quotes );
//      }else if(formulaName.equals( "ROC" )){
//         quotes = getQuotes(stockService, params[1]);
//         result = new RateOfChange( Integer.parseInt( params[0] ), quotes );
//      }else if(formulaName.equals( "ADTV" )){
//         quotes = getQuotes(stockService, params[1]);
//         result = new AverageDailyTradingVolume( Integer.parseInt( params[0] ), quotes );
//      }else{
//         throw new RuntimeException("Unknown Formula: " + expression);
//      }
//      return result;
//   }
//   
//   private static List<Quote> getQuotes(IStockService stockService,String symbol){
//      List<Quote> quotes;
//      try {
//         quotes = stockService.getHistory( symbol );
//      } catch (Exception e) {
//         quotes = new ArrayList<Quote>();
//         e.printStackTrace();
//      }
//      return quotes;
//   }
//
//}
