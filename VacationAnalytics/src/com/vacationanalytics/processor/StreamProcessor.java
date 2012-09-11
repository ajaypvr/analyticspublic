package com.vacationanalytics.processor;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;

import org.springframework.dao.DuplicateKeyException;

import com.vacationanalytics.classification.TweetCategory.TweetCategoryType;
import com.vacationanalytics.classification.classify.NaiveBayeClassifier;
import com.vacationanalytics.search.DestinationMatcher;
import com.vacationanalytics.search.dao.TweetSearchDAO;
import com.vacationanalytics.tweets.TweetStatus;

/**
 * Process the TweetStatus
 * 
 * @author ajaymangalam
 *
 */
public class StreamProcessor {
	
	private DestinationMatcher destinationMatcher;
	private NaiveBayeClassifier tweetClassifier;
	private TweetSearchDAO tweetSearchDAO;
	
	
	/**
	 * Does the following processing on the TweetSatus
	 * 1. Try to find the location in the status with an existing location information that is stored
	 * 2. Checks if the tweet is a spam
	 * 3. Once the status passes the step (1) and (2) then it is stored for analytics
	 * 
	 * @param message
	 */
	public void processStream(TweetStatus message){
		//Get the tweet text
		String tweetText = message.getTweetText();
		
		//First try to see if the tweet text matches any destination
		String destination = matchDestination(tweetText);
		if(destination != null){
			//if destination found then check if it is spam
			System.out.println("Tweet: " + tweetText);
			System.out.println("Destination found: " + destination);
			message.setDestination(destination);
			try{
				tweetSearchDAO.insertTweetDestinationIdentifiedNotClassified(message);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			//classify the tweet
			TweetCategoryType tweetCategory = tweetClassifier.classify(message);
			System.out.println("Category: " + tweetCategory);
			
			try{
				if(TweetCategoryType.NOTSPAM.equals(tweetCategory)){
					tweetSearchDAO.insertTweetDestinationValid(message);
				}else{
					tweetSearchDAO.insertTweetDestinationInValid(message);
				}
			}catch(DuplicateKeyException dke){
				System.out.println("DuplicateKeyException");
			}
		}
		
		
	}
	
	/**
	 * match destination. It will return the destination name if a match is found otherwise it will return null
	 * @param tweetText
	 * @return
	 */
	private String matchDestination(String tweetText){
		StringBuffer buffer = new StringBuffer();
		  
		  
		  try {
			cleanString(tweetText, buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return destinationMatcher.matchDestination(buffer.toString().trim());
		
	}
	
	
	/**
	 * Clean the tweets from characters like #, ), (, !, * etc
	 * @param reader
	 * @param buffer
	 * @throws IOException
	 */
	private  void cleanString(String text, StringBuffer buffer) throws IOException{
		
		
		StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(text));
		
		while(tokenizer.nextToken() != StreamTokenizer.TT_EOF){
			if(tokenizer.sval != null){
				//System.out.println(tokenizer.ttype);
				//System.out.println(tokenizer.sval);
				if(tokenizer.ttype == '\'' || tokenizer.ttype == '"'){
					//System.out.println("quote matched");
					cleanString(tokenizer.sval, buffer);
				}else{
					buffer.append(tokenizer.sval);
				}
				buffer.append(" ");
			}
			
		}
		
		
	}
	
	public void setDestinationMatcher(DestinationMatcher destinationMatcher) {
		this.destinationMatcher = destinationMatcher;
	}

	public void setTweetClassifier(NaiveBayeClassifier tweetClassifier) {
		this.tweetClassifier = tweetClassifier;
	}

	public void setTweetSearchDAO(TweetSearchDAO tweetSearchDAO) {
		this.tweetSearchDAO = tweetSearchDAO;
	}
	
	
	
	

}
