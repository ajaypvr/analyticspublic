package com.vacationanalytics.classification.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.vacationanalytics.classification.TweetCategory.TweetCategoryType;
import com.vacationanalytics.classification.train.TweetTrainingInstance;

/**
 * DAO to retrieve tweet information
 * 
 * @author ajaymangalam
 *
 */
public class TweetClassificationDAOImpl implements TweetClassificationDAO{
	
	private String schema;
	
	private JdbcTemplate jdbcTemplate;
	
	private static final String TWEET_CATEGORY_COUNT = "TWEET_CATEGORY_COUNT".toLowerCase();
	private static final String TWEET_CATEGORY_WORD_COUNT = "TWEET_CATEGORY_WORD_COUNT".toLowerCase();
	private static final String TWEET_TRAINING = "TWEET_TRAINING".toLowerCase();

	/**
	 * Get the category type and it's respective count. The count is the total number of tweets that have been classified as 
	 * the given category. The classification would have taken in during training.
	 */
	public Map<TweetCategoryType, Integer> getTweetCategoryCount() {
		
		Map<TweetCategoryType, Integer> tweetCategoryCountMap = new HashMap<TweetCategoryType, Integer>();
		
		String query = "select CATEGORY, COUNT from " + schema + "." + TWEET_CATEGORY_COUNT;
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query);
		
		for(Map<String, Object> resultMap : resultList){
			String category = (String)resultMap.get("CATEGORY");
			Long count = (Long)resultMap.get("COUNT");
			tweetCategoryCountMap.put(TweetCategoryType.valueOf(category), count.intValue());
			
		}
		
		return tweetCategoryCountMap;
	}

	/**
	 * Get the count of attribute within each category
	 */
	public Map<TweetCategoryType, Map<String, Integer>> getTweetCategoryWordCount() {
		Map<TweetCategoryType, Map<String, Integer>> tweetCategoryWordCountMap = new HashMap<TweetCategoryType, Map<String, Integer>>();
		
		String query = "select WORD, CATEGORY, COUNT from " + schema + "." + TWEET_CATEGORY_WORD_COUNT;
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query);
		
		for(Map<String, Object> resultMap : resultList){
			String word = (String)resultMap.get("WORD");
			String category = (String)resultMap.get("CATEGORY");
			Long count = (Long)resultMap.get("COUNT");
			Map<String, Integer> wordCountMap = null;
			if(tweetCategoryWordCountMap.containsKey(TweetCategoryType.valueOf(category))){
				wordCountMap = tweetCategoryWordCountMap.get(TweetCategoryType.valueOf(category));
			}else{
				wordCountMap = new HashMap<String, Integer>();
			}
			
			wordCountMap.put(word, count.intValue());
			tweetCategoryWordCountMap.put(TweetCategoryType.valueOf(category), wordCountMap);
			
		}
		
		return tweetCategoryWordCountMap;
	}

	/**
	 * Get the list of tweets that are used for training
	 * @return
	 */
	public List<TweetTrainingInstance> getTweetTraining() {
		List<TweetTrainingInstance> tweetInstanceList = new ArrayList<TweetTrainingInstance>();
		
		String query = "select TWEET_ID, TWEET_TEXT, CATEGORY from " + schema + "." + TWEET_TRAINING;
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query);
		
		for(Map<String, Object> resultMap : resultList){
			TweetTrainingInstance tweetTraining = new TweetTrainingInstance();
			long tweetId = (Long)resultMap.get("TWEET_ID");
			String tweetText = (String)resultMap.get("TWEET_TEXT");
			String category = (String)resultMap.get("CATEGORY");
			tweetTraining.setTweetText(tweetText);
			tweetTraining.setTweetCategoryType(TweetCategoryType.valueOf(category));
			tweetTraining.setTweetId(tweetId);
			tweetInstanceList.add(tweetTraining);
			
		}
		
		return tweetInstanceList;
	}

	/**
	 * Insert the list of tweets that are used for training
	 * @param tweetInstanceList
	 */
	public void insertTweetTraining(List<TweetTrainingInstance> tweetInstanceList) {
		final String query = "insert into " + schema + "." + TWEET_TRAINING + "(TWEET_ID, TWEET_TEXT, CATEGORY) VALUES (?, ?,?) ";
			

		for(TweetTrainingInstance tweetTraining : tweetInstanceList){
			final long tweetId = tweetTraining.getTweetId();
			final String tweetText = tweetTraining.getTweetText();
			final String category = tweetTraining.getTweetCategoryType().name();
			
		
			jdbcTemplate.update(new PreparedStatementCreator() {
		
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setLong(1, tweetId);
					ps.setString(2, tweetText);
					ps.setString(3, category);
					return ps;
				}
			});
		
		}
		
	}

	/**
	 * Insert or update the number of instances that belongs to a particular category
	 * @param tweetCategoryCountMap
	 */
	public void insertUpdateTweetCategoryCount(Map<TweetCategoryType, Integer> tweetCategoryCountMap) {
		final String query = "insert into " + schema + "." + TWEET_CATEGORY_COUNT + " (CATEGORY, COUNT) VALUES (?,?) "
							+ "ON DUPLICATE KEY UPDATE COUNT = ?";
		
		for(TweetCategoryType tweetCategoryType : tweetCategoryCountMap.keySet()){
			final String category = tweetCategoryType.name();
			final Integer count = tweetCategoryCountMap.get(tweetCategoryType);
			jdbcTemplate.update(new PreparedStatementCreator() {
				
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(query);
						ps.setString(1, category);
						ps.setInt(2, count);
						ps.setInt(3, count);
						return ps;
					}
			});
			
		}
		
	}

	/**
	 * Insert the count of attribute within each category
	 */
	public void insertUpdateTweetCategoryWordCount(Map<TweetCategoryType, Map<String, Integer>> tweetCategoryWordCountMap) {
		final String query = "insert into " + schema + "." + TWEET_CATEGORY_WORD_COUNT + " (WORD, CATEGORY, COUNT) VALUES (?,?, ?) "
			+ "ON DUPLICATE KEY UPDATE COUNT = ?";

		for(TweetCategoryType tweetCategoryType : tweetCategoryWordCountMap.keySet()){
			final String category = tweetCategoryType.name();
			Map<String, Integer> wordCountMap = tweetCategoryWordCountMap.get(tweetCategoryType);
			for(final String word : wordCountMap.keySet()){
				final Integer count = wordCountMap.get(word);
				jdbcTemplate.update(new PreparedStatementCreator() {
				
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(query);
						ps.setString(1, word);
						ps.setString(2, category);
						ps.setInt(3, count);
						ps.setInt(4, count);
						return ps;
					}
				});
				
				}
				
			}
		
	}
	
	
	public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	public void setSchema(String schema) {
		this.schema = schema;
	}
}
