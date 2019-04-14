package model.formulas;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FormulasCacheValues {
   
   static private FormulasCacheValues instance;
   
   private Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
   
   static public  FormulasCacheValues getInstance(){
      if( instance == null ){
         instance = new FormulasCacheValues();
      }
      return instance;
   }
   
   public void addToCache( String key, BigDecimal object ){
      map.put( key, object );
   }
   
   public void clearCache( ){
      map.clear();
   }
   
   public BigDecimal getFromCache( String key ){
      return map.get( key );
   }

}
