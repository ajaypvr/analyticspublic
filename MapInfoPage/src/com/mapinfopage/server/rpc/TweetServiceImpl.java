package com.mapinfopage.server.rpc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xerces.impl.io.Latin1Reader;

import com.mapinfopage.server.dao.VacationDAO;
import com.mapinfopage.shared.DestinationTweetCount;
import com.mapinfopage.shared.TweetStatusDO;
import com.mapinfopage.shared.VacationDO;
import com.mapinfopage.shared.LatitudeLongitude;




public class TweetServiceImpl {

	private static Logger log = Logger.getLogger(TweetServiceImpl.class);
	
	
	private VacationDAO vacationDAO;
	
	public List<TweetStatusDO> getTweetsSinceLastUpdate(String location, Timestamp lastTweetTimestamp){
		List<TweetStatusDO> tweetStatusList;
		
		if(null == location || "".equals(location.trim())){
			
			tweetStatusList = vacationDAO.getTweetsSinceLastUpdate(lastTweetTimestamp);
		}else{
			tweetStatusList = vacationDAO.getTweetsSinceLastUpdate(location, lastTweetTimestamp);
		}
		
		
		return tweetStatusList;
		
	}
	
	public List<TweetStatusDO> getUpdatedTweetsForPreviousDays(int days, String location) {
		log.debug("BEGIN getUpdatedVacationDO");
		
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.add(Calendar.DAY_OF_YEAR, -days);
		Timestamp timetamp = new Timestamp(currentCalendar.getTimeInMillis());
		List<TweetStatusDO> tweetStatusList;
		
		if(null == location || "".equals(location.trim())){
			
			tweetStatusList = vacationDAO.getTweetsSinceLastUpdate(timetamp);
		}else{
			tweetStatusList = vacationDAO.getTweetsSinceLastUpdate(location, timetamp);
		}
		
		
		//sort the tweet based on timestamp and return the top 4 tweets
		Collections.sort(tweetStatusList, new TimestampComparator());
		
		List<TweetStatusDO> resultList = new ArrayList<TweetStatusDO>();
		
		int maxNumberOfResult = 10;
		
		
		for(int i = tweetStatusList.size() - 1; i >tweetStatusList.size() - maxNumberOfResult && i >=0; i-- ){
			resultList.add(tweetStatusList.get(i));
		}
		
		return resultList;
	}
	

	public List<DestinationTweetCount> getDestinationTweetCount(int days) {
		log.debug("BEGIN getDestinationTweetCount");

		
		List<DestinationTweetCount> destinationTweetCountList = vacationDAO.getDestinationTweetCount(days);
		
		
		
		return destinationTweetCountList;
	}
	
	
	public LatitudeLongitude getLatitudeLongitude(String location) {
		log.info("BEGIN getLatitudeLongitude: location = " + location);
		
		LatitudeLongitude latitudeLongitude = vacationDAO.getLatitudeLongitude(location);
		
		return latitudeLongitude;
	}
	
	
	

	
	public class TimestampComparator implements Comparator<TweetStatusDO>{
		

		public int compare(TweetStatusDO first, TweetStatusDO second) {
			
			Timestamp timestampFirst = first.getTimeCreated();
			Timestamp timestampSecond = second.getTimeCreated();
			
			if(timestampFirst == null || timestampSecond == null)
				return 0;
			else
				return timestampFirst.compareTo(timestampSecond);
		}
		
	}


	public void setVacationDAO(VacationDAO vacationDAO) {
		this.vacationDAO = vacationDAO;
	}
	
	

	
	
}


	
