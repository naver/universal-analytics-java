/*
 * Copyright (C) 2012 - 2012 NHN Corporation
 * All rights reserved.
 *
 * This file is part of The nGrinder software distribution. Refer to
 * the file LICENSE which is part of The nGrinder distribution for
 * licensing details. The nGrinder distribution is available on the
 * Internet at http://nhnopensource.org/ngrinder
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.ngrinder.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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

	public MeasureProtocolRequest(String trackId, String clientID, String applicationName) {
		this.track_ID = trackId;
		this.client_ID = clientID;
		this.applicationName = applicationName;
	}

	public boolean execRequest(String name, String value) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post;

		try {
			post = new HttpPost(AnalyticsParameterConstants.POST_URL);

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(AnalyticsParameterConstants.PROTOCAL_VERSION, "1"));
			nameValuePairs.add(new BasicNameValuePair(AnalyticsParameterConstants.TRACKING_ID, track_ID));
			nameValuePairs.add(new BasicNameValuePair(AnalyticsParameterConstants.CLIENT_ID, client_ID));
			nameValuePairs.add(new BasicNameValuePair(AnalyticsParameterConstants.HIT_TYPE, "event"));
			nameValuePairs.add(new BasicNameValuePair(AnalyticsParameterConstants.APPLICATION_NAME, applicationName));

			nameValuePairs.add(new BasicNameValuePair(AnalyticsParameterConstants.EVENT_CATEGORY, "TestStaticData"));
			nameValuePairs.add(new BasicNameValuePair(AnalyticsParameterConstants.EVENT_ACTION, "collect"));
			nameValuePairs.add(new BasicNameValuePair(AnalyticsParameterConstants.EVENT_LABEL, name));
			nameValuePairs.add(new BasicNameValuePair(AnalyticsParameterConstants.EVENT_VALUE, value));

			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = client.execute(post);

			return (response.getStatusLine().getStatusCode() == 200);

		} catch (Exception e) {
			LOG.error("ERROR: {}", e.getMessage());
			return false;
		}
	}

}
