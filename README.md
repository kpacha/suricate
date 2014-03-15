suricate
========

A [curator](http://curator.apache.org/getting-started.html) service discovery server implementation

#Requirements

* git
* jdk 1.7
* mvn 3
* zookeeper 3.4.6

#Install

Clone the project and install it!

	$ git clone https://github.com/kpacha/suricate.git
	$ cd suricate
	$ mvn install

And, after testing the code, the suricate-0.0.1-SNAPSHOT.jar should be on your local maven repo

#Environment

Start your ZooKeeper server(s) before running suricate. More details on http://zookeeper.apache.org/doc/trunk/zookeeperStarted.html

#Run with maven

If your ZooKeeper is running in your local machine with the default config, just type one more maven command and done! 

	$ mvn clean compile exec:java

Your service should be waiting for you at port `8080`. Nice, uh?

#Build and Run the fat-jar

So, let's build it for real, deploy it to an actual server and run it. Remember, in production environments, you should run your ZooKeeper services in replicated mode...

	$ mvn clean compile assembly:single
	# ...and you are ready for deploy the fat-jar! (it's placed at `target/`)

	# ... your deployment process here ...

	# start the suricate service
	$ java -jar suricate-0.0.1-SNAPSHOT-full.jar [-p <jetty_port>] [-b <zk_base_path>] [-c <zk_connection_string>]

And you already have a suricate server running!

The options are:

	-p <arg>   jetty port (default '8080')
	-c <arg>   zookeeper connection string (default 'localhost:2181')
	-b <arg>   zookeeper base path (default '/suricate/service-directory')

#Usage

Register your service nodes (hosts and clusters) by sending a PUT request to the [putService](http://curator.apache.org/curator-x-discovery-server/index.html#putService) method

	# Register the host 'ca2fff8e-d756-480c-b59e-8297ff88624b' in the cluster 'test'
	$ curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X PUT -d '{"name": "test", "id": "ca2fff8e-d756-480c-b59e-8297ff88624b", "address": "10.20.30.40", "port": 1234, "payload": "supu", "registrationTimeUTC": 1325129459728, "serviceType": "STATIC"}' http://localhost:8080/v1/service/test/ca2fff8e-d756-480c-b59e-8297ff88624b

Get a list of registered services with the [getAllNames](http://curator.apache.org/curator-x-discovery-server/index.html#getAllNames) method

	$ curl -i http://localhost:8080/v1/service

Get a list of registered nodes in the 'test' service (hosts in a cluster) with the [getAll](http://curator.apache.org/curator-x-discovery-server/index.html#getAll) method

	$ curl -i http://localhost:8080/v1/service/test

Get a registered node in the 'test' service by its id with the [get](http://curator.apache.org/curator-x-discovery-server/index.html#get) method

	$ curl -i http://localhost:8080/v1/service/test/ca2fff8e-d756-480c-b59e-8297ff88624b

Remove a service node with the [removeService](http://curator.apache.org/curator-x-discovery-server/index.html#removeService) method

	$ curl -i -X DELETE http://localhost:8080/v1/service/test/ca2fff8e-d756-480c-b59e-8297ff88624b

Check the [curator-x-discovery-server](http://curator.apache.org/curator-x-discovery-server/index.html) for more details about the Discovery Service and its REST interface.

The REST entities are described in the [curator-x-discovery-server repo](https://github.com/apache/curator/tree/master/curator-x-discovery-server)