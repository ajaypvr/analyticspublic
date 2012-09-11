package com.mapinfopage.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class GoogleMapWidget
extends Composite {

	
	////////////////////////////////////////////////////////////
	// WIDGET SETUP
	////////////////////////////////////////////////////////////
	interface GoogleMapWidgetUiBinder extends UiBinder<Widget, GoogleMapWidget> {}
	private static GoogleMapWidgetUiBinder uiBinder = GWT.create(GoogleMapWidgetUiBinder.class);

	
	////////////////////////////////////////////////////////////
	// UI ELEMENTS
	////////////////////////////////////////////////////////////
	@UiField DivElement mapContainer;

	
	////////////////////////////////////////////////////////////
	// PRIVATE INSTANCE VARIABLES
	////////////////////////////////////////////////////////////
	private JavaScriptObject map;
	private JavaScriptObject mapOverlay;
	private JavaScriptObject mapBounds;
	private List<JavaScriptObject> markers;
	private MapMarkerClickHandler markerClickHandler;

	
	////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	////////////////////////////////////////////////////////////
	public GoogleMapWidget() {
		
		initWidget(uiBinder.createAndBindUi(this));
		initUI();

	}

	
	////////////////////////////////////////////////////////////
	// PUBLIC INTERFACE METHODS
	////////////////////////////////////////////////////////////
	/**
	 * Set the handler to react to map marker clicks.
	 */
	public void setMapMarkerClickHandler(MapMarkerClickHandler handler) {
		this.markerClickHandler = handler;
	}
	
	
	/**
	 * Add a marker to the map.
	 */
	public void addMarker(Object markerId,
			              double lat,
			              double lng,
			              String label) {
		
		JavaScriptObject marker = buildMarkerObject(this.mapOverlay,
									                markerId,
									                lat,
									                lng,
									                label);
		
		if (null == this.markers) {
			this.markers = new ArrayList<JavaScriptObject>();
		}
		this.markers.add(marker);
		
		this.mapBounds = addMarkerToMap(marker, this.map, this.mapBounds);
		
	}
	
	
	/**
	 * Reset the map bounds to fit all of the markers
	 * displayed on the map.
	 */
	public void fitBoundsToMarkers() {
		
		setMapBounds(this.mapBounds, this.map);
		
	}
	
	
	/**
	 * Clear all the markers from the map.
	 */
	public void clearAllMarkers() {
		
		if (null != this.markers) {	
			for (JavaScriptObject marker : this.markers) {
				removeMarkerFromMap(marker);
			}
			this.markers.clear();
		}
		this.mapBounds = null;
		
	}
	

	
	////////////////////////////////////////////////////////////
	// UI STATE HELPER METHODS
	////////////////////////////////////////////////////////////
	/**
	 * Initialize the user interface for the first time.
	 */
	private void initUI() {
		
		initMap();
		
	}
	
	
	/**
	 * Initialize the map.
	 */
	private void initMap() {
		
		this.map = buildMap(this.mapContainer);
		this.mapOverlay = buildMapOverlay(this.map);
		this.mapBounds = null;
		
	}

	
	////////////////////////////////////////////////////////////
	// ACTION HANDLER METHODS
	////////////////////////////////////////////////////////////
	/**
	 * Report a marker click event to whoever is listening.
	 */
	private void reportMarkerClicked(Object markerId,
						            String markerLabel,
						            int clickXCoordinate,
						            int clickYCoordinate) {

			this.markerClickHandler.markerClicked(markerId,
							                     markerLabel,
							                     clickXCoordinate,
							                     clickYCoordinate);

}

	
	////////////////////////////////////////////////////////////
	// GOOGLE MAPS API HELPER METHODS
	////////////////////////////////////////////////////////////
	/**
	 * Build the JavaScript Map object to hold the map itself.
	 * Also build a LatLngBounds object
	 */
	private final native JavaScriptObject buildMap(Element e)/*-{
	
		var featureStyles = [
			{
				featureType: "all",
				elementType: "labels",
    			stylers: [
    				{ visibility: "off" }
    			]
			}
		];
		var latlng = new $wnd.google.maps.LatLng(40, -95);
		var mapOptions = {
			zoom: 4,
			center: latlng,
			mapTypeId: $wnd.google.maps.MapTypeId.ROADMAP,
			styles: featureStyles,
			streetViewControl: false,
			mapTypeControl: false
		};
		
		var map = new $wnd.google.maps.Map(e, mapOptions);	
	    return map;
	    
	}-*/;
	
	
	/**
	 * Build the JavaScript Overlay object to assist with
	 * projecting latitude-longitude coordinates to
	 * screen x- and y-coordinates.
	 */
	private final native JavaScriptObject buildMapOverlay(JavaScriptObject map)/*-{
		
		var overlay = new $wnd.google.maps.OverlayView();
		overlay.draw = function() {};
		overlay.setMap(map);
		return overlay;
		
	}-*/;

	
	/**
	 * Build a MarkerWithLabel Javascript object.
	 */
	private final native JavaScriptObject buildMarkerObject(JavaScriptObject mapOverlay,
												            Object markerId,
												            double lat,
												            double lng,
												            String label) /*-{
	
	var latlng = new $wnd.google.maps.LatLng(lat, lng);		
	var options = {
		position: latlng, 
		labelContent: label
	};
	var marker = new $wnd.MarkerWithLabel(options);
	marker.setIcon("images/bullet_blue.png");
	marker.__mipMarkerId = markerId;
	var thisObject = this;
	$wnd.google.maps.event.addListener(marker, "click", function() {
		var projection = mapOverlay.getProjection(); 
		var pixel = projection.fromLatLngToContainerPixel(marker.getPosition());
		thisObject.@com.mapinfopage.client.widget.GoogleMapWidget::reportMarkerClicked(Ljava/lang/Object;Ljava/lang/String;II)
		(marker.__mipMarkerId, label, Math.round(pixel.x), Math.round(pixel.y));
	});
	
	return marker;
	
	}-*/;
	
	
	/**
	 * Add a marker to the map.  Update the mapBounds object
	 * to reflect the new marker and return the new bounds back
	 * as the return value from the method.  This method does NOT
	 * resize the map to fit the bounds; that should be done
	 * separately at a convenient time, so that the map is not
	 * constantly resizing as new markers are added.
	 */
	private final native JavaScriptObject addMarkerToMap(JavaScriptObject marker,
			                                             JavaScriptObject map,
			                                             JavaScriptObject mapBounds) /*-{
			                                 	
		marker.setMap(map);

		if (null == mapBounds) {
			var mapBounds = new $wnd.google.maps.LatLngBounds();
		}
		mapBounds.extend(marker.position);
		
		return mapBounds;
		
		
	}-*/;

	
	/**
	 * Remove a marker from the map.
	 */
	private final native void removeMarkerFromMap(JavaScriptObject marker) /*-{
			                                 	
		marker.setMap(null);
		
	}-*/;
	
	
	/**
	 * Reset the map to show everything within the bounds.
	 */
	private final native void setMapBounds(JavaScriptObject mapBounds,
			                               JavaScriptObject map) /*-{

		if (null != mapBounds) {
			map.fitBounds(mapBounds);
		}

	}-*/;
	
	
	/**
	 * Resize the map to fit it parent's window.
	 */
	private final native void resizeMap(JavaScriptObject map) /*-{
		
		if (null != map) {
			$wnd.google.maps.event.trigger(map, 'resize')
		}
		
	}-*/;
}
