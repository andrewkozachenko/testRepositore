package com.academysmart.bookagolf.provider;

import com.academysmart.bookagolf.config.ApplicationConfigValues;
import com.academysmart.bookagolf.encryption.GolfApi;
import com.academysmart.bookagolf.logging.Loggers;
import com.academysmart.bookagolf.model.Club;

public class ClubProvider {
	private static Club club = null;

	private ClubProvider() {
	}

	public static Club getClub() {
		if (club == null) {
			try {
				// GolfApi golfApi = new GolfApi();
				GolfApi golfApi = GolfApiProvider.getGolfApi();
				club = golfApi.getClub(ApplicationConfigValues.CLUB_ID);
			} catch (Exception e) {
				// e.printStackTrace();
				Loggers.FILE_LOGGER.error("Error occured.", e);
			}
		}
		return club;
	}

	public static void updateClub() {

		GolfApi golfApi = GolfApiProvider.getGolfApi();
		if (!golfApi.updateClub(ApplicationConfigValues.CLUB_ID, club)) {
			// TODO put updated club info to inner storage
			// System.out.println("Unable to update club info!");
			Loggers.FILE_LOGGER.warn("Unable to update club info!");
		}

	}
}
