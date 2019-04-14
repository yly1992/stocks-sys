package  model.formulas;

import  model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class AverageDailyTradingVolume extends AbstractFormula {

   private int period;

   public AverageDailyTradingVolume( int period, List<Quote> quotes ) {
      super( quotes );
      this.period = period;
   }

   @Override
   public BigDecimal calculateValue() {
      validate();
      long sum = 0;
      for(Quote quote : quotes.subList( 0, period )){
         sum += quote.getVolume();
      }
      
      if (quotes.size() > 0 && period > 0 ){
         return  new BigDecimal( sum / period );
      }else{
         return new BigDecimal( sum );
      }
   }
   
   private void validate() {
      if(quotes.size() < period){
         throw new RuntimeException( "ADTV: There are no enough quotes to calculate ADTV" );
      }
   }
   
   @Override
   public String getKeySufix() {
      return String.valueOf( period );
   }

}
