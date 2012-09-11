package com.mapinfopage.shared;

import java.io.Serializable;
import java.util.List;


/**
 * Information about one category of recommendations.
 */
public class Category
implements Serializable {

	
	////////////////////////////////////////////////////////////
	// PRIVATE CLASS VARIABLES
	////////////////////////////////////////////////////////////
	private static final long serialVersionUID = -1195931560725280536L;

	
	////////////////////////////////////////////////////////////
	// PRIVATE INSTANCE VARIABLES
	////////////////////////////////////////////////////////////
	private String name;
	
	private String imageUrl = "http://icons.iconarchive.com/icons/tpdkdesign.net/refresh-cl/256/System-Java-icon.png";
	private List<Recommendation> recommendations;
	
	
	////////////////////////////////////////////////////////////
	// GETTER/SETTER METHODS
	////////////////////////////////////////////////////////////
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public List<Recommendation> getRecommendations() {
		return recommendations;
	}
	public void setRecommendations(List<Recommendation> recommendations) {
		this.recommendations = recommendations;
	}

	
}
