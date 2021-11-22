package com.prodapt.mulesoft.connectors.aria.internal.operations.parametergroup;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

public class ManagePendingInvoiceParam {
	@Parameter
	@DisplayName("API Name")
	@Summary("This API can be used to approve, discard, or regenerate a pending invoice. A pending invoice can be retrieved based on the invoice number or master plan instance ID. After a pending invoice is approved, this call can also collect a payment and send a statement. If the collection fails, the pending invoice will automatically be voided provided the system setting \"Auto Void Pending Invoice on Collection Failure\" is set to True. This API can also set the custom_status_label, and client_notes at the time of approval, or regeneration.")
	@Optional(defaultValue = "manage_pending_invoice_m")
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
