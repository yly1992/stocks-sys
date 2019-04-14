package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="alerts")
public class Alert {
   
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   @Column(name="id")
   private String id;
   @Column(name="active")
   private Boolean active;
   @Column(name="expression")
   private String expression;
   @Column(name="name")
   private String name;
   @Column(name="description")
   private String description;
   @Column(name="sendEmail")
   private Boolean sendEmail;
   @Column(name="symbol")
   private String symbol;
   @Column(name="opposedAlertId")
   private String opposedAlertId;
   
   
   public synchronized String getSymbol() {
      return symbol;
   }

   
   public synchronized void setSymbol( String symbol ) {
      this.symbol = symbol;
   }

   
   public synchronized String getOpposedAlertId() {
      return opposedAlertId;
   }

   
   public synchronized void setOpposedAlertId( String opposedAlertId ) {
      this.opposedAlertId = opposedAlertId;
   }

   public synchronized Boolean getActive() {
      return active;
   }
   
   public synchronized void setActive( Boolean active ) {
      this.active = active;
   }
   
   public synchronized String getExpression() {
      return expression;
   }
   
   public synchronized void setExpression( String expression ) {
      this.expression = expression;
   }
   
   public synchronized String getName() {
      return name;
   }
   
   public synchronized void setName( String name ) {
      this.name = name;
   }
   
   public synchronized String getDescription() {
      return description;
   }
   
   public synchronized void setDescription( String description ) {
      this.description = description;
   }
   
   public synchronized Boolean getSendEmail() {
      return sendEmail;
   }
   
   public synchronized void setSendEmail( Boolean sendEmail ) {
      this.sendEmail = sendEmail;
   }

   public String getId() {
      return id;
   }

   public void setId( String id ) {
      this.id = id;
   }
   
   @Override
   public String toString() {
       return "Alert{" +
               "id=" + id +
               ", active='" + active + '\'' +
               ", expression='" + expression + '\'' +
               ", name='" + name + '\'' +
               ", description=" + description +
               ", sendEmail=" + sendEmail +
               ", symbol=" + symbol +
               ", opposedAlertId=" + opposedAlertId +
               '}';
   }
   

}
