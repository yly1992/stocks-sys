package dao;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import lombok.SneakyThrows;
import model.Quote;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import java.util.ArrayList;

@Repository
public class QuoteDAO {

   AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
   DynamoDB dynamoDB = new DynamoDB(client);
   Table table = dynamoDB.getTable("Quotes");

   public List<Quote> findByRange( String symbol, Calendar from, Calendar to ) {
      return findByRangeInBulk(new String[]{ symbol }, from, to).get( symbol );
   }

   @SneakyThrows
   public Map<String, List<Quote>> findByRangeInBulk( String[] symbols, Calendar from, Calendar to ) {
      Map<String, List<Quote>>  symbolToQuotes = new HashMap<>();
      for (String symbol : symbols) {
         symbolToQuotes.put(symbol,new ArrayList<>());
         QuerySpec spec = new QuerySpec()
                 .withKeyConditionExpression("symbol = :v_symbol and time_stamp BETWEEN :v_from_dt_tm AND :v_to_dt_tm")
                 .withValueMap(new ValueMap().withString(":v_symbol", symbol)
                         .withLong(":v_to_dt_tm", to.getTimeInMillis())
                         .withLong(":v_from_dt_tm", from.getTimeInMillis())
                 );
         ItemCollection<QueryOutcome> items = table.query(spec);
         System.out.println("\nfindsomething results:");
         Iterator<Item> iterator = items.iterator();
         while (iterator.hasNext()) {
            Map<String, Object> item = iterator.next().asMap();
            Calendar c = Calendar.getInstance();
            Date d = new Date(Long.parseLong(item.get("time_stamp") + ""));
            c.setTime(d);
            symbolToQuotes.get(symbol).add(Quote.builder()
                    .symbol(item.get("symbol") +"")
                    .date(c)
                    .open(new BigDecimal(item.get("openPrice") + ""))
                    .close(new BigDecimal(item.get("closePrice") + ""))
                    .high(new BigDecimal(item.get("highPrice") + ""))
                    .low(new BigDecimal(item.get("lowPrice") + ""))
                    .volume(Long.parseLong(item.get("volume") + ""))
                    .build()
            );
         }
      }
      System.out.println(symbolToQuotes);
      return symbolToQuotes;

   }

   public List<String> getLoadedSymbols() {
      return new ArrayList<>();
   }


   private void putQuoteInMap( Quote quote, Map<String, List<Quote>> map ) {
      List<Quote> internalList = map.get( quote.getSymbol() );
      if(internalList == null){
         internalList = new ArrayList<>();
         map.put( quote.getSymbol(), internalList );
      }
      internalList.add( quote );
   }

   public void removeQuotes( String symbol ) {
      //delete quote
   }

   public void update( Quote quote ) {

      UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("symbol", quote.getSymbol(),
              "time_stamp", quote.getDate().getTimeInMillis())
              .withUpdateExpression("set openPrice = :r, lowPrice=:p, highPrice=:a, closePrice=:d, volume=:c")
              .withValueMap(new ValueMap().withNumber(":r", quote.getOpen())
                      .withNumber(":p", quote.getLow())
                      .withNumber(":a", quote.getHigh())
                      .withNumber(":d", quote.getClose())
                      .withLong(":c", quote.getVolume()))
              .withReturnValues(ReturnValue.UPDATED_NEW);

      try {
         System.out.println("Updating the item...");
         UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
         System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

      }
      catch (Exception e) {
         System.err.println("Unable to update item: " + quote.getSymbol() + " " + quote.getDate());
         System.err.println(e.getMessage());
      }
   }

//   public void addAQuote(Quote q) {
//      HashMap<String,AttributeValue> item_values =
//              new HashMap<String,AttributeValue>();
//
//      item_values.put("Name", new AttributeValue(name));
//
//      for (String[] field : extra_fields) {
//         item_values.put(field[0], new AttributeValue(field[1]));
//      }
//
//      final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();
//
//      try {
//         ddb.putItem("Quotes", item_values);
//      } catch (ResourceNotFoundException e) {
//         System.err.format("Error: The table \"%s\" can't be found.\n", table_name);
//         System.err.println("Be sure that it exists and that you've typed its name correctly!");
//         System.exit(1);
//      } catch (AmazonServiceException e) {
//         System.err.println(e.getMessage());
//         System.exit(1);
//   }



   
}
