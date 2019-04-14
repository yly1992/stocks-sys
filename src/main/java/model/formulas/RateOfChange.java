package model.formulas;

import model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class RateOfChange extends AbstractFormula {
   
   private int period;

   public RateOfChange( int period, List<Quote> quotes ) {
      super( quotes );
      this.period = period;
   }

   @Override
   public BigDecimal calculateValue() {
      validate();
      double todayClose = quotes.get( 0 ).getClose().doubleValue();
      double nPeriodClose = quotes.get( period ).getClose().doubleValue();
      double result = ( todayClose - nPeriodClose ) / nPeriodClose * 100d;
      return new BigDecimal( result );
   }
   
   private void validate() {
      if(quotes.size() < period){
         throw new RuntimeException( "ROC: There are no enough quotes to calculate ROC" );
      }
   }
   
   @Override
   public String getKeySufix() {
      return String.valueOf( period );
   }

}
