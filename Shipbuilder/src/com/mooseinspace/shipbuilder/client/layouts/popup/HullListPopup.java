package com.mooseinspace.shipbuilder.client.layouts.popup;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.mooseinspace.shipbuilder.client.gridobj.GridObjHelper;
import com.mooseinspace.shipbuilder.client.loaders.BlobService;
import com.mooseinspace.shipbuilder.client.loaders.BlobServiceAsync;
import com.mooseinspace.shipbuilder.shared.ShipHull;
import com.mooseinspace.shipbuilder.shared.SpaceShip;

public class HullListPopup extends PopupPanel 
{
	final PopupPanel thisPopup = this;
	
	public HullListPopup(final GridObjHelper shipEditor)
	{
		super(true);
		
		this.setPopupPosition(50, 50);
		
		HullCell cell = new HullCell();
		final CellList<ShipHull> cellList = new CellList<ShipHull>(cell);
		
		BlobServiceAsync blobService = GWT.create(BlobService.class);
		
		AsyncCallback<List<ShipHull>> callback = new AsyncCallback<List<ShipHull>>()
		{

			@Override
			public void onFailure(Throwable caught)
			{
				Window.alert("Hull Load didn't work: " + caught.getMessage());
				
			}

			@Override
			public void onSuccess(List<ShipHull> result) 
			{
				cellList.setRowData(result);
				
			}
			
		};
		
		blobService.getAllShipHulls(callback);
		
		final SingleSelectionModel<ShipHull> selectionModel = new SingleSelectionModel<ShipHull>();
	    cellList.setSelectionModel(selectionModel);
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() 
	    {
	    	public void onSelectionChange(SelectionChangeEvent event) 
	    	{
	    		ShipHull selected = selectionModel.getSelectedObject();
	    		if (selected != null) 
	    		{
//	    			shipEditor.setShip(new SpaceShip(selected));
	    			thisPopup.hide();
	    		}
	    	}
	    });
		
		this.setWidget(cellList);
		cellList.setSize("253px", "356px");

	}
}
