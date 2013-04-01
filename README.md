Universal-Analytics-Java
========================
    This is Java Implementation for Measurement Protocol.
    
The Measurement Protocol is part of Universal Analytics,which allows developers to make HTTP 
requests to send raw user interaction data directly to Google Analytics servers.

Getting Started
========================

First,you must have an account in Google Analytics and selecting App to track when you sign in.

Next,wirting following codes in Java

    GoogleAnalytic analytic = new GoogleAnalytic(AppName,AppVersion,trackingCode);
    boolean result = analytic.sendStaticDataToUA(StaticsDataName, DataValue);

Finaly result is ture which means Google Analytic have received your statistic data successfully.

Note that since "Time Delay" Problems,you'd better to wait some times and then observe statistic data later in Google Analytic.
