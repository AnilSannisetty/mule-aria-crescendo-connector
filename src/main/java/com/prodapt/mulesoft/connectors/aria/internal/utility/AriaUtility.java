package com.prodapt.mulesoft.connectors.aria.internal.utility;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodapt.mulesoft.connectors.api.HttpResponseAttributes;

import java.io.InputStream;
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
}
