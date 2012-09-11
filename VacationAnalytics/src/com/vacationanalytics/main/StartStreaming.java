package com.vacationanalytics.main;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.vacationanalytics.stream.tweets.StreamTweets;

public class StartStreaming {
	
	public static void main(String[] args){
		
		XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("com/vacationanalytics/spring/resources/applicationContextStreaming.xml"));

		StreamTweets streamTweets = (StreamTweets) beanFactory.getBean("streamTweets");
		
		try {
			streamTweets.stream();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

}
