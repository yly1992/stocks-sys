package model.formulas;

import java.math.BigDecimal;

public interface Formula extends Cacheable{
   
   public BigDecimal calculate();

}
