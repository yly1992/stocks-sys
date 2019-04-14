package model.formulas;

import model.Quote;
import java.math.BigDecimal;
import java.util.List;

public class MACDHistogram extends AbstractFormula{
   
   private int slowPeriod; //12
   private int fastPeriod; //26
   private int signalPeriod; //9

   public MACDHistogram( int fastPeriod, int slowPeriod, int signalPeriod, List<Quote> quotes){
      super( quotes );
      this.fastPeriod = fastPeriod;
      this.slowPeriod = slowPeriod;
      this.signalPeriod = signalPeriod;
   }


   @Override
   public BigDecimal calculateValue() {
      MACD macd = new MACD( fastPeriod, slowPeriod, quotes );
      MACDSignalLine macdSignalLine = new MACDSignalLine( fastPeriod, slowPeriod, signalPeriod, quotes );
      return macd.calculate().subtract( macdSignalLine.calculate() );
   }
   
   @Override
   public String getKeySufix(){
      return String.valueOf( slowPeriod ) + "-" + String.valueOf( fastPeriod ) + "-"  + String.valueOf( signalPeriod );
   }

}
