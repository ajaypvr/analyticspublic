package com.mapinfopage.shared;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * It will hold the information that will be displayed on the table on the web page.
 */
public class TableInformationDO implements Serializable{
	
	
	////////////////////////////////////////////////////////////
	// PRIVATE CLASS VARIABLES
	////////////////////////////////////////////////////////////
	private static final long serialVersionUID = 2426922656672972125L;

	
	////////////////////////////////////////////////////////////
	// PRIVATE INSTANCE VARIABLES
	////////////////////////////////////////////////////////////
	private Timestamp timeCreated;
	private String text;
	private String imageURL;
	private String userName;

	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Timestamp getTimeCreated() {
		return timeCreated;
	}
	public void setTimeCreated(Timestamp timeCreated) {
		this.timeCreated = timeCreated;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}


}
