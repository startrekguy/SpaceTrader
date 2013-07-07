package com.mooseinspace.shipbuilder.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mooseinspace.shipbuilder.client.loaders.BlobService;
import com.mooseinspace.shipbuilder.shared.ShipHull;
import com.mooseinspace.shipbuilder.shared.TraderImage;
import com.mooseinspace.shipbuilder.shared.systems.ShipSystem;

public class BlobServiceImpl extends RemoteServiceServlet implements BlobService
{
	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	@Override
	public String uploadShipPic() 
	{
		return blobstoreService.createUploadUrl("/shipbuilder/FileUpload?gwt.codesvr=127.0.0.1:9997");
	}
	
	@Override
	public ShipHull getShipHull(String hullName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ShipHull> getAllShipHulls() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(ShipHull.class);
//		q.setFilter("shipHullName == nameParam");
//		q.declareParameters("String nameParam");
		
		final List<ShipHull> results, detachedResults;
		
		try
		{
			results = (List<ShipHull>) q.execute();
			detachedResults = new ArrayList<ShipHull>();
			
			TraderImage tempImg;
			ShipHull tempHull;
			
			for (ShipHull hull : results)
			{
				hull.getImgGrid();
				tempImg = pm.getObjectById(TraderImage.class, hull.getObjImageName());
				tempHull = pm.detachCopy(hull);
				
				tempHull.setObjImage(pm.detachCopy(tempImg));
				
				detachedResults.add(tempHull);
				
			}
		}
		finally
		{
			q.closeAll();
//			pm.close();
		}
		
		return detachedResults;
	}
	
	@Override
	public List<ShipSystem> getShipSystems() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(ShipSystem.class);
//		q.setFilter("shipHullName == nameParam");
//		q.declareParameters("String nameParam");
		
		final List<ShipSystem> results, detachedResults;
		
		try
		{
			results = (List<ShipSystem>) q.execute();
			detachedResults = new ArrayList<ShipSystem>();
			
			TraderImage tempImg;
			ShipSystem tempSystem;
			
			for (ShipSystem system : results)
			{
				system.setObjConfig(ShipSystem.LARGE_INNER_GRID_CONFIG);
				system.getImgGrid();
				
				system.setObjConfig(ShipSystem.SMALLER_OUTER_GRID_CONFIG);
				system.getImgGrid();
				
				tempImg = pm.getObjectById(TraderImage.class, system.getObjImageName());
				tempImg = pm.detachCopy(tempImg);
				tempSystem = pm.detachCopy(system);
				
				tempSystem.setObjImage(pm.detachCopy(tempImg));
				
				detachedResults.add(tempSystem);
				
			}
		}
		finally
		{
			q.closeAll();
//			pm.close();
		}
		
		return detachedResults;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
        blobstoreService.serve(blobKey, resp);
	}

}
