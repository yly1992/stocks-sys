package model.operations;

import model.formulas.Formula;

public class OperationFormula implements OperationTerm {
   
   private Formula formula;

   public OperationFormula(Formula formula){
      this.formula = formula;
   }

   public double getValue() {
      return formula.calculate().doubleValue();
   }

}
