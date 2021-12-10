package com.prodapt.mulesoft.connectors.aria.internal.operations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.function.BiConsumer;
import java.lang.Throwable;

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

import com.prodapt.mulesoft.connectors.aria.internal.configuration.RestConfiguration;
import com.prodapt.mulesoft.connectors.aria.internal.configuration.parametergroup.ConfigurationOverrides;
import com.prodapt.mulesoft.connectors.aria.internal.configuration.parametergroup.EntityRequestParameters;
import com.prodapt.mulesoft.connectors.aria.internal.connection.AriaConnection;

import com.prodapt.mulesoft.connectors.aria.internal.operations.parametergroup.BillingAcctRegistrationParam;
import com.prodapt.mulesoft.connectors.aria.internal.utility.AriaUtility;
import com.prodapt.mulesoft.connectors.aria.internal.utility.RestRequestBuilder;
import com.prodapt.mulesoft.connectors.aria.api.HttpResponseAttributes;
import com.prodapt.mulesoft.connectors.aria.api.operation.RequestParameters;

public class BillingAccountRegistration {

	private static final Logger logger = LoggerFactory.getLogger(BillingAccountRegistration.class);
	public static final RestRequestBuilder.QueryParamFormat QUERY_PARAM_FORMAT = RestRequestBuilder.QueryParamFormat.MULTIMAP;

	@DisplayName("CRUD: Create Billing Account")
	@MediaType(value = "application/json")
	@Summary("This operation is used to  create multiple accounts within a parent-child account hierarchy and assigns multiple master plans and, optionally, multiple suppleresp_master_plan_inst_nomental plans to each account in the parent-child account hierarchy for Aria clients.\r\n"
			+ "All of the following information can be assigned to the account as it is created using this operation.\r\n"
			+ "\r\n"
			+ "User ID and related login information: User ID, password and secret question information for the account.\r\n"
			+ "Contact information: Account, billing and statement contact information, including name, address, email and phone number.\r\n"
			+ "Statement information: Specify the statement notification method and format, such as HTML or PDF, and the associated template used to generate a statement for this account.\r\n"
			+ "Parent and child plan assignment: Specify which plans on this account are parent plans or child plans and the relationships between them. You can also assign standard and custom rate schedules to plans on the account.\r\n"
			+ "Collection, dunning and functional account group assignment: Specify the collection, dunning or functional account groups to which this account is assigned.\r\n"
			+ "Payment method/term and taxation status: Specify if the account payment method is a card, direct debit, or a payment term, an whether the account is tax-exempt.\r\n"
			+ "Invoicing/billing information: Specify proration policy and account or billing start/end dates.\r\n"
			+ "Surcharges: Specify the type and amount of surcharges applied to the account.\r\n"
			+ "Usage: Specify whether unbilled usage is invoiced and if usage pooling is enabled for the account,\r\n"
			+ "Coupons and promotions: Specify coupons and promotions to be applied to the account.\r\n"
			+ "Revenue recognition: Specify the revenue recognition profile assigned to this account.")
	public void createBillingAccount(@Config RestConfiguration rc, @Connection AriaConnection ac,
			@ParameterGroup(name = "Input Arguments") BillingAcctRegistrationParam ar,
			@ParameterGroup(name = "Request Parameters") EntityRequestParameters parameters,
			@ParameterGroup(name = "Connector Overrides") ConfigurationOverrides overrides,
			@Content(primary = true) TypedValue<InputStream> payload, StreamingHelper streamingHelper,
			CompletionCallback<InputStream, HttpResponseAttributes> callback) {
		String apiName = "create_acct_complete_m";
		HttpClient httpClient = ac.getHttpClient();

		JSONObject mergedJson = AriaUtility.attachStandardParamsToPayload(payload, apiName, ac, ar);

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

}
