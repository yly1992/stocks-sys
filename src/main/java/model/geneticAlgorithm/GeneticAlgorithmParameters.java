package model.geneticAlgorithm;

import model.simulator.SimulatorParameters;

public class GeneticAlgorithmParameters {
   
   private SimulatorParameters simulatorParameters;
   private int populationSize;
   private int numberOfEvolutions;
   private boolean useStochastic = true; //true by default
   private boolean usePerformancePercentage = true; //true by default
   private boolean useEMA;
   private boolean useMACD;

   public GeneticAlgorithmParameters(){
      simulatorParameters = SimulatorParameters.createDefault();
      populationSize = 10;
      numberOfEvolutions = 10;
   }
   /**
    * @return the simulatorParameters
    */
   public synchronized SimulatorParameters getSimulatorParameters() {
      return simulatorParameters;
   }

   
   /**
    * @param simulatorParameters the simulatorParameters to set
    */
   public synchronized void setSimulatorParameters( SimulatorParameters simulatorParameters ) {
      this.simulatorParameters = simulatorParameters;
   }

   
   /**
    * @return the populationSize
    */
   public synchronized int getPopulationSize() {
      return populationSize;
   }

   
   /**
    * @param populationSize the populationSize to set
    */
   public synchronized void setPopulationSize( int populationSize ) {
      this.populationSize = populationSize;
   }
   public int getNumberOfEvolutions() {
      return numberOfEvolutions;
   }
   public void setNumberOfEvolutions( int numberOfEvolutions ) {
      this.numberOfEvolutions = numberOfEvolutions;
   }
   
   /**
    * @return the useStochastic
    */
   public synchronized boolean isUseStochastic() {
      return useStochastic;
   }
   
   /**
    * @param useStochastic the useStochastic to set
    */
   public synchronized void setUseStochastic( boolean useStochastic ) {
      this.useStochastic = useStochastic;
   }
   
   /**
    * @return the usePerformancePercentage
    */
   public synchronized boolean isUsePerformancePercentage() {
      return usePerformancePercentage;
   }
   
   /**
    * @param usePerformancePercentage the usePerformancePercentage to set
    */
   public synchronized void setUsePerformancePercentage( boolean usePerformancePercentage ) {
      this.usePerformancePercentage = usePerformancePercentage;
   }
   
   /**
    * @return the useEMA
    */
   public synchronized boolean isUseEMA() {
      return useEMA;
   }
   
   /**
    * @param useEMA the useEMA to set
    */
   public synchronized void setUseEMA( boolean useEMA ) {
      this.useEMA = useEMA;
   }
   
   /**
    * @return the useMACD
    */
   public synchronized boolean isUseMACD() {
      return useMACD;
   }
   
   /**
    * @param useMACD the useMACD to set
    */
   public synchronized void setUseMACD( boolean useMACD ) {
      this.useMACD = useMACD;
   }
   

}
