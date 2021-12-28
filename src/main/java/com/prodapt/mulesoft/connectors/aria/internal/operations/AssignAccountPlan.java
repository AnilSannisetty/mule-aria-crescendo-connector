package com.prodapt.mulesoft.connectors.aria.internal.operations;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.function.BiConsumer;

import org.json.JSONObject;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.process.CompletionCallback;
import org.mule.runtime.extension.api.runtime.streaming.StreamingHelper;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodapt.mulesoft.connectors.aria.api.HttpResponseAttributes;
import com.prodapt.mulesoft.connectors.aria.api.operation.RequestParameters;
import com.prodapt.mulesoft.connectors.aria.internal.configuration.RestConfiguration;
import com.prodapt.mulesoft.connectors.aria.internal.configuration.parametergroup.ConfigurationOverrides;
import com.prodapt.mulesoft.connectors.aria.internal.configuration.parametergroup.EntityRequestParameters;
import com.prodapt.mulesoft.connectors.aria.internal.connection.AriaConnection;
import com.prodapt.mulesoft.connectors.aria.internal.operations.parametergroup.AssignAccountPlanParam;
import com.prodapt.mulesoft.connectors.aria.internal.utility.RestRequestBuilder;

public class AssignAccountPlan {
	private static final Logger logger = LoggerFactory.getLogger(AssignAccountPlan.class);
	public static final RestRequestBuilder.QueryParamFormat QUERY_PARAM_FORMAT = RestRequestBuilder.QueryParamFormat.MULTIMAP;

	@DisplayName("Assign Account Plan")
	@MediaType(value = "application/json")
	@Summary("This operation assigns a new master or supplemental plan to the specified account.\r\n"
			+ "When you assign a new plan to an account, you can assign the following:\r\n"
			+ "\r\n"
			+ "Billing group\r\n"
			+ "Notification method and template\r\n"
			+ "Payment methods\r\n"
			+ "Contact information for billing, statements, and payment methods\r\n"
			+ "Dunning information\r\n"
			+ "Start date and proration options\r\n"
			+ "Balance forward\r\n"
			+ "Relationship to other plans (parent, child, etc.)\r\n"
			+ "Rate schedules\r\n"
			+ "Coupons, promotion codes and surcharges\r\n"
			+ "NSO and SKU information.")
	public void createAccountPlan(@Config RestConfiguration rc, @Connection AriaConnection ac,
			@ParameterGroup(name = "Input Arguments") AssignAccountPlanParam ar,
			@ParameterGroup(name = "Request Parameters") EntityRequestParameters parameters,
			@ParameterGroup(name = "Connector Overrides") ConfigurationOverrides overrides,
			@Content(primary = true) TypedValue<InputStream> payload, StreamingHelper streamingHelper,
			CompletionCallback<InputStream, HttpResponseAttributes> callback) {
		String apiName = "assign_acct_plan_m";
		HttpClient httpClient = ac.getHttpClient();

		JSONObject mergedJson = attachStandardParamsToPayload(payload, apiName, ac, ar);

		logger.info(mergedJson.toString());
		logger.info("URL" + ac.getBaseURL());

		InputStream mergedPayloadStream = new ByteArrayInputStream(mergedJson.toString().getBytes());
		TypedValue<InputStream> mergedPayload = new TypedValue<InputStream>(mergedPayloadStream, DataType.INPUT_STREAM);
		RestRequestBuilder builder = (new RestRequestBuilder(ac.getBaseURL(), "", HttpConstants.Method.POST,
				(RequestParameters) parameters).setQueryParamFormat(QUERY_PARAM_FORMAT)
						.addHeader("Content-Type", "application/json").addHeader("accept", "application/json")
						.setBody(mergedPayload, overrides.getStreamingType()));
		try {
			ac.request(builder, 0, null, streamingHelper).whenComplete(handleResponse(callback));
		} catch (Throwable t) {
			t.printStackTrace();
			callback.error(t);
		}
	}

	private <T> BiConsumer<Result<T, HttpResponseAttributes>, Throwable> handleResponse(
			CompletionCallback<T, HttpResponseAttributes> callback) {
		return (r, e) -> {
			if (e != null) {
				callback.error(e);
			} else {
				callback.success(r);
			}
		};
	}
private JSONObject attachStandardParamsToPayload(TypedValue<InputStream> payload, String apiName, AriaConnection ac, AssignAccountPlanParam ar) {
		
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
		
		return mergedJson;
	}
}
