package model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.quotes.stock.StockQuote;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@DynamoDBTable(tableName = "Quotes")
public class Quote {

    @DynamoDBHashKey(attributeName = "symbol")
    private String symbol;
    @DynamoDBRangeKey(attributeName = "date")
    private Calendar date;
    private BigDecimal open;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal close;
    private Long volume;

    public Quote(){
    }

    public Quote(StockQuote stockQuote){
        this.setSymbol(stockQuote.getSymbol());
        this.setDate(stockQuote.getLastTradeTime());
        this.open = stockQuote.getOpen();
        this.low = stockQuote.getDayLow();
        this.high = stockQuote.getDayHigh();
        //the current quote has no close price
        this.close = stockQuote.getPrice();
        this.volume = stockQuote.getVolume() != null ? stockQuote.getVolume() : Long.valueOf( 0L );
    }

    public Quote(HistoricalQuote historicalQuote){
        this.setSymbol(historicalQuote.getSymbol());
        this.setDate(historicalQuote.getDate());
        this.open = historicalQuote.getOpen();
        this.low = historicalQuote.getLow();
        this.high = historicalQuote.getHigh();
        this.close = historicalQuote.getClose();
        this.volume = historicalQuote.getVolume() != null ? historicalQuote.getVolume() : Long.valueOf( 0L );
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */




}
