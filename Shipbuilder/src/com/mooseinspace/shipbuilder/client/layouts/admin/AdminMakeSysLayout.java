package com.mooseinspace.shipbuilder.client.layouts.admin;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.mooseinspace.shipbuilder.client.gridobj.GridObjHelper;
import com.mooseinspace.shipbuilder.client.gridobj.GridObjImage;
import com.mooseinspace.shipbuilder.client.layouts.PageLayout;
import com.mooseinspace.shipbuilder.client.layouts.popup.ImageListPopup;
import com.mooseinspace.shipbuilder.client.loaders.AdminService;
import com.mooseinspace.shipbuilder.client.loaders.AdminServiceAsync;
import com.mooseinspace.shipbuilder.shared.PlayerInfo;
import com.mooseinspace.shipbuilder.shared.TraderImage;
import com.mooseinspace.shipbuilder.shared.Vector;
import com.mooseinspace.shipbuilder.shared.systems.ShipSystem;
import com.mooseinspace.shipbuilder.shared.systems.SystemProperty;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

public class AdminMakeSysLayout implements PageLayout 
{
	Panel rootPanel;
	
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
		rootPanel.add(backPanel);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		backPanel.add(horizontalPanel);
		horizontalPanel.setHeight("369px");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		horizontalPanel.add(verticalPanel);
		
		// System Name Input
		Label lblNewLabel = new Label("System Name");
		verticalPanel.add(lblNewLabel);
		lblNewLabel.setStyleName("label");
		
		final TextBox sysNameText = new TextBox();
		verticalPanel.add(sysNameText);
		sysNameText.setStyleName("textBox");
		sysNameText.setWidth("400px");
		
		// System Properties Input
		Label lblNewLabel_1 = new Label("System Properties");
		verticalPanel.add(lblNewLabel_1);
		lblNewLabel_1.setStyleName("label");
		
		// ******* The Property line ********
		HorizontalPanel horzPanel = new HorizontalPanel();
		verticalPanel.add(horzPanel);
		horzPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		final ListBox propList = new ListBox();
		propList.setStyleName("textBox");
		propList.setVisibleItemCount(1);
		horzPanel.add(propList);
		propList.setStyleName("textBox");
		
		Label lblNewLabel_2 = new Label("Value");
		lblNewLabel_2.setStyleName("label");
		horzPanel.add(lblNewLabel_2);
		
		final TextBox propValue = new TextBox();
		propValue.setStyleName("textBox");
		horzPanel.add(propValue);
		
		Button btnNewButton = new Button("Add Property");
		
		btnNewButton.setStyleName("button");
		horzPanel.add(btnNewButton);
		btnNewButton.setSize("93px", "33px");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.add(horizontalPanel_1);
		
		final ListBox systemPropAdded = new ListBox();
		horizontalPanel_1.add(systemPropAdded);
		systemPropAdded.setStyleName("textBox");
		systemPropAdded.setWidth("255px");
		systemPropAdded.setVisibleItemCount(5);
		
		Button removePropButton = new Button("Remove Property");
		removePropButton.setStyleName("button");
		horizontalPanel_1.add(removePropButton);
		removePropButton.setHeight("35px");
		
		Button submitButton = new Button("Upload");
		verticalPanel.add(submitButton);
		
		submitButton.setStyleName("button");
		submitButton.setSize("93px", "33px");
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.add(verticalPanel_1);
		
		Button imgChooseButton = new Button("Choose Image");
		imgChooseButton.setStyleName("button");
		verticalPanel_1.add(imgChooseButton);
		imgChooseButton.setSize("120px", "27px");
		
		Canvas outerGridCanvas = Canvas.createIfSupported();
		outerGridCanvas.setStyleName("gridObjCanvas");
		verticalPanel_1.add(outerGridCanvas);
		outerGridCanvas.setSize("150px", "150px");
		
		Canvas innerGridCanvas = Canvas.createIfSupported();
		innerGridCanvas.setStyleName("gridObjCanvas");
		verticalPanel_1.add(innerGridCanvas);
		innerGridCanvas.setSize("150px", "150px");
		
		/**********************************************************/
		/**** Layout Done ****************************************/
		
		final GridObjHelper innerGridHelper = new GridObjHelper(innerGridCanvas);
		final GridObjHelper outerGridHelper = new GridObjHelper(outerGridCanvas);
		final ShipSystem newShipSystem = new ShipSystem();
		
		//Add property button handler
		btnNewButton.addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event)
			{
				if (propValue.getText().isEmpty() || String.valueOf(propValue.getText()) == null)
				{
					Window.alert("Add a Property Value");
				}
				else
				{
					try
					{
						if (newShipSystem.addProperty(propList.getItemText(propList.getSelectedIndex()), Integer.parseInt(propValue.getText())))
							systemPropAdded.addItem(propList.getItemText(propList.getSelectedIndex()));
					}
					catch (NumberFormatException e)
					{
						Window.alert("Enter an Integer Value Only");
					}
				}
			}
		});
		
		//Remove Property button handler
		removePropButton.addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event) 
			{
				int index = systemPropAdded.getSelectedIndex();
				if (index > -1)
				{
					newShipSystem.removeProperty(index);
					systemPropAdded.removeItem(index);
				}
			}
		});
		
		//Property List Selection Handler
		systemPropAdded.addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event) 
			{
				int index = systemPropAdded.getSelectedIndex();
				
				if (index == -1)
					return;
				
				String propName = newShipSystem.getPropertyNames().get(index);
				propValue.setText(newShipSystem.getPropertyValues().get(index).toString());

				for (int i = 0; i < propList.getItemCount(); i++)
				{
					if (propList.getItemText(i).equals(propName))
					{
						propList.setItemSelected(i, true);
						break;
					}
				}
			}
		});
		
		//Image Choose Button Handler
		imgChooseButton.addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event) 
			{
				final ImageListPopup imageList = new ImageListPopup("system");
				
				Handler selectHandler = new SelectionChangeEvent.Handler() 
			    {
			    	public void onSelectionChange(SelectionChangeEvent event) 
			    	{
			    		SingleSelectionModel<TraderImage> selectionModel = (SingleSelectionModel<TraderImage>) imageList.getCellList().getSelectionModel();
			    		TraderImage selected = selectionModel.getSelectedObject();
			    		if (selected != null) 
			    		{
			    			newShipSystem.setObjImage(selected);
			    			newShipSystem.setObjConfig(ShipSystem.SMALLER_OUTER_GRID_CONFIG);
			    			newShipSystem.setImgGrid(null);

			    			outerGridHelper.draw(new GridObjImage(newShipSystem), new Vector(0,0), true);
			    			
			    			newShipSystem.setObjConfig(ShipSystem.LARGE_INNER_GRID_CONFIG);
			    			newShipSystem.setImgGrid(null);
			    			
			    			innerGridHelper.draw(new GridObjImage(newShipSystem), new Vector(0,0), true);
			    			imageList.hide();
			    		}
			    	}
			    };
			    
			    imageList.setSelectionHandler(selectHandler);
			    imageList.show();
			}
		});
		
		//Submit Button
		submitButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event)
			{
				if (sysNameText.getText().isEmpty() || newShipSystem.getPropertyNames().size() == 0)
				{
					Window.alert("Name the System and add at least one Property");
				}
				else
				{
					newShipSystem.setSystemName(sysNameText.getText());
					AdminServiceAsync adminSvc = GWT.create(AdminService.class);
					adminSvc.addShipSystem(newShipSystem, AdminLayout.getUploadCallback(rootPanel));
				}
			}
		});
		
		//Get all the system properties for the dropdown list
		AsyncCallback<List<SystemProperty>> callback = new AsyncCallback<List<SystemProperty>>() 
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(caught.getMessage());
			}

			public void onSuccess(List<SystemProperty> result) 
			{
				for (SystemProperty sysProp : result)
				{
					propList.addItem(sysProp.getPropertyName());
				}
			}
		};
				
		AdminServiceAsync adminSvc = GWT.create(AdminService.class);
		adminSvc.getAllProperties(callback);
	}

}
