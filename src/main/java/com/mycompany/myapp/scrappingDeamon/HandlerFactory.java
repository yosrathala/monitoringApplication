package com.mycompany.myapp.scrappingDeamon;

import org.springframework.context.ApplicationContext;

import com.mycompany.myapp.spark.PostInterestManager;
import com.mycompany.myapp.spark.RandomForestPostInterest;
import com.mycompany.myapp.spark.SVMPostInterest;


public class HandlerFactory {


	private static ApplicationContext context;

	public static void initContext(ApplicationContext context) {
		HandlerFactory.context = context;
	}

	public static SearchScrappingHandler getSearchHandler(String type) {

		SearchScrappingHandler scrapHandler = null;
		if (type.equals("linkedin")) {

			return context.getBean(LinkedScappingHandler.class);
		}
		if (type.equals("rss")) {

			return context.getBean(RssScrappingHandler.class);
		}
		if (type.equals("facebook")) {
			return context.getBean(FacebookScrappingHandler.class);
		}
		if (type.equals("progonline")) {
			return context.getBean(ProgonlineScrappingHandler.class);
		}
		return scrapHandler;
	}
	
	public  static PostInterestManager getPredictionHnadler(String type) {
		if(type.equals("svm")) {
			return context.getBean(SVMPostInterest.class);
		}else if(type.equals("rf")) {
			return context.getBean(RandomForestPostInterest.class);
		}else 
			return null;
		
	}
	
	public static SearchResultHandler getSearchResultHandler(String type) {
		if(type.equals("jdbc")) {
			return context.getBean(JdbcSave.class);
		}
		if(type.equals("jms")) {
			context.getBean(JmsSave.class);
		}
		return null;
	}
	
	public static NotificationHandler getNotificationHandler(String type) {
		if(type.equals("email")) {
			return context.getBean(MailNotification.class); 
		}
		if(type.equals("sms")) {
			return context.getBean(SmsNotification.class); 
		}
		if(type.equals("push")) {
			return context.getBean(PuchNotification.class); 
		}
		return null;
		
	}

}
