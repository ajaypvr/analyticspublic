package com.mapinfopage.shared;

import java.io.Serializable;
import java.util.List;



public class VacationDO implements Serializable{
	
	private List<TweetStatusDO> tweetStatusList;
	
	private List<DestinationTweetCount> destinationTweetCount;

	public List<TweetStatusDO> getTweetStatusList() {
		return tweetStatusList;
	}

	public void setTweetStatusList(List<TweetStatusDO> tweetStatusList) {
		this.tweetStatusList = tweetStatusList;
	}

	public List<DestinationTweetCount> getDestinationTweetCount() {
		return destinationTweetCount;
	}

	public void setDestinationTweetCount(List<DestinationTweetCount> destinationTweetCount) {
		this.destinationTweetCount = destinationTweetCount;
	}
	
	
	
	

}
