package com.prodapt.mulesoft.connectors.aria.internal.configuration;

//import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

import com.prodapt.mulesoft.connectors.aria.internal.connection.providers.AriaConnectionProvider;
import com.prodapt.mulesoft.connectors.aria.internal.operations.AssignAccountPlan;
import com.prodapt.mulesoft.connectors.aria.internal.operations.BillingAccountRegistration;
import com.prodapt.mulesoft.connectors.aria.internal.operations.CreateAcctBillingGroup;
import com.prodapt.mulesoft.connectors.aria.internal.operations.CreateAcctDunningGroup;
import com.prodapt.mulesoft.connectors.aria.internal.operations.GetAllAccountDetails;
//import com.prodapt.mulesoft.connectors.aria.internal.operations.GetClientPlanBasic;
import com.prodapt.mulesoft.connectors.aria.internal.operations.GetInvoiceCMDetails;
import com.prodapt.mulesoft.connectors.aria.internal.operations.GetInvoiceDetails;
import com.prodapt.mulesoft.connectors.aria.internal.operations.GetInvoiceHistory;
import com.prodapt.mulesoft.connectors.aria.internal.operations.GetOrder;
import com.prodapt.mulesoft.connectors.aria.internal.operations.GetStatementForInvoice;
import com.prodapt.mulesoft.connectors.aria.internal.operations.ModifyAcctBillingGroup;
import com.prodapt.mulesoft.connectors.aria.internal.operations.ModifyAcctCredentials;
import com.prodapt.mulesoft.connectors.aria.internal.operations.ModifyAcctDunningGroup;
import com.prodapt.mulesoft.connectors.aria.internal.operations.ModifyAcctInvoice;
import com.prodapt.mulesoft.connectors.aria.internal.operations.ModifyAcctMultiPlans;
import com.prodapt.mulesoft.connectors.aria.internal.operations.ModifyAcctStatus;
import com.prodapt.mulesoft.connectors.aria.internal.operations.ModifyCompleteBillingAccount;
import com.prodapt.mulesoft.connectors.aria.internal.operations.ModifyContact;
import com.prodapt.mulesoft.connectors.aria.internal.operations.ModifyOrder;
import com.prodapt.mulesoft.connectors.aria.internal.operations.ModifyPaymentMethod;
import com.prodapt.mulesoft.connectors.aria.internal.operations.GetAcctBalance;
import com.prodapt.mulesoft.connectors.aria.internal.operations.GetAcctPlanBalance;
import com.prodapt.mulesoft.connectors.aria.internal.operations.GenInvoice;
import com.prodapt.mulesoft.connectors.aria.internal.operations.CreateCreditMemo;
import com.prodapt.mulesoft.connectors.aria.internal.operations.CreateOrder;
import com.prodapt.mulesoft.connectors.aria.internal.operations.CreateOrderWithPlan;
import com.prodapt.mulesoft.connectors.aria.internal.operations.CancelOrder;
import com.prodapt.mulesoft.connectors.aria.internal.operations.CancelAcctPlan;
import com.prodapt.mulesoft.connectors.aria.internal.operations.ModifyAcctPlan;
import com.prodapt.mulesoft.connectors.aria.internal.operations.ModifyAcctPlanStatus;

@Operations({ BillingAccountRegistration.class, ModifyCompleteBillingAccount.class, 
		AssignAccountPlan.class,
		CreateAcctBillingGroup.class, CreateAcctDunningGroup.class, 
		GetAllAccountDetails.class,GetInvoiceDetails.class,	GetInvoiceHistory.class,
		ModifyAcctBillingGroup.class, ModifyAcctDunningGroup.class,
		ModifyAcctCredentials.class, ModifyAcctMultiPlans.class, ModifyAcctStatus.class, 
		ModifyContact.class,ModifyPaymentMethod.class, ModifyAcctInvoice.class, 
		GetInvoiceCMDetails.class, GetStatementForInvoice.class,
		GetOrder.class, ModifyOrder.class, GetAcctBalance.class, 
		GetAcctPlanBalance.class, GenInvoice.class,
		CreateCreditMemo.class, CreateOrder.class, CreateOrderWithPlan.class, 
		CancelOrder.class, CancelAcctPlan.class,
		ModifyAcctPlan.class, ModifyAcctPlanStatus.class })
@ConnectionProviders(AriaConnectionProvider.class)
public class AriaConfiguration extends RestConfiguration {
}