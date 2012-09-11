package com.mapinfopage.shared;

import java.io.Serializable;


/**
 * Information about one recommendation.
 */
public class Recommendation
implements Serializable {

	
	////////////////////////////////////////////////////////////
	// PRIVATE CLASS VARIABLES
	////////////////////////////////////////////////////////////
	private static final long serialVersionUID = -3216870309804277619L;

	
	////////////////////////////////////////////////////////////
	// PRIVATE INSTANCE VARIABLES
	////////////////////////////////////////////////////////////
	private String entityName;
	private String entityUrl;
	private String description;
	private String imageUrl;
	
	
	////////////////////////////////////////////////////////////
	// GETTER/SETTER METHODS
	////////////////////////////////////////////////////////////
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getEntityUrl() {
		return entityUrl;
	}
	public void setEntityUrl(String entityUrl) {
		this.entityUrl = entityUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
}
