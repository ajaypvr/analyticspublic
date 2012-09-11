package com.mapinfopage.server.foursquare;

public enum FoursquareCategory {
	
	//MAIN CATEGORIES
	FOOD("Food", "4d4b7105d754a06374d81259"),
	ART_ENTERTAINMENT("Entertainment", "4d4b7104d754a06370d81259"),
	GREAT_OUTDOOR("Outdoor", "4d4b7105d754a06377d81259"),
	NIGHTLIFE("Nightlife", "4d4b7105d754a06376d81259"),
	SHOP_SERVICE("Shop", "4d4b7105d754a06378d81259"),
	TRAVEL_TRANSPORT("Travel", "4d4b7105d754a06379d81259"),
											
	//SUBCATEGORIES
	
	GENERAL_ENTERTAINMENT("General Entertainment",	"4bf58dd8d48988d1f1931735"),
	HISTORIC_SITE("Historic site", "4deefb944765f83613cdba6e"),
	GENERAL_TRAVEL("General Travel",  "4bf58dd8d48988d1f6931735"),
	SCENIC_LOOKOUT("Scenic Lookout",   "4bf58dd8d48988d165941735");
	
	
											
											
	private String foursquareCategoryName;
	private String foursquareCategoryId;
	
	FoursquareCategory(String foursquareCategoryName, String foursquareCategoryId){
		this.foursquareCategoryId = foursquareCategoryId;
		this.foursquareCategoryName = foursquareCategoryName;
	}
	
	public String getFoursquareCategoryId(){
		return foursquareCategoryId;
	}
	
	public String getFoursquareCategoryName(){
		return foursquareCategoryName;
	}

}
