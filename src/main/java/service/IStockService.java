package service;

import model.IStockWrapper;
import model.Quote;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IStockService {
   
   public IStockWrapper getStock( String symbol ) throws IOException;
   
   public Map<String,IStockWrapper> getStocks( String[] symbol ) throws IOException;
   
   public List<Quote> getHistory( String symbol )  throws IOException;
   
   public List<Quote> getHistory( String symbol, Calendar from, Calendar to ) throws IOException;
   
   public  Map<String, List<Quote>> getHistory( String[] symbols, Calendar from, Calendar to ) throws IOException;
   
   public void importQuotes(Collection<Quote> quotes);

   public List<String> getSymbols();
   
   public void updateDBJob();

   public void deleteStock( String symbol );

   public void autoUpdateDBHistory( Integer fromYear );
   
   public void updateDBHistory( Integer year, String symbol );

}
