package com.prodapt.mulesoft.connectors.aria.internal.operations;

import java.util.List;


import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodapt.mulesoft.connectors.aria.internal.connection.AriaConnection;
import com.prodapt.mulesoft.connectors.aria.internal.operations.parametergroup.BillingAcctRegistrationParam;

public class BillingAccountRegistration {
	
	private static final Logger logger = LoggerFactory.getLogger(BillingAccountRegistration.class);
	@DisplayName("CRUD:Create Billing Account")
	@MediaType(value = "application/json")
	public String createBillingAccount (@Connection AriaConnection ac, @ParameterGroup (name = "Input Arguments") BillingAcctRegistrationParam ar, @Content (primary = true) String message)
	{
		logger.info("start of billing account registration");
		return null;
	}

}
