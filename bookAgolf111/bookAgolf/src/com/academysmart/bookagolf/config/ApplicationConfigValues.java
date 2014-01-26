package com.academysmart.bookagolf.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Properties;

import com.academysmart.bookagolf.logging.Loggers;

/**
 * Is used to store configuration values used in application
 * 
 */
public class ApplicationConfigValues {

	/**
	 * API host
	 */
	public static String HOST;
	/**
	 * email is used for authorization in API
	 */
	public static String EMAIL;
	/**
	 * password is used for authorization in API
	 */
	public static String PASSWORD;
	/**
	 * secret key is used in encryption algorithm
	 */
	public static String SECRET_KEY;
	/**
	 * id of golf club where application is installed
	 */
	public static int CLUB_ID;
	/**
	 * application user id
	 */
	public static int USER_ID;
	/**
	 * Date time format used in API fields (e.g. CreatedAt)
	 */
	public static SimpleDateFormat API_DATE_TIME_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	/**
	 * Data update period in milliseconds
	 */
	public static int UPDATE_INTERVAL = 1000 * 60 * 5;
	// public static int UPDATE_INTERVAL = 1000 * 30;
	/**
	 * Path to file where data that couldn't be updated via API is stored
	 */
	public static String LOCAL_STORAGE_FILEPATH;
	/**
	 * Path to properties file where {@link #HOST}, {@link #EMAIL},
	 * {@link #PASSWORD}, {@link #SECRET_KEY}, {@link #CLUB_ID} and
	 * {@link #USER_ID} are stored
	 */
	public static String CONFIG_FILEPATH;

	/**
	 * Loads values from properties file specified in {@link #CONFIG_FILEPATH}
	 * and assign them to {@link #HOST}, {@link #EMAIL}, {@link #PASSWORD},
	 * {@link #SECRET_KEY}, {@link #CLUB_ID} and {@link #USER_ID}
	 */
	public static void loadConfigProperties() {
		Properties configProperties = new Properties();
		try {
			System.setProperty(
					"com.academysmart.bookagolf.logging.logfile",
					URLDecoder.decode(ClassLoader.getSystemClassLoader()
							.getResource(".").getPath()
							+ "logs/app.log", "UTF-8"));

			LOCAL_STORAGE_FILEPATH = URLDecoder.decode(ClassLoader
					.getSystemClassLoader().getResource(".").getPath()
					+ "localstorage", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			Loggers.FILE_LOGGER.error("Error occured.", e1);
			// e1.printStackTrace();
		}
		try {
			CONFIG_FILEPATH = URLDecoder.decode(ClassLoader
					.getSystemClassLoader().getResource(".").getPath()
					+ "config.properties", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			Loggers.FILE_LOGGER.error("Error occured.", e1);
			// e1.printStackTrace();
		}

		try {

			configProperties.load(new FileInputStream(CONFIG_FILEPATH));

			HOST = configProperties.getProperty("host");
			EMAIL = configProperties.getProperty("email");
			PASSWORD = configProperties.getProperty("password");
			SECRET_KEY = configProperties.getProperty("secretkey");
			CLUB_ID = Integer.parseInt(configProperties.getProperty("clubid"));
			USER_ID = Integer.parseInt(configProperties.getProperty("userid"));
		} catch (IOException e) {
			// System.err.println("Unable to load properties");
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Unable to load properties", e);
		}
	}

	/**
	 * Saves {@link #HOST}, {@link #EMAIL}, {@link #PASSWORD},
	 * {@link #SECRET_KEY}, {@link #CLUB_ID} and {@link #USER_ID} values to
	 * properties file specified in {@link #CONFIG_FILEPATH}
	 */
	public static void saveConfigProperties() {
		Properties configProperties = new Properties();

		try {
			configProperties.setProperty("clubid", String.valueOf(CLUB_ID));
			configProperties.setProperty("userid", String.valueOf(USER_ID));
			configProperties.setProperty("host", HOST);
			configProperties.setProperty("email", EMAIL);
			configProperties.setProperty("password", PASSWORD);
			configProperties.setProperty("secretkey", SECRET_KEY);
			configProperties.store(new FileOutputStream(CONFIG_FILEPATH), null);
		} catch (FileNotFoundException e) {
			Loggers.FILE_LOGGER.error("Error occured.", e);
			// e.printStackTrace();
		} catch (IOException e) {
			Loggers.FILE_LOGGER.error("Error occured.", e);
			// e.printStackTrace();
		}
	}
}
