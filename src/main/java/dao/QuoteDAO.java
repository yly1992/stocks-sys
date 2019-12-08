package dao;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import model.Quote;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
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

//   public Map<String, List<Quote>> findByRangeInBulk( String[] symbols, Calendar from, Calendar to ) {
//
//      QueryRequest queryRequest = new QueryRequest()
//              .withTableName("Quotes")
//              .withKeyConditionExpression("#symbol =:symbol");
//
//      QueryResult queryResult = client.query(queryRequest);
//      List<Map<String,AttributeValue>> items = new ArrayList<>();
//
//
//      for(Quote quote: list){
//         putQuoteInMap( quote, resultMap );
//      }
//      return resultMap;
//   }

   public List<Map<String ,AttributeValue>> findByRangeInBulk(String symbol, Calendar from, Calendar to) {

      List<Map<String,AttributeValue>> items = new ArrayList<>();

      Map<String,String> expressionAttributesNames = new HashMap<>();
      expressionAttributesNames.put("#symbol","symbol");
      expressionAttributesNames.put("#date","date");

      Map<String,AttributeValue> expressionAttributeValues = new HashMap<>();
      expressionAttributeValues.put(":emailValue",new AttributeValue().withS(symbol));
      expressionAttributeValues.put(":from",new AttributeValue().withN(Long.toString(from.getTimeInMillis())));
      expressionAttributeValues.put(":to",new AttributeValue().withN(Long.toString(to.getTimeInMillis())));

      QueryRequest queryRequest = new QueryRequest()
              .withTableName("Quotes")
              .withKeyConditionExpression("#email = :emailValue and #timestamp BETWEEN :from AND :to ");

      Map<String,AttributeValue> lastKey = null;

      do {

         QueryResult queryResult = client.query(queryRequest);
         List<Map<String,AttributeValue>> results = queryResult.getItems();
         items.addAll(results);
         lastKey = queryResult.getLastEvaluatedKey();
         queryRequest.setExclusiveStartKey(lastKey);
      } while (lastKey!=null);

      return items;
   }

   public List<String> getLoadedSymbols() {
      return new ArrayList<>();
   }

   public Map<String, List<Quote>> findByRangeInBulk( String[] symbols, Calendar from, Calendar to ) {
      Map<String, List<Quote>> resultMap = new HashMap<String, List<Quote>>();
      return resultMap;
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
              "time_stamp", String.valueOf(quote.getDate().getTimeInMillis()))
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
