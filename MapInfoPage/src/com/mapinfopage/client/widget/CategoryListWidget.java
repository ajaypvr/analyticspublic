package com.mapinfopage.client.widget;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mapinfopage.shared.Category;
import com.mapinfopage.shared.Recommendation;

public class CategoryListWidget
extends Composite {

	
	////////////////////////////////////////////////////////////
	// WIDGET SETUP
	////////////////////////////////////////////////////////////
	interface CategoryListWidgetUiBinder extends UiBinder<Widget, CategoryListWidget> {}
	private static CategoryListWidgetUiBinder uiBinder = GWT.create(CategoryListWidgetUiBinder.class);

	////////////////////////////////////////////////////////////
	// PUBLIC CLASS VARIABLES
	////////////////////////////////////////////////////////////
	public static final int PREFERRED_WIDTH = 390;
	public static final int PREFERRED_HEIGHT = 226;
	
	////////////////////////////////////////////////////////////
	// UI ELEMENTS
	////////////////////////////////////////////////////////////
	@UiField TabLayoutPanel tabLayoutPanel;
	@UiField Image closeButton;
	@UiField Label footerText;
	
	
	////////////////////////////////////////////////////////////
	// PRIVATE INSTANCE VARIABLES
	////////////////////////////////////////////////////////////
	private CategoryListCloseHandler closeHandler;
	
	
	////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	////////////////////////////////////////////////////////////
	public CategoryListWidget(List<Category> categories,
            String markerLabel) {
		
		initWidget(uiBinder.createAndBindUi(this));
		initUI(categories, markerLabel);
		
	}
	
	
	////////////////////////////////////////////////////////////
	// PUBLIC INTERFACE METHODS
	////////////////////////////////////////////////////////////
	/**
	 * Set the handler to react to close requests.
	 */
	public void setCloseHandler(CategoryListCloseHandler handler) {
		this.closeHandler = handler;
	}

	
	////////////////////////////////////////////////////////////
	// UI STATE HELPER METHODS
	////////////////////////////////////////////////////////////
	/**
	 * Initialize the user interface for the first time.
	 */
	private void initUI(List<Category> categories,
            				String markerLabel) {
		
		buildTabs(categories);
		initCloseButton();
		this.footerText.setText(markerLabel);
		
	}
		
	
	/**
	 * Build the tabs based on a list of categories.
	 */
	private void buildTabs(List<Category> categories) {
		
		if (null != categories) {
			for (Category category : categories) {
				CategoryListTabWidget tabWidget = new CategoryListTabWidget(category);
				HTMLPanel itemContainer = new HTMLPanel("");
				if (null != category.getRecommendations()) {
					for (Recommendation recommendation : category.getRecommendations()) {
						itemContainer.add(new CategoryListItemWidget(recommendation));
					}
				}
				this.tabLayoutPanel.add(itemContainer, tabWidget);
			}
		}
		
	}

	
	/**
	 * Initialize the close button.
	 */
	private void initCloseButton() {
		
		this.closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (null != closeHandler) {
					closeHandler.closeRequested();
				}
			}
		});
		
	}

	
}
