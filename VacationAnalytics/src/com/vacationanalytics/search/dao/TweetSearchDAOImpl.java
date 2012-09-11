package com.vacationanalytics.search.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.vacationanalytics.tweets.TweetStatus;

public class TweetSearchDAOImpl implements TweetSearchDAO{
	
	private String schema;
	
	private JdbcTemplate jdbcTemplate;
	
	private static final String TWEET_DEST_IDN_NO_CLASSIFY = "TWEET_DEST_IDN_NO_CLASSIFY".toLowerCase();
	private static final String TWEET_DEST_IN_VALID = "TWEET_DEST_IN_VALID".toLowerCase();
	private static final String TWEET_DEST_VALID = "TWEET_DEST_VALID".toLowerCase();
	private static final String VACATION_DESTINATION = "VACATION_DESTINATION".toLowerCase();
	

	public void insertTweetDestinationIdentifiedNotClassified(TweetStatus tweetStatus) {
		final String query = "insert into " + schema + "." + TWEET_DEST_IDN_NO_CLASSIFY + " (TWEET_ID, TWEET_TEXT, DESTINATION_LOCATION)"
						+ " VALUES (?, ?, ?) ";
		

		final long tweetId = tweetStatus.getTweetId();
		final String tweetText = tweetStatus.getTweetText();
		final String destination = tweetStatus.getDestination();
			
		
		jdbcTemplate.update(new PreparedStatementCreator() {
	
		public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setLong(1, tweetId);
				ps.setString(2, tweetText);
				ps.setString(3, destination);
				return ps;
			}
		});
		
		
		
	}
	
	public List<TweetStatus> getTweetDestinationIdentifiedNotClassified() {
		List<TweetStatus> tweetStatuList = new ArrayList<TweetStatus>();
		
		String query = "select TWEET_ID, TWEET_TEXT, DESTINATION_LOCATION from " + schema + "." + TWEET_DEST_IDN_NO_CLASSIFY;
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query);
		
		for(Map<String, Object> resultMap : resultList){
			TweetStatus tweetStatus = new TweetStatus();
			long tweetId = (Long)resultMap.get("TWEET_ID");
			String tweetText = (String)resultMap.get("TWEET_TEXT");
			String destination = (String)resultMap.get("DESTINATION_LOCATION");
			tweetStatus.setTweetId(tweetId);
			tweetStatus.setTweetText(tweetText);
			tweetStatus.setDestination(destination);
			tweetStatuList.add(tweetStatus);
			
		}
		
		return tweetStatuList;
	}
	
	
	
	public List<String> getDestinationLocation() {
		List<String> destinationLocation = new ArrayList<String>();
		
		String query = "select CITY from " + schema + "." + VACATION_DESTINATION;
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query);
		
		for(Map<String, Object> resultMap : resultList){
			String city = (String)resultMap.get("CITY");
			destinationLocation.add(city);
			
		}
		
		return destinationLocation;
	}

	public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public void insertTweetDestinationInValid(TweetStatus tweetStatus) {
		final String query = "insert into " + schema + "." + TWEET_DEST_IN_VALID + " ("
								+ " TWEET_ID, USER_ID, TWEET_TEXT, USER_LOCATION, USER_NAME, TIME_CREATED, VACATION_DESTINATION, PROFILE_IMAGE_URL)"
								+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";


		final long tweetId = tweetStatus.getTweetId();
		final long userId = tweetStatus.getUserId();
		final String tweetText = tweetStatus.getTweetText();
		final String userLocation = tweetStatus.getUserLocation();
		final String userName = tweetStatus.getUserName();
		final Timestamp timeCreated = tweetStatus.getTimeCreated();
		final String destination = tweetStatus.getDestination();
		final String profileImageURL = tweetStatus.getProfileImageURL();
		
		
		jdbcTemplate.update(new PreparedStatementCreator() {
		
		public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setLong(1, tweetId);
				ps.setLong(2, userId);
				ps.setString(3, tweetText);
				ps.setString(4, userLocation);
				ps.setString(5, userName);
				ps.setTimestamp(6, timeCreated);
				ps.setString(7, destination);
				ps.setString(8, profileImageURL);
				return ps;
			}
		});
		
	}

	public void insertTweetDestinationValid(TweetStatus tweetStatus) {
		final String query = "insert into " + schema + "." + TWEET_DEST_VALID + "("
		+ " TWEET_ID, USER_ID, TWEET_TEXT, USER_LOCATION, USER_NAME, TIME_CREATED, VACATION_DESTINATION, PROFILE_IMAGE_URL)"
		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";


		final long tweetId = tweetStatus.getTweetId();
		final long userId = tweetStatus.getUserId();
		final String tweetText = tweetStatus.getTweetText();
		final String userLocation = tweetStatus.getUserLocation();
		final String userName = tweetStatus.getUserName();
		final Timestamp timeCreated = tweetStatus.getTimeCreated();
		final String destination = tweetStatus.getDestination();
		final String profileImageURL = tweetStatus.getProfileImageURL();
		
		
		jdbcTemplate.update(new PreparedStatementCreator() {
		
		public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setLong(1, tweetId);
				ps.setLong(2, userId);
				ps.setString(3, tweetText);
				ps.setString(4, userLocation);
				ps.setString(5, userName);
				ps.setTimestamp(6, timeCreated);
				ps.setString(7, destination);
				ps.setString(8, profileImageURL);
				return ps;
			}
		});
				
	}

	

}
