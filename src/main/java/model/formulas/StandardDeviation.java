package model.formulas;

import model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class StandardDeviation extends AbstractFormula {
   
   private int period;
   

   public StandardDeviation( int period, List<Quote> quotes){
      super( quotes );
      this.period = period;
   }


   @Override
   public BigDecimal calculateValue() {
      double variance = new Variance( period, quotes ).calculate().doubleValue();
      return new BigDecimal( Math.sqrt( variance ) );
   }
   
   @Override
   public String getKeySufix(){
      return String.valueOf( period );
   }

}
