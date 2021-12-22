package com.prodapt.mulesoft.connectors.aria.internal.operations.parametergroup;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

public class GetItemsByClassParam {
	@Parameter
	@DisplayName("API Name")
	@Summary("Aria API call to returns the items for a given class.")
	@Optional(defaultValue = "get_items_by_class_m")
	@Expression(ExpressionSupport.SUPPORTED)
	private String rest_Call;
	
	@Parameter
	@DisplayName("API Version")
	@Summary("Version of the API")
	@Expression(ExpressionSupport.SUPPORTED)
	private int releaseVersion;
		
	
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
//
//	public String getClient_receipt_id() {
//		return client_receipt_id;
//	}
//
//	public void setClient_receipt_id(String client_receipt_id) {
//		this.client_receipt_id = client_receipt_id;
//	}
}
