package com.vacationanalytics.processor;

import junit.framework.Assert;

import com.vacationanalytics.classification.TweetCategory.TweetCategoryType;
import com.vacationanalytics.classification.classify.MockNaiveBayeClassifier;
import com.vacationanalytics.classification.classify.NaiveBayeClassifier;
import com.vacationanalytics.search.DestinationMatcher;
import com.vacationanalytics.search.MockDestinationMatcher;
import com.vacationanalytics.search.dao.MockTweetSearchDAO;
import com.vacationanalytics.search.dao.TweetSearchDAO;
import com.vacationanalytics.tweets.TweetStatus;

import junit.framework.TestCase;

public class UTStreamProcessor extends TestCase{

	private StreamProcessor streamProcessor;
	private DestinationMatcher destinationMatcher;
	private NaiveBayeClassifier tweetClassifier;
	private TweetSearchDAO tweetSearchDAO;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		streamProcessor = new StreamProcessor();
		destinationMatcher = new MockDestinationMatcher();
		streamProcessor.setDestinationMatcher(destinationMatcher);
		tweetClassifier = new MockNaiveBayeClassifier();
		streamProcessor.setTweetClassifier(tweetClassifier);
		tweetSearchDAO = new MockTweetSearchDAO();
		streamProcessor.setTweetSearchDAO(tweetSearchDAO);
	}
	
	/**
	 * Test the tweet which does not match any destination
	 */
	public void testNoDestinationMatch(){
		
		TweetStatus tweetStatus = new TweetStatus();
		tweetStatus.setTweetText("this is not valid vacation tweet");
		
		TweetCategoryType tweetCategoryType = streamProcessor.processStream(tweetStatus);
		
		Assert.assertNull(tweetCategoryType);
		
	}
	
	/**
	 * Test the tweet which has valid destination but is classified as a spam
	 */
	public void testValidDestinationButSpam(){
		TweetStatus tweetStatus = new TweetStatus();
		tweetStatus.setTweetText(MockNaiveBayeClassifier.SPAM_TWEET);
		
		TweetCategoryType tweetCategoryType = streamProcessor.processStream(tweetStatus);
		
		Assert.assertEquals(TweetCategoryType.SPAM, tweetCategoryType);
		
	}
	
	/**
	 * Test the tweet which has valid destination and is not a spam 
	 */
	public void testValidDestinationAndNotSpam(){
		TweetStatus tweetStatus = new TweetStatus();
		tweetStatus.setTweetText(MockNaiveBayeClassifier.NOTSPAM_TWEET);
		
		TweetCategoryType tweetCategoryType = streamProcessor.processStream(tweetStatus);
		
		Assert.assertEquals(TweetCategoryType.NOTSPAM, tweetCategoryType);
		
	}
	
	

}
