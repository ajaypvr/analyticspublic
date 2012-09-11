package com.vacationanalytics.classification.train;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.vacationanalytics.classification.TweetCategory.TweetCategoryType;
import com.vacationanalytics.classification.dao.TweetClassificationDAO;

public abstract class AbstractCreateTweetTrainingData {
	
	private TweetClassificationDAO tweetClassificationDAO;
	
	
	
	/**
	 * Read from a file and then take input from user to see if it is spam or not
	 */
	public void train() throws IOException{
		
		
		List<TweetTrainingInstance> tweetTrainingList = getTweetTrainingList();
		List<TweetTrainingInstance> classifiedTweets = new ArrayList<TweetTrainingInstance>();
		
		for(TweetTrainingInstance tweetTrainingInstance : tweetTrainingList) {
			
			try{
				
				String tweetText = tweetTrainingInstance.getTweetText();
				System.out.println("Tweet = " +  tweetText);
				
				Scanner scanner = new Scanner(System.in);
				int userInput = scanner.nextInt();
				tweetTrainingInstance.setTweetText(tweetText);
				if(userInput == 1){
					//it's not a spanm
					
					tweetTrainingInstance.setTweetCategoryType(TweetCategoryType.NOTSPAM);
					classifiedTweets.add(tweetTrainingInstance);
				}else if( userInput == -1 ){
					//it's  a spam
					tweetTrainingInstance.setTweetCategoryType(TweetCategoryType.SPAM);
					classifiedTweets.add(tweetTrainingInstance);
				}else if(userInput == 0){
					break;
				}
				
				
			}catch(Exception e){
				//IGNORE THIS TWEET
				//System.out.println(Arrays.asList(tokens));
				e.printStackTrace();
				continue;
			}
		}
		
		tweetClassificationDAO.insertTweetTraining(classifiedTweets);
		
		
		
	}
	
	public abstract List<TweetTrainingInstance> getTweetTrainingList() throws IOException;

	public void setTweetClassificationDAO(TweetClassificationDAO tweetClassificationDAO) {
		this.tweetClassificationDAO = tweetClassificationDAO;
	}

	
	
	

}
