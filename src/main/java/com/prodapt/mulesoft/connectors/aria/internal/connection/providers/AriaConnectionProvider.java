package com.prodapt.mulesoft.connectors.aria.internal.connection.providers;

import java.net.URLConnection;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.prodapt.mulesoft.connectors.aria.internal.connection.AriaConnection;

public class AriaConnectionProvider implements ConnectionProvider<AriaConnection> {
	
	private static final Logger logger = LoggerFactory.getLogger(AriaConnectionProvider.class);
	//@ParameterGroup(name = "Connection")
	@Parameter
	private String baseURL;
	@Parameter
	private long client_number;
	@Parameter
	private String auth_key;
	private int responeTimeout;
	
	@Inject
	HttpService httpService;

	@Override
	public AriaConnection connect() throws ConnectionException {
		// TODO Auto-generated method stub
		logger.info("Before creating connection from Connection class");
		return new AriaConnection(baseURL,client_number,auth_key,httpService) ;
	}

	@Override
	public void disconnect(AriaConnection connection) {
		// TODO Auto-generated method stub
		logger.info("Before disconnecting the connection from Connection class");
		connection.invalidate();
		logger.info("After disconnecting the connection from Connection class");
	}

	@Override
	public ConnectionValidationResult validate(AriaConnection connection) {
		logger.info("In Validate method()");
		ConnectionValidationResult result = null;
		try {
			
			HttpResponse response = connection.getHttpResponse(this.baseURL, this.client_number, this.auth_key, this.responeTimeout).get();
			
			String responseMessage = new String (response.getEntity().getBytes());
			logger.info("Response from test connection call" + responseMessage);
			JsonObject responseJSON = new Gson().fromJson(responseMessage, JsonObject.class);
			result = responseJSON.get("error_code").getAsInt() == 0 ? ConnectionValidationResult.success() : ConnectionValidationResult.failure(responseJSON.get("error_msg").getAsString(), new Exception());
			logger.info("result:" + result.getMessage());
		} catch (Exception e) {
			logger.error("Error occurred int validate() method:", e);

		}
		return result;
			
	}
	
	
}
