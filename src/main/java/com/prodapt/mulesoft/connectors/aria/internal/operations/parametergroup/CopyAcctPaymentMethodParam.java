package com.prodapt.mulesoft.connectors.aria.internal.operations.parametergroup;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

public class CopyAcctPaymentMethodParam {
	@Parameter
	@DisplayName("API Name")
	@Summary("This API copies one or more payment methods (and their associated billing contact information) from one customer account in Aria to a different customer account. Clients must validate that the account owner is the same for both the source and target accounts prior to calling this API. Note that payment methods copied to the target account are not by default associated with a billing group. If no payment methods are specified, all non-disabled payment methods on the source account are copied to the target account.")
	@Optional(defaultValue = "get_acct_coupon_details_m")
	@Expression(ExpressionSupport.SUPPORTED)
	private String rest_Call;
	
	@Parameter
	@DisplayName("API Version")
	@Summary("Version of the API")
	@Expression(ExpressionSupport.SUPPORTED)
	private int releaseVersion;
	
//	@Parameter
//	@DisplayName("Do Write")
//	@Summary("Default value is true")
//	@Optional(defaultValue = "true")
//	private boolean do_write;
	
//	@Parameter
//	@DisplayName("Client Receipt Id")
//	@Summary("Client-defined unique identifier used to track related system actions")
//	private String client_receipt_id;
	
	
	public String getRest_Call() {
		return rest_Call;
	}

	public void setRest_Call(String rest_Call) {
		this.rest_Call = rest_Call;
	}

	public int getReleaseVersion() {
		return releaseVersion;
	}

	public void setReleaseVersion(int releaseVersion) {
		this.releaseVersion = releaseVersion;
	}

//	public boolean isDo_write() {
//		return do_write;
//	}
//
//	public void setDo_write(boolean do_write) {
//		this.do_write = do_write;
//	}

//	public String getClient_receipt_id() {
//		return client_receipt_id;
//	}
//
//	public void setClient_receipt_id(String client_receipt_id) {
//		this.client_receipt_id = client_receipt_id;
//	}
}
