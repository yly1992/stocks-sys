package service;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

@Service
public class YahooFinanceService {
   
   public Stock getStock( String symbol ) throws IOException{
      return YahooFinance.get( symbol, true );
   }
   
   public Map<String,Stock> getStocks( String[] symbols ) throws IOException{
      return YahooFinance.get( symbols, true );
   }
   
   /**
    * Returns a year of daily quotes in DESC order. It doesn't retrieve the last price
    * @param symbol
    * @return
    * @throws IOException
    */
   public List<HistoricalQuote> getHistory( String symbol, Calendar from, Calendar to ) throws IOException{
      return getHistory( new String[]{ symbol }, from, to ).get( symbol );
   }
   
   /**
    * Always returns a map with its keySet full of the symbols passed as parameters
    * @param symbols
    * @param from
    * @param to
    * @return
    * @throws IOException
    */
   public Map<String, List<HistoricalQuote>> getHistory( String[] symbols, Calendar from, Calendar to ) throws IOException{
      Map<String, Stock> map = YahooFinance.get( symbols );
      Map<String, List<HistoricalQuote>> resultMap = new HashMap<String, List<HistoricalQuote>>();
      for(String symbol : symbols){
         Stock stock = map.get( symbol );
         if( stock == null){
            System.err.println( "Could not get History from symbol: " + symbol );
            continue;
         }
         resultMap.put( symbol, stock.getHistory( from, to, Interval.DAILY ) );
      }
      return resultMap;
   }

}