package com.mooseinspace.shipbuilder.client.loaders;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mooseinspace.shipbuilder.shared.ShipHull;
import com.mooseinspace.shipbuilder.shared.systems.ShipSystem;

public interface BlobServiceAsync 
{
	void uploadShipPic(AsyncCallback<String> callback);
	void getShipHull(String hullName, AsyncCallback<ShipHull> callback);
	void getAllShipHulls(AsyncCallback<List<ShipHull>> callback);
	void getShipSystems(AsyncCallback<List<ShipSystem>> callback);
}
