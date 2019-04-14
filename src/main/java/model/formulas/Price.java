package model.formulas;

import model.Quote;
import java.math.BigDecimal;
import java.util.Arrays;


/**
 * Price represents current stock price as a formula, i.e. PRICE(FRAN.BA)
 * @author Sergio Cormio
 *
 */
public class Price extends AbstractFormula {
   
   private Quote quote;

   public Price( Quote quote ){
      super( Arrays.asList( quote ) );
      this.quote = quote;
   }

   public BigDecimal calculateValue() {
      return quote.getClose();
   }

}
