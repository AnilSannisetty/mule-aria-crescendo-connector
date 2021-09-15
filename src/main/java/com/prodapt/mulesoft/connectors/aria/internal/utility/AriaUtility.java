package com.prodapt.mulesoft.connectors.aria.internal.utility;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodapt.mulesoft.connectors.api.HttpResponseAttributes;
import com.prodapt.mulesoft.connectors.aria.internal.connection.AriaConnection;
import com.prodapt.mulesoft.connectors.aria.internal.operations.parametergroup.BillingAcctRegistrationParam;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class AriaUtility {
	private static final Logger logger = LoggerFactory.getLogger(AriaUtility.class);
	
	public static String getBodyAsString(Map<String,Object> bodyMap) {
		StringBuilder body = new StringBuilder();
		Iterator<Map.Entry<String, Object>> iterator = bodyMap.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();
			body = body.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		logger.info(body.toString());
		return StringUtils.removeEnd(body.toString(), "&");
	}
	
	public static InputStream getInputStreamFromResponse(HttpResponse response) {
		return response.getEntity().getContent();
	}
	
	public static HttpResponseAttributes setResponseAttributes(HttpResponse httpResponse) {
		return new HttpResponseAttributes(httpResponse.getStatusCode(), httpResponse.getReasonPhrase(), httpResponse.getHeaders());
	}
	
	public static boolean containsIgnoreCase(String value, String predicate) {
	    if (value == null || predicate == null)
	      return false; 
	    return value.toLowerCase().contains(predicate.toLowerCase());
	  }
	
	public static JSONObject attachStandardParamsToPayload(TypedValue<InputStream> payload, String apiName, AriaConnection ac, BillingAcctRegistrationParam ar) {
		
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
