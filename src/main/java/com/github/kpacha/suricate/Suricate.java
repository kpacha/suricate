package com.github.kpacha.suricate;

public class Suricate {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
	Configuration config = new Configuration(args);
	JettyServer jetty = new JettyServer(new Application(config),
		config.getPort());
	jetty.run();
    }

}
