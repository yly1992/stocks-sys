package model.formulas;

import model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class Average extends AbstractFormula {
   private int period;
   

   public Average(int period, List<Quote> quotes){
      super( quotes );
      this.period = period;
   }

   @Override
   public BigDecimal calculateValue() {
      double sum = 0;
      for(Quote quote : quotes.subList( 0, period )){
         sum += quote.getClose().doubleValue();
      }
      
      if (quotes.size() > 0 && period > 0 ){
         return  new BigDecimal( sum / period );
      }else{
         return new BigDecimal( sum );
      }
   }
   
   @Override
   public String getKeySufix(){
      return String.valueOf( period );
   }

}
