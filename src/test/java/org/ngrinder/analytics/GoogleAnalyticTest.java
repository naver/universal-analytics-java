package org.ngrinder.analytics;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import org.junit.Test;
import org.ngrinder.analytics.GoogleAnalytic;

public class GoogleAnalyticTest {
	
	@Test
	public void sendDataToUA() {
		GoogleAnalytic analytic = new GoogleAnalytic("AppName","1.1.0","UA-39698063-1");
		int value = (new Random()).nextInt(500);
		boolean result = analytic.sendStaticDataToUA("statics_data", String.valueOf(value));
		assertTrue(result);
		
		analytic = new GoogleAnalytic("AppName","UA-39698063-1");
		analytic.getMeasureProtocolRequest().setEventCategory("CategoryData");
		analytic.getMeasureProtocolRequest().setEventAction("changeData");
		value = (new Random()).nextInt(500);
		result = analytic.sendStaticDataToUA("statics_data", String.valueOf(value));
		assertTrue(result);
	}
	
}
