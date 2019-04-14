package model;

import java.io.Serializable;

public interface IStockWrapper extends Serializable {
   
   public String getSymbol();
   
   public Quote getLastQuote();
   
}
