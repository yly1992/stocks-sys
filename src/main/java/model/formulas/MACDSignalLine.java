package model.formulas;

import model.Quote;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class MACDSignalLine extends AbstractFormula {
   
   private int slowPeriod; //12
   private int fastPeriod; //26
   private int signalPeriod; //9

   public MACDSignalLine( int fastPeriod, int slowPeriod, int signalPeriod, List<Quote> quotes){
      super( quotes );
      this.fastPeriod = fastPeriod;
      this.slowPeriod = slowPeriod;
      this.signalPeriod = signalPeriod;
   }

   @Override
   public BigDecimal calculateValue() {
      List<Quote> macdQuotes = new ArrayList<Quote>();
      MACD macd;
      BigDecimal macdValue;
      Quote macdQuote;
      //Calculate MACD of every quote and store in macdQuotes, then calculate EMA(signalPeriod)
      for(int i= quotes.size() - slowPeriod; i >= 0; i--){
         macd = new MACD( fastPeriod, slowPeriod, quotes.subList( i, quotes.size() ) );
         macdValue = macd.calculate();
         macdQuote = new Quote();
         macdQuote.setSymbol( quotes.get( i ).getSymbol() );
         macdQuote.setDate( quotes.get(i).getDate());
         macdQuote.setClose( macdValue );
         macdQuote.setVolume( quotes.get( i ).getVolume() );
         //Quotes always must be in descending order
         macdQuotes.add( 0, macdQuote );
      }
      
      ExponentialMovingAverage ema = new ExponentialMovingAverage( signalPeriod, macdQuotes );
      
      return ema.calculate();
   }
   
   @Override
   public String getKeySufix(){
      return String.valueOf( slowPeriod ) + "-" + String.valueOf( fastPeriod ) + "-"  + String.valueOf( signalPeriod );
   }

}
