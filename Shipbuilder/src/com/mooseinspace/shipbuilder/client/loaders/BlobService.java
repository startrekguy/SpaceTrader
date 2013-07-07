package com.mooseinspace.shipbuilder.client.loaders;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mooseinspace.shipbuilder.shared.ShipHull;
import com.mooseinspace.shipbuilder.shared.systems.ShipSystem;

@RemoteServiceRelativePath("blobService")
public interface BlobService extends RemoteService 
{
	String uploadShipPic();
	
	ShipHull getShipHull(String hullName);
	
	List<ShipHull> getAllShipHulls();
	
	List<ShipSystem> getShipSystems();
}
