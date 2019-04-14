package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="alerts")
public class Alert implements Serializable {

   private static final long serialVersionUID = 1L;
	
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   @Column(name="alert_id",nullable = false)
   private Integer alert_id;
   @Column(name="active", nullable = true)
   private Boolean active;
   @Column(name="expression", nullable = true)
   private String expression;
   @Column(name="name", nullable = true)
   private String name;
   @Column(name="description", nullable = true)
   private String description;
   @Column(name="sendEmail", nullable = true)
   private Boolean sendEmail;
   @Column(name="symbol", nullable = true)
   private String symbol;
   @Column(name="opposedAlertId", nullable = true)
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

   public Integer getAlert_Id() {
      return alert_id;
   }

   public void setAlert_Id( Integer id ) {
      this.alert_id = alert_id;
   }
   
   @Override
   public String toString() {
       return "Alert{" +
               "id=" + alert_id +
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
