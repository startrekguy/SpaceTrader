package com.mooseinspace.shipbuilder.client.layouts;

import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.mooseinspace.shipbuilder.client.gridobj.GridObjHelper;
import com.mooseinspace.shipbuilder.client.gridobj.GridObjImage;
import com.mooseinspace.shipbuilder.client.gridobj.ShipViewCanvasControl;
import com.mooseinspace.shipbuilder.client.layouts.popup.FileLoadPopup;
import com.mooseinspace.shipbuilder.client.layouts.popup.HullListPopup;
import com.mooseinspace.shipbuilder.shared.PlayerInfo;
import com.mooseinspace.shipbuilder.shared.ShipHull;
import com.mooseinspace.shipbuilder.shared.SpaceShip;
import com.mooseinspace.shipbuilder.shared.Vector;
import com.mooseinspace.shipbuilder.shared.systems.ShipSystem;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.cellview.client.CellList;
import com.mooseinspace.shipbuilder.client.layouts.popup.HullCell;
import com.mooseinspace.shipbuilder.client.loaders.BlobService;
import com.mooseinspace.shipbuilder.client.loaders.BlobServiceAsync;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.mooseinspace.shipbuilder.client.layouts.popup.SystemCell;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;


public class ShipViewLayout implements PageLayout {
	
	public static final String SHIPVIEW_TOKEN = "shipview";
	
	GridObjHelper editor;
	PlayerInfo playerInfo;
	
	@Override
	public void showLayout(PlayerInfo playerInfo) 
	{
		showLayout(playerInfo, RootPanel.get());
		
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void showLayout(final PlayerInfo playerInfo, Panel rootPanel) {
		
		this.playerInfo = playerInfo;
		
//		RootPanel.get().clear();
		
		//***********************
		//****  Load Layout *****
		//***********************
//		RootPanel rootPanel = RootPanel.get();
		rootPanel.clear();
		rootPanel.setStyleName("blackBack");
		rootPanel.setSize("100%", "100%");
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_1.setStyleName("blackBack");
		rootPanel.add(verticalPanel_1);
		verticalPanel_1.setSize("100%", "100%");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_1.add(verticalPanel);
		verticalPanel.setStyleName("blackBack");
		verticalPanel.setSize("1000px", "100%");
		
		MenuBar menuBar = new MenuBar(false);
		menuBar.setStyleName("gridObjCanvas");
		verticalPanel.add(menuBar);
		menuBar.setWidth("100%");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(3, Unit.EM);
		
		HullCell cell = new HullCell();
		final CellList<ShipHull> hullCellList = new CellList<ShipHull>(cell);
		tabLayoutPanel.add(hullCellList, "Hulls", false);
		hullCellList.setSize("235px", "100%");
		
		FlowPanel verticalPanel_2 = new FlowPanel();
		tabLayoutPanel.add(verticalPanel_2, "Systems", false);
		verticalPanel_2.setSize("235px", "100%");
		
		ListBox systemTypeSelector = new ListBox();
		systemTypeSelector.setStyleName("textBox");
		verticalPanel_2.add(systemTypeSelector);
		systemTypeSelector.setSize("100%", "30");
		
		final CellList<ShipSystem> systemCellList = new CellList<ShipSystem>(new SystemCell());
		verticalPanel_2.add(systemCellList);
		systemCellList.setSize("100%", "100%");
		horizontalPanel.add(tabLayoutPanel);
		tabLayoutPanel.setSize("250px", "100%");
		
		AbsolutePanel absolutePanel = new AbsolutePanel();
		horizontalPanel.add(absolutePanel);
		absolutePanel.setSize("750px", "750px");
		
		final Canvas shipCanvas = Canvas.createIfSupported();
		absolutePanel.add(shipCanvas, 0, 0);
		shipCanvas.setStyleName("gridObjCanvas");
		shipCanvas.setSize("750px", "750px");
		shipCanvas.setCoordinateSpaceHeight(shipCanvas.getElement().getClientHeight());
		shipCanvas.setCoordinateSpaceWidth(shipCanvas.getElement().getClientWidth());
		
		editor = new GridObjHelper(shipCanvas);
		/**** Layout Done ****************************************/
		
		final ShipViewCanvasControl canvasHelper = new ShipViewCanvasControl(shipCanvas, absolutePanel);
		
		MenuItem loadShipMenu = new MenuItem("New Ship", false, new Command() {
			public void execute() 
			{
				
			}
		});
		menuBar.addItem(loadShipMenu);
		loadShipMenu.setStyleName("menuBar");
		
		MenuItem saveShip = menuBar.addItem("Load New Hull", new Command()
		{
			public void execute()
			{
				
			}
		});
		saveShip.setHTML("Save Ship");
		saveShip.setStyleName("menuBar");
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		separator.setWidth("82.5%");
		
		MenuItem logOff = new MenuItem("Log Off", false, new Command() {
			public void execute() 
			{
				Window.Location.assign(playerInfo.getLogoutURL());
			}
		});
		logOff.setStyleName("menuBar");
		menuBar.addItem(logOff);
		
		/**********************************************************/
		
		final SingleSelectionModel<ShipHull> selectionModel = new SingleSelectionModel<ShipHull>();
		hullCellList.setSelectionModel(selectionModel);
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() 
	    {
	    	public void onSelectionChange(SelectionChangeEvent event) 
	    	{
	    		ShipHull selected = selectionModel.getSelectedObject();
	    		if (selected != null) 
	    		{
	    			//canvas.setPixelSize(selected.getObjImage().getWidth(), selected.getObjImage().getHeight());
	    			Vector centerShip = new Vector(shipCanvas.getOffsetWidth()/2 - selected.getObjImage().getWidth()/2,
	    										   shipCanvas.getOffsetHeight()/2 - selected.getObjImage().getHeight()/2);
	    			canvasHelper.setShipHull(new SpaceShip(selected), centerShip);
	    		}
	    	}
	    });
	    
	    final SingleSelectionModel<ShipSystem> systemSelectionModel = new SingleSelectionModel<ShipSystem>();
		systemCellList.setSelectionModel(systemSelectionModel);
		systemSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() 
	    {
	    	public void onSelectionChange(SelectionChangeEvent event) 
	    	{
	    		ShipSystem selected = systemSelectionModel.getSelectedObject();
	    		if (selected != null) 
	    		{
	    			canvasHelper.setShipSystem(selected);
	    		}
	    	}
	    });
		
		//Get The Hulls for the Hull List
		BlobServiceAsync blobService = GWT.create(BlobService.class);
		
		AsyncCallback<List<ShipHull>> callbackHullList = new AsyncCallback<List<ShipHull>>()
		{

			@Override
			public void onFailure(Throwable caught)
			{
				Window.alert("Hull Load didn't work: " + caught.getMessage());
				
			}

			@Override
			public void onSuccess(List<ShipHull> result) 
			{
				hullCellList.setRowData(result);
				
			}
			
		};
		
		AsyncCallback<List<ShipSystem>> callbackShipSystems = new AsyncCallback<List<ShipSystem>>()
				{

					@Override
					public void onFailure(Throwable caught)
					{
						Window.alert("Hull Load didn't work: " + caught.getMessage());
						
					}

					@Override
					public void onSuccess(List<ShipSystem> result) 
					{
						systemCellList.setRowData(result);
						
					}
					
				};
		
		blobService.getAllShipHulls(callbackHullList);
		blobService.getShipSystems(callbackShipSystems);
	}
	
}
