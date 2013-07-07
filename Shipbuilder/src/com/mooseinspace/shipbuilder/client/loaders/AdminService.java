package com.mooseinspace.shipbuilder.client.loaders;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mooseinspace.shipbuilder.shared.GridObject;
import com.mooseinspace.shipbuilder.shared.ShipHull;
import com.mooseinspace.shipbuilder.shared.TraderImage;
import com.mooseinspace.shipbuilder.shared.systems.ShipSystem;
import com.mooseinspace.shipbuilder.shared.systems.SystemProperty;

@RemoteServiceRelativePath("AdminService")
public interface AdminService extends RemoteService  
{
	Boolean addProperty(SystemProperty newProperty);
	List<SystemProperty> getAllProperties();
	
	Boolean addShipSystem(ShipSystem newSystem);
	List<ShipSystem> getAllShipSystems();
	
	List<String> getAllImageTypes();
	
	Boolean updateTraderImage(TraderImage traderImage);
	List<TraderImage> getAllTraderImages();
	List<TraderImage> getAllTraderImages(String imgType);
	
	Boolean addShipHull(ShipHull newShipHull);
	
	Boolean updateGridObj(GridObject gridObj);
}
