package model;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Alert implements Serializable {

   private static final long serialVersionUID = 1L;
	
   private Integer alert_id;
   private Boolean active;
   private String expression;
   private String name;
   private String description;
   private Boolean sendEmail;
   private String symbol;
   private String opposedAlertId;

   
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
