package model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.quotes.stock.StockQuote;

@Entity
public class Quote {
   
   @EmbeddedId
   private QuoteId id;
   
   private BigDecimal open;
   private BigDecimal low;
   private BigDecimal high;
   private BigDecimal close;
   
   private Long volume;
   
   public Quote(){
   }
   
   public Quote(StockQuote stockQuote){
      setId( new QuoteId(stockQuote.getSymbol(), stockQuote.getLastTradeTime()));
      this.open = stockQuote.getOpen();
      this.low = stockQuote.getDayLow();
      this.high = stockQuote.getDayHigh();
      //the current quote has no close price
      this.close = stockQuote.getPrice();
      this.volume = stockQuote.getVolume() != null ? stockQuote.getVolume() : Long.valueOf( 0L );
   }
   
   public Quote(HistoricalQuote historicalQuote){
      setId( new QuoteId(historicalQuote.getSymbol(), historicalQuote.getDate()));
      this.open = historicalQuote.getOpen();
      this.low = historicalQuote.getLow();
      this.high = historicalQuote.getHigh();
      this.close = historicalQuote.getClose();
      this.volume = historicalQuote.getVolume() != null ? historicalQuote.getVolume() : Long.valueOf( 0L );
   }
   
   @JsonSerialize(using = CalendarSerializer.class)
   public Calendar getDate(){
      return id.getDate();
   }

   
   public synchronized String getSymbol() {
      return id.getSymbol();
   }

   
   public synchronized BigDecimal getOpen() {
      return open != null ? open : new BigDecimal( -1 );
   }

   
   public synchronized BigDecimal getLow() {
      return low != null ? low : new BigDecimal( -1 );
   }

   
   public synchronized BigDecimal getHigh() {
      return high != null ? high : new BigDecimal( -1 );
   }

   
   public synchronized BigDecimal getClose() {
      return close != null ? close : new BigDecimal( -1 );
   }

   
   public synchronized Long getVolume() {
      return volume;
   }

   
   
   public synchronized void setOpen( BigDecimal open ) {
      this.open = open;
   }

   
   public synchronized void setLow( BigDecimal low ) {
      this.low = low;
   }

   
   public synchronized void setHigh( BigDecimal high ) {
      this.high = high;
   }

   
   public synchronized void setClose( BigDecimal close ) {
      this.close = close;
   }

   
   public synchronized void setVolume( Long volume ) {
      if( volume == null ){
         volume = Long.valueOf( 0L );
      }
      this.volume = volume;
   }

   public QuoteId getId() {
      return id;
   }

   public void setId( QuoteId id ) {
      this.id = id;
   }

   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((close == null) ? 0 : close.hashCode());
      result = prime * result + ((high == null) ? 0 : high.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((low == null) ? 0 : low.hashCode());
      result = prime * result + ((open == null) ? 0 : open.hashCode());
      result = prime * result + ((volume == null) ? 0 : volume.hashCode());
      return result;
   }

   /* (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
   @Override
   public boolean equals( Object obj ) {
      if ( this == obj ) return true;
      if ( obj == null ) return false;
      if ( getClass() != obj.getClass() ) return false;
      Quote other = (Quote)obj;
      if ( close == null ) {
         if ( other.close != null ) return false;
      } else if ( !close.equals( other.close ) ) return false;
      if ( high == null ) {
         if ( other.high != null ) return false;
      } else if ( !high.equals( other.high ) ) return false;
      if ( id == null ) {
         if ( other.id != null ) return false;
      } else if ( !id.equals( other.id ) ) return false;
      if ( low == null ) {
         if ( other.low != null ) return false;
      } else if ( !low.equals( other.low ) ) return false;
      if ( open == null ) {
         if ( other.open != null ) return false;
      } else if ( !open.equals( other.open ) ) return false;
      if ( volume == null ) {
         if ( other.volume != null ) return false;
      } else if ( !volume.equals( other.volume ) ) return false;
      return true;
   }
   
   
   
}
