package model.formulas;

import model.Quote;
import java.math.BigDecimal;
import java.util.List;

public class ExponentialMovingAverage extends AbstractFormula{
   
   private int period;
   private double alpha;

   public ExponentialMovingAverage( int period, List<Quote> quotes){
      super( quotes );
      this.period = period;
   }
   
   public BigDecimal calculateValue(){
      validate();
      alpha = 2d / (period + 1);
      
      return new BigDecimal(calculateEMA());
   }

   private double calculateEMA() {
      SimpleMovingAverage sma = new SimpleMovingAverage( period, quotes.subList( quotes.size() - period, quotes.size() ) );
      double currentEMA = sma.calculate().doubleValue();
      
      Quote quote;
      for(int i = quotes.size()- period - 1; i >= 0; i-- ){
         quote = quotes.get( i );
         if(quote.getVolume().equals( 0L )){
            continue;
         }
         currentEMA = alpha * quote.getClose().doubleValue() + ((1 - alpha) * currentEMA);
      }
      
      return currentEMA;
   }

   private void validate() {
      if(quotes.size() < period){
         throw new RuntimeException( "EMA: There are not enough quotes to calculate EMA(" + period + ") Quantity of Quotes: " + quotes.size() );
      }
   }
   
   @Override
   public String getKeySufix(){
      return String.valueOf( period );
   }
}
