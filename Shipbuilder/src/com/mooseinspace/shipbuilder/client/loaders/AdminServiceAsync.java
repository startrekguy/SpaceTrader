package com.mooseinspace.shipbuilder.client.loaders;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mooseinspace.shipbuilder.shared.GridObject;
import com.mooseinspace.shipbuilder.shared.ShipHull;
import com.mooseinspace.shipbuilder.shared.TraderImage;
import com.mooseinspace.shipbuilder.shared.systems.ShipSystem;
import com.mooseinspace.shipbuilder.shared.systems.SystemProperty;

public interface AdminServiceAsync 
{
	void addProperty(SystemProperty newProperty, AsyncCallback<Boolean> callback);
	void getAllProperties(AsyncCallback<List<SystemProperty>> callback);
	
	void addShipSystem(ShipSystem newSystem, AsyncCallback<Boolean> callback);
	void getAllShipSystems(AsyncCallback<List<ShipSystem>> callback);
	
	void getAllImageTypes(AsyncCallback<List<String>> callback);
	
	void updateTraderImage(TraderImage traderImage, AsyncCallback<Boolean> callback);
	void getAllTraderImages(AsyncCallback<List<TraderImage>> callback);
	void getAllTraderImages(String imgType, AsyncCallback<List<TraderImage>> callback);
	
	void addShipHull(ShipHull newShipHull, AsyncCallback<Boolean> callback);
	
	void updateGridObj(GridObject gridObj, AsyncCallback<Boolean> callback);
}
