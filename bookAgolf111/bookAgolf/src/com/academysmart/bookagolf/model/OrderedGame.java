package com.academysmart.bookagolf.model;

public class OrderedGame {
	private Long id;
	private Long gameId;
	private Long orderId;
	private Integer start;
	private Integer end;
	private Integer players;
	private Double price;
	private Integer holes;
	private String createdAt;
	private String updatedAt;
	private Long golfFieldId;

	public OrderedGame() {
		// default constructor
	}

	public OrderedGame(Game game) {
		gameId = game.getId();
		start = game.getGameStart();
		end = game.getGameEnd();
		players = game.getPlayers();
		price = game.getPrice();
		holes = game.getHoles();
		golfFieldId = game.getGolfFieldId();
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
	 * @return the gameId
	 */
	public Long getGameId() {
		return gameId;
	}

	/**
	 * @param gameId
	 *            the gameId to set
	 */
	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	/**
	 * @return the orderId
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the start
	 */
	public Integer getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(Integer start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public Integer getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(Integer end) {
		this.end = end;
	}

	/**
	 * @return the players
	 */
	public Integer getPlayers() {
		return players;
	}

	/**
	 * @param players
	 *            the players to set
	 */
	public void setPlayers(Integer players) {
		this.players = players;
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
	 * @return the golfFieldId
	 */
	public Long getGolfFieldId() {
		return golfFieldId;
	}

	/**
	 * @param golfFieldId
	 *            the golfFieldId to set
	 */
	public void setGolfFieldId(Long golfFieldId) {
		this.golfFieldId = golfFieldId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrderedGame [id=" + id + ", gameId=" + gameId + ", orderId="
				+ orderId + ", start=" + start + ", end=" + end + ", players="
				+ players + ", price=" + price + ", holes=" + holes
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", golfFieldId=" + golfFieldId + "]";
	}

}
