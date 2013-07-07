package com.mooseinspace.shipbuilder.client.layouts.admin;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.mooseinspace.shipbuilder.client.gridobj.GridObjHelper;
import com.mooseinspace.shipbuilder.client.gridobj.GridObjImage;
import com.mooseinspace.shipbuilder.client.layouts.PageLayout;
import com.mooseinspace.shipbuilder.client.layouts.popup.ImageListPopup;
import com.mooseinspace.shipbuilder.client.loaders.AdminService;
import com.mooseinspace.shipbuilder.client.loaders.AdminServiceAsync;
import com.mooseinspace.shipbuilder.shared.PlayerInfo;
import com.mooseinspace.shipbuilder.shared.ShipHull;
import com.mooseinspace.shipbuilder.shared.TraderImage;
import com.mooseinspace.shipbuilder.shared.Vector;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

public class AdminMakeHullLayout implements PageLayout {

	VerticalPanel verticalPanel;
	
	@Override
	public void showLayout(PlayerInfo playerInfo) 
	{
		showLayout(playerInfo, RootPanel.get());

	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void showLayout(PlayerInfo playerInfo, final Panel rootPanel) 
	{
		rootPanel.clear();
		
		FlowPanel backPanel = new FlowPanel();
		backPanel.setSize("100%", "100%");
		rootPanel.add(backPanel);
		
		verticalPanel = new VerticalPanel();
		backPanel.add(verticalPanel);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.add(horizontalPanel);
		
		Label lblNewLabel = new Label("Hull Name");
		lblNewLabel.setStyleName("label");
		horizontalPanel.add(lblNewLabel);
		
		final TextBox textBox = new TextBox();
		textBox.setStyleName("textBox");
		horizontalPanel.add(textBox);
		textBox.setWidth("326px");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.add(horizontalPanel_1);
		
		Canvas hullImageCanvas = Canvas.createIfSupported();
		if (hullImageCanvas == null)
		{
			horizontalPanel_1.add(new Label("Use a modern bowser please!"));
			return;
		}
		
		final GridObjHelper gridObjHelper = new GridObjHelper(hullImageCanvas);
		final ShipHull newHull = new ShipHull();
		
		Button chooseImageButton = new Button("Choose Image");
		chooseImageButton.addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event) 
			{
				final ImageListPopup imageList = new ImageListPopup("ship");
				
				Handler selectHandler = new SelectionChangeEvent.Handler() 
			    {
			    	public void onSelectionChange(SelectionChangeEvent event) 
			    	{
			    		SingleSelectionModel<TraderImage> selectionModel = (SingleSelectionModel<TraderImage>) imageList.getCellList().getSelectionModel();
			    		TraderImage selected = selectionModel.getSelectedObject();
			    		if (selected != null) 
			    		{
			    			newHull.setObjImage(selected);
			    			
			    			gridObjHelper.draw(new GridObjImage(newHull), new Vector(0,0), true);
			    			imageList.hide();
			    		}
			    	}
			    };
			    
			    imageList.setSelectionHandler(selectHandler);
			    imageList.show();
			}
		});
		
		horizontalPanel_1.add(chooseImageButton);
		chooseImageButton.setSize("111px", "31px");
		chooseImageButton.setStyleName("button");
		
		hullImageCanvas.setStyleName("gridObjCanvas");
		horizontalPanel_1.add(hullImageCanvas);
		hullImageCanvas.setSize("150px", "150px");
		
		Button uploadButton = new Button("Upload");
		uploadButton.addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event) 
			{
				newHull.setShipHullName(textBox.getText());
				
				AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>()
						{

							@Override
							public void onFailure(Throwable caught)
							{
								Window.alert("Image Load didn't work: " + caught.getMessage());
								
							}

							@Override
							public void onSuccess(Boolean result) 
							{
								Label uploadLbl = new Label("Unit Abv.");
								uploadLbl.setStyleName("warningLabel");
								
								if (result)
					  			{
					  				uploadLbl.setText("Upload Success");
					  			}
					  			else
					  			{
					    		  	uploadLbl.setText("Upload Failed!");
					  			}
								
								verticalPanel.add(uploadLbl);
								
							}
							
						};
						
				AdminServiceAsync adminSvc = GWT.create(AdminService.class);
				adminSvc.addShipHull(newHull, callback);
			}
		});
		uploadButton.setStyleName("button");
		verticalPanel.add(uploadButton);
		uploadButton.setWidth("99px");

	}

}


