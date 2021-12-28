package com.prodapt.mulesoft.connectors.aria.internal.configuration;

//import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

import com.prodapt.mulesoft.connectors.aria.internal.connection.providers.AriaConnectionProvider;
import com.prodapt.mulesoft.connectors.aria.internal.operations.AssignAccountPlan;
import com.prodapt.mulesoft.connectors.aria.internal.operations.BillingAccountRegistration;
import com.prodapt.mulesoft.connectors.aria.internal.operations.CreateAcctBillingGroup;
import com.prodapt.mulesoft.connectors.aria.internal.operations.CreateAcctDunningGroup;
import com.prodapt.mulesoft.connectors.aria.internal.operations.GetAcctDetailsAll;
import com.prodapt.mulesoft.connectors.aria.internal.operations.GetInvoiceDetails;
import com.prodapt.mulesoft.connectors.aria.internal.operations.GetInvoiceHistory;
import com.prodapt.mulesoft.connectors.aria.internal.operations.ModifyAcctBillingGroup;
import com.prodapt.mulesoft.connectors.aria.internal.operations.ModifyAcctDunningGroup;
import com.prodapt.mulesoft.connectors.aria.internal.operations.ModifyCompleteBillingAccount;

@Operations({
	BillingAccountRegistration.class, ModifyCompleteBillingAccount.class,
	AssignAccountPlan.class, CreateAcctBillingGroup.class, 
	CreateAcctDunningGroup.class, GetAcctDetailsAll.class, 
	GetInvoiceDetails.class, GetInvoiceHistory.class, 
	ModifyAcctBillingGroup.class, ModifyAcctDunningGroup.class})
@ConnectionProviders(AriaConnectionProvider.class)
public class AriaConfiguration extends RestConfiguration {}