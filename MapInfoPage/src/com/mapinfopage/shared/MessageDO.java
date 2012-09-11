package com.mapinfopage.shared;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * Container class to hold a list of map marker information
 * and table information that should be updated on the screen.
 */
public class MessageDO implements Serializable{
	
	
	////////////////////////////////////////////////////////////
	// PRIVATE CLASS VARIABLES
	////////////////////////////////////////////////////////////
	private static final long serialVersionUID = -2575969892003675656L;

	
	////////////////////////////////////////////////////////////
	// PRIVATE INSTANCE VARIABLES
	////////////////////////////////////////////////////////////
	private List<TableInformationDO> tableInformationList;
	private List<MapInformationDO> mapInformationList;
	private Timestamp maxTimestampOftheTweets;

	
	////////////////////////////////////////////////////////////
	// GETTER/SETTER METHODS
	////////////////////////////////////////////////////////////
	public List<TableInformationDO> getTableInformationList() {
		return tableInformationList;
	}
	public void setTableInformationList(List<TableInformationDO> tableInformationList) {
		this.tableInformationList = tableInformationList;
	}
	public List<MapInformationDO> getMapInformationList() {
		return mapInformationList;
	}
	public void setMapInformationList(List<MapInformationDO> mapInformationList) {
		this.mapInformationList = mapInformationList;
	}
	public Timestamp getMaxTimestampOftheTweets() {
		return maxTimestampOftheTweets;
	}
	public void setMaxTimestampOftheTweets(Timestamp maxTimestampOftheTweets) {
		this.maxTimestampOftheTweets = maxTimestampOftheTweets;
	}
	


}


