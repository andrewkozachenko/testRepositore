package com.academysmart.bookagolf.logging;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Loggers {
	static {
		PropertyConfigurator.configure(Loggers.class
				.getResourceAsStream("log4j.properties"));
	}
	// public final static Logger DEBUG_LOGGER = Logger.getRootLogger();
	public final static Logger FILE_LOGGER = Logger.getLogger("logfile");

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// DEBUG_LOGGER.info("Hi Logger info!");
		// FILE_LOGGER.warn("logfile write!");
		// FILE_LOGGER.debug("debugMessage");
		// System.out.println("LOG.equals(localLog) is " +
		// DEBUG_LOGGER.equals(FILE_LOGGER));

	}

}
