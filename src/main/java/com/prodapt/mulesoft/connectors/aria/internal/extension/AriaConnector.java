package com.prodapt.mulesoft.connectors.aria.internal.extension;

import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;

import com.prodapt.mulesoft.connectors.aria.internal.configuration.AriaConfiguration;

@Xml(prefix = "mule-aria-connector")
@Extension(name = "Aria Crescendo Connector - Mule4")
@Configurations(AriaConfiguration.class)
public class AriaConnector {

}
