package com.mapinfopage.server.dao;

import java.sql.Timestamp;
import java.util.List;

import com.mapinfopage.shared.DestinationTweetCount;
import com.mapinfopage.shared.LatitudeLongitude;
import com.mapinfopage.shared.TweetStatusDO;


public interface VacationDAO {
	
	public List<TweetStatusDO> getTweetStatusGreaterThanTweetId(long tweetId);
	
	public List<TweetStatusDO> getTweetStatusGreaterThanTimestamp(Timestamp timestamp);
	
	public List<DestinationTweetCount> getDestinationTweetCount(int days);
	
	
	public List<TweetStatusDO> getTweetsSinceLastUpdate(String location, Timestamp lastTweetTimestamp);
	
	public List<TweetStatusDO> getTweetsSinceLastUpdate(Timestamp lastTweetTimestamp);
	
	public LatitudeLongitude getLatitudeLongitude(String location);
	

}
