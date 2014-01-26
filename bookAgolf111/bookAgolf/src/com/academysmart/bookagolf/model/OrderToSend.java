package com.academysmart.bookagolf.model;

import java.lang.reflect.Modifier;
import java.util.ArrayList;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * <p>
 * Orders – several games that were bought by client
 * </p>
 * <p>
 * This class is used to avoid problems with API. This class' fields are almost
 * the same as fields in {@link Order}. The difference is that equipment_set and
 * ordered_games fields of this class' instance encoded to JSON, contain strings
 * made of JSON arrays instead of arrays themselves
 * </p>
 */
//TODO add order validation before sending
public class OrderToSend {

	private Long id;
	private Long userId;
	private Double price;
	private Integer status;
	private String createdAt;
	private String updatedAt;
	private Long clubId;
	private String date;
	private String identifier;
	private Double paid;
	private Double equipmentPrice;
	private Boolean site;
	@SerializedName("equipment_set")
	private String equipmentSetString;

	public OrderToSend(Order order) {
		setClubId(order.getClubId());
		setCreatedAt(order.getCreatedAt());
		setDate(order.getDate());
		setEquipmentPrice(order.getEquipmentPrice());
		setIdentifier(order.getIdentifier());
		setId(order.getId());
		setPaid(order.getPaid());
		setPrice(order.getPrice());
		setSite(order.isSite());
		setStatus(order.getStatus());
		setUpdatedAt(order.getUpdatedAt());
		setUserId(order.getUserId());

		Gson gson = new GsonBuilder()
				.excludeFieldsWithModifiers(Modifier.STATIC)
				.setFieldNamingPolicy(
						FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		if (order.getEquipmentSet() == null) {
			order.setEquipmentSet(new ArrayList<EquipmentSetItem>());
		}
		equipmentSetString = gson.toJson(order.getEquipmentSet());
	}

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
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	public Long getClubId() {
		return clubId;
	}

	/**
	 * @param clubId
	 *            the clubId to set
	 */
	public void setClubId(Long clubId) {
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
	public Double getPaid() {
		return paid;
	}

	/**
	 * @param paid
	 *            the paid to set
	 */
	public void setPaid(Double paid) {
		this.paid = paid;
	}

	/**
	 * @return the equipmentPrice
	 */
	public Double getEquipmentPrice() {
		return equipmentPrice;
	}

	/**
	 * @param equipmentPrice
	 *            the equipmentPrice to set
	 */
	public void setEquipmentPrice(Double equipmentPrice) {
		this.equipmentPrice = equipmentPrice;
	}

	/**
	 * @return the site
	 */
	public Boolean getSite() {
		return site;
	}

	/**
	 * @param site
	 *            the site to set
	 */
	public void setSite(Boolean site) {
		this.site = site;
	}

	/**
	 * @return the equipmentSet
	 */
	public String getEquipmentSetString() {
		return equipmentSetString;
	}

	/**
	 * @param equipmentSet
	 *            the equipmentSet to set
	 */
	public void setEquipmentSet(String equipmentSet) {
		this.equipmentSetString = equipmentSet;
	}

}
