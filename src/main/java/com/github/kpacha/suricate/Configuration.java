package com.github.kpacha.suricate;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

public class Configuration {
    private static final int DEFAULT_JETTY_PORT = 8080;
    private static final int DEFAULT_INSTANCE_CLEANUP = 15000;
    private static final String DEFAULT_ZK_BASE_PATH = "/suricate/service-directory";
    private static final String DEFAULT_ZK_CONNECTION = "127.0.0.1:2181";
    private static Logger logger = Logger.getLogger(Configuration.class);

    private CommandLine cmd;
    private int jettyPort;
    private int instanceCleanup;
    private String zkConnection;
    private String zkBasePath;

    public Configuration(String[] args) throws ParseException {
	initCmdLine(args);
	jettyPort = getValueOrDefault("p", DEFAULT_JETTY_PORT);
	zkConnection = getValueOrDefault("c", DEFAULT_ZK_CONNECTION);
	zkBasePath = getValueOrDefault("b", DEFAULT_ZK_BASE_PATH);
	instanceCleanup = getValueOrDefault("t", DEFAULT_INSTANCE_CLEANUP);
    }

    public int getPort() {
	return jettyPort;
    }

    public String getZkConnection() {
	return zkConnection;
    }

    public String getZkBasePath() {
	return zkBasePath;
    }

    public int getInstanceCleanupMillis() {
	return instanceCleanup;
    }

    private void initCmdLine(String[] args) throws ParseException {
	CommandLineParser parser = new GnuParser();
	Options options = getOptions();
	cmd = parser.parse(options, args);
    }

    private Options getOptions() {
	Options options = new Options();

	options.addOption("p", true, "jetty port (default '"
		+ DEFAULT_JETTY_PORT + "')");
	options.addOption("t", true, "instance cleanup time (default '"
		+ DEFAULT_INSTANCE_CLEANUP + "')");
	options.addOption("c", true, "zookeeper connection string (default '"
		+ DEFAULT_ZK_CONNECTION + "')");
	options.addOption("b", true, "zookeeper base path (default '"
		+ DEFAULT_ZK_BASE_PATH + "')");

	return options;
    }

    private String getValueOrDefault(String paramName, String defaultValue) {
	String paramValue;
	if (cmd.hasOption("m")) {
	    paramValue = cmd.getOptionValue(paramName);
	} else {
	    paramValue = defaultValue;
	}
	logger.info("Param [" + paramName + "]: " + paramValue);
	return paramValue;
    }

    private int getValueOrDefault(String paramName, int defaultValue) {
	int paramValue;
	if (cmd.hasOption("m")) {
	    paramValue = Integer.parseInt(cmd.getOptionValue(paramName));
	} else {
	    paramValue = defaultValue;
	}
	logger.info("Param [" + paramName + "]: " + paramValue);
	return paramValue;
    }

}
