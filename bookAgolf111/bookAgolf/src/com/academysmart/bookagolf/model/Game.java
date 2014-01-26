package com.academysmart.bookagolf.model;

/**
 * Games – schedule of games in club that could be bought by client
 * 
 */
public class Game {
	private Long id;
	private Integer players;
	private Double price;
	private Integer gameStart; // time?
	private Integer gameEnd; // time?
	private String createdAt;
	private String updatedAt;
	private Boolean mon;
	private Boolean tue;
	private Boolean wed;
	private Boolean thu;
	private Boolean fri;
	private Boolean sat;
	private Boolean sun;
	private Integer holes;
	private String date;
	private Long golfFieldId;

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
	 * @return the players
	 */
	public int getPlayers() {
		return players;
	}

	/**
	 * @param players
	 *            the players to set
	 */
	public void setPlayers(int players) {
		this.players = players;
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
	 * @return the gameStart
	 */
	public int getGameStart() {
		return gameStart;
	}

	/**
	 * @param gameStart
	 *            the gameStart to set
	 */
	public void setGameStart(int gameStart) {
		this.gameStart = gameStart;
	}

	/**
	 * @return the gameEnd
	 */
	public int getGameEnd() {
		return gameEnd;
	}

	/**
	 * @param gameEnd
	 *            the gameEnd to set
	 */
	public void setGameEnd(int gameEnd) {
		this.gameEnd = gameEnd;
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
	 * @return the mon
	 */
	public boolean isMon() {
		return mon;
	}

	/**
	 * @param mon
	 *            the mon to set
	 */
	public void setMon(boolean mon) {
		this.mon = mon;
	}

	/**
	 * @return the tue
	 */
	public boolean isTue() {
		return tue;
	}

	/**
	 * @param tue
	 *            the tue to set
	 */
	public void setTue(boolean tue) {
		this.tue = tue;
	}

	/**
	 * @return the wed
	 */
	public boolean isWed() {
		return wed;
	}

	/**
	 * @param wed
	 *            the wed to set
	 */
	public void setWed(boolean wed) {
		this.wed = wed;
	}

	/**
	 * @return the thu
	 */
	public boolean isThu() {
		return thu;
	}

	/**
	 * @param thu
	 *            the thu to set
	 */
	public void setThu(boolean thu) {
		this.thu = thu;
	}

	/**
	 * @return the fri
	 */
	public boolean isFri() {
		return fri;
	}

	/**
	 * @param fri
	 *            the fri to set
	 */
	public void setFri(boolean fri) {
		this.fri = fri;
	}

	/**
	 * @return the sat
	 */
	public boolean isSat() {
		return sat;
	}

	/**
	 * @param sat
	 *            the sat to set
	 */
	public void setSat(boolean sat) {
		this.sat = sat;
	}

	/**
	 * @return the sun
	 */
	public boolean isSun() {
		return sun;
	}

	/**
	 * @param sun
	 *            the sun to set
	 */
	public void setSun(boolean sun) {
		this.sun = sun;
	}

	/**
	 * @return the holes
	 */
	public Integer getHoles() {
		return holes;
	}

	/**
	 * @param holes
	 *            the holes to set
	 */
	public void setHoles(Integer holes) {
		this.holes = holes;
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
	 * @return the golfFieldId
	 */
	public long getGolfFieldId() {
		return golfFieldId;
	}

	/**
	 * @param golfFieldId
	 *            the golfFieldId to set
	 */
	public void setGolfFieldId(long golfFieldId) {
		this.golfFieldId = golfFieldId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Game [id=" + id + ", players=" + players + ", price=" + price
				+ ", gameStart=" + gameStart + ", gameEnd=" + gameEnd
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", mon=" + mon + ", tue=" + tue + ", wed=" + wed + ", thu="
				+ thu + ", fri=" + fri + ", sat=" + sat + ", sun=" + sun
				+ ", holes=" + holes + ", date=" + date + ", golfFieldId="
				+ golfFieldId + "]";
	}

}
