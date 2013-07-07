	package com.mooseinspace.shipbuilder.client.layouts.popup;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import com.mooseinspace.shipbuilder.client.loaders.AdminService;
import com.mooseinspace.shipbuilder.client.loaders.AdminServiceAsync;
import com.mooseinspace.shipbuilder.shared.TraderImage;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;

public class ImageListPopup extends PopupPanel 
{
	final private CellList<TraderImage> cellList;
	final private ListBox comboBox;
	final private SingleSelectionModel<TraderImage> selectionModel;
	
	AdminServiceAsync adminService = GWT.create(AdminService.class);
	
	final private String defaultPicType;

	public ImageListPopup(String picType)
	{
		super(true);
		
		defaultPicType = picType;
		
		VerticalPanel verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
//		this.setPopupPosition(50, 50);
		
		ImageCell cell = new ImageCell();
		
		comboBox = new ListBox();
		comboBox.addChangeHandler(new ChangeHandler() 
		{
			public void onChange(ChangeEvent event) 
			{
				if (comboBox.getSelectedIndex() == 0)
				{
					adminService.getAllTraderImages(getPicCallback());
				}
				else
				{
					adminService.getAllTraderImages(comboBox.getItemText(comboBox.getSelectedIndex()), getPicCallback());
				}
			}
		});
		verticalPanel.add(comboBox);
		comboBox.setWidth("100%");
		comboBox.addItem("All Images");
		
		cellList = new CellList<TraderImage>(cell);
		
		verticalPanel.add(cellList);
		
		adminService.getAllImageTypes(getTypeCallback());
		
		selectionModel = new SingleSelectionModel<TraderImage>();
	    cellList.setSelectionModel(selectionModel);
	    
		cellList.setSize("253px", "356px");
	}
	
	public void setSelectionHandler(Handler selectionHandler)
	{
		selectionModel.addSelectionChangeHandler(selectionHandler);
	}
	
	/**
	 * @return the cellList
	 */
	public CellList<TraderImage> getCellList() 
	{
		return cellList;
	}
	
	private AsyncCallback<List<TraderImage>> getPicCallback()
	{
		AsyncCallback<List<TraderImage>> callback = new AsyncCallback<List<TraderImage>>()
		{

			@Override
			public void onFailure(Throwable caught)
			{
				Window.alert("Image Load didn't work: " + caught.getMessage());
				
			}

			@Override
			public void onSuccess(List<TraderImage> result) 
			{
				cellList.setRowData(result);
				
			}
			
		};
		
		return callback;
	}
	
	private AsyncCallback<List<String>> getTypeCallback()
	{
		AsyncCallback<List<String>> callback = new AsyncCallback<List<String>>()
		{

			@Override
			public void onFailure(Throwable caught)
			{
				Window.alert("Image Type Load didn't work: " + caught.getMessage());
				
			}

			@Override
			public void onSuccess(List<String> result) 
			{
				boolean useFilterType = false;
				
				for (String imgType : result)
				{
					comboBox.addItem(imgType);
					
					if (imgType.equals(defaultPicType))
						useFilterType = true;
				}
				
				if (useFilterType)
					adminService.getAllTraderImages(defaultPicType, getPicCallback());
				else
					adminService.getAllTraderImages(getPicCallback());
				
			}
			
		};
		
		return callback;
	}
}

