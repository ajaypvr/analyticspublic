package com.vacationanalytics.classification.classify;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.vacationanalytics.classification.TweetCategory.TweetCategoryType;
import com.vacationanalytics.search.dao.TweetSearchDAO;
import com.vacationanalytics.tweets.TweetStatus;

/**
 * Class that is used to execute the classification of tweets using bayes classification algorithm
 * @author ajaymangalam
 *
 */
public class RunNaiveBayeCalssifier {
	
	private static final Logger log = Logger.getLogger(RunNaiveBayeCalssifier.class.getName());
	
	public static void main(String[] args){
		
		XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("com/vacationanalytics/spring/resources/applicationContextBayeTraining.xml"));

		NaiveBayeClassifier naiveBayeClassifier = (NaiveBayeClassifier)beanFactory.getBean("naiveBayeClassifier");
		
		TweetSearchDAO tweetSearchDAO = (TweetSearchDAO)beanFactory.getBean("tweetSearchDAO");
		
		naiveBayeClassifier.setUpNaiveBayeClassifier();
		
		List<TweetStatus> tweetTrainingList = tweetSearchDAO.getTweetDestinationIdentifiedNotClassified();
		
		for(TweetStatus tweetStatus : tweetTrainingList){
			TweetCategoryType classifiedCategory = naiveBayeClassifier.classify(tweetStatus);
			log.info("TWEET TEXT = " + tweetStatus.getTweetText());
			log.info("CATEGORY = " + classifiedCategory);
		}
	}

}
