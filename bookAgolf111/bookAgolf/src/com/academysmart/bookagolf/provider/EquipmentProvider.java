package com.academysmart.bookagolf.provider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;

import com.academysmart.bookagolf.config.ApplicationConfigValues;
import com.academysmart.bookagolf.encryption.GolfApi;
import com.academysmart.bookagolf.logging.Loggers;
import com.academysmart.bookagolf.model.Equipment;

public enum EquipmentProvider {
	INSTANCE;

	private Map<Integer, Equipment> equipments;

	private EquipmentProvider() {
		GolfApi golfApi;
		equipments = new HashMap<Integer, Equipment>();
		try {
			golfApi = GolfApiProvider.getGolfApi();
			List<Equipment> equipmentList = new ArrayList<>();
			equipmentList = golfApi
					.getClubEquipment(ApplicationConfigValues.CLUB_ID);
			for (Equipment equipment : equipmentList) {
				equipments.put((int) equipment.getId(), equipment);
			}
		} catch (Exception e) {
			// System.err.println("Something's wrong with API");
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Unable to load equipment", e);
		}
	}

	public List<Equipment> getEquipments() {
		return new ArrayList<Equipment>(equipments.values());
	}

	public Equipment getEquipment(int id) {
		return equipments.get(id);
	}

	public void refreshEquipments() {
		try {
			List<Equipment> equipmentList = new ArrayList<>();
			equipmentList = GolfApiProvider.getGolfApi().getClubEquipment(
					ApplicationConfigValues.CLUB_ID);
			Map<Integer, Equipment> newEquipments = new HashMap<Integer, Equipment>();
			for (Equipment equipment : equipmentList) {
				newEquipments.put((int) equipment.getId(), equipment);
			}
			equipments = newEquipments;
		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			// System.out.println("Equipments refresh failed!");
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Equipments refresh failed!", e);
		}
	}

	public void addEquipment(Equipment equipment) {
		if (!GolfApiProvider.getGolfApi().addClubEquipment(
				ApplicationConfigValues.CLUB_ID, equipment)) {
			LocalStorage.getInstance().addEquipmentToAdd(equipment);
			// System.out.println("Unable to add equipment via Api");
			Loggers.FILE_LOGGER.warn("Unable to add equipment via Api");
		}
		equipments.put((int) equipment.getId(), equipment);
	}

	public void updateEquipment(Equipment equipment) {
		GolfApi golfApi;
		golfApi = GolfApiProvider.getGolfApi();
		if (!golfApi.updateClubEquipment(ApplicationConfigValues.CLUB_ID,
				equipment)) {
			LocalStorage.getInstance().addEquipmentToUpdate(equipment);
			// System.out.println("Unable to update equipment");
			Loggers.FILE_LOGGER.warn("Unable to update equipment");
		}
	}

	public void deleteEquipment(long id) {
		Equipment equipment = equipments.remove((int) id);
		if (!GolfApiProvider.getGolfApi().deleteClubEquipment(
				ApplicationConfigValues.CLUB_ID, (int) equipment.getId())) {
			LocalStorage.getInstance().addEquipmentToDelete(equipment);
			// System.out.println("Unable to delete equipment via Api");
			Loggers.FILE_LOGGER.warn("Unable to delete equipment via Api");
		}

	}

}
