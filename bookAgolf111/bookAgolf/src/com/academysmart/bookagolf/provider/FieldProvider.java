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
import com.academysmart.bookagolf.model.Field;

public enum FieldProvider {
	INSTANCE;

	private List<Field> fields;

	private FieldProvider() {
		GolfApi golfApi;
		try {
			golfApi = GolfApiProvider.getGolfApi();
			fields = golfApi.getClubFields(ApplicationConfigValues.CLUB_ID);
		} catch (Exception e) {
			// System.err.println("Unable to get fields via API");
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Unable to get fields via API",e);
			fields = new ArrayList<Field>();
		}
	}

	public List<Field> getFields() {
		return fields;
	}

	public Field getFieldById(long id) {
		for (Field field : fields) {
			if (field.getId() == id) {
				return field;
			}
		}
		return null;
	}

	public void refreshFields() {
		List<Field> newFields;
		try {
			newFields = GolfApiProvider.getGolfApi().getClubFields(
					ApplicationConfigValues.CLUB_ID);
			fields = newFields;
		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			// System.out.println("Unable to refresh fields!");
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Unable to refresh fields!",e);
		}

	}

	public String[] getAllFieldNames() {
		String[] fieldsNamesArray = new String[fields.size()];
		int i = 0;
		for (Field field : fields) {
			fieldsNamesArray[i] = field.getName();
			i++;
		}
		return fieldsNamesArray;
	}
}
