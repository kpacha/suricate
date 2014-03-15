package com.github.kpacha.suricate;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.jersey.spi.container.servlet.ServletContainer;

public class JettyServer {

    private int port;
    private Application app;
    private Server server;

    public JettyServer(Application app, int port) {
	this.app = app;
	this.port = port;
    }

    public void run() throws Exception {
	ServletContainer container = new ServletContainer(app);

	server = new Server(port);

	ServletContextHandler context = new ServletContextHandler(
		ServletContextHandler.SESSIONS);
	context.setContextPath("/");
	server.setHandler(context);
	context.addServlet(new ServletHolder(container), "/*");

	server.start();
	server.join();
    }

    public void shutdown() {
	try {
	    server.stop();
	    server.destroy();
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

}
