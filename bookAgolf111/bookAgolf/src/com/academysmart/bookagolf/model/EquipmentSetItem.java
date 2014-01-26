package com.academysmart.bookagolf.model;

/**
 * equipment that was bought for an ordered game.
 * 
 */
public class EquipmentSetItem {
	private Long id;
	private Integer amount;

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
	 * @return the amount
	 */
	public int getAmount() {
		return amount != null ? amount : 0;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EquipmentSetItem [id=" + id + ", amount=" + amount + "]";
	}
}
