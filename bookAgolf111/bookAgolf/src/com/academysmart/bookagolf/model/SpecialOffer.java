package com.academysmart.bookagolf.model;

/**
 * Special offers
 * 
 */
public class SpecialOffer {
	private Long id;
	private String title;
	private String description;
	private Long clubId;
	private String imageFileName;
	private String imageContentType;
	private Long imageFileSize;
	private String imageUpdatedAt;
	private Boolean important;
	private String createdAt;
	private String updatedAt;

	// @SerializedName("image")
	private String linkToImage;

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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the imageFileName
	 */
	public String getImageFileName() {
		return imageFileName;
	}

	/**
	 * @param imageFileName
	 *            the imageFileName to set
	 */
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	/**
	 * @return the imageContentType
	 */
	public String getImageContentType() {
		return imageContentType;
	}

	/**
	 * @param imageContentType
	 *            the imageContentType to set
	 */
	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	/**
	 * @return the imageFileSize
	 */
	public long getImageFileSize() {
		return imageFileSize;
	}

	/**
	 * @param imageFileSize
	 *            the imageFileSize to set
	 */
	public void setImageFileSize(long imageFileSize) {
		this.imageFileSize = imageFileSize;
	}

	/**
	 * @return the imageUpdatedAt
	 */
	public String getImageUpdatedAt() {
		return imageUpdatedAt;
	}

	/**
	 * @param imageUpdatedAt
	 *            the imageUpdatedAt to set
	 */
	public void setImageUpdatedAt(String imageUpdatedAt) {
		this.imageUpdatedAt = imageUpdatedAt;
	}

	/**
	 * @return the important
	 */
	public boolean isImportant() {
		return important;
	}

	/**
	 * @param important
	 *            the important to set
	 */
	public void setImportant(boolean important) {
		this.important = important;
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
	 * @return the linkToImage
	 */
	public String getLinkToImage() {
		return linkToImage;
	}

	/**
	 * @param linkToImage
	 *            the linkToImage to set
	 */
	public void setLinkToImage(String linkToImage) {
		this.linkToImage = linkToImage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SpecialOffer [id=" + id + ", title=" + title + ", description="
				+ description + ", clubId=" + clubId + ", imageFileName="
				+ imageFileName + ", imageContentType=" + imageContentType
				+ ", imageFileSize=" + imageFileSize + ", imageUpdatedAt="
				+ imageUpdatedAt + ", important=" + important + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}
}
