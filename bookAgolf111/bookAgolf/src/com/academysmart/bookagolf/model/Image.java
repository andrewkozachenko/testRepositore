package com.academysmart.bookagolf.model;

/**
 * Gallery of images
 * 
 */
public class Image {
	private Long id;
	private String name;
	private Long galleryId;
	private String createdAt;
	private String updatedAt;
	private String pathFileName;
	private String pathContentType;
	private Long pathFileSize;
	private String pathUpdatedAt;
	private String url;

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
	 * @return the galleryId
	 */
	public long getGalleryId() {
		return galleryId;
	}

	/**
	 * @param galleryId
	 *            the galleryId to set
	 */
	public void setGalleryId(long galleryId) {
		this.galleryId = galleryId;
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
	 * @return the pathFileName
	 */
	public String getPathFileName() {
		return pathFileName;
	}

	/**
	 * @param pathFileName
	 *            the pathFileName to set
	 */
	public void setPathFileName(String pathFileName) {
		this.pathFileName = pathFileName;
	}

	/**
	 * @return the pathContentType
	 */
	public String getPathContentType() {
		return pathContentType;
	}

	/**
	 * @param pathContentType
	 *            the pathContentType to set
	 */
	public void setPathContentType(String pathContentType) {
		this.pathContentType = pathContentType;
	}

	/**
	 * @return the pathFileSize
	 */
	public long getPathFileSize() {
		return pathFileSize;
	}

	/**
	 * @param pathFileSize
	 *            the pathFileSize to set
	 */
	public void setPathFileSize(long pathFileSize) {
		this.pathFileSize = pathFileSize;
	}

	/**
	 * @return the pathUpdatedAt
	 */
	public String getPathUpdatedAt() {
		return pathUpdatedAt;
	}

	/**
	 * @param pathUpdatedAt
	 *            the pathUpdatedAt to set
	 */
	public void setPathUpdatedAt(String pathUpdatedAt) {
		this.pathUpdatedAt = pathUpdatedAt;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Image [id=" + id + ", name=" + name + ", galleryId="
				+ galleryId + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", pathFileName=" + pathFileName
				+ ", pathContentType=" + pathContentType + ", pathFileSize="
				+ pathFileSize + ", pathUpdatedAt=" + pathUpdatedAt + ", url="
				+ url + "]";
	}
}
