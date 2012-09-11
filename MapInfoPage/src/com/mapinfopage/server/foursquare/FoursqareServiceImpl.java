package com.mapinfopage.server.foursquare;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mapinfopage.shared.Category;
import com.mapinfopage.shared.LatitudeLongitude;
import com.mapinfopage.shared.Recommendation;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteVenue;
import fi.foyt.foursquare.api.entities.Photo;
import fi.foyt.foursquare.api.entities.PhotoGroup;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

public class FoursqareServiceImpl implements FoursquareService{
	
	private static Logger log = Logger.getLogger(FoursqareServiceImpl.class);
	//Initialize FoursquareApi.      
	private static FoursquareApi foursquareApi = new FoursquareApi("WR32UIKABUEEQELLESAAPNTQVPRVEA3XLELUTKHRZYZMNTXM", 
																"BKJPLBLYITC0O3YOLTKHDWZXLIWRRPY35MULY4ARFSF5V35X", 
																"http://www.ajaymangalam.com"); 

	@Override
	public List<Category> getCategoryInformation(LatitudeLongitude latitudeLongitude, List<FoursquareCategory> foursqaureCategoryList) {
		
		List<Category> categoryList = new ArrayList<Category>();
		for(FoursquareCategory foursquareCategory : foursqaureCategoryList){
			Category category = getCategoryInformation(latitudeLongitude, foursquareCategory);
			categoryList.add(category);
			
		}
		
		return categoryList;
	}
	
	
	@Override
	public Category getCategoryInformation(LatitudeLongitude latitudeLongitude, FoursquareCategory foursquareCategory)  { 
		
		log.info("Enter getCategoryInformation: category " + foursquareCategory.getFoursquareCategoryName());
		
		String foursquareCategoryId = foursquareCategory.getFoursquareCategoryId();
		String foursquareCategoryName = foursquareCategory.getFoursquareCategoryName();
		String latLong = createLatLongString(latitudeLongitude);
		
		Category category = new Category();
		category.setName(foursquareCategoryName);
		List<Recommendation> recommendationList = new ArrayList<Recommendation>();
		category.setRecommendations(recommendationList);
		
		      
		
		// After client has been initialized we can make queries.    
		Result<VenuesSearchResult> result;
		try {
			result = foursquareApi.venuesSearch(latLong, null, null, null, null, 100, null, foursquareCategoryId, null, null, null);
		} catch (FoursquareApiException e) {
			log.error("Error while querying for FoursquareCategory:  " + foursquareCategoryName, e);
			
			e.printStackTrace();
			//for now return empty category
			return category;
		}          
		if (result.getMeta().getCode() == 200) {      
			// if query was ok we can finally we do something with the data      
			int maxNumberVenuesToGet = 10;
			for (CompactVenue venue : result.getResult().getVenues()) { 
				Recommendation recommendation = getVenueRecommendation(venue);
				//if image url is null then don't add the recommendation
				if(recommendation.getImageUrl() != null){
					recommendationList.add(recommendation);
					//Get only the max nuber of venues defined for a given category
					if(--maxNumberVenuesToGet <=0 ){
						break;
					}
				}
			}     
		} else {       
			// TODO: Proper error handling       
			log.error("Error occured: ");       
			log.error("  code: " + result.getMeta().getCode());      
			log.error("  type: " + result.getMeta().getErrorType());      
			log.error("  detail: " + result.getMeta().getErrorDetail());      
		} 
		
		return category;
	}
	
	
	private Recommendation getVenueRecommendation(CompactVenue venue) {    
		log.info("Enter populateVenueInformation: venue " + venue.getName());
		
		Recommendation recommendation = new Recommendation();
		String venueId = venue.getId();
		//query for venue information
		Result<CompleteVenue> result;
		try {
			result = foursquareApi.venue(venueId);
		} catch (FoursquareApiException e) {
			log.error(" Error while querying Venue " + venue.getName(), e);
			e.printStackTrace();
			//for now return empty recommendation
			return recommendation;
		}     
		
		if (result.getMeta().getCode() == 200) {      
			// if query was ok we can finally we do something with the data   
			CompleteVenue completeVenue = result.getResult();
			recommendation.setEntityName(completeVenue.getName());
			recommendation.setDescription(completeVenue.getDescription());
			recommendation.setEntityUrl(completeVenue.getShortUrl());
			
			for(PhotoGroup photoGroup : completeVenue.getPhotos().getGroups()){
				for (Photo photo: photoGroup.getItems()) {
					String photoUrl = photo.getUrl();
					if(photoUrl != null && !"".equals(photoUrl.trim())){
						recommendation.setImageUrl(photo.getUrl());
						//get the first photo url and exit. Currently only one photo will be needed per venue
						break;
					}
					
				} 
			}
			//System.out.println(photo.getType());  
			  
		} else {          
			log.error("Error occured: ");       
			log.error("  code: " + result.getMeta().getCode());      
			log.error("  type: " + result.getMeta().getErrorType());      
			log.error("  detail: " + result.getMeta().getErrorDetail());      
		}  
		
		return recommendation;
	}
	
	private String createLatLongString(LatitudeLongitude latitudeLongitude){
		
		double latitude = new BigDecimal(latitudeLongitude.getLatitude(), new MathContext(2)).doubleValue();
		double longitude = new BigDecimal(latitudeLongitude.getLongitude(), new MathContext(2)).doubleValue();
		
		return latitude + ","+ longitude;
	}

}
