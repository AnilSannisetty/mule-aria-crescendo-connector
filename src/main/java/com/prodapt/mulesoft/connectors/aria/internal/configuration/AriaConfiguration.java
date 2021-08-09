package com.prodapt.mulesoft.connectors.aria.internal.configuration;

//import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

import com.prodapt.mulesoft.connectors.aria.internal.connection.providers.AriaConnectionProvider;
import com.prodapt.mulesoft.connectors.aria.internal.operations.BillingAccountRegistration;
import com.prodapt.mulesoft.connectors.aria.internal.operations.ModifyCompleteBillingAccount;

@Operations({BillingAccountRegistration.class, ModifyCompleteBillingAccount.class})
@ConnectionProviders(AriaConnectionProvider.class)
public class AriaConfiguration extends RestConfiguration {}