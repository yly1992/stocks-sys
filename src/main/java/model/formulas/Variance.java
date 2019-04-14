package model.formulas;

import model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class Variance extends AbstractFormula {
   
   private int period;

   public Variance(int period, List<Quote> quotes){
      super( quotes );
      this.period = period;
   }

   @Override
   public BigDecimal calculateValue() {
      double average = new Average( period, quotes ).calculate().doubleValue();
      double sum = 0;
      for(Quote quote : quotes.subList( 0, period )){
         sum += Math.pow( quote.getClose().doubleValue() - average, 2d );
      }
      if(quotes.size() == 0){
         return new BigDecimal( 0 );
      }
      return new BigDecimal( sum / period );
   }
   
   @Override
   public String getKeySufix(){
      return String.valueOf( period );
   }

}
