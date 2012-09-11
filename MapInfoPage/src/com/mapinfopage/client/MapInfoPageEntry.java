package com.mapinfopage.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.mapinfopage.client.markerwithlabel.MarkerWithLabelJSBundle;
import com.mapinfopage.client.page.MapInfoPage;


/**
 * GWT entry point for the MapInfoPage app.
 */
public class MapInfoPageEntry
implements EntryPoint {

	
	////////////////////////////////////////////////////////////
	// EVENT HANDLERS
	////////////////////////////////////////////////////////////
	public void onModuleLoad() {
		
		MarkerWithLabelJSBundle bundle = GWT.create(MarkerWithLabelJSBundle.class);
        JSInjector.inject(bundle.markerWithLabelJS().getText());
        
		RootLayoutPanel.get().add(new MapInfoPage());
				
	}

	
}
