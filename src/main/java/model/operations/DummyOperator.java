package model.operations;

/**
 * Always return false
 * @author Sergio Cormio
 *
 */
public class DummyOperator implements Operator {

   public boolean evaluate() {
      return false;
   }

}
