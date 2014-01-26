package com.academysmart.bookagolf.provider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;

import com.academysmart.bookagolf.config.ApplicationConfigValues;
import com.academysmart.bookagolf.encryption.BCAESEncrypter;
import com.academysmart.bookagolf.logging.Loggers;
import com.academysmart.bookagolf.model.Equipment;
import com.academysmart.bookagolf.model.Game;
import com.academysmart.bookagolf.model.Order;
import com.academysmart.bookagolf.model.OrderToSend;
import com.google.gson.Gson;

public class LocalStorage {
	private List<Order> ordersToAdd;
	private List<Order> ordersToUpdate;
	private List<Game> gamesToAdd;
	private List<Game> gamesToUpdate;
	private List<Game> gamesToDelete;
	private List<Equipment> equipmentToAdd;
	private List<Equipment> equipmentToUpdate;
	private List<Equipment> equipmentToDelete;
	private static LocalStorage instance = null;

	private LocalStorage() {

		ordersToAdd = new ArrayList<Order>();
		ordersToUpdate = new ArrayList<Order>();
		gamesToAdd = new ArrayList<Game>();
		gamesToUpdate = new ArrayList<Game>();
		gamesToDelete = new ArrayList<Game>();
		equipmentToAdd = new ArrayList<Equipment>();
		equipmentToUpdate = new ArrayList<Equipment>();
		equipmentToDelete = new ArrayList<Equipment>();

		// mock Orders
		// for (int i = 0; i < 300; i++) {
		// Order order = new Order();
		// order.setClubId(ApplicationConfigValues.CLUB_ID);
		// order.setCreatedAt(ApplicationConfigValues.API_DATE_TIME_FORMAT
		// .format(new Date()));
		// order.setDate("2013-08-1" + String.valueOf(i % 10));
		// order.setEquipmentPrice(52.25);
		// List<EquipmentSetItem> equipmentSet = new
		// ArrayList<EquipmentSetItem>();
		// EquipmentSetItem equipmentSetItem = new EquipmentSetItem();
		// equipmentSetItem.setId(8);
		// equipmentSetItem.setAmount(2);
		// equipmentSet.add(equipmentSetItem);
		// order.setEquipmentSet(equipmentSet);
		// order.setIdentifier("ident4");
		// order.setPaid(143);
		// order.setPrice(321);
		// order.setSite(false);
		// order.setStatus(Order.STATUS_CONFIRMED);
		// order.setUserId(i);
		// order.setId(219);
		// ordersToUpdate.add(order);
		// }
		// Order order = new Order();
		// order.setClubId(ApplicationConfigValues.CLUB_ID);
		// order.setCreatedAt(ApplicationConfigValues.API_DATE_TIME_FORMAT
		// .format(new Date()));
		// order.setDate("2013-08-12");
		// // order.setEquipmentPrice(52.25);
		// List<EquipmentSetItem> equipmentSet = new
		// ArrayList<EquipmentSetItem>();
		// // EquipmentSetItem equipmentSetItem = new EquipmentSetItem();
		// // equipmentSetItem.setId(8);
		// // equipmentSetItem.setAmount(2);
		// // equipmentSet.add(equipmentSetItem);
		// order.setEquipmentSet(equipmentSet);
		// // order.setIdentifier("ident4");
		// // order.setPaid(143);
		// order.setPrice(321);
		// // order.setSite(false);
		// order.setStatus(Order.STATUS_PENDING);
		// order.setUserId(33);
		// order.setId(216);
		// ordersToUpdate.add(order);

	}

	public static LocalStorage getInstance() {
		if (instance == null) {
			instance = new LocalStorage();
		}

		return instance;
	}

	/**
	 * @return the ordersToAdd
	 */
	public List<Order> getOrdersToAdd() {
		return ordersToAdd;
	}

	/**
	 * @return the ordersToUpdate
	 */
	public List<Order> getOrdersToUpdate() {
		return ordersToUpdate;
	}

	/**
	 * @return the gamesToAdd
	 */
	public List<Game> getGamesToAdd() {
		return gamesToAdd;
	}

	/**
	 * @return the gamesToUpdate
	 */
	public List<Game> getGamesToUpdate() {
		return gamesToUpdate;
	}

	/**
	 * @return the gamesToDelete
	 */
	public List<Game> getGamesToDelete() {
		return gamesToDelete;
	}

	/**
	 * @return the equipmentToAdd
	 */
	public List<Equipment> getEquipmentToAdd() {
		return equipmentToAdd;
	}

	/**
	 * @return the equipmentToUpdate
	 */
	public List<Equipment> getEquipmentToUpdate() {
		return equipmentToUpdate;
	}

	/**
	 * @return the equipmentToDelete
	 */
	public List<Equipment> getEquipmentToDelete() {
		return equipmentToDelete;
	}

	public void addOrderToAdd(Order order) {
		ordersToAdd.add(order);
		saveDataToFile();
	}

	public void addOrderToUpdate(Order order) {
		ordersToUpdate.add(order);
		saveDataToFile();
	}

	public void addGameToAdd(Game game) {
		gamesToAdd.add(game);
		saveDataToFile();
	}

	public void addGameToUpdate(Game game) {
		gamesToUpdate.add(game);
		saveDataToFile();
	}

	public void addGameToDelete(Game game) {
		gamesToDelete.add(game);
		saveDataToFile();
	}

	public void addEquipmentToAdd(Equipment equipment) {
		equipmentToAdd.add(equipment);
		saveDataToFile();
	}

	public void addEquipmentToUpdate(Equipment equipment) {
		equipmentToUpdate.add(equipment);
		saveDataToFile();
	}

	public void addEquipmentToDelete(Equipment equipment) {
		equipmentToDelete.add(equipment);
		saveDataToFile();
	}

	public void saveDataToFile() {
		File localStorageFile = new File(
				ApplicationConfigValues.LOCAL_STORAGE_FILEPATH);
		FileWriter writer;

		try {
			BCAESEncrypter encrypter = new BCAESEncrypter(
					ApplicationConfigValues.SECRET_KEY);
			writer = new FileWriter(localStorageFile);
			writer.append(encrypter.encrypt(toJson()));
			writer.flush();
			writer.close();

		} catch (IOException | NoSuchAlgorithmException | DataLengthException
				| IllegalStateException | InvalidCipherTextException e) {
			// System.out.println("Unable to save local storage file");
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Unable to save local storage file", e);
		}
		// System.out.println("Local storage saved");
		Loggers.FILE_LOGGER.info("Local storage saved");
		// System.out.println("Data: " + toJson());
	}

	public void loadDataFromFile() {
		String result = "";
		File localStorageFile = new File(
				ApplicationConfigValues.LOCAL_STORAGE_FILEPATH);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					localStorageFile));
			BCAESEncrypter encrypter = new BCAESEncrypter(
					ApplicationConfigValues.SECRET_KEY);
			String lineFromFile;
			while ((lineFromFile = reader.readLine()) != null) {
				result += lineFromFile;
			}
			reader.close();
			LocalStorage localStorageFromFile = fromJson(encrypter.decrypt(
					result).trim());
			instance.gamesToAdd = localStorageFromFile.gamesToAdd;
			instance.ordersToAdd = localStorageFromFile.ordersToAdd;
			instance.ordersToUpdate = localStorageFromFile.ordersToUpdate;
			instance.gamesToUpdate = localStorageFromFile.gamesToUpdate;
			instance.gamesToDelete = localStorageFromFile.gamesToDelete;
			instance.equipmentToAdd = localStorageFromFile.equipmentToAdd;
			instance.equipmentToUpdate = localStorageFromFile.equipmentToUpdate;
			instance.equipmentToDelete = localStorageFromFile.equipmentToDelete;

		} catch (FileNotFoundException e) {
			saveDataToFile();
		} catch (IOException | NoSuchAlgorithmException | DataLengthException
				| IllegalStateException | InvalidCipherTextException e) {
			// System.out.println("Unable to load local storage file");
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Unable to load local storage file", e);
		}
	}

	private String toJson() {
		Gson gson = new Gson();
		return gson.toJson(instance);
	}

	private LocalStorage fromJson(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, getClass());
	}

	public void updateViaApi() {

		Iterator<Order> ordersToAddIterator = ordersToAdd.iterator();
		while (ordersToAddIterator.hasNext()) {
			Order order = ordersToAddIterator.next();

			OrderToSend orderToSend = new OrderToSend(order);

			if (GolfApiProvider.getGolfApi().addOrderToSend(
					ApplicationConfigValues.CLUB_ID, orderToSend,
					order.getOrderedGames())) {
				ordersToAddIterator.remove();
			}
		}

		Iterator<Order> ordersToUpdateIterator = ordersToUpdate.iterator();
		while (ordersToUpdateIterator.hasNext()) {
			Order order = ordersToUpdateIterator.next();
			if (GolfApiProvider.getGolfApi().updateOrder(
					ApplicationConfigValues.CLUB_ID, order)) {

				ordersToUpdateIterator.remove();

			}
		}

		Iterator<Game> gamesToAddIterator = gamesToAdd.iterator();
		while (gamesToAddIterator.hasNext()) {
			Game game = gamesToAddIterator.next();

			if (GolfApiProvider.getGolfApi().addGame(
					ApplicationConfigValues.CLUB_ID,
					(int) game.getGolfFieldId(), game)) {
				gamesToAddIterator.remove();
			}
		}

		Iterator<Game> gamesToUpdateIterator = gamesToUpdate.iterator();
		while (gamesToUpdateIterator.hasNext()) {
			Game game = gamesToUpdateIterator.next();

			if (GolfApiProvider.getGolfApi().updateGame(
					ApplicationConfigValues.CLUB_ID,
					(int) game.getGolfFieldId(), game)) {
				gamesToUpdateIterator.remove();
			}
		}

		Iterator<Game> gamesToDeleteIterator = gamesToDelete.iterator();
		while (gamesToDeleteIterator.hasNext()) {
			Game game = gamesToDeleteIterator.next();

			if (GolfApiProvider.getGolfApi().deleteGame(
					ApplicationConfigValues.CLUB_ID,
					(int) game.getGolfFieldId(), (int) game.getId())) {
				gamesToDeleteIterator.remove();
			}
		}

		Iterator<Equipment> equipmentToAddIterator = equipmentToAdd.iterator();
		while (equipmentToAddIterator.hasNext()) {
			Equipment equipment = equipmentToAddIterator.next();

			if (GolfApiProvider.getGolfApi().addClubEquipment(
					ApplicationConfigValues.CLUB_ID, equipment)) {
				equipmentToAddIterator.remove();
			}
		}

		Iterator<Equipment> equipmentToUpdateIterator = equipmentToUpdate
				.iterator();
		while (equipmentToUpdateIterator.hasNext()) {
			Equipment equipment = equipmentToUpdateIterator.next();

			if (GolfApiProvider.getGolfApi().updateClubEquipment(
					ApplicationConfigValues.CLUB_ID, equipment)) {
				equipmentToUpdateIterator.remove();
			}
		}

		Iterator<Equipment> equipmentToDeleteIterator = equipmentToDelete
				.iterator();
		while (equipmentToDeleteIterator.hasNext()) {
			Equipment equipment = equipmentToDeleteIterator.next();

			if (GolfApiProvider.getGolfApi().deleteClubEquipment(
					ApplicationConfigValues.CLUB_ID, (int) equipment.getId())) {
				equipmentToDeleteIterator.remove();
			}
		}

		saveDataToFile();
	}

	public static void main(String[] args) {
		LocalStorage localStorage = LocalStorage.getInstance();
		// localStorage.saveDataToFile();
		localStorage.loadDataFromFile();
		// localStorage = LocalStorage.getInstance();
		System.out.println(localStorage.toJson());
		// localStorage.updateViaApi();
		// System.out.println(localStorage.toJson());
		// instance = localStorage.fromJson(localStorage.toJson());
		// System.out.println("New json:");
		// localStorage.saveDataToFile();
	}
}
