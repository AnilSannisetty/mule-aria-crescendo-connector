package com.prodapt.mulesoft.connectors.aria.internal.configuration;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

import com.prodapt.mulesoft.connectors.aria.internal.connection.providers.AriaConnectionProvider;
import com.prodapt.mulesoft.connectors.aria.internal.operations.BillingAccountRegistration;

@ConnectionProviders(AriaConnectionProvider.class)
@Operations(BillingAccountRegistration.class)
public class AriaConfiguration {

}
