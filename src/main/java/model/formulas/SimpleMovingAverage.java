package model.formulas;

import model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class SimpleMovingAverage extends AbstractFormula {
   
   private int period;

   /**
    * @param period must be greater than 0
    * @param quotes must be in inverse order: Most recent quote first
    */
   public SimpleMovingAverage( int period, List<Quote> quotes ){
      super( quotes );
      this.period = period;
   }
   
   public BigDecimal calculateValue(){
      validate();
      BigDecimal sum = new BigDecimal( 0 );
      
      for( int i = 0; i < period; i++){
         sum = sum.add( quotes.get(i).getClose() );
      }
      
      return new BigDecimal( sum.doubleValue() /  period  );
   }

   private void validate() {
      if(quotes.size() < period){
         throw new RuntimeException( "SMA: There are no quotes to calculate SMA" );
      }
   }
   
   @Override
   public String getKeySufix(){
      return String.valueOf( period );
   }

}
