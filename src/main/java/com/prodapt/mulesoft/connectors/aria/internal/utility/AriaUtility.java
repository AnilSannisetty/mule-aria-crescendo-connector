package com.prodapt.mulesoft.connectors.aria.internal.utility;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

}
