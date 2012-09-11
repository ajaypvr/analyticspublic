package com.vacationanalytics.classification.train;

import java.util.List;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.vacationanalytics.classification.dao.TweetClassificationDAO;

public class RunNaiveBayeTraining {
	
	
	
	public static void main(String[] args){
		
		XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("com/vacationanalytics/spring/resources/applicationContextBayeTraining.xml"));

		NaiveBayeTraining naiyeBayeTraining = (NaiveBayeTraining)beanFactory.getBean("naiveBayeTraining");
		
		TweetClassificationDAO tweetClassificationDAO = (TweetClassificationDAO)beanFactory.getBean("tweetClassificationDAO");
		
		naiyeBayeTraining.setUpBayeTraining();
		
		List<TweetTrainingInstance> tweetTrainingList = tweetClassificationDAO.getTweetTraining();
		
		naiyeBayeTraining.train(tweetTrainingList);
		
		
		
	}

}
