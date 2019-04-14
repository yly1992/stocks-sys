package model.formulas;

import model.Quote;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StochasticOscillatorD extends AbstractFormula {
   private int length;
   private int period;

   public StochasticOscillatorD( int length, int period, List<Quote> quotes){
      super( quotes );
      this.length = length;
      this.period = period;
   }
   
   @Override
   public BigDecimal calculateValue() {
      validate();
      List<BigDecimal> ks = new ArrayList<BigDecimal>();
      for(int i = 0; i < period ; i++){
         ks.add( new StochasticOscillatorK( length, quotes.subList( i, quotes.size() ) ).calculate());
      }
      
      double sum = 0;
      for(BigDecimal k : ks){
         sum += k.doubleValue();
      }
      
      return new BigDecimal( sum / period );
   }

   private void validate() {
      if( quotes.size() < length ){
         throw new RuntimeException( "Stochastic Oscillator: There are no quotes to calculate Stochastic Oscillator" );
      }
   }
   
   @Override
   public String getKeySufix(){
      return String.valueOf( period ) + "-" + String.valueOf( length );
   }
}
