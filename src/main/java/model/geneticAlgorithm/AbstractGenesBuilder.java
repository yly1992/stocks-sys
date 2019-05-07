package model.geneticAlgorithm;

import java.util.Arrays;
import org.jgap.Gene;
import org.jgap.IChromosome;

public abstract class AbstractGenesBuilder implements GenesBuilder {
   
   private int firstIndex = 0;

   public void setFirstIndex( int index ) {
      firstIndex = index;
   }
   
   public int getFirstIndex() {
      return firstIndex;
   }

   public int getLastIndex() {
      return firstIndex + getSize() - 1;
   }
   
   public String translatePart( IChromosome chromosome ) {
      //OPERATION_PERFORMANCE_PERCENTAGE>3
      Gene[] genesSubSet = Arrays.copyOfRange( chromosome.getGenes(), getFirstIndex(), getLastIndex() + 1 );
      //
      if( (Boolean) genesSubSet[0].getAllele() == false ){
         return "";
      }
      
      return translateAfterFlag( genesSubSet );
   }
   
   public abstract String translateAfterFlag( Gene[] genesSubSet );


}
