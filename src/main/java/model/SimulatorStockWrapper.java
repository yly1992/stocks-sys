package model;


public class SimulatorStockWrapper implements IStockWrapper {
   
   private static final long serialVersionUID = -8028908418065747738L;
   private String symbol;
   private Quote lastQuote;

   public SimulatorStockWrapper(String symbol, Quote lastQuote){
      this.symbol = symbol;
      this.lastQuote = lastQuote;
   }

   public String getSymbol() {
      return symbol;
   }

   public Quote getLastQuote() {
      return lastQuote;
   }

}
