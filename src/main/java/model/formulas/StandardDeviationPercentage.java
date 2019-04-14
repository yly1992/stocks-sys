package model.formulas;

import model.Quote;
import java.math.BigDecimal;
import java.util.List;

public class StandardDeviationPercentage extends AbstractFormula{
   
   private int period;

   public StandardDeviationPercentage( int period, List<Quote> quotes ) {
      super( quotes );
      this.period = period;
   }

   @Override
   public BigDecimal calculateValue() {
      double sd = new StandardDeviation( period, quotes ).calculate().doubleValue();
      double average = new Average( period, quotes ).calculate().doubleValue();
      return new BigDecimal( (sd / average ) * 100d);
   }
   
   @Override
   public String getKeySufix(){
      return String.valueOf( period );
   }

}
