package com.academysmart.bookagolf.provider;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.academysmart.bookagolf.encryption.GolfApi;
import com.academysmart.bookagolf.logging.Loggers;

public class GolfApiProvider {

	private static GolfApi golfApi = null;

	private GolfApiProvider() {
	}

	public static GolfApi getGolfApi() {
		if (golfApi == null) {
			try {
				golfApi = new GolfApi();
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
				Loggers.FILE_LOGGER.error("Error occured.", e);
				// e.printStackTrace();
			}
		}
		return golfApi;
	}
}
