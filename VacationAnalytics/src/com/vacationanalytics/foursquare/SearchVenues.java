package com.vacationanalytics.foursquare;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.Category;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteTip;
import fi.foyt.foursquare.api.entities.CompleteVenue;
import fi.foyt.foursquare.api.entities.Photo;
import fi.foyt.foursquare.api.entities.PhotoGroup;
import fi.foyt.foursquare.api.entities.RecommendationGroup;
import fi.foyt.foursquare.api.entities.Recommended;
import fi.foyt.foursquare.api.entities.Stats;
import fi.foyt.foursquare.api.entities.TipGroup;
import fi.foyt.foursquare.api.entities.Tips;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

public class SearchVenues {
	
	
	public void searchVenues(String ll) throws FoursquareApiException {     
		// First we need a initialize FoursquareApi.      
		FoursquareApi foursquareApi = new FoursquareApi("WR32UIKABUEEQELLESAAPNTQVPRVEA3XLELUTKHRZYZMNTXM", 
															"BKJPLBLYITC0O3YOLTKHDWZXLIWRRPY35MULY4ARFSF5V35X", 
															"http://www.ajaymangalam.com");       
		
		// After client has been initialized we can make queries.    
		Result<VenuesSearchResult> result = foursquareApi.venuesSearch(ll, null, null, null, null, 100, null, "4bf58dd8d48988d1d5941735", null, null, null);          
		if (result.getMeta().getCode() == 200) {      
			// if query was ok we can finally we do something with the data       
			for (CompactVenue venue : result.getResult().getVenues()) {         
				// TODO: Do something we the data        
				System.out.println("Venue Name = " + venue.getName()); 
				venue(venue.getId());
				System.out.println(venue.getId()); 
				//System.out.println(venue.getUrl());  
			}     
		} else {       
			// TODO: Proper error handling       
			System.out.println("Error occured: ");       
			System.out.println("  code: " + result.getMeta().getCode());      
			System.out.println("  type: " + result.getMeta().getErrorType());      
			System.out.println("  detail: " + result.getMeta().getErrorDetail());      
		}   
	}
	
	public void exploreVenues(String ll) throws FoursquareApiException {     
		// First we need a initialize FoursquareApi.      
		FoursquareApi foursquareApi = new FoursquareApi("WR32UIKABUEEQELLESAAPNTQVPRVEA3XLELUTKHRZYZMNTXM", 
															"BKJPLBLYITC0O3YOLTKHDWZXLIWRRPY35MULY4ARFSF5V35X", 
															"http://www.ajaymangalam.com");       
		
		// After client has been initialized we can make queries.    
		Result<Recommended> result = foursquareApi.venuesExplore(ll, null, null, null, 10000, null, null, null, null);          
		if (result.getMeta().getCode() == 200) {      
			// if query was ok we can finally we do something with the data       
			for (RecommendationGroup group : result.getResult().getGroups()) {         
				// TODO: Do something we the data        
				System.out.println("Venue Name = " + group.getName()); 
				System.out.println(group.getType());  
			}     
		} else {       
			// TODO: Proper error handling       
			System.out.println("Error occured: ");       
			System.out.println("  code: " + result.getMeta().getCode());      
			System.out.println("  type: " + result.getMeta().getErrorType());      
			System.out.println("  detail: " + result.getMeta().getErrorDetail());      
		}   
	}
	
	public void venuePhoto(String venueId) throws FoursquareApiException {     
		// First we need a initialize FoursquareApi.      
		FoursquareApi foursquareApi = new FoursquareApi("WR32UIKABUEEQELLESAAPNTQVPRVEA3XLELUTKHRZYZMNTXM", 
															"BKJPLBLYITC0O3YOLTKHDWZXLIWRRPY35MULY4ARFSF5V35X", 
															"http://www.ajaymangalam.com");       
		
		// After client has been initialized we can make queries.    
		Result<PhotoGroup> result = foursquareApi.venuesPhotos(venueId, null, 10, 0);     
		
		if (result.getMeta().getCode() == 200) {      
			// if query was ok we can finally we do something with the data   
			
			if(result.getResult().getItems() == null){
				return;
			}
			
			for (Photo photo: result.getResult().getItems()) {         
				// TODO: Do something we the data        
				System.out.println("Photo URL = " + photo.getUrl()); 
				//System.out.println(photo.getType());  
			}     
		} else {       
			// TODO: Proper error handling       
			System.out.println("Error occured: ");       
			System.out.println("  code: " + result.getMeta().getCode());      
			System.out.println("  type: " + result.getMeta().getErrorType());      
			System.out.println("  detail: " + result.getMeta().getErrorDetail());      
		}   
	}
	
	public void venue(String venueId) throws FoursquareApiException {     
		// First we need a initialize FoursquareApi.      
		FoursquareApi foursquareApi = new FoursquareApi("WR32UIKABUEEQELLESAAPNTQVPRVEA3XLELUTKHRZYZMNTXM", 
															"BKJPLBLYITC0O3YOLTKHDWZXLIWRRPY35MULY4ARFSF5V35X", 
															"http://www.ajaymangalam.com");       
		
		// After client has been initialized we can make queries.    
		Result<CompleteVenue> result = foursquareApi.venue(venueId);     
		
		if (result.getMeta().getCode() == 200) {      
			// if query was ok we can finally we do something with the data   
			CompleteVenue venue = result.getResult();
			
			     
			// TODO: Do something we the data        
			System.out.println("Short URL = " + venue.getShortUrl()); 
			System.out.println("URL = " + venue.getUrl()); 
			System.out.println("Description = " + venue.getDescription()); 
			System.out.println("Location = " + venue.getLocation().getAddress()); 
			for(PhotoGroup photoGroup : venue.getPhotos().getGroups()){
				for (Photo photo: photoGroup.getItems()) {         
					// TODO: Do something we the data        
					System.out.println("Photo URL = " + photo.getUrl()); 
					//System.out.println(photo.getType());  
				} 
			}
			
			Stats stats = venue.getStats();
			System.out.println("checkin counts = " + stats.getCheckinsCount());
			Tips tips = venue.getTips();
			
			System.out.println("Tips count = " + tips.getCount());
			
			for(TipGroup  tipGroup : tips.getGroups()){
				System.out.println("		Tip Name: " + tipGroup.getName());
				for(CompleteTip completeTip : tipGroup.getItems()){
					System.out.println("			Tip Text: " + completeTip.getText());	
					System.out.println("			Tip Status: " + completeTip.getStatus());	}
			}
			
			//System.out.println(photo.getType());  
			  
		} else {       
			// TODO: Proper error handling       
			System.out.println("Error occured: ");       
			System.out.println("  code: " + result.getMeta().getCode());      
			System.out.println("  type: " + result.getMeta().getErrorType());      
			System.out.println("  detail: " + result.getMeta().getErrorDetail());      
		}   
	}
	
	
	public void venuesCategories() throws Exception{
		// First we need a initialize FoursquareApi.      
		FoursquareApi foursquareApi = new FoursquareApi("WR32UIKABUEEQELLESAAPNTQVPRVEA3XLELUTKHRZYZMNTXM", 
																	"BKJPLBLYITC0O3YOLTKHDWZXLIWRRPY35MULY4ARFSF5V35X", 
																	"http://www.ajaymangalam.com");       
				
		// After client has been initialized we can make queries.    
		Result<Category[]> result = foursquareApi.venuesCategories();
		
		Category[] categories = result.getResult();
		
		for(int i = 0; i < categories.length; i++){
			Category category = categories[i];
			System.out.println(category.getName() + "  ID = " + category.getId());
			Category[] subCategories = category.getCategories();
			if(subCategories != null){
				for(int j = 0; j < subCategories.length; j++){
					Category subCategory = subCategories[j];
					System.out.println("	 - " + subCategory.getName() + "   ID = " + subCategory.getId());
				}
			}
			
		}
		
	}
	
	public void venuesTrending(String latLong) throws Exception{
		FoursquareApi foursquareApi = new FoursquareApi("WR32UIKABUEEQELLESAAPNTQVPRVEA3XLELUTKHRZYZMNTXM", 
				"BKJPLBLYITC0O3YOLTKHDWZXLIWRRPY35MULY4ARFSF5V35X", 
				"http://www.ajaymangalam.com"); 
		
		Result<CompactVenue[]>  result = foursquareApi.venuesTrending(latLong, null, null);
		
		if (result.getMeta().getCode() == 200) {      
			// if query was ok we can finally we do something with the data       
			for (CompactVenue venue : result.getResult()) {         
				// TODO: Do something we the data        
				System.out.println("Venue Name = " + venue.getName()); 
				venue(venue.getId());
				System.out.println(venue.getId()); 
				//System.out.println(venue.getUrl());  
			}     
		} else {       
			// TODO: Proper error handling       
			System.out.println("Error occured: ");       
			System.out.println("  code: " + result.getMeta().getCode());      
			System.out.println("  type: " + result.getMeta().getErrorType());      
			System.out.println("  detail: " + result.getMeta().getErrorDetail());      
		}   

	}
	
	public static void main(String [] args) throws Exception{
		SearchVenues searchVenues = new SearchVenues();
		//searchVenues.searchVenues("41.87,-87.62");
		//searchVenues.venuesCategories();
		searchVenues.venuesTrending("40.7,-74");
	}

}
