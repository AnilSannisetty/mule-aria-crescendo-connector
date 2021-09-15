package com.prodapt.mulesoft.connectors.aria.internal.operations.parametergroup;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

public class GetAcctBalanceParam {

		@Parameter
		@DisplayName("API Name")
		@Summary("Aria API call to get Account Balance")
		@Optional(defaultValue = "get_acct_balance_m")
		@Expression(ExpressionSupport.SUPPORTED)
		private String rest_Call;
		
		@Parameter
		@DisplayName("API Version")
		@Summary("Version of the API")
		@Expression(ExpressionSupport.SUPPORTED)
		private int releaseVersion;
		
		/*
		 * 	@Parameter
			@DisplayName("Do Write")
			@Summary("Default value is true")
			@Optional(defaultValue = "true")
			private boolean do_write;
			
			@Parameter
			@DisplayName("Client Receipt Id")
			@Summary("Client-defined unique identifier used to track related system actions")
			private String client_receipt_id;
		*/

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
		
}
