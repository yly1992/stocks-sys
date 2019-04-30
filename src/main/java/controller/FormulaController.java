package controller;

import service.FormulaService;
import java.io.IOException;
import java.text.DecimalFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@RestController
@RequestMapping("/formulas")
public class FormulaController {
   
	@Autowired
   private FormulaService formulaService;
   DecimalFormat df = new DecimalFormat("0.00"); 
   
   @RequestMapping(value= "/average", method = RequestMethod.GET)
   public ResponseEntity<String> average(@RequestParam(value = "period", required=false) Integer period, @RequestParam("symbol") String symbol ) throws IOException {
      if(period == null){
         period = 20;
      }
      return new ResponseEntity<String>( df.format( formulaService.getAverage( period, symbol ).doubleValue() ), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/variance", method = RequestMethod.GET)
   public ResponseEntity<String> variance(@RequestParam(value = "period", required=false) Integer period, @RequestParam("symbol") String symbol ) throws IOException {
      if(period == null){
         period = 20;
      }
      return new ResponseEntity<String>( df.format( formulaService.getVariance( period, symbol )), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/sd", method = RequestMethod.GET)
   public ResponseEntity<String> standardDeviation(@RequestParam(value = "period", required=false) Integer period, @RequestParam("symbol") String symbol ) throws IOException {
      if(period == null){
         period = 20;
      }
      return new ResponseEntity<String>( df.format( formulaService.getStandardDeviation( period, symbol )), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/sdp", method = RequestMethod.GET)
   public ResponseEntity<String> standardDeviationPercentage(@RequestParam(value = "period", required=false) Integer period, @RequestParam("symbol") String symbol ) throws IOException {
      if(period == null){
         period = 20;
      }
      return new ResponseEntity<String>( df.format( formulaService.getStandardDeviationPercentage( period, symbol )), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/sma", method = RequestMethod.GET)
   public ResponseEntity<String> sma(@RequestParam("period") int period, @RequestParam("symbol") String symbol ) throws IOException {
      return new ResponseEntity<String>( df.format( formulaService.getSMA( period, symbol )), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/ema", method = RequestMethod.GET)
   public ResponseEntity<String> ema(@RequestParam("period") int period, @RequestParam("symbol") String symbol ) throws IOException {
      return new ResponseEntity<String>( df.format( formulaService.getEMA( period, symbol )), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/rsi", method = RequestMethod.GET)
   public ResponseEntity<String> rsi(@RequestParam(value = "period", required=false) Integer period, @RequestParam("symbol") String symbol ) throws IOException {
      if(period == null){
         period = 14;
      }
      return new ResponseEntity<String>( df.format( formulaService.getRSI( period, symbol )), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/macd", method = RequestMethod.GET)
   public ResponseEntity<String> macd(@RequestParam(value = "fastPeriod", required=false) Integer fastPeriod, 
                                          @RequestParam(value = "slowPeriod", required=false) Integer slowPeriod,
                                          @RequestParam("symbol") String symbol ) throws IOException {
      if(fastPeriod == null){
         fastPeriod = 12;
      }
      if(slowPeriod == null){
         slowPeriod = 26;
      }
      return new ResponseEntity<String>( df.format( formulaService.getMACD( fastPeriod, slowPeriod, symbol )), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/macdsignalline", method = RequestMethod.GET)
   public ResponseEntity<String> macdSignalLine(@RequestParam(value = "fastPeriod", required=false) Integer fastPeriod, 
                                          @RequestParam(value = "slowPeriod", required=false) Integer slowPeriod,
                                          @RequestParam(value = "signalPeriod", required=false) Integer signalPeriod,
                                          @RequestParam("symbol") String symbol ) throws IOException {
      if(fastPeriod == null){
         fastPeriod = 12;
      }
      if(slowPeriod == null){
         slowPeriod = 26;
      }
      if(signalPeriod == null){
         signalPeriod = 9;
      }
      
      return new ResponseEntity<String>( df.format( formulaService.getMACDSignalLine( fastPeriod, slowPeriod, signalPeriod, symbol )), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/macdhistogram", method = RequestMethod.GET)
   public ResponseEntity<String> macdHistogram(@RequestParam(value = "fastPeriod", required=false) Integer fastPeriod, 
                                          @RequestParam(value = "slowPeriod", required=false) Integer slowPeriod,
                                          @RequestParam(value = "signalPeriod", required=false) Integer signalPeriod,
                                          @RequestParam("symbol") String symbol ) throws IOException {
      if(fastPeriod == null){
         fastPeriod = 12;
      }
      if(slowPeriod == null){
         slowPeriod = 26;
      }
      if(signalPeriod == null){
         signalPeriod = 9;
      }
      
      return new ResponseEntity<String>( df.format( formulaService.getMACDHistogram( fastPeriod, slowPeriod, signalPeriod, symbol )), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/price", method = RequestMethod.GET)
   public ResponseEntity<String> price( @RequestParam("symbol") String symbol ) throws IOException {
      return new ResponseEntity<String>( df.format( formulaService.getPrice( symbol )), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/volume", method = RequestMethod.GET)
   public ResponseEntity<String> volume( @RequestParam("symbol") String symbol ) throws IOException {
      return new ResponseEntity<String>( formulaService.getVolume( symbol ).toString(), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/stochasticd", method = RequestMethod.GET)
   public ResponseEntity<String> stochasticD(@RequestParam(value = "length", required=false) Integer length, 
                                          @RequestParam(value = "period", required=false) Integer period,
                                          @RequestParam("symbol") String symbol ) throws IOException {
      if(length == null){
         length = 14;
      }
      if(period == null){
         period = 3;
      }
      
      return new ResponseEntity<String>( df.format( formulaService.getStochasticOscillatorD( length, period, symbol )), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/stochastick", method = RequestMethod.GET)
   public ResponseEntity<String> stochasticD(@RequestParam(value = "length", required=false) Integer length, 
                                          @RequestParam("symbol") String symbol ) throws IOException {
      if(length == null){
         length = 14;
      }
      
      return new ResponseEntity<String>( df.format( formulaService.getStochasticOscillatorK( length, symbol )), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/bblower", method = RequestMethod.GET)
   public ResponseEntity<String> bbLower(@RequestParam(value = "period", required=false) Integer period, 
                                          @RequestParam(value = "k", required=false) Integer k,
                                          @RequestParam("symbol") String symbol ) throws IOException {
      if(k == null){
         k = 2;
      }
      if(period == null){
         period = 20;
      }
      
      return new ResponseEntity<String>( df.format( formulaService.getBollingerBandLower( period, k, symbol )), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/bbupper", method = RequestMethod.GET)
   public ResponseEntity<String> bbUpper(@RequestParam(value = "period", required=false) Integer period, 
                                          @RequestParam(value = "k", required=false) Integer k,
                                          @RequestParam("symbol") String symbol ) throws IOException {
      if(k == null){
         k = 2;
      }
      if(period == null){
         period = 20;
      }
      
      return new ResponseEntity<String>( df.format( formulaService.getBollingerBandUpper( period, k, symbol )), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/atr", method = RequestMethod.GET)
   public ResponseEntity<String> atr(@RequestParam(value = "period", required=false) Integer period, 
                                          @RequestParam("symbol") String symbol ) throws IOException {
      if(period == null){
         period = 14;
      }
      
      return new ResponseEntity<String>( df.format( formulaService.getAverageTrueRange( period, symbol )), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/atrp", method = RequestMethod.GET)
   public ResponseEntity<String> atrp(@RequestParam(value = "period", required=false) Integer period, 
                                          @RequestParam("symbol") String symbol ) throws IOException {
      if(period == null){
         period = 14;
      }
      
      return new ResponseEntity<String>( df.format( formulaService.getAverageTrueRangePercentage( period, symbol )), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/roc", method = RequestMethod.GET)
   public ResponseEntity<String> roc(@RequestParam(value = "period", required=false) Integer period, 
                                          @RequestParam("symbol") String symbol ) throws IOException {
      if(period == null){
         period = 14;
      }
      
      return new ResponseEntity<String>( df.format( formulaService.getRateOfChange( period, symbol )), HttpStatus.OK );
   }
   
   @RequestMapping(value= "/adtv", method = RequestMethod.GET)
   public ResponseEntity<String> adtv(@RequestParam(value = "period", required=false) Integer period, 
                                          @RequestParam("symbol") String symbol ) throws IOException {
      if(period == null){
         period = 30;
      }
      
      return new ResponseEntity<String>( formulaService.getAverageDailyTradingVolume( period, symbol ).toString(), HttpStatus.OK );
   }
}
