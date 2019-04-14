package model.formulas;

import model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class AverageTrueRange extends AbstractFormula {
   
   private int period = 14;

   public AverageTrueRange( int period, List<Quote> quotes){
      super( quotes );
      this.period  = period;
   }

   @Override
   public BigDecimal calculateValue() {
      validate();
      double[] tr = new double[quotes.size()];
      double sumTR = 0d;
      double[] atr = new double[quotes.size()];
      int j = 0;
      Quote qi,qj;
      for(int i = quotes.size() - 1 ; i >= 0  ; --i){
         j = i+1;
         qi = quotes.get( i );
         qj = quotes.size() > j ? quotes.get( j ) : null;
         tr[i] = calculateTrueRange( qi, qj );
         sumTR += tr[i];
         if( i < quotes.size() - period ){
            if( atr[j] > 0 ){
               atr[i] = (atr[j]*(period -1) + tr[i]) / (double)period;
            }else{
               atr[i] = sumTR / (double)period;
            }
         }
      }
      return new BigDecimal( atr[0] );
   }

   private double calculateTrueRange( Quote qi, Quote qj ) {
      double highLow = qi.getHigh().doubleValue() - qi.getLow().doubleValue();
      if( qj == null){
         return highLow;
      }
      double highClose = Math.abs( qi.getHigh().doubleValue() - qj.getClose().doubleValue() );
      double lowClose = Math.abs( qi.getLow().doubleValue() - qj.getClose().doubleValue() );
      return Math.max( highLow, Math.max( highClose, lowClose ));
   }
   
   @Override
   public String getKeySufix(){
      return String.valueOf( period );
   }
   
   private void validate() {
      if( period < 2 ){
         throw new RuntimeException( "Average True Range: Minimum period must be 2, current period: " + period);
      }
      if( quotes.size() < period ){
         throw new RuntimeException( "Average True Range: There are no quotes to calculate Average True Range. "
                  + "Min number of quotes required: " + period 
                  + " Current number of quotes: " + quotes.size() );
      }
   }

}
