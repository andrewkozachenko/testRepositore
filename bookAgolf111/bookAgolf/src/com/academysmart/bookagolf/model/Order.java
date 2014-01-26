package com.academysmart.bookagolf.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.academysmart.bookagolf.config.ApplicationConfigValues;
import com.academysmart.bookagolf.i18n.Messages;
import com.academysmart.bookagolf.logging.Loggers;
import com.academysmart.bookagolf.provider.EquipmentProvider;
import com.academysmart.bookagolf.provider.FieldProvider;
import com.academysmart.bookagolf.provider.UserProvider;

/**
 * Orders – several games that were bought by client
 * 
 */
public class Order {

	public static final int STATUS_PENDING = 0;
	public static final int STATUS_DECLINED_BY_USER = 1;
	public static final int STATUS_CONFIRMED = 2;
	public static final int STATUS_APPROVED = 3;
	public static final int STATUS_DECLINED_BY_CLUB = 4;

	public static final Map<Integer, String> STATUS_MAP = new HashMap<Integer, String>();
	static {
		STATUS_MAP.put(STATUS_PENDING, Messages.Order_StatusPending);
		STATUS_MAP.put(STATUS_CONFIRMED, Messages.Order_StatusConfirmed);
		STATUS_MAP.put(STATUS_APPROVED, Messages.Order_StatusApproved);
		STATUS_MAP.put(STATUS_DECLINED_BY_CLUB,
				Messages.Order_StatusDeclinedByClub);
		STATUS_MAP.put(STATUS_DECLINED_BY_USER,
				Messages.Order_StatusDeclinedByUser);
	}

	private Long id;
	private Long userId;
	private Double price;
	private Integer status;
	private List<EquipmentSetItem> equipmentSet;
	private String createdAt;
	private String updatedAt;
	private Long clubId;
	private String date;
	private String identifier;
	private Double paid;
	private Double equipmentPrice;
	private Boolean site;
	// private List<Game> orderedGames;
	private List<OrderedGame> orderedGames;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the user_id
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param user_id
	 *            the user_id to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the equipmentSet
	 */
	public List<EquipmentSetItem> getEquipmentSet() {
		return equipmentSet;
	}

	/**
	 * @param equipmentSet
	 *            the equipmentSet to set
	 */
	public void setEquipmentSet(List<EquipmentSetItem> equipmentSet) {
		this.equipmentSet = equipmentSet;
	}

	/**
	 * @return the createdAt
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public String getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt
	 *            the updatedAt to set
	 */
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the clubId
	 */
	public long getClubId() {
		return clubId;
	}

	/**
	 * @param clubId
	 *            the clubId to set
	 */
	public void setClubId(long clubId) {
		this.clubId = clubId;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the paid
	 */
	public double getPaid() {
		return paid;
	}

	/**
	 * @param paid
	 *            the paid to set
	 */
	public void setPaid(double paid) {
		this.paid = paid;
	}

	/**
	 * @return the equipmentPrice
	 */
	public double getEquipmentPrice() {
		return equipmentPrice;
	}

	/**
	 * @param equipmentPrice
	 *            the equipmentPrice to set
	 */
	public void setEquipmentPrice(double equipmentPrice) {
		this.equipmentPrice = equipmentPrice;
	}

	/**
	 * @return the site
	 */
	public boolean isSite() {
		return site;
	}

	/**
	 * @param site
	 *            the site to set
	 */
	public void setSite(boolean site) {
		this.site = site;
	}

	/**
	 * @return the orderedGames
	 */
	public List<OrderedGame> getOrderedGames() {
		return orderedGames;
	}

	/**
	 * @param orderedGames
	 *            the orderedGames to set
	 */
	public void setOrderedGames(List<OrderedGame> orderedGames) {
		this.orderedGames = orderedGames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// String equipmentSet = " ";
		// for (EquipmentSetItem item : this.equipmentSet) {
		// equipmentSet += item.toString();
		// }
		return "Order [id=" + id + ", userId=" + userId + ", price=" + price //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ ", status=" + status + ", equipmentSet=" + equipmentSet //$NON-NLS-1$ //$NON-NLS-2$
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt //$NON-NLS-1$ //$NON-NLS-2$
				+ ", clubId=" + clubId + ", date=" + date + ", identifier=" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ identifier + ", paid=" + paid + ", equipmentPrice=" //$NON-NLS-1$ //$NON-NLS-2$
				+ equipmentPrice + ", site=" + site + ", orderedGames=" //$NON-NLS-1$ //$NON-NLS-2$
				+ orderedGames + "]"; //$NON-NLS-1$
	}

	public int getPlayersNumber() {
		if ((orderedGames != null) && !orderedGames.isEmpty()) {
			return orderedGames.get(0).getPlayers();
		}
		return 0;
	}

	public String getGameDateTime() {
		int hour = 0;
		if ((orderedGames != null) && !orderedGames.isEmpty()) {
			// hour = orderedGames.get(0).getGameStart();
			hour = orderedGames.get(0).getStart();
		}
		String hourString = ""; //$NON-NLS-1$
		if (hour < 10) {
			hourString += "0"; //$NON-NLS-1$
		}
		hourString += hour + ":00"; //$NON-NLS-1$
		return date + " " + hourString; //$NON-NLS-1$
	}

	public String getInfo() {
		SimpleDateFormat createdAtFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm"); //$NON-NLS-1$
		Date createdAt = null;
		try {
			createdAt = ApplicationConfigValues.API_DATE_TIME_FORMAT
					.parse(getCreatedAt());
		} catch (ParseException e) {
			createdAt = new Date();
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}

		String info = Messages.Order_id + ": " //$NON-NLS-2$ //$NON-NLS-1$
				+ getIdentifier() + "\n" //$NON-NLS-1$
				+ Messages.Order_user + ": " //$NON-NLS-1$
				+ UserProvider.getUser(getUserId()).getFullName() + "\n" //$NON-NLS-1$
				+ Messages.Order_price + ": " //$NON-NLS-1$
				+ getPrice() + "\n" //$NON-NLS-1$
				+ Messages.Order_paid + ": " //$NON-NLS-1$
				+ getPaid() + "\n" //$NON-NLS-1$
				+ Messages.Order_equipmentPrice + ":  " //$NON-NLS-1$
				+ getEquipmentPrice() + "\n" //$NON-NLS-1$
				+ Messages.Order_createdAt + ": " //$NON-NLS-1$
				+ createdAtFormat.format(createdAt) + "\n" //$NON-NLS-1$
				+ Messages.Order_date + ": " //$NON-NLS-1$
				+ getDate() + "\n" //$NON-NLS-1$
				+ Messages.Order_status + ": " //$NON-NLS-1$
				// + ((getStatus() == Order.STATUS_CONFIRMED) ?
				// Messages.Order_confirmed
				// : Messages.Order_pending);
				+ STATUS_MAP.get(getStatus());
		if (getOrderedGames() != null) {
			info += "\n" + Messages.Order_orderedGames + ": "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			for (OrderedGame game : getOrderedGames()) {
				info += "\n\t" //$NON-NLS-1$
						+ Messages.Order_golfField
						+ ": " //$NON-NLS-1$
						+ FieldProvider.INSTANCE.getFieldById(
								game.getGolfFieldId()).getName()
						+ "\n\t" //$NON-NLS-1$
						+ Messages.Order_start
						+ ": " + (game.getStart() < 10 ? "0" : "") //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
						+ game.getStart()
						+ ":00\n\t" + Messages.Order_end + ": " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						+ (game.getEnd() < 10 ? "0" : "") //$NON-NLS-1$ //$NON-NLS-2$
						+ game.getEnd() + ":00"; //$NON-NLS-1$
			}
		}

		if (getEquipmentSet() != null) {
			info += "\n" + Messages.Order_additionalEquipment + ": "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			for (EquipmentSetItem item : getEquipmentSet()) {
				Equipment equipment = null;
				if (item.getId() != null) {
					equipment = EquipmentProvider.INSTANCE.getEquipment(item
							.getId().intValue());

					info += "\n\t" + Messages.Order_equipment + ": " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							+ (equipment != null ? equipment.getName() : "") //$NON-NLS-1$
							+ "\n\t" + Messages.Order_amount + ": " + item.getAmount(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
			}
		}
		return info;
	}

}
