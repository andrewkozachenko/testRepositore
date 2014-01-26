package com.academysmart.bookagolf.connection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.academysmart.bookagolf.config.ApplicationConfigValues;
import com.academysmart.bookagolf.encryption.GolfApi;
import com.academysmart.bookagolf.provider.GolfApiProvider;

/**
 * Is used to check connection status
 */
public class Connection {
	/**
	 * Checks connection to address specified in url.
	 * 
	 * @param url
	 * @return true if connection is available
	 */
	public static boolean hasConnection(String url) {
		try {
			final URLConnection connection = new URL(url).openConnection();
			connection.connect();
			// System.out.println("Service " + url + " available, yeah!");
			return true;
		} catch (final MalformedURLException e) {
			throw new IllegalStateException("Bad URL: " + url, e);
		} catch (final IOException e) {
			// System.out.println("Service " + url + " unavailable, oh no!");
			return false;
		}
	}

	/**
	 * Checks connection to http://google.com
	 * 
	 * @return true if connection is available
	 */
	public static boolean hasInternetConnection() {
		return hasConnection("http://google.com");
	}

	/**
	 * Checks connection to http://{@link ApplicationConfigValues#HOST}/api
	 * 
	 * @return true if connection is available
	 */
	public static boolean hasApiConnection() {
		return hasConnection("http://" + ApplicationConfigValues.HOST + "/api");
	}

	/**
	 * Checks authorization, using {@link GolfApi#isAuthorized()} method
	 * 
	 * @return true if authorization is success
	 */
	public static boolean hasAuth() {

		GolfApi golfApi = GolfApiProvider.getGolfApi();
		return golfApi.isAuthorized();

	}

	public static void main(String[] args) throws IOException {
		System.out.println(hasApiConnection());
	}
}
