package model.formulas;

import model.Quote;
import java.math.BigDecimal;
import java.util.ArrayList;


public class Difference extends AbstractFormula {

   private Formula formula2;
   private Formula formula1;

   public Difference( Formula formula1, Formula formula2 ) {
      super( new ArrayList<Quote>() );
      this.formula1 = formula1;
      this.formula2 = formula2;
   }

   @Override
   public BigDecimal calculateValue() {
      double f1 = formula1.calculate().doubleValue();
      double f2 = formula2.calculate().doubleValue();
      return new BigDecimal( Math.abs( f1 - f2 ) );
   }
   
   @Override
   public String getKeySufix(){
      return this.formula1.getKey() + "-" + this.formula2.getKey();
   }

}
