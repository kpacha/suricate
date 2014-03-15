package com.github.kpacha.suricate;

import java.util.Set;

import com.google.common.collect.Sets;
import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.ExponentialBackoffRetry;
import com.netflix.curator.x.discovery.details.JsonInstanceSerializer;
import com.netflix.curator.x.discovery.details.ServiceDiscoveryImpl;
import com.netflix.curator.x.discovery.server.contexts.StringDiscoveryContext;
import com.netflix.curator.x.discovery.server.entity.JsonServiceInstanceMarshaller;
import com.netflix.curator.x.discovery.server.entity.JsonServiceInstancesMarshaller;
import com.netflix.curator.x.discovery.server.entity.JsonServiceNamesMarshaller;
import com.netflix.curator.x.discovery.strategies.RandomStrategy;

public class Application extends javax.ws.rs.core.Application {
    private StringDiscoveryContext context;
    private JsonServiceNamesMarshaller serviceNamesMarshaller = new JsonServiceNamesMarshaller();
    private JsonServiceInstanceMarshaller<String> serviceInstanceMarshaller;
    private JsonServiceInstancesMarshaller<String> serviceInstancesMarshaller;

    public Application(Configuration configuration) {
	CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(
		configuration.getZkConnection(), new ExponentialBackoffRetry(
			1000, 3));
	curatorFramework.start();

	context = new StringDiscoveryContext(new ServiceDiscoveryImpl<String>(
		curatorFramework, configuration.getZkBasePath(),
		new JsonInstanceSerializer<String>(String.class), null),
		new RandomStrategy<String>(), 10);

	serviceInstanceMarshaller = new JsonServiceInstanceMarshaller<String>(
		context);
	serviceInstancesMarshaller = new JsonServiceInstancesMarshaller<String>(
		context);
    }

    @Override
    public Set<Class<?>> getClasses() {
	Set<Class<?>> classes = Sets.newHashSet();
	classes.add(StringDiscoveryResource.class);
	return classes;
    }

    @Override
    public Set<Object> getSingletons() {
	Set<Object> singletons = Sets.newHashSet();
	singletons.add(context);
	singletons.add(serviceNamesMarshaller);
	singletons.add(serviceInstanceMarshaller);
	singletons.add(serviceInstancesMarshaller);
	return singletons;
    }

}
