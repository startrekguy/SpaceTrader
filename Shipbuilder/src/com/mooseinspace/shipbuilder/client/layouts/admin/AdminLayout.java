package com.mooseinspace.shipbuilder.client.layouts.admin;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.mooseinspace.shipbuilder.client.layouts.PageLayout;
import com.mooseinspace.shipbuilder.client.loaders.AdminService;
import com.mooseinspace.shipbuilder.client.loaders.AdminServiceAsync;
import com.mooseinspace.shipbuilder.shared.PlayerInfo;
import com.mooseinspace.shipbuilder.shared.systems.ShipSystem;
import com.mooseinspace.shipbuilder.shared.systems.SystemProperty;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class AdminLayout implements PageLayout 
{
	public static final String ADMIN_TOKEN = "admin";
	
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
	public void showLayout(final PlayerInfo playerInfo, Panel rootPanel) 
	{
		this.playerInfo = playerInfo;
		
//		RootPanel.get().clear();
		
		//***********************
		//****  Load Layout *****
		//***********************
//		RootPanel rootPanel = RootPanel.get();
		rootPanel.clear();
		rootPanel.setStyleName("blackBack");
		rootPanel.setSize("100%", "100%");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		rootPanel.add(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		VerticalPanel addVertPanel = new VerticalPanel();
		verticalPanel.add(addVertPanel);
		addVertPanel.setSize("750px", "100%");
		
		MenuBar menuBar = new MenuBar(false);
		menuBar.setStyleName("menuBar");
		addVertPanel.add(menuBar);
		
		final Panel flowPanel = new FlowPanel();
		addVertPanel.add(flowPanel);
		flowPanel.setSize("100%", "100%");
		flowPanel.setStyleName("centerBox");
		
		MenuItem createNewSystemMenuItem = new MenuItem("Create New System", false, new Command() 
		{
			public void execute() 
			{
				new AdminMakeSysLayout().showLayout(playerInfo, flowPanel);
			}
		});
		createNewSystemMenuItem.setStyleName("menuBar");
		menuBar.addItem(createNewSystemMenuItem);
		
		MenuItem loadSystemMenuItem = new MenuItem("Load System", false, (Command) null);
		loadSystemMenuItem.setStyleName("menuBar");
		menuBar.addItem(loadSystemMenuItem);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		separator.setWidth("15px");
		
		MenuItem createNewPropertyMenuItem = new MenuItem("Create New Property", false, new Command()
		{
			public void execute()
			{
				new AdminMakePropLayout().showLayout(playerInfo, flowPanel);
			}
		});
		createNewPropertyMenuItem.setStyleName("menuBar");
		menuBar.addItem(createNewPropertyMenuItem);
		
		MenuItem loadPropertyMenuItem = new MenuItem("Load Property", false, (Command) null);
		loadPropertyMenuItem.setStyleName("menuBar");
		menuBar.addItem(loadPropertyMenuItem);
		
		
		
		MenuItemSeparator separator_1 = new MenuItemSeparator();
		menuBar.addSeparator(separator_1);
		separator_1.setWidth("15px");
		
		MenuItem mntmCreateNewHull = new MenuItem("Create New Hull", false, new Command() 
		{
			public void execute() 
			{
				new AdminMakeHullLayout().showLayout(playerInfo, flowPanel);
			}
		});
		mntmCreateNewHull.setStyleName("menuBar");
		menuBar.addItem(mntmCreateNewHull);
		
		MenuItemSeparator separator_2 = new MenuItemSeparator();
		menuBar.addSeparator(separator_2);
		separator_2.setWidth("15px");
		
		MenuItem mntmNewItem = new MenuItem("Upload Picture", false, new Command() 
		{
			public void execute() 
			{
				new AdminImgLoadLayout().showLayout(playerInfo, flowPanel);
			}
		});
		mntmNewItem.setStyleName("menuBar");
		menuBar.addItem(mntmNewItem);
		
	}
	
	public static AsyncCallback<Boolean> getUploadCallback(final Panel widgetPanel)
	{
		
		final Label uploadLbl = new Label("Unit Abv.");
		uploadLbl.setStyleName("warningLabel");
		
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() 
		{
		      	public void onFailure(Throwable caught)
		      	{
		      		uploadLbl.setText(caught.getMessage());
		      	}

		      	public void onSuccess(Boolean result) 
		      	{
		  			if (result)
		  			{
		  				uploadLbl.setText("Upload Success");
		  			}
		  			else
		  			{
		    		  	uploadLbl.setText("Upload Failed!");
		  			}
		  			
		  			widgetPanel.add(uploadLbl);
		      	}

		    };
		    
		return callback;
	}

}
