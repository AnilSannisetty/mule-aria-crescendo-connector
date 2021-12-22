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
import com.prodapt.mulesoft.connectors.aria.internal.operations.parametergroup.RecordMandateApprovalParam;
import com.prodapt.mulesoft.connectors.aria.internal.utility.RestRequestBuilder;

public class RecordMandateApproval {
	private static final Logger logger = LoggerFactory.getLogger(RecordMandateApproval.class);
	public static final RestRequestBuilder.QueryParamFormat QUERY_PARAM_FORMAT = RestRequestBuilder.QueryParamFormat.MULTIMAP;

	@DisplayName("Record Mandate Approval")
	@MediaType(value = "application/json")
	@Summary("This operation electronically verifies that the end user (Aria account holder) has physically signed a SEPA Direct Debit mandate. Payments can only be made with SEPA Tokenized Direct Debit after the mandate has been approved with this operation. It is the client's full responsibility to ensure that the end user signed a paper mandate that verifies that this electronic mandate is valid. Neither Aria or the payment gateway can intervene if the end user disputes signing the mandate later. The client must collect in the account's currency, which must be a currency supported by SEPA DD.")
	public void recordMandateApproval(@Config RestConfiguration rc, @Connection AriaConnection ac,
			@ParameterGroup(name = "Input Arguments") RecordMandateApprovalParam ar,
			@ParameterGroup(name = "Request Parameters") EntityRequestParameters parameters,
			@ParameterGroup(name = "Connector Overrides") ConfigurationOverrides overrides,
			@Content(primary = true) TypedValue<InputStream> payload, StreamingHelper streamingHelper,
			CompletionCallback<InputStream, HttpResponseAttributes> callback) {
		String apiName = "record_mandate_approval_m";
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
	private JSONObject attachStandardParamsToPayload(TypedValue<InputStream> payload, String apiName, AriaConnection ac, RecordMandateApprovalParam ar) {
		
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
		//commonJSON.put("client_receipt_id", ar.getClient_receipt_id());
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
