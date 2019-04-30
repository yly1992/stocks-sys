package model.geneticAlgorithm;

public class GeneticAlgorithmEvolutionResult {
   
   private int evolutionNumber;
   private String bestBuyExpression;
   private String bestSellExpression;
   private double bestPerformance;
   
   /**
    * @return the evolutionNumber
    */
   public synchronized int getEvolutionNumber() {
      return evolutionNumber;
   }
   
   /**
    * @param evolutionNumber the evolutionNumber to set
    */
   public synchronized void setEvolutionNumber( int evolutionNumber ) {
      this.evolutionNumber = evolutionNumber;
   }
   
   /**
    * @return the bestBuyExpression
    */
   public synchronized String getBestBuyExpression() {
      return bestBuyExpression;
   }
   
   /**
    * @param bestBuyExpression the bestBuyExpression to set
    */
   public synchronized void setBestBuyExpression( String bestBuyExpression ) {
      this.bestBuyExpression = bestBuyExpression;
   }
   
   /**
    * @return the bestSellExpression
    */
   public synchronized String getBestSellExpression() {
      return bestSellExpression;
   }
   
   /**
    * @param bestSellExpression the bestSellExpression to set
    */
   public synchronized void setBestSellExpression( String bestSellExpression ) {
      this.bestSellExpression = bestSellExpression;
   }
   
   /**
    * @return the bestPerformance
    */
   public synchronized double getBestPerformance() {
      return bestPerformance;
   }
   
   /**
    * @param bestPerformance the bestPerformance to set
    */
   public synchronized void setBestPerformance( double bestPerformance ) {
      this.bestPerformance = bestPerformance;
   }

   
}
