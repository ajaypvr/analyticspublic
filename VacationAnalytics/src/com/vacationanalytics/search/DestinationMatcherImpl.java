package com.vacationanalytics.search;

/**
 * It does matching of the destination and if a match is found then add the destination information
 * @author ajaymangalam
 *
 */
public class DestinationMatcherImpl {

	SearchLocation searchLocation;
	
	/**
	 * For the given tweet it find if the location name exist within the tweet text.
	 * @param tweetText
	 * @return
	 */
	public String matchDestination(String tweetText){
		
		return searchLocation.searchLocation(tweetText);
		
	}

	public void setSearchLocation(SearchLocation searchLocation) {
		this.searchLocation = searchLocation;
	}

}
