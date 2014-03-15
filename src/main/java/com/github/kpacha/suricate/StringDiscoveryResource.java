package com.github.kpacha.suricate;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ContextResolver;

import com.netflix.curator.x.discovery.server.rest.DiscoveryContext;
import com.netflix.curator.x.discovery.server.rest.DiscoveryResource;

@Path("/")
public class StringDiscoveryResource extends DiscoveryResource<String> {
    public StringDiscoveryResource(
	    @Context ContextResolver<DiscoveryContext<String>> resolver) {
	super(resolver.getContext(DiscoveryContext.class));
    }
}
