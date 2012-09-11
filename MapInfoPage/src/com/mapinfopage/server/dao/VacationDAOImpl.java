package com.mapinfopage.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.mapinfopage.shared.DestinationTweetCount;
import com.mapinfopage.shared.LatitudeLongitude;
import com.mapinfopage.shared.TweetStatusDO;


public class VacationDAOImpl implements VacationDAO{
	
	private String schema;
	
	private JdbcTemplate jdbcTemplate;
	
	private static final String TWEET_DEST_VALID_TABLE = "tweet_dest_valid";
	
	private static final String LOCATION_GEOCODE_MAP_TABLE = "location_geocode_map";

	public List<TweetStatusDO> getTweetStatusGreaterThanTimestamp(Timestamp timestamp) {
		List<TweetStatusDO> tweetStatuList = new ArrayList<TweetStatusDO>();
		
		String query = "select TWEET_ID, USER_ID, TWEET_TEXT, USER_LOCATION, USER_NAME, TIME_CREATED, VACATION_DESTINATION, PROFILE_IMAGE_URL" +
						" from " + schema + "." + TWEET_DEST_VALID_TABLE +
						" where TIME_CREATED > "  + timestamp;
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query);
		
		for(Map<String, Object> resultMap : resultList){
			TweetStatusDO tweetStatus = new TweetStatusDO();
			long tweetId = (Long)resultMap.get("TWEET_ID");
			long userId = (Long)resultMap.get("USER_ID");
			String tweetText = (String)resultMap.get("TWEET_TEXT");
			String userLocation = (String)resultMap.get("USER_LOCATION");
			String userName = (String)resultMap.get("USER_NAME");
			Timestamp timeCreated = (Timestamp)resultMap.get("TIME_CREATED");
			String vacationDestination = (String)resultMap.get("VACATION_DESTINATION");
			String profileImageURL = (String)resultMap.get("PROFILE_IMAGE_URL");
			
			tweetStatus.setTweetId(tweetId);
			tweetStatus.setUserId(userId);
			tweetStatus.setTweetText(tweetText);
			tweetStatus.setUserLocation(userLocation);
			tweetStatus.setUserName(userName);
			tweetStatus.setTimeCreated(timeCreated);
			tweetStatus.setDestination(vacationDestination);
			tweetStatus.setProfileImageURL(profileImageURL);
			
			tweetStatuList.add(tweetStatus);
			
		}
		
		return tweetStatuList;
	}

	public List<TweetStatusDO> getTweetStatusGreaterThanTweetId(long lastTweetId) {
		List<TweetStatusDO> tweetStatuList = new ArrayList<TweetStatusDO>();
		
		String query = "select TWEET_ID, USER_ID, TWEET_TEXT, USER_LOCATION, USER_NAME, TIME_CREATED, VACATION_DESTINATION, PROFILE_IMAGE_URL" +
		" from " + schema + "." + TWEET_DEST_VALID_TABLE +
		" where TWEET_ID > "  + lastTweetId;

		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query);
		
		for(Map<String, Object> resultMap : resultList){
			TweetStatusDO tweetStatus = new TweetStatusDO();
			long tweetId = (Long)resultMap.get("TWEET_ID");
			long userId = (Long)resultMap.get("USER_ID");
			String tweetText = (String)resultMap.get("TWEET_TEXT");
			String userLocation = (String)resultMap.get("USER_LOCATION");
			String userName = (String)resultMap.get("USER_NAME");
			Timestamp timeCreated = (Timestamp)resultMap.get("TIME_CREATED");
			String vacationDestination = (String)resultMap.get("VACATION_DESTINATION");
			String profileImageURL = (String)resultMap.get("PROFILE_IMAGE_URL");
			
			tweetStatus.setTweetId(tweetId);
			tweetStatus.setUserId(userId);
			tweetStatus.setTweetText(tweetText);
			tweetStatus.setUserLocation(userLocation);
			tweetStatus.setUserName(userName);
			tweetStatus.setTimeCreated(timeCreated);
			tweetStatus.setDestination(vacationDestination);
			tweetStatus.setProfileImageURL(profileImageURL);
			
			tweetStatuList.add(tweetStatus);
		}
		
		return tweetStatuList;
	}
	
	/**
	 * Get the count of tweets since the days passed in as parameter
	 */
	public List<DestinationTweetCount> getDestinationTweetCount(int days) {
		
		String query = "SELECT tweet.VACATION_DESTINATION VACATION_DESTINATION, map.LONGITUDE LONGITUDE, map.LATITUDE LATITUDE, COUNT(*) DESTINATION_COUNT from "   + 
						schema + "." + TWEET_DEST_VALID_TABLE + " tweet, "+
						schema + "." + LOCATION_GEOCODE_MAP_TABLE + " map " +
						" where tweet.TIME_CREATED > ?  " +
						" and tweet.VACATION_DESTINATION = map.LOCATION " +
						" group by tweet.VACATION_DESTINATION, map.LONGITUDE, map.LATITUDE";
		
		
		
		Timestamp cutoffTimestamp = getTimestampMinusDays(days);
		List<DestinationTweetCount> result = jdbcTemplate.query(query, new Object[]{cutoffTimestamp}, new DestinationTweetCountRowMapper());
		
		return result;
	}
	
	
	
	@Override
	public List<TweetStatusDO> getTweetsSinceLastUpdate(String location, Timestamp lastTweetTimestamp) {
		List<TweetStatusDO> tweetStatuList = new ArrayList<TweetStatusDO>();
		
		String query = "select TWEET_ID, USER_ID, TWEET_TEXT, USER_LOCATION, USER_NAME, TIME_CREATED, VACATION_DESTINATION, PROFILE_IMAGE_URL" +
		" from " + schema + "." + TWEET_DEST_VALID_TABLE +
		" where TIME_CREATED > ? and VACATION_DESTINATION = ? ";

		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query, new Object[]{lastTweetTimestamp, location});
		
		for(Map<String, Object> resultMap : resultList){
			TweetStatusDO tweetStatus = new TweetStatusDO();
			long tweetId = (Long)resultMap.get("TWEET_ID");
			long userId = (Long)resultMap.get("USER_ID");
			String tweetText = (String)resultMap.get("TWEET_TEXT");
			String userLocation = (String)resultMap.get("USER_LOCATION");
			String userName = (String)resultMap.get("USER_NAME");
			Timestamp timeCreated = (Timestamp)resultMap.get("TIME_CREATED");
			String vacationDestination = (String)resultMap.get("VACATION_DESTINATION");
			String profileImageURL = (String)resultMap.get("PROFILE_IMAGE_URL");
			
			tweetStatus.setTweetId(tweetId);
			tweetStatus.setUserId(userId);
			tweetStatus.setTweetText(tweetText);
			tweetStatus.setUserLocation(userLocation);
			tweetStatus.setUserName(userName);
			tweetStatus.setTimeCreated(timeCreated);
			tweetStatus.setDestination(vacationDestination);
			tweetStatus.setProfileImageURL(profileImageURL);
			
			tweetStatuList.add(tweetStatus);
		}
		
		return tweetStatuList;
	}

	@Override
	public List<TweetStatusDO> getTweetsSinceLastUpdate(Timestamp lastTweetTimestamp) {
		List<TweetStatusDO> tweetStatuList = new ArrayList<TweetStatusDO>();
		
		String query = "select TWEET_ID, USER_ID, TWEET_TEXT, USER_LOCATION, USER_NAME, TIME_CREATED, VACATION_DESTINATION, PROFILE_IMAGE_URL" +
		" from " + schema + "." + TWEET_DEST_VALID_TABLE +
		" where TIME_CREATED > ? " ;

		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query, new Object[]{lastTweetTimestamp});
		
		
		for(Map<String, Object> resultMap : resultList){
			TweetStatusDO tweetStatus = new TweetStatusDO();
			long tweetId = (Long)resultMap.get("TWEET_ID");
			long userId = (Long)resultMap.get("USER_ID");
			String tweetText = (String)resultMap.get("TWEET_TEXT");
			String userLocation = (String)resultMap.get("USER_LOCATION");
			String userName = (String)resultMap.get("USER_NAME");
			Timestamp timeCreated = (Timestamp)resultMap.get("TIME_CREATED");
			String vacationDestination = (String)resultMap.get("VACATION_DESTINATION");
			String profileImageURL = (String)resultMap.get("PROFILE_IMAGE_URL");
			
			tweetStatus.setTweetId(tweetId);
			tweetStatus.setUserId(userId);
			tweetStatus.setTweetText(tweetText);
			tweetStatus.setUserLocation(userLocation);
			tweetStatus.setUserName(userName);
			tweetStatus.setTimeCreated(timeCreated);
			tweetStatus.setDestination(vacationDestination);
			tweetStatus.setProfileImageURL(profileImageURL);
			
			tweetStatuList.add(tweetStatus);
		}
		
		return tweetStatuList;
	}
	
	

	@Override
	public LatitudeLongitude getLatitudeLongitude(String location) {
		String query = "SELECT LOCATION, LONGITUDE, LATITUDE from "   + 
				schema + "." + LOCATION_GEOCODE_MAP_TABLE +
				" where   LOCATION = ? ";

		List<LatitudeLongitude> result = jdbcTemplate.query(query, new Object[]{location}, new LatitudeLongitudeRowMapper());

		//return the first one. If no result found then return null
		if(result.size()>0){
			return result.get(0);
		}
		
		return null;
	}

	private Timestamp getTimestampMinusDays(int days){
		
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.add(Calendar.DATE, -days);
		
		return new Timestamp(currentCalendar.getTimeInMillis());
	}
	
	
	
	public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	public void setSchema(String schema) {
		this.schema = schema;
	}
	
	
	
	public class LatitudeLongitudeRowMapper implements RowMapper<LatitudeLongitude> {

		@Override
		public LatitudeLongitude mapRow(ResultSet rs, int arg1)throws SQLException {
			LatitudeLongitude latLong = new LatitudeLongitude();
			latLong.setLocation(rs.getString("LOCATION"));
			latLong.setLatitude(rs.getDouble("LATITUDE"));
			latLong.setLongitude(rs.getDouble("LONGITUDE"));
			return latLong;
		}
		
	}
	
	public class DestinationTweetCountRowMapper implements RowMapper<DestinationTweetCount> {

		public DestinationTweetCount mapRow(ResultSet rs, int line) throws SQLException {
			DestinationTweetCount destinationTweetCount = new DestinationTweetCount();
			destinationTweetCount.setDestination(rs.getString("VACATION_DESTINATION"));
			destinationTweetCount.setTweetCount(rs.getInt("DESTINATION_COUNT"));
			destinationTweetCount.setLongitude(rs.getDouble("LONGITUDE"));
			destinationTweetCount.setLatitude(rs.getDouble("LATITUDE"));
			
			
			return destinationTweetCount;
		}

	}

	

}
