package com.mapinfopage.client.widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mapinfopage.shared.TableInformationDO;

public class InfoTableWidget
extends Composite {

	
	////////////////////////////////////////////////////////////
	// CLASS VARIABLES
	////////////////////////////////////////////////////////////
	private static final int CELL_WIDTH = 330;
	private static final int CELL_HEIGHT = 120;
	private static final int SLIDE_DURATION = 2000;
	private static final int FADE_DURATION = 2000;

	
	////////////////////////////////////////////////////////////
	// WIDGET SETUP
	////////////////////////////////////////////////////////////
	interface InfoTableWidgetUiBinder extends UiBinder<Widget, InfoTableWidget> {}
	private static InfoTableWidgetUiBinder uiBinder = GWT.create(InfoTableWidgetUiBinder.class);

	
	////////////////////////////////////////////////////////////
	// UI ELEMENTS
	////////////////////////////////////////////////////////////
	@UiField LayoutPanel cellContainer;
	@UiField Image image;

	
	////////////////////////////////////////////////////////////
	// PRIVATE INSTANCE VARIABLEs
	////////////////////////////////////////////////////////////
	private List<InfoTableCell> cells;

	
	////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	////////////////////////////////////////////////////////////
	public InfoTableWidget() {
		
		initWidget(uiBinder.createAndBindUi(this));
		initUI();
		
	}

	
	////////////////////////////////////////////////////////////
	// PUBLIC INTERFACE METHODS
	////////////////////////////////////////////////////////////
	/**
	 * Add one cell to the top of the table.  If any cells
	 * exist, this method will slide them down first; then it
	 * will fade in the new cell.
	 */
	public void addCellToTop(TableInformationDO newInfo) {
		
		List<TableInformationDO> newInfos = new ArrayList<TableInformationDO>();
		newInfos.add(newInfo);
		addCellsToTop(newInfos);
		
	}
	
	
	/**
	 * Add one or more cells to the top of the table.  If any
	 * cells exist, this method will slide them down first;
	 * then it will fade in the new cells.
	 */
	public void addCellsToTop(List<TableInformationDO> newInfos) {
		
		if ((null != newInfos) &&
			(newInfos.size() > 0)) {

			List<InfoTableCell> newCells = new ArrayList<InfoTableCell>();
			for (TableInformationDO info : newInfos) {
				newCells.add(new InfoTableCell(info.getImageURL(),
                                               info.getUserName(),
                                               info.getText(),
                                               info.getTimeCreated()));
			}
			
			if (!this.cells.isEmpty()) {
				slideCellsDownAndAnimateNewCell(newCells);
			} else {
				animateNewCell(newCells);
			}

		}


	}
	
	
	/**
	 * Clear all cells from the table.
	 */
	public void clear() {

		for (InfoTableCell cell : this.cells) {
			this.cellContainer.remove(cell);
		}
		this.cells.clear();
		
	}
	
	
	////////////////////////////////////////////////////////////
	// ANIMATION HELPER METHODS
	////////////////////////////////////////////////////////////
	/**
	 * Slide the existing cells down so that there is room for
	 * another.  When the slide animation is done, start the
	 * animation to show the new cell.
	 */
	private void slideCellsDownAndAnimateNewCell(final List<InfoTableCell> newCells) {
		
		if ((null != this.cells) &&
		    (!this.cells.isEmpty())) {
			// Set the new top for each of the cells
			for (InfoTableCell cell : this.cells) {
				int newTop = cell.getTop() + (CELL_HEIGHT * newCells.size());
				cell.setTop(newTop);
				this.cellContainer.setWidgetTopHeight(cell, newTop, Unit.PX, CELL_HEIGHT, Unit.PX);
			}
			// Start the slide animation
			this.cellContainer.animate(SLIDE_DURATION,
					                   new AnimationCallback() {
				@Override
				public void onLayout(Layer layer, double progress) {
				}
				@Override
				public void onAnimationComplete() {
					removeScrolledOffCells();
					animateNewCell(newCells);
				}
			});
		}
		
	}
	
	
	/**
	 * Add a new cell to the list and animate it into the widget.
	 */
	private void animateNewCell(List<InfoTableCell> newCells) {

		int cellCount = 0;
		for (final InfoTableCell newCell : newCells) {
			cellCount++;
			newCell.setTop(CELL_HEIGHT * (cellCount-1));
			this.cells.add(newCell);
			this.cellContainer.add(newCell);
			this.cellContainer.setWidgetTopHeight(newCell, newCell.getTop(), Unit.PX, CELL_HEIGHT, Unit.PX);
			this.cellContainer.setWidgetLeftWidth(newCell, 0, Unit.PX, CELL_WIDTH, Unit.PX);
			Animation animation = new Animation() {
				@Override
				protected void onUpdate(double progress) {
					newCell.getElement()
					       .getStyle()
					       .setProperty("opacity",
					    		        String.valueOf(progress));
					newCell.getElement()
					       .getStyle()
					       .setProperty("filter",
					    		        "alpha(opacity = "
					    		          + String.valueOf(progress * ((double)100))
					    		          + ")");
				}
				@Override
			    protected void onComplete() {
					newCell.getElement()
					       .getStyle()
					       .setProperty("opacity", "1");
					newCell.getElement()
				       .getStyle()
				       .setProperty("filter",
				    		        "alpha(opacity = 100)");
				}
			};
			animation.run(FADE_DURATION);
		}
		
	}

	
	/**
	 *  Remove any cells that are now scrolled off
	 *  the bottom of the window.
	 */
	private void removeScrolledOffCells() {
		
		Iterator<InfoTableCell> i = this.cells.iterator();
		while (i.hasNext()) {
			InfoTableCell cell = i.next();
			if (cell.getAbsoluteTop() > Window.getClientHeight()) {
				this.cellContainer.remove(cell);
				i.remove();
			}
		}
		
	}


	
	////////////////////////////////////////////////////////////
	// UI STATE HELPER METHODS
	////////////////////////////////////////////////////////////
	/**
	 * Initialize the user interface for the first time.
	 */
	private void initUI() {
		
		this.cells = new ArrayList<InfoTableCell>();
		this.image.setUrl("images/vacationtweets_y.jpg");
		this.image.setHeight("100%");
		this.image.setWidth("100%");
		
	}	
	
	
}
