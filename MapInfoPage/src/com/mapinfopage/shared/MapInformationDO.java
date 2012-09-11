package com.mapinfopage.shared;

import java.io.Serializable;


/**
 * It will hold information that will be displayed on the map
 */
public class MapInformationDO implements Serializable{

	
	////////////////////////////////////////////////////////////
	// PRIVATE CLASS VARIABLES
	////////////////////////////////////////////////////////////
	private static final long serialVersionUID = 5011657141818418283L;

	
	////////////////////////////////////////////////////////////
	// PRIVATE INSTANCE VARIABLES
	////////////////////////////////////////////////////////////
	private String markerId;
	private String displayText;
	private double longitude;
	private double latitude;

	
	////////////////////////////////////////////////////////////
	// GETTER/SETTER METHODS
	////////////////////////////////////////////////////////////
	public String getMarkerId() {
		return markerId;
	}
	public void setMarkerId(String markerId) {
		this.markerId = markerId;
	}
	public String getDisplayText() {
		return displayText;
	}
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	
}
