package com.mapinfopage.client.page;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mapinfopage.client.rpc.MessageService;
import com.mapinfopage.client.rpc.MessageServiceAsync;
import com.mapinfopage.client.widget.CategoryListCloseHandler;
import com.mapinfopage.client.widget.CategoryListWidget;
import com.mapinfopage.client.widget.GoogleMapWidget;
import com.mapinfopage.client.widget.InfoTableWidget;
import com.mapinfopage.client.widget.MapMarkerClickHandler;
import com.mapinfopage.client.widget.TimePeriodSliderValueChangeHandler;
import com.mapinfopage.client.widget.TimePeriodSliderWidget;
import com.mapinfopage.shared.Category;
import com.mapinfopage.shared.MapInformationDO;
import com.mapinfopage.shared.MessageDO;
import com.mapinfopage.shared.TableInformationDO;
import com.mapinfopage.shared.LocationInformationDO;

public class MapInfoPage
extends Composite {

	
	////////////////////////////////////////////////////////////
	// PRIVATE CLASS VARIABLES
	////////////////////////////////////////////////////////////
	private static SliderTimePeriod[] sliderTimePeriods
	  = { new SliderTimePeriod("1 Day", 1),
		  new SliderTimePeriod("1 Week", 7),
		  new SliderTimePeriod("2 Weeks", 14),
		  new SliderTimePeriod("1 Month", 30),
		  new SliderTimePeriod("2 Months", 60),
		  new SliderTimePeriod("3 Months", 90),
		  new SliderTimePeriod("6 Months", 180),
		  new SliderTimePeriod("1 Year", 365)
	    };

	
	////////////////////////////////////////////////////////////
	// WIDGET SETUP
	////////////////////////////////////////////////////////////
	interface MapInfoPageUiBinder extends UiBinder<Widget, MapInfoPage> {}
	private static MapInfoPageUiBinder uiBinder = GWT.create(MapInfoPageUiBinder.class);

	
	////////////////////////////////////////////////////////////
	// UI ELEMENTS
	////////////////////////////////////////////////////////////
	@UiField LayoutPanel mapContainer;
	@UiField GoogleMapWidget map;
	@UiField TimePeriodSliderWidget timePeriodSlider;
	@UiField InfoTableWidget infoTable;
	
	
	////////////////////////////////////////////////////////////
	// PRIVATE INSTANCE VARIABLES
	////////////////////////////////////////////////////////////
	private MessageServiceAsync messageService = GWT.create(MessageService.class);
	
	private Timer dataRefreshTimer;
	private String selectedMarkerId;
	private SliderTimePeriod selectedSliderTimePeriod;
	private Timestamp lastTweetUpdateTimestamp= new Timestamp((new Date()).getTime());
	private CategoryListWidget categoryList;
	
	////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	////////////////////////////////////////////////////////////
	public MapInfoPage() {
		
		initWidget(uiBinder.createAndBindUi(this));
		initUI();
		refreshDataForNewSliderValue();

	}

	
	////////////////////////////////////////////////////////////
	// DATA REFRESH HELPER METHODS
	////////////////////////////////////////////////////////////
	/**
	 * Refresh the data based on a new slider value.
	 */
	private void refreshDataForNewSliderValue() {
		
		stopDataRefreshTimer();
		this.infoTable.clear();
		this.map.clearAllMarkers();

		messageService.slider(selectedSliderTimePeriod.getDays(), selectedMarkerId,
	               			  new AsyncCallback<MessageDO>() {
				@Override
				public void onSuccess(MessageDO message) {
					populateNewData(message);
					startDataRefreshTimer();
				}
				@Override
				public void onFailure(Throwable caught) {
					GWT.log("Error in messageService.slider", caught);
				}
			}
		);

	}

	
	/**
	 * Refresh the data based on a map marker click.
	 */
	private void refreshDataForMapMarkerClick(String markerId,
									            final String markerLabel,
									            final int clickXCoordinate,
									            final int clickYCoordinate) {
		
		
		hideCategoryList();
		stopDataRefreshTimer();
		this.infoTable.clear();
		
		this.selectedMarkerId = markerId;

		messageService.userClickedMarkerOnMap(selectedSliderTimePeriod.getDays(), markerId,
	               			                  new AsyncCallback<LocationInformationDO>() {
				@Override
				public void onSuccess(LocationInformationDO locationInformationDO) {
					populateNewData(locationInformationDO.getTableInformationList());
					showCategoryList(locationInformationDO.getCategoryList(),
							         markerLabel,
							         clickXCoordinate,
							         clickYCoordinate);
					startDataRefreshTimer();
				}
				@Override
				public void onFailure(Throwable caught) {
					GWT.log("Error in messageService.userClickedMarkerOnMap", caught);
				}
			}
		);

	}

	
	/**
	 * Set up and start a timer to periodically refresh the data.
	 */
	private void startDataRefreshTimer() {
		
		if (null != this.dataRefreshTimer) {
			stopDataRefreshTimer();
		}
		
		this.dataRefreshTimer = new Timer() {
			@Override
			public void run() {
				messageService.refresh(selectedSliderTimePeriod.getDays(),
						               selectedMarkerId, lastTweetUpdateTimestamp,
						               new AsyncCallback<MessageDO>() {
						@Override
						public void onSuccess(MessageDO message) {
							map.clearAllMarkers();
							populateNewData(message);
						}
						@Override
						public void onFailure(Throwable caught) {
							GWT.log("Error in messageService.refresh", caught);
						}
					}
				);
				
			}
		};
		this.dataRefreshTimer.scheduleRepeating(60000);
		
	}
	
	
	/**
	 * Stop the data refresh timer.
	 */
	private void stopDataRefreshTimer() {
		if (null != this.dataRefreshTimer) {
			this.dataRefreshTimer.cancel();
			this.dataRefreshTimer = null;
		}
	}

	
	/**
	 * Populate new data received from the server onto the page.
	 */
	private void populateNewData(MessageDO message) {
		
		if (null != message) {
			
			//set the timestamp to the max timestamp of the tweet 
			lastTweetUpdateTimestamp = new Timestamp((new Date()).getTime());
			
			if ((null != message.getMapInformationList()) &&
			    (!message.getMapInformationList().isEmpty())) {
				for (MapInformationDO markerData : message.getMapInformationList()) {
					this.map.addMarker(markerData.getMarkerId(),
							           markerData.getLatitude(),
							           markerData.getLongitude(),
							           markerData.getDisplayText());
				}
				//this.map.fitBoundsToMarkers();
			}
			if (null != message.getTableInformationList()) {
				this.infoTable.addCellsToTop(message.getTableInformationList());
			}
		}
		
	}

	
	/**
	 * Populate new data received from the server onto the page.
	 */
	private void populateNewData(List<TableInformationDO> tableInfoList) {
		
		if (null != tableInfoList) {
			this.infoTable.addCellsToTop(tableInfoList);
		}
		
	}
	
	
	
	////////////////////////////////////////////////////////////
	// CATEGORY HELPER METHODS
	////////////////////////////////////////////////////////////
	/**
	 * Show the category list to the user.
	 */
	private void showCategoryList(List<Category> categories,
						            String markerLabel,
						            int clickXCoordinate,
						            int clickYCoordinate) {
		
		this.categoryList = new CategoryListWidget(categories, markerLabel);
		this.categoryList.setCloseHandler(new CategoryListCloseHandler() {
			@Override
			public void closeRequested() {
				hideCategoryList();
			}
		});
		
		// Figure out the x- and y-coordinates at which to show the widget.
				// Always make sure that the top-right corner is visible, since that
				// has the close button
				int topLeftX = clickXCoordinate;
				if ((topLeftX + CategoryListWidget.PREFERRED_WIDTH) > this.map.getOffsetWidth()) {
					topLeftX = this.map.getOffsetWidth() - CategoryListWidget.PREFERRED_WIDTH;
				}
				int topLeftY = clickYCoordinate;
				if ((topLeftY + CategoryListWidget.PREFERRED_HEIGHT) > this.map.getOffsetHeight()) {
					topLeftY = this.map.getOffsetHeight() - CategoryListWidget.PREFERRED_HEIGHT;
				}
				if (topLeftY < 0) {
					topLeftY = 0;
				}
				
				// Show the widget
				this.mapContainer.add(this.categoryList);
				this.mapContainer.setWidgetLeftWidth(this.categoryList,
						                             topLeftX, Unit.PX,
						                             CategoryListWidget.PREFERRED_WIDTH, Unit.PX);
				this.mapContainer.setWidgetTopHeight(this.categoryList,
						                             topLeftY, Unit.PX,
						                             CategoryListWidget.PREFERRED_HEIGHT, Unit.PX);
		
	}
	
	
	/**
	 * Hide the category list if it is currently displayed.  Do
	 * nothing if the category list is not currently displayed.
	 */
	private void hideCategoryList() {
		
		if (null != this.categoryList) {
			this.mapContainer.remove(this.categoryList);
			this.categoryList = null;
		}
		
	}
	

	
	////////////////////////////////////////////////////////////
	// UI STATE HELPER METHODS
	////////////////////////////////////////////////////////////
	/**
	 * Initialize the user interface for the first time.
	 */
	private void initUI() {
		
		this.categoryList = null;
		initMap();
		initSlider();
		
	}

	
	
	/**
	 * Initialize the map.
	 */
	private void initMap() {
		
		this.map.setMapMarkerClickHandler(new MapMarkerClickHandler() {
			@Override
			public void markerClicked(Object markerId,
					                  String markerLabel,
					                  int clickXCoordinate,
					                  int clickYCoordinate) {
				
				refreshDataForMapMarkerClick((String)markerId,
						                     markerLabel,
						                     clickXCoordinate,
						                     clickYCoordinate);
				
			}
		});

	}
	
	
	/**
	 * Initialize the slider.
	 */
	private void initSlider() {
		
		this.selectedSliderTimePeriod = sliderTimePeriods[this.timePeriodSlider.getCurrentSliderValue()];
		this.timePeriodSlider.setSliderValueChangeHandler(new TimePeriodSliderValueChangeHandler() {
			@Override
			public void valueChanged(int newValue) {
				selectedSliderTimePeriod = sliderTimePeriods[newValue];
				refreshDataForNewSliderValue();
			}
		});
				
	}

	
}
