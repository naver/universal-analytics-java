/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package org.ngrinder.http;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrapping HTTP Post method using Google Analytics's Measure Protocol
 * 
 * @author maoyb
 */
public class MeasureProtocolRequest {

	private static final Logger LOG = LoggerFactory.getLogger(MeasureProtocolRequest.class);

	private String track_ID;

	private String client_ID;

	private String applicationName;
	
	private String appVersion;
	
	private String eventCategory = "TestStaticData";

	private String eventAction = "collect";

	
  /**
   *  Constructor passing the application name, application version  and google analytics tracking code
   *
   * @param appName       
   * @param appVersion         
   * @param trackingCode 
   * @param client ID
   */
	public MeasureProtocolRequest(String appName, String appVersion, String trackingCode, String clientID) {
		this(appName, trackingCode, clientID);
		this.appVersion = appVersion;
	}

  /**
   *  Constructor passing the application name and google analytics tracking code
   *
   * @param appName        
   * @param trackingCode 
   * @param client ID
   */
	public MeasureProtocolRequest(String appName, String trackingCode, String clientID) {
		this.track_ID = trackingCode;
		this.client_ID = clientID;
		this.applicationName = appName;
	}

  /**
   *  Execute  http post method and send data to Google Analytics
   *
   * @param Name       
   * @param value         
   */
	public boolean execRequest(String name, String value) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(AnalyticsParameterConstants.POST_URL);
		method.addParameter(AnalyticsParameterConstants.PROTOCAL_VERSION, "1");
		method.addParameter(AnalyticsParameterConstants.TRACKING_ID, track_ID);
		method.addParameter(AnalyticsParameterConstants.CLIENT_ID, client_ID);
		method.addParameter(AnalyticsParameterConstants.HIT_TYPE, "event");
		method.addParameter(AnalyticsParameterConstants.APPLICATION_NAME, applicationName);
		method.addParameter(AnalyticsParameterConstants.APPLICATION_VERSION,
				(!(this.appVersion == null || this.appVersion.length() == 0)) ? this.appVersion
						: AnalyticsParameterConstants.DEFAULT_VERSION);
		method.addParameter(AnalyticsParameterConstants.EVENT_CATEGORY, eventCategory);
		method.addParameter(AnalyticsParameterConstants.EVENT_ACTION, eventAction);
		method.addParameter(AnalyticsParameterConstants.EVENT_LABEL, name);
		method.addParameter(AnalyticsParameterConstants.EVENT_VALUE, value);

		try {
			int returnCode = client.executeMethod(method);
			return (returnCode == HttpStatus.SC_OK);
		} catch (Exception e) {
			LOG.error("ERROR: {}", e.getMessage());
			return false;
		}
	}
	
	/**
	 * Get event category and its default is TestStaticData
	 */
	public String getEventCategory() {
		return eventCategory;
	}
	
	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}

	/**
	 * Get event action and its default is collect
	 */
	public String getEventAction() {
		return eventAction;
	}
	
	public void setEventAction(String eventAction) {
		this.eventAction = eventAction;
	}

}
