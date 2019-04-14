package service;

import model.Alert;
import model.Notification;
import model.operations.Operator;
import dao.AlertDAO;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableScheduling
@PropertySource("classpath:application.properties")
@Transactional
public class AlertService {
   

   @Autowired
   private AlertDAO alertDAO;
   
   
   @Transactional
   public void saveAlert( Alert newAlert ) {
      alertDAO.addAlert( newAlert );
   }
   
   
//   private void normalizeId( Alert alert ) {
//	      if(alert.getAlert_Id() ){
//	         alert.setAlert_Id( toCamelCase( alert.getAlert_Id() ) );
//	      }
//   }
   
   private String toCamelCase(final String init) {
	      if (init==null)
	          return null;
	      
	      final StringBuilder ret = new StringBuilder(init.length());
	      String word;
	      String[] split = init.split(" ");
	      for ( int i=0 ; i < split.length ; i++ ) {
	         word = split[i];
	         if (word.isEmpty()) {
	            continue;
	         }
	         if( i == 0 ){
	            ret.append( word.toLowerCase() );
	         }else{
	            ret.append(word.substring(0, 1).toUpperCase());
	            ret.append(word.substring(1).toLowerCase());
	         }
	      }

	      return ret.toString();
	  }
   
   /**
    * This is a "Utils" method
    * @param t
    * @return
    */
   private String stackTraceToString( Throwable t ){
      StringWriter writer = new StringWriter();
      PrintWriter printWriter = new PrintWriter( writer );
      t.printStackTrace( printWriter );
      printWriter.flush();
      return writer.toString();
   }

}
