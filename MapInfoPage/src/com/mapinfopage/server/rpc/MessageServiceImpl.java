package com.mapinfopage.server.rpc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mapinfopage.client.rpc.MessageService;
import com.mapinfopage.shared.Category;
import com.mapinfopage.shared.DestinationTweetCount;
import com.mapinfopage.shared.LatitudeLongitude;
import com.mapinfopage.shared.LocationInformationDO;
import com.mapinfopage.shared.MapInformationDO;
import com.mapinfopage.shared.MessageDO;
import com.mapinfopage.shared.Recommendation;
import com.mapinfopage.shared.TableInformationDO;
import com.mapinfopage.shared.TweetStatusDO;
import com.mapinfopage.shared.VacationDO;
import com.mapinfopage.util.DOTranslator;
import com.mapinfopage.server.foursquare.FoursquareService;
import com.mapinfopage.server.foursquare.FoursquareCategory;


/**
 * Server-side implementation of MessageService.  This provides
 * data to the map/info page.
 */
public class MessageServiceImpl
extends RemoteServiceServlet
implements MessageService {

	private static Logger log = Logger.getLogger(MessageServiceImpl.class);
	
	////////////////////////////////////////////////////////////
	// PRIVATE CLASS VARIABLES
	////////////////////////////////////////////////////////////
	private static final long serialVersionUID = -1809820749163380101L;
	
	private static TweetServiceImpl tweetServiceImpl;
	private static FoursquareService foursquareService;
	
	static{
		
		ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext (new String[]{"com/mapinfopage/server/resources/applicationContextVacation.xml"});

		tweetServiceImpl = (TweetServiceImpl)beanFactory.getBean("tweetServiceImpl");
		foursquareService = (FoursquareService)beanFactory.getBean("foursquareService");
		//call to load the properties
		
		
	}


	////////////////////////////////////////////////////////////
	// PUBLIC INTERFACE METHODS
	////////////////////////////////////////////////////////////
	@Override
	public LocationInformationDO userClickedMarkerOnMap(int days, String markerId) {

		log.debug("BEGIN userClickedMarkerOnMap");
		
		List<TweetStatusDO> tweetStatuDOList = tweetServiceImpl.getUpdatedTweetsForPreviousDays(days, markerId);
		
		
		List<TableInformationDO> tableInformationDO = DOTranslator.getTableInformationDO(tweetStatuDOList);
		
		LocationInformationDO locationInformationDO = new LocationInformationDO();
		locationInformationDO.setTableInformationList(tableInformationDO);
		
		List<Category> categoryList = createCategories(markerId);
		
		locationInformationDO.setCategoryList(categoryList);
		
		return  locationInformationDO;

	}

	
	@Override
	public MessageDO slider(int days, String markerId) {
		log.debug("BEGIN slider");

		List<DestinationTweetCount> destinationTweetCount = tweetServiceImpl.getDestinationTweetCount(days);
		List<TweetStatusDO> tweetStatuDOList = tweetServiceImpl.getUpdatedTweetsForPreviousDays(days, markerId);
		
		VacationDO vacationDO = new VacationDO();
		vacationDO.setDestinationTweetCount(destinationTweetCount);
		vacationDO.setTweetStatusList(tweetStatuDOList);
		
		MessageDO messageDO = DOTranslator.getMessageDO(vacationDO);
		
		return messageDO;

	}

	
	@Override
	public MessageDO refresh(int days, String markerId, Timestamp lastTweetTimestamp) {
		log.debug("BEGIN refresh");
		
		List<DestinationTweetCount> destinationTweetCount = tweetServiceImpl.getDestinationTweetCount(days);
		List<TweetStatusDO> tweetStatuDOList = tweetServiceImpl.getTweetsSinceLastUpdate(markerId, lastTweetTimestamp);
		
		VacationDO vacationDO = new VacationDO();
		vacationDO.setDestinationTweetCount(destinationTweetCount);
		vacationDO.setTweetStatusList(tweetStatuDOList);
		
		MessageDO messageDO = DOTranslator.getMessageDO(vacationDO);
		
		return messageDO;
		
		
		
	}

	
	
	/**
	 * USED AS TEST
	 */
	
	
	public List<TableInformationDO> userClickedMarkerOnMapTemp(String markerId) {

		List<TableInformationDO> infos = createTableInformation("This is a message added because marker ID " + markerId + " was clicked.");
		return infos;

	}

	
	
	public MessageDO sliderTemp(int days) {

		MessageDO message = new MessageDO();
		message.setMapInformationList(createMapMarkers());
		message.setTableInformationList(createTableInformation("This is a message added because the slider was changed to " + days + " days"));

		return message;

	}

	
	
	public MessageDO refreshTemp(int days, String markerId) {
		
		MessageDO message = new MessageDO();
		message.setMapInformationList(createMapMarkers());
		message.setTableInformationList(createTableInformation("This is a message added by the periodic timed refresh."));
		return message;
		
	}
	
	////////////////////////////////////////////////////////////
	// PRIVATE HELPER METHODS
	////////////////////////////////////////////////////////////
	/**
	 * Create between 3 and 8 map markers, returning a List of
	 * MapInformationDO objects containing the markers.
	 */
	private List<MapInformationDO> createMapMarkers() {
		
		// Initialize
		List<MapInformationDO> markers = new ArrayList<MapInformationDO>();
		
		// Build each marker
		int count = 3 + (int)(Math.random() * 6);
		for (int i=1; i<=count; i++) {
			MapInformationDO marker = new MapInformationDO();
			marker.setMarkerId("mid" + i);
			marker.setLatitude((double)30 + (Math.random() * 15));
			marker.setLongitude((double)-120 + (Math.random() * 50));
			marker.setDisplayText("marker " + i);
			markers.add(marker);

		}
		
		// Return value to caller
		return markers;
		
	}

	
	/**
	 * Create a table information object, returning it inside
	 * a List of TableInformationDO objects.
	 */
	private List<TableInformationDO> createTableInformation(String text) {
		
		// Initialize
		List<TableInformationDO> infos = new ArrayList<TableInformationDO>();
		
		for(int i = 0; i < 5; i++){
			// Build the table information object
			TableInformationDO info = new TableInformationDO();
			info.setText(text + "  " + i);
			
			info.setTimeCreated(new Timestamp(new Date().getTime()));
			info.setImageURL("http://www.google.com/intl/en_com/images/srpr/logo3w.png");
			infos.add(info);
		}
		
		// Return value to caller
		return infos;
		
	}
	
	/**
	 * Create a List of Category objects.
	 */
	private List<Category> createCategories() {
		
		// Initialize
		List<Category> categories = new ArrayList<Category>();
		
		// Build the categories
		int categoryCount = 1 + (int)(Math.random() * 3);
		for (int i=1; i<=categoryCount; i++) {
			Category category = new Category();
			category.setName("CAT" + i);
			category.setImageUrl("http://icons.iconarchive.com/icons/tpdkdesign.net/refresh-cl/256/System-Java-icon.png");
			List<Recommendation> recommendations = new ArrayList<Recommendation>();
			int recommendationCount = 1 + (int)(Math.random() * 5);
			for (int j=1; j<=recommendationCount; j++) {
				Recommendation recommendation = new Recommendation();
				recommendation.setEntityName("Place " + j);
				recommendation.setDescription("Description of place " + j);
				recommendation.setImageUrl("http://icons.iconarchive.com/icons/tpdkdesign.net/refresh-cl/256/System-Java-icon.png");
				recommendation.setEntityUrl("http://www.google.com");
				recommendations.add(recommendation);
			}
			category.setRecommendations(recommendations);
			categories.add(category);
		}
		
		// Return value to caller
		return categories;
		
	}
	
	private List<Category> createCategories(String location){
		
		LatitudeLongitude latLong = tweetServiceImpl.getLatitudeLongitude(location);
		
		List<FoursquareCategory> foursquareCategoryList = new ArrayList<FoursquareCategory>();
		foursquareCategoryList.add(FoursquareCategory.ART_ENTERTAINMENT);
		foursquareCategoryList.add(FoursquareCategory.FOOD);
		foursquareCategoryList.add(FoursquareCategory.NIGHTLIFE);
		foursquareCategoryList.add(FoursquareCategory.GREAT_OUTDOOR);
		
		List<Category> categoryList = foursquareService.getCategoryInformation(latLong, foursquareCategoryList);
		return categoryList;
		
	}
	


}
