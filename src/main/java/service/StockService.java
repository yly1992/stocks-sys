package service;

import model.IStockWrapper;
import model.Quote;
import model.StockWrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import yahoofinance.Stock;
import yahoofinance.histquotes.HistoricalQuote;

@Service
@EnableScheduling

public class StockService implements IStockService{
   
	
   @Autowired
   private YahooFinanceService yahooFinanceService;
   

   public IStockWrapper getStock( String symbol ) throws IOException{
      return new StockWrapper( yahooFinanceService.getStock( normalizeSymbol( symbol ) ) );
   }
   
  
   private String normalizeSymbol(String symbol){
      return symbol.toUpperCase().trim();
   }
   
   private String[] normalizeSymbols(String[] symbols){
      for(int i = 0; i < symbols.length; i++){
         symbols[i] = normalizeSymbol( symbols[i] );
      }
      return symbols;
   }

public List<Quote> getHistory(String symbol) throws IOException {
	// TODO Auto-generated method stub
	return null;
}

public List<Quote> getHistory(String symbol, Calendar from, Calendar to) throws IOException {
	// TODO Auto-generated method stub
	return null;
}

public Map<String, List<Quote>> getHistory(String[] symbols, Calendar from, Calendar to) throws IOException {
	// TODO Auto-generated method stub
	return null;
}

public void importQuotes(Collection<Quote> quotes) {
	// TODO Auto-generated method stub
	
}

public List<String> getSymbols() {
	// TODO Auto-generated method stub
	return null;
}

public void updateDBJob() {
	// TODO Auto-generated method stub
	
}

public void deleteStock(String symbol) {
	// TODO Auto-generated method stub
	
}

public void autoUpdateDBHistory(Integer fromYear) {
	// TODO Auto-generated method stub
	
}

public void updateDBHistory(Integer year, String symbol) {
	// TODO Auto-generated method stub
	
}


public Map<String, IStockWrapper> getStocks(String[] symbol) throws IOException {
	// TODO Auto-generated method stub
	return null;
}
   
}
