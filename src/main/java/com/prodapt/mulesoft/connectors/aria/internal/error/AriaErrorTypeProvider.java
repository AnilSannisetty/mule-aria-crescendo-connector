package com.prodapt.mulesoft.connectors.aria.internal.error;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

public class AriaErrorTypeProvider implements ErrorTypeProvider {

	public Set<ErrorTypeDefinition> getErrorTypes() {
	    return  Stream.<AriaErrorTypes>of(AriaErrorTypes.values()).collect(Collectors.toSet());
	  }
}
