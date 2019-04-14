package model.formulas;

import model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class AverageTrueRangePercentage extends AbstractFormula {
   
   private int period;

   public AverageTrueRangePercentage( int period, List<Quote> quotes){
      super( quotes );
      this.period  = period;
   }

   @Override
   public BigDecimal calculateValue() {
      double atr = new AverageTrueRange( period, quotes ).calculate().doubleValue();
      double average = new Average( period, quotes ).calculate().doubleValue();
      return new BigDecimal( (atr / average ) * 100d);
   }
   
   @Override
   public String getKeySufix(){
      return String.valueOf( period );
   }

}
