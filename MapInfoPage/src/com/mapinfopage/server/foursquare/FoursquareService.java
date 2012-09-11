package com.mapinfopage.server.foursquare;

import java.util.List;
import com.mapinfopage.shared.Category;
import com.mapinfopage.shared.LatitudeLongitude;

public interface FoursquareService {
	
	
	/**
	 * For a list of category get the recommendations
	 * @return
	 */
	public List<Category> getCategoryInformation(LatitudeLongitude latitudeLongitude, List<FoursquareCategory> foursqaureCategoryList);
	
	public Category getCategoryInformation(LatitudeLongitude latitudeLongitude, FoursquareCategory foursquareCategory);

}
