package com.mapinfopage.client.rpc;

import java.sql.Timestamp;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mapinfopage.shared.LocationInformationDO;
import com.mapinfopage.shared.MessageDO;

public interface MessageServiceAsync{
	
	/**
	 * This method is called when a user clicks a marker on the map.
	 * The method will be passed the markerId that was clicked. 
	 * Using the markerId the server will return  list
	 * of information that will be displayed on the table.
	 * @param location
	 */
	public void userClickedMarkerOnMap(int days, String markerId, AsyncCallback<LocationInformationDO> callback);
	
	
	/**
	 * This method is called when the user moves the slider below the map. The method will be passed in the days
	 * that was selected using the slider.
	 * 
	 * The method will return MessageDO which will have two list of information.
	 * 1) The list of marker that needs to be updated on the map
	 * 2) the list of information that needs to be update on the table
	 * 
	 * @param days
	 */
	public void slider(int days, String markerId, AsyncCallback<MessageDO> callback);
	
	
	/**
	 * The method is called when the web page refreshed itself regularly.
	 * It will pass in the days that was last selected through the slider (if the slider was never moved
	 * then it will just send the default value). It will also send the markerId that was last selected by the user
	 * on the map. If the user had never selected a markerId then the markerId will be null.
	 * 
	 * The method will return MessageDO which will have two list of information.
	 * 1) The list of marker that needs to be updated on the map
	 * 2) the list of information that needs to be update on the table
	 * 
	 * @param days
	 * @param markerId
	 */
	public void refresh(int days, String markerId, Timestamp lastTweetTimestamp, AsyncCallback<MessageDO> callback);

}
