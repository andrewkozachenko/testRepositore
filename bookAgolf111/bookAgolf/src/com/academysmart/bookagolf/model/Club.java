package com.academysmart.bookagolf.model;

import com.google.gson.annotations.SerializedName;

/**
 * Clubs – main resource, contains several fields.
 *
 */
public class Club {
	private Long id;
	private String name;
	private Long categoryId;
	private String country;
	private String worldRegion;
	private String region;
	private String city;
	private String administrativeCenter;
	private String eMail;
	private String metaTitle;
	private String metaDescription;
	private String metaKeywords;
	private String url;
	private String description;
	private Integer holesList;
	private Integer hitsToWin;
	private String createdAt; 
	private String updatedAt; 
	private String shortDescription;
	private String zip;
	private String phone;
	private String court;
	private Boolean visible;
	private Boolean car;
	private String amenities;
	private String address;
	@SerializedName("private")
	private Boolean _private;
	private String currency;
	private Double lat;
	private Double lng;

	/**
	 * @return the id
	 */
	public long getId() {
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the categoryId
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the worldRegion
	 */
	public String getWorldRegion() {
		return worldRegion;
	}

	/**
	 * @param worldRegion
	 *            the worldRegion to set
	 */
	public void setWorldRegion(String worldRegion) {
		this.worldRegion = worldRegion;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region
	 *            the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the administrativeCenter
	 */
	public String getAdministrativeCenter() {
		return administrativeCenter;
	}

	/**
	 * @param administrativeCenter
	 *            the administrativeCenter to set
	 */
	public void setAdministrativeCenter(String administrativeCenter) {
		this.administrativeCenter = administrativeCenter;
	}

	/**
	 * @return the eMail
	 */
	public String geteMail() {
		return eMail;
	}

	/**
	 * @param eMail
	 *            the eMail to set
	 */
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	/**
	 * @return the metaTitle
	 */
	public String getMetaTitle() {
		return metaTitle;
	}

	/**
	 * @param metaTitle
	 *            the metaTitle to set
	 */
	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}

	/**
	 * @return the metaDescription
	 */
	public String getMetaDescription() {
		return metaDescription;
	}

	/**
	 * @param metaDescription
	 *            the metaDescription to set
	 */
	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	/**
	 * @return the metaKeywords
	 */
	public String getMetaKeywords() {
		return metaKeywords;
	}

	/**
	 * @param metaKeywords
	 *            the metaKeywords to set
	 */
	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the holesList
	 */
	public int getHolesList() {
		return holesList!=null?holesList:0;
	}

	/**
	 * @param holesList
	 *            the holesList to set
	 */
	public void setHolesList(int holesList) {
		this.holesList = holesList;
	}

	/**
	 * @return the hitsToWin
	 */
	public int getHitsToWin() {
		return hitsToWin!=null?hitsToWin:0;
	}

	/**
	 * @param hitsToWin
	 *            the hitsToWin to set
	 */
	public void setHitsToWin(int hitsToWin) {
		this.hitsToWin = hitsToWin;
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
	 * @return the shortDscription
	 */
	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * @param shortDscription
	 *            the shortDscription to set
	 */
	public void setShortDescription(String shortDscription) {
		this.shortDescription = shortDscription;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip
	 *            the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the court
	 */
	public String getCourt() {
		return court;
	}

	/**
	 * @param court
	 *            the court to set
	 */
	public void setCourt(String court) {
		this.court = court;
	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible!=null?visible:false;
	}

	/**
	 * @param visible
	 *            the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return the car
	 */
	public boolean isCar() {
		return car!=null?car:false;
	}

	/**
	 * @param car
	 *            the car to set
	 */
	public void setCar(boolean car) {
		this.car = car;
	}

	/**
	 * @return the amenities
	 */
	public String getAmenities() {
		return amenities;
	}

	/**
	 * @param amenities
	 *            the amenities to set
	 */
	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the _private
	 */
	public boolean is_private() {
		return _private!=null?_private:false;
	}

	/**
	 * @param _private
	 *            the _private to set
	 */
	public void set_private(boolean _private) {
		this._private = _private;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat!=null?lat:0;
	}

	/**
	 * @param lat
	 *            the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @return the lng
	 */
	public double getLng() {
		return lng!=null?lng:0;
	}

	/**
	 * @param lng
	 *            the lng to set
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Club [id=" + id + ", name=" + name + ", categoryId="
				+ categoryId + ", country=" + country + ", worldRegion="
				+ worldRegion + ", region=" + region + ", city=" + city
				+ ", administrativeCenter=" + administrativeCenter + ", eMail="
				+ eMail + ", metaTitle=" + metaTitle + ", metaDescription="
				+ metaDescription + ", metaKeywords=" + metaKeywords + ", url="
				+ url + ", description=" + description + ", holesList="
				+ holesList + ", hitsToWin=" + hitsToWin + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", shortDescription="
				+ shortDescription + ", zip=" + zip + ", phone=" + phone
				+ ", court=" + court + ", visible=" + visible + ", car=" + car
				+ ", amenities=" + amenities + ", address=" + address
				+ ", _private=" + _private + ", currency=" + currency
				+ ", lat=" + lat + ", lng=" + lng + "]";
	}

}
