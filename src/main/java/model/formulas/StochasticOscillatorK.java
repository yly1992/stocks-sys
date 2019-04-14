package model.formulas;

import model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class StochasticOscillatorK extends AbstractFormula {

   private int length;

   public StochasticOscillatorK( int length, List<Quote> quotes){
      super( quotes );
      this.length = length;
   }
   
   @Override
   public BigDecimal calculateValue() {
      validate();
      Quote firstQuote = getFirstValidQuote();
      double highestHigh = firstQuote.getHigh().doubleValue();
      double lowestLow = firstQuote.getLow().doubleValue();
      
      Quote quote;
      for(int i = 0 ; i < length; i++ ){
         quote = quotes.get( i );
         if(quote.getVolume().equals( 0L )){
            continue;
         }
         
         if(quote.getLow().doubleValue() < lowestLow){
            lowestLow = quote.getLow().doubleValue();
         }
         
         if(quote.getHigh().doubleValue() > highestHigh){
            highestHigh = quote.getHigh().doubleValue();
         }
      }
      //100(C - L14)/(H14 - L14)
      double dividend = highestHigh - lowestLow ;
      double numerator = firstQuote.getClose().doubleValue() - lowestLow ;
      try{
         return new BigDecimal( (numerator / dividend) * 100d);
      }catch(NumberFormatException nfe){
//         System.err.println( "Error calculating: (numerator / dividend) * 100d: (" + numerator + " / " + dividend +") * 100d = " + (numerator / dividend) * 100d);
         return new BigDecimal(0);
      }
   }

   private void validate() {
      if( quotes.size() < length ){
         throw new RuntimeException( "Stochastic Oscillator: There are no quotes to calculate Stochastic Oscillator" );
      }
   }
   
   @Override
   public String getKeySufix(){
      return String.valueOf( length );
   }

}
