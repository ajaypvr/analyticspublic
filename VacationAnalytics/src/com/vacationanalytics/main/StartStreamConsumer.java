package com.vacationanalytics.main;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.vacationanalytics.messaging.MessageConsumer;

public class StartStreamConsumer {
	
	public static void main(String[] args){
		
		XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("com/vacationanalytics/spring/resources/applicationContextStreamConsumer.xml"));

		MessageConsumer messageConsumer = (MessageConsumer)beanFactory.getBean("messageConsumer");
		
		messageConsumer.waitForMessages();
		
	}

}
