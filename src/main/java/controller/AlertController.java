package controller;

import model.Alert;
import service.AlertService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@RestController
@RequestMapping("/alerts")
public class AlertController {
   
   @Autowired
   private AlertService alertService;
   
  
   @RequestMapping(value= "/alert", method = RequestMethod.GET)
   public ResponseEntity<HttpStatus> createAlert() throws IOException {
	  Alert alert = new Alert();
	  alert.setAlert_Id(12345);
	  alert.setActive(true);
	  alert.setDescription("ba lallalalal ");
	  alert.setExpression("a b c");
	  alert.setName("niu bi");
	  alert.setSymbol("AAPL");
	  System.out.println("alert "+ alert.getAlert_Id());
      alertService.saveAlert( alert);
      return new ResponseEntity<HttpStatus> ( HttpStatus.OK );
   }
   
  
}
