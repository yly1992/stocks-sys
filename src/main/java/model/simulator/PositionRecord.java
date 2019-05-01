package model.simulator;

import java.util.Calendar;

public class PositionRecord {
   private SimulatorRecord buyRecord;
   private double lastPrice;
   
   public PositionRecord( SimulatorRecord buyRecord ){
      this.buyRecord = buyRecord;
      lastPrice = buyRecord.getOrderPrice();
   }
   
   /**
    * @return the buyRecord
    */
   public synchronized SimulatorRecord getBuyRecord() {
      return buyRecord;
   }
   
   /**
    * @param buyRecord the buyRecord to set
    */
   public synchronized void setBuyRecord( SimulatorRecord buyRecord ) {
      this.buyRecord = buyRecord;
   }
   
   /**
    * @return the lastPrice
    */
   public synchronized double getLastPrice() {
      return lastPrice;
   }
   
   /**
    * @param lastPrice the lastPrice to set
    */
   public synchronized void setLastPrice( double lastPrice ) {
      this.lastPrice = lastPrice;
   }
   
   public double getOrderAmount(){
      return buyRecord.getOrderAmount();
   }
   
   public double getOrderTotalCost(){
      return buyRecord.getOrderTotalCost();
   }

   public Calendar getOrderDate() {
      return buyRecord.getOrderDate();
   }
   
}
