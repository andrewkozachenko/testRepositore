package com.academysmart.bookagolf.provider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;

import com.academysmart.bookagolf.config.ApplicationConfigValues;
import com.academysmart.bookagolf.encryption.GolfApi;
import com.academysmart.bookagolf.logging.Loggers;
import com.academysmart.bookagolf.model.Image;

public enum ImageProvider {
	INSTANCE;

	private List<Image> images;

	private ImageProvider() {
		GolfApi golfApi;
		try {
			golfApi = GolfApiProvider.getGolfApi();
			images = golfApi.getImages(ApplicationConfigValues.CLUB_ID);
		} catch (Exception e) {
			// System.err.println("Unable to get images");
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Unable to get images", e);
			images = new ArrayList<Image>();
		}
	}

	public List<Image> getImages() {
		return images;
	}

	public void addImage(String pathToImage) {
		String imageName = pathToImage.substring(
				pathToImage.lastIndexOf("/") + 1, pathToImage.length());
		GolfApi golfApi = GolfApiProvider.getGolfApi();
		if (!golfApi.addImage(ApplicationConfigValues.CLUB_ID, imageName,
				pathToImage)) {
			// TODO Add image to local storage
			// System.out.println("Unable to add image via Api");
			Loggers.FILE_LOGGER.warn("Unable to add image via Api");
		}
	}

	public void refreshImages() {
		try {
			List<Image> newImages = GolfApiProvider.getGolfApi().getImages(
					ApplicationConfigValues.CLUB_ID);
			images = newImages;
		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			// System.out.println("Images refresh failed!");
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Images refresh failed!",e);
		}
	}
}