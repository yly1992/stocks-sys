package model.formulas;

import model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class BollingerBandUpper extends AbstractFormula {

   private int period;
   private int k;
   
   public BollingerBandUpper( int period, int k, List<Quote> quotes){
      super( quotes );
      this.period = period;
      this.k = k;
   }

   @Override
   public BigDecimal calculateValue() {
      double sma = new SimpleMovingAverage( period, quotes ).calculate().doubleValue();
      double sd = new StandardDeviation( period, quotes ).calculate().doubleValue();
      return new BigDecimal( sma + (k * sd));
   }
   
   @Override
   public String getKeySufix(){
      return String.valueOf( period ) + "-" + String.valueOf( k );
   }

}
