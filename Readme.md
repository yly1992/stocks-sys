# stock-alerts

Configure your financial alerts from your favorite stock market and receive notifications by email when their expressions are evaluated as true. Every expression is a set of technical indicators evaluated on quotes from your portfolio defined by you. "Stock-Alerts" is your personal technical analyst and adviser using your own rules.

## Features
* Uses Yahoo Finance API (you can use all his ticker symbols)
* Integrated parser of a "Human Readable" financial expressions
* Processes alerts periodically (scheduled by a cron expression) or on demand
* Sends custom email notifications to a recipient when an alert is triggered
* RESTful API
* Made in JAVA

## Installation
* Download the project
* mvn package -DskipTests
* Change values of application.properties
* Deploy in your favorite application server (tomcat, etc)


##APIs
http://localhost:8080/stocks/GOOGL
http://localhost:8080/stocks?symbols=GOOGL,AMZN
http://localhost:8080/stocks/oneyearhistory/symbols=GOOGL



## Valid expressions examples
* PRICE(GOOGL)>318.5
* SMA(50,GOOGL)>310
* PRICE(GOOGL)>318.5&&130000<VOLUME(GOOGL)
* EMA(5,GOOGL)>EMA(20,GOOGL)&&RSI(14,GOOGL)>50
* MACD(12,26,MIRG.BA)<MACD_SIGNAL_LINE(12,26,9,MIRG.BA)
* MACD_HISTOGRAM(12,26,9,GOOGL)>0
* STOCHASTIC_K(14,GOOGL)>STOCHASTIC_D(14,3,GOOGL)
* PRICE(GOOGL)>AVERAGE(20,GOOGL)
* BB_LOWER(20,2,GOOGL)&lt;PRICE(GOOGL)&&PRICE(GOOGL)&gt;SMA(20,GOOGL)&&BB_UPPER(20,2,GOOGL)&lt;PRICE(GOOGL)
* DIFF(EMA(5,GOOGL),EMA(20,GOOGL))>2
* STANDARD_DEVIATION(14,GOOGL)>1
* STANDARD_DEVIATION_PERCENTAGE(14,GOOGL)>2
* ATR(14,GOOGL)>1
* ATRP(14,GOOGL)>2
* ROC(14,TSLA)>0
* ADTV(30,TSLA)>500000

## Formulas
* __SMA__: Simple Moving Average. Parameters: period and symbol.
* __EMA__: Exponential Moving Average. Parameters: period and symbol.
* __RSI__: Relative Strength Index. Parameters: period and symbol.
* __PRICE__: Last price of a single stock. Parameter: symbol.
* __VOLUME__: Last volume of a single stock. Parameter: symbol.
* __ADTV__: Average Daily Trading Volume. Parameters: period and symbol.
* __MACD__: Moving Average Convergence/Divergence. Parameters: fastPeriod, slowPeriod and symbol.
* __MACD_SIGNAL_LINE__: Signal Line value of Moving Average Convergence/Divergence. Parameters: fastPeriod, slowPeriod, signalPeriod and symbol.
* __MACD_HISTOGRAM__: Histogram value of Moving Average Convergence/Divergence. Parameters: fastPeriod, slowPeriod, signalPeriod and symbol.
* __STOCHASTIC_K__: Stochastic Oscillator %K value. Parameters: length and symbol.
* __STOCHASTIC_D__: Stochastic Oscillator %D is a X-period simple moving average of %K. Parameters: length, period and symbol.
* __AVERAGE__: Price average in a period. Parameters: period and symbol.
* __VARIANCE__: Price variance in a period. Parameters: period and symbol.
* __STANDARD_DEVIATION__: Price standard deviation in a period. Parameters: period and symbol.
* __STANDARD_DEVIATION_PERCENTAGE__: Percentage of standard deviation in a period. Parameters: period and symbol.
* __BB_LOWER__: Lower Bollinger Band (trademark). Parameters: period, k and symbol.
* __BB_UPPER__: Upper Bollinger Band (trademark). Parameters: period, k and symbol.
* __DIFF__: Difference between 2 formula expressions.
* __ATR__: Average True Range. Parameters: period and symbol.
* __ATRP__: Average True Range Percentage. Parameters: period and symbol.
* __ROC__: Rate of Change percentage based on closed price. Parameters: period and symbol.


## Operators
* __&&__: Logical AND operator
* __>__: GREATER THAN operator
* __<__: LESS THAN operator

## API REST
#### System test
* GET /stock-alerts/ping
  * Useful to test server is up
* GET /stock-alerts/emails/test
  * Sends a test email, useful to test email configuration

#### Stocks
* GET /stock-alerts/stocks/GOOGL
  * Returns stock information
* GET /stock-alerts/stocks?symbols=GOOGL,AAPL
  * Returns stock information from several ticker symbols
* GET /stock-alerts/stocks/history/GOOGL
  * Returns historical stock information
* GET /stock-alerts/stocks/symbols
  * Returns all ticker symbols loaded in database
* POST /stock-alerts/stocks/import/csv?symbol=GOOGL
  * Import a csv file with historical quotes in a parameter called "content_file"
* GET /stock-alerts/stocks/daily_update
  * Update all Stocks values in database with last quote
* DELETE /stock-alerts/stocks/GOOGL
  * Removes all stock information about a ticker
* PUT /stock-alerts/stocks/history/1999
  * Update all historical stocks values in database from a year passed as parameter
* PUT /stock-alerts/stocks/history/1999/GOOGL
  * Update historical stock values in database from s specific ticker symbol and a year passed as parameters


#### Formulas
* GET /stock-alerts/formulas/price?symbol=GOOGL
* GET /stock-alerts/formulas/volume?symbol=GOOGL
* GET /stock-alerts/formulas/average?period=20&symbol=GOOGL
  * period parameter is optional, default value is 20
* GET /stock-alerts/formulas/sd?period=20&symbol=GOOGL
  * period parameter is optional, default value is 20
* GET /stock-alerts/formulas/variance?period=20&symbol=GOOGL
  * period parameter is optional, default value is 20
* GET /stock-alerts/formulas/sma?period=50&symbol=GOOGL
* GET /stock-alerts/formulas/ema?period=14&symbol=GOOGL
* GET /stock-alerts/formulas/rsi?period=14&symbol=GOOGL
  * period parameter is optional, default value is 14
* GET /stock-alerts/formulas/macd?fastPeriod=12&slowPeriod=26&symbol=GOOGL
  * fastPeriod parameter is optional, default value is 12
  * slowPeriod parameter is optional, default value is 26
* GET /stock-alerts/formulas/macdsignalline?fastPeriod=12&slowPeriod=26&signalPeriod=9&symbol=GOOGL
  * fastPeriod parameter is optional, default value is 12
  * slowPeriod parameter is optional, default value is 26
  * signalPeriod parameter is optional, default value is 9
* GET /stock-alerts/formulas/macdhistogram?fastPeriod=12&slowPeriod=26&signalPeriod=9&symbol=GOOGL
  * fastPeriod parameter is optional, default value is 12
  * slowPeriod parameter is optional, default value is 26
  * signalPeriod parameter is optional, default value is 9
* GET /stock-alerts/formulas/stochastick?length=14&symbol=GOOGL
  * length parameter is optional, default value is 14
* GET /stock-alerts/formulas/stochasticd?length=14&period=3&symbol=GOOGL
  * length parameter is optional, default value is 14
  * period parameter is optional, default value is 3
* GET /stock-alerts/formulas/bblower?period=20&k=2&symbol=GOOGL
  * period parameter is optional, default value is 20
  * k parameter is optional, default value is 2
* GET /stock-alerts/formulas/bbupper?period=20&k=2&symbol=GOOGL
  * period parameter is optional, default value is 20
  * k parameter is optional, default value is 2
* GET /stock-alerts/formulas/atr?period=14&symbol=GOOGL
  * period parameter is optional, default value is 14
* GET /stock-alerts/formulas/atrp?period=14&symbol=GOOGL
  * period parameter is optional, default value is 14
* GET /stock-alerts/formulas/roc?period=14&symbol=GOOGL
  * period parameter is optional, default value is 14
* GET /stock-alerts/formulas/adtv?period=30&symbol=GOOGL
  * period parameter is optional, default value is 30


#### Alerts
* GET /stock-alerts/alerts
  * Retrieves all active and inactive alerts loaded
* GET /stock-alerts/alerts?symbol=GOOGL
  * Retrieves alerts loaded by symbol
 * GET /stock-alerts/alerts/{alertId}
  * Retrieves a particular alert
* GET /stock-alerts/alerts/{alertId}/activate
  * Activates an alert
* GET /stock-alerts/alerts/{alertId}/deactivate
  * Deactivates an alert
* POST /stock-alerts/alerts
  * Creates a new Alert
  * Passing a JSON representation of an alert as body
* PUT /stock-alerts/alerts
  * Updates an existing Alert
  * Passing a JSON representation of an alert as body
* DELETE /stock-alerts/alerts/{alertId}
  * Deletes an existing Alert
  * Passing an alert id by URL parameter
* GET /stock-alerts/alerts/{alertId}/process
  * Processes a particular alert immediately
* GET /stock-alerts/alerts/process
  * Processes all active alerts immediately

#### Simulator
* POST /stock-alerts/simulator
  * Run a new Simulation
  * Passing a JSON representation of a "Simulator Parameters" object as body

#### Genetic Algorithm
* POST /genetic-algorithm
  * Run a Genetic Algorithm looking for good expressions to buy and sell
  * Passing a JSON representation of a "Genetic Algorithm Parameters" object as body

## Alert object structure
* __id__ = An alert identifier. For example: GOOGLE1
* __active__ = true or false. Represents whether this alert is currently vigent or not.
* __description__ = Long description for this alert. This field will be the content of the email notification. For example: "The price of GOOGLE is optimum to buy right now!"
* __expression__ = Financial expression to be evaluated when this alert will processed
* __name__ = Short description of the alert. This field will be the subject of the email notification. For example: "GOOGLE buy signal"
* __sendEmail__ = true or false. If you want or not to receive notification by email about this alert.
* __symbol__ = A ticker symbol that the alert is related to.
* __opposedAlertId__ = Id of an opposed alert. For example: An opposed alert of a buy signal is a sell signal.

### JSON Example
```
{
   "id": "franBuy",
   "active": true,
   "description": "BBVA Frances buy signal",
   "expression": "EMA(14,FRAN.BA)>95",
   "name": "Buy Banco Francés as soon as possible",
   "sendEmail": true,
   "symbol": "FRAN.BA",
   "opposedAlertId": "franSell"
}
```

## Strategy Simulator

### Simulation Constants valid in formulas
* __[SYMBOL]__: It will be replaced by the current Symbol in simulation
* __[OPERATION_PERFORMANCE]__: It will be replaced by the operation value of leaving position from current Symbol in simulation
* __[OPERATION_PERFORMANCE_PERCENTAGE]__: It will be replaced by the operation percentage of leaving position from current Symbol in simulation

### Simulator Parameters JSON object
```
{
   "initialCapital": 100000,
   "commissionPercentage": 0.6,
   "positionMinimumValue": 20000,
   "positionPercentage": 20,
   "positionMaximumValue": 150000,
   "buyExpression": "MACD(12,26,[SYMBOL])>MACD_SIGNAL_LINE(12,26,9,[SYMBOL])",
   "sellExpression": "MACD(12,26,[SYMBOL])<MACD_SIGNAL_LINE(12,26,9,[SYMBOL])",
   "stopLossPercentage" :  2,
   "positionTimeoutDays" : 360,
   "symbols": ["GOOGL","AAPL","TSLA"],
   "yearFrom": 2016,
   "yearTo": 2017,
   "previousDaysOfAnalysis": 365
}
```

## Genetic Algorithm
The Genetic algorithm will try to find a good pair of expressions (buy and sell expressions) in order to maximize the capital balance.

### Genetic Algorithm Parameters JSON object
```
{
   "populationSize":"2",
   "numberOfEvolutions":"1",
   "usePerformancePercentage":true,
   "useStochastic":true,
   "useEMA":true,
   "useMACD":false,
   "simulatorParameters":
   {
      "initialCapital": 100000,
      "commissionPercentage": 0.6,
      "positionMinimumValue": 20000,
      "positionPercentage": 20,
      "positionMaximumValue": 150000,
      "buyExpression": "",
      "sellExpression": "",
      "stopLossPercentage" :  2,
      "symbols": ["GOOGL","AAPL","TSLA"],
      "yearFrom": 2016,
      "yearTo": 2017,
      "previousDaysOfAnalysis": 365
   }
}
```

## Special Thanks
* Alejandro Curci
* Nicolás Zdanovicz
* Hernán Soulages
