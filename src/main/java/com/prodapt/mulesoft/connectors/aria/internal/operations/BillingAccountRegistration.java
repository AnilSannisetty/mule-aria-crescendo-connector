package com.prodapt.mulesoft.connectors.aria.internal.operations;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.lang.Throwable;

import org.json.JSONObject;
import org.mule.runtime.api.exception.MuleRuntimeException;
import org.mule.runtime.api.i18n.I18nMessageFactory;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.process.CompletionCallback;
import org.mule.runtime.extension.api.runtime.streaming.StreamingHelper;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.domain.entity.ByteArrayHttpEntity;
import org.mule.runtime.http.api.domain.entity.HttpEntity;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.request.HttpRequestBuilder;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodapt.mulesoft.connectors.api.HttpResponseAttributes;
import com.prodapt.mulesoft.connectors.aria.internal.configuration.RestConfiguration;
import com.prodapt.mulesoft.connectors.aria.internal.configuration.parametergroup.ConfigurationOverrides;
import com.prodapt.mulesoft.connectors.aria.internal.configuration.parametergroup.EntityRequestParameters;
import com.prodapt.mulesoft.connectors.aria.internal.connection.AriaConnection;
import com.prodapt.mulesoft.connectors.aria.internal.error.exception.AriaException;
import com.prodapt.mulesoft.connectors.aria.internal.error.exception.ExceptionHandler;
import com.prodapt.mulesoft.connectors.aria.internal.operations.parametergroup.BillingAcctRegistrationParam;
import com.prodapt.mulesoft.connectors.aria.internal.utility.AriaUtility;
import com.prodapt.mulesoft.connectors.aria.internal.utility.RestRequestBuilder;
import com.prodapt.mulesoft.connectors.aria.api.connection.DefaultRestConnection;
import com.prodapt.mulesoft.connectors.aria.api.connection.RestConnection;
import com.prodapt.mulesoft.connectors.aria.api.operation.RequestParameters;


public class BillingAccountRegistration {
	
	private static final Logger logger = LoggerFactory.getLogger(BillingAccountRegistration.class);
	public static final RestRequestBuilder.QueryParamFormat QUERY_PARAM_FORMAT = RestRequestBuilder.QueryParamFormat.MULTIMAP;
	@DisplayName("CRUD:Create Billing Account")
	@MediaType(value = "application/json")
	public void createBillingAccount (@Config RestConfiguration rc, @Connection AriaConnection ac, @ParameterGroup (name = "Input Arguments") BillingAcctRegistrationParam ar, @ParameterGroup (name = "Request Parameters") EntityRequestParameters parameters, @ParameterGroup(name = "Connector Overrides") ConfigurationOverrides overrides, @Content (primary = true) TypedValue<InputStream> payload, StreamingHelper streamingHelper, CompletionCallback<InputStream, HttpResponseAttributes> callback)
	{
		String apiName = "create_acct_complete_m";
		HttpClient httpClient = ac.getHttpClient();
		
				
		/*
		 * JsonObject commonJSON = new JsonObject(); 
		 * commonJSON.addProperty("rest_call", apiName );
		 * commonJSON.addProperty("releaseVersion", ar.getReleaseVersion() );
		 * commonJSON.addProperty("output_format", "json" );
		 * commonJSON.addProperty("client_no", ac.getClient_no() );
		 * commonJSON.addProperty("auth_key", ac.getAuth_key() );
		 * commonJSON.addProperty("do_write", ar.isDo_write() );
		 * commonJSON.addProperty("client_receipt_id", ar.getClient_receipt_id() );
		 */
		
		/*
		 * Gson g = new Gson(); String jsonstring = g.toJson(commonJSON); JsonObject
		 * message = new JsonObject(); message = message.getAsJsonObject(payload);
		 */
		StringBuilder strRequestPayload = new StringBuilder();
		BufferedReader brStream =  new BufferedReader(new InputStreamReader(payload.getValue()));
		String line = "";
		try {
			while ((line = brStream.readLine()) != null)
			{
				strRequestPayload.append(line);
			}
			logger.info(strRequestPayload.toString());
			payload.getValue().close();;
		}catch(Exception e) {
			logger.info("Error while reading payload:", e);
			e.printStackTrace();
		}
		
		logger.info(strRequestPayload.toString());
		JSONObject json1 = new JSONObject(strRequestPayload.toString());
		logger.info(json1.toString());
		HashMap commonJSON = new HashMap();
		commonJSON.put("rest_call", apiName);
		commonJSON.put("releaseVersion", ar.getReleaseVersion());
		commonJSON.put("output_format", "json");
		commonJSON.put("client_no", ac.getClient_no());
		commonJSON.put("auth_key", ac.getAuth_key());
		commonJSON.put("do_write", ar.isDo_write());
		commonJSON.put("client_receipt_id", ar.getClient_receipt_id());
		JSONObject json2 = new JSONObject(commonJSON);
		JSONObject mergedJson = new JSONObject();
		if (json1.length() > 0)
		{
			mergedJson = new JSONObject(json1, JSONObject.getNames(json1));
		}
		if (json2.length() > 0 )
		{
			for (String key : JSONObject.getNames(json2))
			{
				mergedJson.put(key, json2.get(key));
			}
		}
		
		logger.info(mergedJson.toString());
		logger.info("URL" + ac.getBaseURL());
		
		InputStream mergedPayloadStream = new ByteArrayInputStream(mergedJson.toString().getBytes());
		TypedValue<InputStream> mergedPayload = new TypedValue<InputStream>(mergedPayloadStream, DataType.INPUT_STREAM);
		//JsonObject payload = g.toJson(message, null); 
		//String body = "{\"rest_call\":\"" +  apiName + "\",\"output_format\": \"json\",\"client_no\":"  + ac.getClient_no() + ",\"auth_key\":\"" + ac.getAuth_key()+ "\",}";
		RestRequestBuilder builder = 
				(new RestRequestBuilder(ac.getBaseURL(),"",HttpConstants.Method.POST, (RequestParameters) parameters).setQueryParamFormat(QUERY_PARAM_FORMAT)
						.addHeader("Content-Type", "application/json").addHeader("accept", "application/json").setBody(mergedPayload, overrides.getStreamingType()));	 
		/*builder.addHeader("Content-Type", "application/json");
		builder.method(HttpConstants.Method.POST).uri(ac.getBaseURL()).entity((HttpEntity) new ByteArrayHttpEntity(mergedJson.toString().getBytes(StandardCharsets.UTF_8)));*/
		try {
			ac.request(builder, 0, null, streamingHelper).whenComplete(handleResponse(callback));
		}catch(Throwable t) 
		{
			t.printStackTrace();
			callback.error(t);
		}
		
		
		//CompletableFuture<HttpResponse> respRegistration = getHttpResponse(builder, httpClient, 30000);
		
		/*
		 * try { //respRegistration.whenComplete((response, error) ->
		 * handleResponse(response, error, callback));
		 * //logger.info("response from http call" + respRegistration.get().toString());
		 * } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}
	
	/*
	 * public CompletableFuture<HttpResponse> getHttpResponse(HttpRequestBuilder
	 * builder, HttpClient httpClient, int responseTimeout ) { return
	 * httpClient.sendAsync(builder.build(),responseTimeout,false,null); }
	 */
	

	
	
	  private <T> BiConsumer<Result<T, HttpResponseAttributes>, Throwable> handleResponse(CompletionCallback<T,	  HttpResponseAttributes> callback) { 
		  return (r,e) -> { if (e != null) {
	  callback.error(e); }else { callback.success(r); } }; }
	 

}
