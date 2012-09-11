package com.vacationanalytics.stream.tweets;

import java.util.logging.Logger;

import com.vacationanalytics.messaging.MessageCreator;
import com.vacationanalytics.tweets.TweetStatus;
import com.vacationanalytics.tweets.TweetTranslator;

import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Connects to the twitter stream and searches for for key words (e.g. vacation).
 * The filtered tweets are then send to the queue where it will be picked up by the consumer thread.
 * 
 * @author ajaymangalam
 *
 */

public class StreamTweets implements StatusListener{
	
	private Logger log = Logger.getLogger(StreamTweets.class.getName());
	
	private MessageCreator messageCreator;
	
	public static void main(String [] args){
		
		StreamTweets test = new StreamTweets();
		test.stream();
		
		
	}
	
	public void stream(){
		log.info("stream");
		
		
		ConfigurationBuilder config = new ConfigurationBuilder();
		//config.setSiteStreamBaseURL("https://stream.twitter.com");
		config.setStreamBaseURL("https://stream.twitter.com/1/");
		config.setUser("ajaymangalam");
		config.setPassword("ajaypvr");
		config.setDebugEnabled(true);
		config.setOAuthAccessToken("37819011-kWoh0J3QxrMwJCjriISzzAkmSYlWk3DlZYWluX14");
		config.setOAuthAccessTokenSecret("pBLPRk2cx7kfijK483a1qUoRV65CxremjNaN4kGNkw");
		config.setOAuthConsumerKey("PHJbIbZtysOmPr4ByW0Ew");
		config.setOAuthConsumerSecret("DLFG6UHrTN82HzhlpuKyZEvOfVQUTqjPb6Yg25IZdw");
		
		TwitterStream twitterStream = new TwitterStreamFactory(config.build()).getInstance();
		
		// sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
	    twitterStream.addListener(this);
	    
	    FilterQuery query = new FilterQuery();
	    query.track(new String[]{"vacation"});
	    
		twitterStream.filter(query);
	}

	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		log.info("onDeletionNotice -> " + statusDeletionNotice);
		System.out.println("onDeletionNotice -> " + statusDeletionNotice);
		
	}

	public void onScrubGeo(long userId, long upToStatusId) {
		log.info("onScrubGeo -> " + upToStatusId);
		
	}

	public void onStatus(Status status) {
		//log.info("onStatus -> " + status);
		TweetStatus tweetStatus = TweetTranslator.translateToTweetStatus(status);
		
		messageCreator.sendMessage(tweetStatus);
		
	}

	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		log.info("onTrackLimitationNotice -> " + numberOfLimitedStatuses);
		System.out.println("onTrackLimitationNotice -> " + numberOfLimitedStatuses);
		
	}

	public void onException(Exception ex) {
		log.info("onException -> " + ex);
		ex.printStackTrace();
		
	}

	public void setMessageCreator(MessageCreator messageCreator) {
		this.messageCreator = messageCreator;
	}
	
	


}
