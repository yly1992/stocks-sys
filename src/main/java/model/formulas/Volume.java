package model.formulas;

import model.Quote;
import java.math.BigDecimal;
import java.util.Arrays;


public class Volume extends AbstractFormula {
   
   private Quote quote;

   public Volume( Quote quote ){
      super(Arrays.asList(quote));
      this.quote = quote;
   }


   @Override
   public BigDecimal calculateValue() {
      return new BigDecimal( quote.getVolume() );
   }
   
}
