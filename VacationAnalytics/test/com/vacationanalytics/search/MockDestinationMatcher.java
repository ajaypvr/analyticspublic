package com.vacationanalytics.search;

public class MockDestinationMatcher implements DestinationMatcher{

	@Override
	public String matchDestination(String tweetText) {
		if(tweetText.contains( "london".subSequence(0, 6))) return "london";
		
		return null;
	}

	

}
