package com.academysmart.bookagolf.provider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.joda.time.DateTime;

import com.academysmart.bookagolf.config.ApplicationConfigValues;
import com.academysmart.bookagolf.encryption.GolfApi;
import com.academysmart.bookagolf.logging.Loggers;
import com.academysmart.bookagolf.model.Order;
import com.academysmart.bookagolf.model.OrderFilter;
import com.academysmart.bookagolf.model.OrderToSend;

public enum OrderProvider {
	INSTANCE;

	private List<Order> orders;

	private OrderProvider() {
		GolfApi golfApi;
		try {
			golfApi = GolfApiProvider.getGolfApi();
			orders = golfApi.getClubOrders(ApplicationConfigValues.CLUB_ID);
			// mock Orders
			// orders = new ArrayList<Order>();
			// for (int i = 0; i < 10; i++) {
			// Order order = new Order();
			// order.setClubId(ApplicationConfigValues.CLUB_ID);
			// order.setCreatedAt(ApplicationConfigValues.API_DATE_TIME_FORMAT
			// .format(new Date()));
			// order.setDate("2013-09-1" + String.valueOf(i % 10));
			// order.setEquipmentPrice(52.25);
			// List<EquipmentSetItem> equipmentSet = new
			// ArrayList<EquipmentSetItem>();
			// EquipmentSetItem equipmentSetItem = new EquipmentSetItem();
			// equipmentSetItem.setId(8L);
			// equipmentSetItem.setAmount(2);
			// equipmentSet.add(equipmentSetItem);
			// order.setEquipmentSet(equipmentSet);
			// order.setIdentifier("ident4");
			// order.setPaid(143);
			// order.setPrice(321);
			// order.setSite(false);
			// order.setStatus(Order.STATUS_PENDING);
			// order.setUserId(i);
			// order.setId(219);
			// orders.add(order);
			// }
			// Order order = new Order();
			// order.setClubId(ApplicationConfigValues.CLUB_ID);
			// order.setCreatedAt(ApplicationConfigValues.API_DATE_TIME_FORMAT
			// .format(new Date()));
			// order.setDate("2013-09-12");
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
			// orders.add(order);

		} catch (Exception e) {
			// System.err.println("Unable to load orders via API");
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Unable to load orders via API", e);
			orders = new ArrayList<Order>();
		}

	}

	public List<Order> getOrders() {
		return orders;
	}

	public List<Order> getFilteredOrders(OrderFilter filter) {
		List<Order> filteredOrders = new ArrayList<Order>();
		for (Order order : orders) {
			if (filter.select(order)) {
				filteredOrders.add(order);
			}
		}
		return filteredOrders;
	}

	public List<Order> getOrdersAtDate(String dateString) {
		// Find out which date is used here
		List<Order> ordersAtDate = new ArrayList<Order>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatCreatedAt = ApplicationConfigValues.API_DATE_TIME_FORMAT;
		Date dateToCompare = new Date();
		try {
			dateToCompare = format.parse(dateString);
		} catch (ParseException e) {
			Loggers.FILE_LOGGER.error("Error occured.", e);
			// e.printStackTrace();
		}
		DateTime dateTimeToCompare = new DateTime(dateToCompare);
		for (Order order : orders) {
			Date dateOrder = new Date();
			try {
				// dateOrder = format.parse(order.getDate());
				dateOrder = formatCreatedAt.parse(order.getCreatedAt());
			} catch (ParseException e) {
				Loggers.FILE_LOGGER.error("Error occured.", e);
				// e.printStackTrace();
			}
			DateTime dateTimeOrder = new DateTime(dateOrder);
			if ((dateTimeOrder.getYear() == dateTimeToCompare.getYear())
					&& (dateTimeOrder.getMonthOfYear() == dateTimeToCompare
							.getMonthOfYear())
					&& (dateTimeOrder.getDayOfMonth() == dateTimeToCompare
							.getDayOfMonth())) {
				ordersAtDate.add(order);
			}
		}
		return ordersAtDate;
	}

	/**
	 * Not implemented correctly!
	 * 
	 * @param dateString
	 * @param time
	 * @return
	 */
	public List<Order> getOrdersAtDateTime(String dateString, String time) {
		// TODO find out is this time is Created At
		// SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
		SimpleDateFormat formatOrderCreatedAt = ApplicationConfigValues.API_DATE_TIME_FORMAT;

		Date timeToCompare = new Date();
		try {
			timeToCompare = formatTime.parse(time);
		} catch (ParseException e) {
			Loggers.FILE_LOGGER.error("Error occured.", e);
			// e.printStackTrace();
		}
		DateTime dateTimeToCompare = new DateTime(timeToCompare);

		List<Order> ordersAtDateTime = new ArrayList<Order>();
		List<Order> ordersAtDate = getOrdersAtDate(dateString);
		for (Order order : ordersAtDate) {
			Date timeOrder = new Date();
			try {
				timeOrder = formatOrderCreatedAt.parse(order.getCreatedAt());
			} catch (ParseException e) {
				Loggers.FILE_LOGGER.error("Error occured.", e);
				// e.printStackTrace();
			}
			DateTime dateTimeOrder = new DateTime(timeOrder);
			int minutesBetweenTwoTimes = dateTimeOrder.getMinuteOfHour()
					- dateTimeToCompare.getMinuteOfHour();
			if ((dateTimeOrder.getHourOfDay() == dateTimeToCompare
					.getHourOfDay())
					// && (minutesBetweenTwoTimes >= -5)
					// && (minutesBetweenTwoTimes < 5)) {
					&& (minutesBetweenTwoTimes >= 0)
					&& (minutesBetweenTwoTimes < 10)) {

				ordersAtDateTime.add(order);
			}
		}
		return ordersAtDateTime;
	}

	public Date getMinOrderDate() {
		List<Date> orderDates = new ArrayList<Date>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (Order order : orders) {
			try {
				orderDates.add(format.parse(order.getDate()));
			} catch (ParseException e) {
//				e.printStackTrace();
				Loggers.FILE_LOGGER.error("Error occured.",e);
			}
		}
		Date minDate;
		if (!orderDates.isEmpty()) {
			minDate = Collections.min(orderDates);
		} else {
			minDate = new Date();
		}
		return minDate;
	}

	public Date getMaxOrderDate() {
		List<Date> orderDates = new ArrayList<Date>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (Order order : orders) {
			try {
				orderDates.add(format.parse(order.getDate()));
			} catch (ParseException e) {
//				e.printStackTrace();
				Loggers.FILE_LOGGER.error("Error occured.",e);
			}
		}
		Date maxDate;
		if (!orderDates.isEmpty()) {
			maxDate = Collections.max(orderDates);
		} else {
			maxDate = new Date();
		}
		return maxDate;
	}

	public int getNumberOfNewOrders() {
		int numberOfNewOrders = 0;
		for (Order order : orders) {
			if (order.getStatus() != Order.STATUS_CONFIRMED
					&& order.getStatus() != Order.STATUS_APPROVED) {
				numberOfNewOrders++;
			}
		}
		return numberOfNewOrders;
	}

	public Order getOrderById(long id) {
		for (Order order : orders) {
			if (order.getId() == id) {
				return order;
			}
		}
		return null;
	}

	public void updateOrder(Order order) {
		GolfApi golfApi;
		golfApi = GolfApiProvider.getGolfApi();
		if (!golfApi.updateOrder(ApplicationConfigValues.CLUB_ID, order)) {
			LocalStorage.getInstance().addOrderToUpdate(order);
			// System.out.println("Unable to update order");
			Loggers.FILE_LOGGER.warn("Unable to update order");
		}

	}

	public void updateOrderStatus(Order order) {
		GolfApi golfApi;
		golfApi = GolfApiProvider.getGolfApi();
		Order orderToChange = new Order();
		orderToChange.setId(order.getId());
		orderToChange.setStatus(order.getStatus());
		if (!golfApi
				.updateOrder(ApplicationConfigValues.CLUB_ID, orderToChange)) {
			LocalStorage.getInstance().addOrderToUpdate(orderToChange);
			// System.out.println("Unable to update order");
			Loggers.FILE_LOGGER.warn("Unable to update order");
		}
	}

	public void addOrder(Order order) {
		GolfApi golfApi;
		golfApi = GolfApiProvider.getGolfApi();
		OrderToSend orderToSend = new OrderToSend(order);
		if (!golfApi.addOrderToSend(ApplicationConfigValues.CLUB_ID,
				orderToSend, order.getOrderedGames())) {
			LocalStorage.getInstance().addOrderToAdd(order);
			// System.out.println("Unable to add order via Api");
			Loggers.FILE_LOGGER.warn("Unable to add order via Api");
		}
		orders.add(order);
	}

	// public void addPeriodicalMockOrder() {
	// Order order = new Order();
	// order.setClubId(ApplicationConfigValues.CLUB_ID);
	// order.setCreatedAt(ApplicationConfigValues.API_DATE_TIME_FORMAT
	// .format(new Date()));
	// order.setDate("2013-08-12");
	// // order.setEquipmentPrice(52.25);
	// List<EquipmentSetItem> equipmentSet = new ArrayList<EquipmentSetItem>();
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
	// orders.add(order);
	//
	// }

	public void refreshOrders() {
		try {
			List<Order> newOrders = GolfApiProvider.getGolfApi().getClubOrders(
					ApplicationConfigValues.CLUB_ID);
			orders = newOrders;
		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			// System.out.println("Orders refresh failed!");
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Orders refresh failed!", e);
		}
	}
}
