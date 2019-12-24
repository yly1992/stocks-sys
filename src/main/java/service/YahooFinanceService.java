package service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.SneakyThrows;
import model.Quote;
import org.springframework.stereotype.Service;
import transformer.FormatterHelper;
import transformer.Java11HttpClient;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;


@Service
public class YahooFinanceService {

   private static final String BASEURL = "https://finnhub.io/api/v1/";
   private static final String APIKEY = "&token=bntbl87rh5rfc44snao0";
   private static final String CANDLE = "stock/candle?resolution=D&symbol=";
   private static final String FROM = "&from=";
   private static final String TO = "&to=";

   public FormatterHelper formatterHelper = new FormatterHelper();

   public Java11HttpClient java11HttpClient = new Java11HttpClient();

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
   @SneakyThrows
   public List<Quote> getHistory(String symbol, Calendar from, Calendar to ) throws IOException{
      symbol = symbol.substring(8);
      String fromTime = formatterHelper.calendarToTimestamp(from).substring(0,10);
      String toTime = formatterHelper.calendarToTimestamp(to).substring(0,10);
      String candleurl = BASEURL + CANDLE + symbol + FROM + fromTime + TO + toTime + APIKEY;
      System.out.println("@@@@@@");
      System.out.println(candleurl);
      HttpRequest request = HttpRequest.newBuilder()
              .GET()
              .uri(URI.create(candleurl))
              .setHeader("liyuy", "Java 11 HttpClient Bot")
              .build();
      String result = java11HttpClient.sendGet(request);
      System.out.println(result);
      return null;
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
      Map<String, List<HistoricalQuote>> resultMap = new HashMap<String, List<HistoricalQuote>>();
      for(String symbol : symbols){

      }
      return resultMap;
   }

}