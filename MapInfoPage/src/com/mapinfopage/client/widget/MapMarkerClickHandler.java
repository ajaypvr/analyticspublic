package com.mapinfopage.client.widget;


/**
 * Handler interface for trapping clicks on a
 * map marker.
 */
public interface MapMarkerClickHandler {

		
	////////////////////////////////////////////////////////////
	// PUBLIC INTERFACE METHODS
	////////////////////////////////////////////////////////////
	public void markerClicked(Object markerId,
								String markerLabel,
								int clickXCoordinate,
								int clickYCoordinate);


	
}
