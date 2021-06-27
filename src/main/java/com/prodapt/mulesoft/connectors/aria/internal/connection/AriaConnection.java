package com.prodapt.mulesoft.connectors.aria.internal.connection;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;
import org.mule.runtime.http.api.client.auth.HttpAuthentication;
import org.mule.runtime.http.api.domain.entity.ByteArrayHttpEntity;
import org.mule.runtime.http.api.domain.entity.HttpEntity;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.request.HttpRequestBuilder;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AriaConnection {
	
	private static final Logger logger = LoggerFactory.getLogger(AriaConnection.class);

	private final long client_no;
	private final String auth_key;
	private final HttpClient httpClient;
	private final String baseURL;
	
	

	public AriaConnection(String baseURL, long client_no, String auth_key, HttpService httpService) {
		logger.info("Start of Aria connection constructor");
		this.baseURL = baseURL;
		this.client_no = client_no;
		this.auth_key = auth_key;
		logger.info("Before httpClient Initialization");
		this.httpClient = createClient(httpService);
		logger.info("After httpClient Initialization");
	}

	private HttpClient createClient(HttpService httpService)
	{
		logger.info("1");
		HttpClientConfiguration.Builder builder = new HttpClientConfiguration.Builder();
		logger.info("2");
		builder.setName("Aria-Connector");
		HttpClient httpClient = httpService.getClientFactory().create(builder.build());
		logger.info("3");
		httpClient.start();
		logger.info("4");
		return httpClient;
	}
	
	public CompletableFuture<HttpResponse> sendRequest(String baseURL, long client_no, String auth_key, int responseTimeout) {
		 logger.info("Inside sendRequest operation");
		 HttpRequestBuilder builder = HttpRequest.builder();	 
		 builder.addHeader("Content-Type", "application/json");
		 String body = "{\"rest_call\": \"get_current_system_version\",\"output_format\": \"json\",\"client_no\":"  + this.client_no + ",\"auth_key\":\"" + this.auth_key + "\"}";
		 builder.method(HttpConstants.Method.POST).uri(baseURL).entity((HttpEntity) new ByteArrayHttpEntity(body.getBytes(StandardCharsets.UTF_8)));
		 logger.info("Body" + body);
		 return this.httpClient.sendAsync(builder.build(),responseTimeout,false,null);
	}
	
	public void invalidate()
	{
		try {
			this.httpClient.stop();
		} catch (Exception e) {
			logger.error("Error occurred while closing the http client connection",e);
		}
	}

	public String getBaseURL() {
		return this.baseURL;
	}
	 
	public CompletableFuture<HttpResponse> getHttpResponse(String baseURL, long client_no, String auth_key, int responseTimeout) {
		return this.sendRequest(baseURL, client_no, auth_key, 30000);
	}
	
	/*
	 * public URLConnection createConnection(String baseURL, String client_number,
	 * String auth_key) { URLConnection httpsconn = null; //String urlProtocol =
	 * "HTTPS".equalsIgnoreCase(protocol) ? "https://" :"http://"; try { httpsconn =
	 * new URL(baseURL).openConnection();
	 * logger.info("Connection opened successfully"); } catch(Exception e) {
	 * e.printStackTrace(); } conn.addRequestProperty("User-Agent", "Mozilla");
	 * return (HttpsURLConnection) httpsconn; }
	 * 
	 * public URLConnection getConnection() {
	 * logger.info("Connection requred in getConnection method"); return this.conn;
	 * }
	 * 
	 * public void invalidate() {
	 * 
	 * if (this.conn !=null) ((HttpsURLConnection) conn).disconnect();
	 * 
	 * logger.info("connection disconnected successfully"); }
	 */

	/*
	 * @Override public void disconnect() { try { this.httpClient.stop();
	 * }catch(Exception e) {
	 * logger.error("Error Occuring while disconnecting the http client connection",
	 * e); }
	 * 
	 * }
	 */

	
	/*
	 * public void validate() { String url = this.baseURL;
	 * 
	 * HttpRequestBuilder builder = HttpRequest.builder(); Map<String,Object>
	 * bodyMap = new HashMap<>(); //bodyMap.put("rest_call",
	 * "get_current_system_version"); //bodyMap.put("output_format", "json");
	 * bodyMap.put("client_no", this.client_no); bodyMap.put("auth_key",
	 * this.auth_key); String body =
	 * "{\"rest_call\": \"get_current_system_version\",\"output_format\": \"json\",\"client_no\":"
	 * + this.client_no + ",\"auth_key\":\"" + this.auth_key + "\"}";
	 * builder.method(HttpConstants.Method.POST).uri(url).entity((HttpEntity) new
	 * ByteArrayHttpEntity(body.getBytes(StandardCharsets.UTF_8)));
	 * 
	 * }
	 */
	 
	
	/*
	 * public CompletableFuture<HttpResponse> send(HttpRequestBuilder
	 * requestBuilder, int responseTimeout, boolean followRedirects) {
	 * requestBuilder.addHeader("Content-Type", "application/json"); return
	 * this.httpClient.sendAsync(requestBuilder.build(),
	 * Optional<HttpAuthentication>);
	 * 
	 * } public HttpResponse sendSource(HttpRequestBuilder requestBuilder, int
	 * responseTimeout, boolean followRedirects) throws IOException,
	 * TimeoutException { requestBuilder.addHeader("Content-Type",
	 * "application/json"); return this.httpClient.send(requestBuilder.build(),
	 * responseTimeout, followRedirects);
	 * 
	 * }
	 */

}
