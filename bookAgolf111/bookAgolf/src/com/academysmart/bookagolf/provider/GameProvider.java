package com.academysmart.bookagolf.provider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import com.academysmart.bookagolf.config.ApplicationConfigValues;
import com.academysmart.bookagolf.encryption.GolfApi;
import com.academysmart.bookagolf.logging.Loggers;
import com.academysmart.bookagolf.model.Field;
import com.academysmart.bookagolf.model.Game;

public enum GameProvider {
	INSTANCE;
	private List<Game> games;
	// private String clubName;
	private Map<Integer, String> fieldNames;
	private GolfApi golfApi;

	private GameProvider() {

		games = new ArrayList<Game>();
		fieldNames = new HashMap<>();
		try {
			golfApi = GolfApiProvider.getGolfApi();
			List<Field> fields = FieldProvider.INSTANCE.getFields();
			for (Field field : fields) {
				fieldNames.put((int) field.getId(), field.getName());
				List<Game> gamesOnField = golfApi.getGames(
						ApplicationConfigValues.CLUB_ID, (int) field.getId());
				if (gamesOnField != null) {
					games.addAll(gamesOnField);
				}

			}
		} catch (Exception e) {
			// System.err.println("Unable to get games via API");
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Unable to get games via API", e);
		}

	}

	public List<Game> getGames() {
		return games;
	}

	public String getFieldName(int id) {
		return fieldNames.get(id);
	}

	public void addGame(Game game) {

		if (!golfApi.addGame(ApplicationConfigValues.CLUB_ID,
				(int) game.getGolfFieldId(), game)) {
			LocalStorage.getInstance().addGameToAdd(game);
			// System.out.println("Unable to add game via Api");
			Loggers.FILE_LOGGER.warn("Unable to add game via Api");
		}
		games.add(game);
	}

	public void updateGame(Game game) {
		GolfApi golfApi;
		golfApi = GolfApiProvider.getGolfApi();
		if (!golfApi.updateGame(ApplicationConfigValues.CLUB_ID,
				(int) game.getGolfFieldId(), game)) {
			LocalStorage.getInstance().addGameToUpdate(game);
			// System.out.println("Unable to update game");
			Loggers.FILE_LOGGER.warn("Unable to update game");
		}
	}

	public void deleteGame(long id) {
		Game game = getGameById(id);
		if (game != null) {
			deleteGame(game);
		}
	}

	public void deleteGame(Game game) {
		if (!golfApi.deleteGame(ApplicationConfigValues.CLUB_ID,
				(int) game.getGolfFieldId(), (int) game.getId())) {
			LocalStorage.getInstance().addGameToDelete(game);
			// System.out.println("Unable to delete game via Api");
			Loggers.FILE_LOGGER.warn("Unable to delete game via Api");
		}
		games.remove(game);
	}

	public Game getGameById(long id) {
		for (Game game : games) {
			if (game.getId() == id) {
				return game;
			}
		}
		return null;
	}

	public List<Game> getGamesOnField(long fieldId) {
		List<Game> gamesField = new ArrayList<Game>();
		for (Game game : games) {
			if (game.getGolfFieldId() == fieldId) {
				gamesField.add(game);
			}
		}
		return gamesField;
	}

	public List<Game> getGamesAtDateTime(Date date, int timeStart, int timeEnd) {
		return getGamesOnFieldAtDateTime(null, date, timeStart, timeEnd);
	}

	public List<Game> getGamesOnFieldAtDateTime(Long fieldId, Date date,
			int timeStart, int timeEnd) {
		List<Game> gamesToCheck = fieldId == null ? this.games
				: getGamesOnField(fieldId);
		DateTime dateTime = new DateTime(date);
		List<Game> gamesAtDate = new ArrayList<Game>();
		for (Game game : gamesToCheck) {
			boolean isGameToAdd = false;
			if (game.getGameEnd() < timeStart || game.getGameStart() > timeEnd) {
				continue;
			}
			switch (dateTime.getDayOfWeek()) {
			case DateTimeConstants.MONDAY:
				isGameToAdd = game.isMon();
				break;
			case DateTimeConstants.TUESDAY:
				isGameToAdd = game.isTue();
				break;
			case DateTimeConstants.WEDNESDAY:
				isGameToAdd = game.isWed();
				break;
			case DateTimeConstants.THURSDAY:
				isGameToAdd = game.isThu();
				break;
			case DateTimeConstants.FRIDAY:
				isGameToAdd = game.isFri();
				break;
			case DateTimeConstants.SATURDAY:
				isGameToAdd = game.isSat();
				break;
			case DateTimeConstants.SUNDAY:
				isGameToAdd = game.isSun();
				break;
			default:
				isGameToAdd = false;
				break;
			}
			if (isGameToAdd) {
				gamesAtDate.add(game);
			}
		}
		return gamesAtDate;
	}

	public void refreshGames() {
		try {
			List<Game> newGames = new ArrayList<Game>();
			golfApi = GolfApiProvider.getGolfApi();
			List<Field> fields = FieldProvider.INSTANCE.getFields();
			for (Field field : fields) {
				// fieldNames.put((int) field.getId(), field.getName());
				List<Game> gamesOnField;

				gamesOnField = golfApi.getGames(
						ApplicationConfigValues.CLUB_ID, (int) field.getId());

				if (gamesOnField != null) {
					newGames.addAll(gamesOnField);
				}
			}
			games = newGames;
		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			Loggers.FILE_LOGGER.error("Error occured.", e);
			// e.printStackTrace();
		}
	}
}