package com.mooseinspace.shipbuilder.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mooseinspace.shipbuilder.client.loaders.AdminService;
import com.mooseinspace.shipbuilder.shared.GridObject;
import com.mooseinspace.shipbuilder.shared.ShipHull;
import com.mooseinspace.shipbuilder.shared.TraderImage;
import com.mooseinspace.shipbuilder.shared.systems.ShipSystem;
import com.mooseinspace.shipbuilder.shared.systems.SystemProperty;

@SuppressWarnings("serial")
public class AdminServiceImpl extends RemoteServiceServlet implements AdminService
{

	@Override
	public Boolean addProperty(SystemProperty newProperty) 
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try
		{
			pm.makePersistent(newProperty);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
		finally
		{
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SystemProperty> getAllProperties() 
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query q = pm.newQuery(SystemProperty.class);
		
		final List<SystemProperty> results, detachedResults;
		
		try
		{
			results = (List<SystemProperty>) q.execute();
			detachedResults = new ArrayList<SystemProperty>();
			
			for (SystemProperty hull : results)
			{
				detachedResults.add(pm.detachCopy(hull));
			}
		}
		finally
		{
			q.closeAll();
		}
		
		return detachedResults;
	}

	@Override
	public Boolean addShipSystem(ShipSystem newSystem) 
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try
		{
			pm.makePersistent(newSystem);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
		finally
		{
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShipSystem> getAllShipSystems() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query q = pm.newQuery(ShipSystem.class);
		
		final List<ShipSystem> results, detachedResults;
		
		try
		{
			results = (List<ShipSystem>) q.execute();
			detachedResults = new ArrayList<ShipSystem>();
			
			for (ShipSystem hull : results)
			{
				detachedResults.add(pm.detachCopy(hull));
			}
		}
		finally
		{
			q.closeAll();
		}
		
		return detachedResults;
	}

	@Override
	public List<String> getAllImageTypes() 
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("ImgType");
		com.google.appengine.api.datastore.PreparedQuery pq = datastore.prepare(q);
		
		List<String> results = new ArrayList<String>();
		
		for (Entity result : pq.asIterable())
		{
			results.add((String) result.getProperty("imgType"));
		}
		
		return results;
	}

	@Override
	public Boolean updateTraderImage(TraderImage traderImage) 
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try
		{
			pm.makePersistent(traderImage);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
		finally
		{
			pm.close();
		}
	}
	
	@Override
	public List<TraderImage> getAllTraderImages()
	{
		return getAllTraderImages(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TraderImage> getAllTraderImages(String imgType) 
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query q = pm.newQuery(TraderImage.class);
		if (imgType != null)
		{
			q.setFilter("imageType == imgTypeFilt");
			q.declareParameters("String imgTypeFilt");
		}
		
		List<TraderImage> results, detachedResults;
		
		try
		{
			if (imgType == null)
			{
				results = (List<TraderImage>) q.execute();
			}
			else
			{
				results = (List<TraderImage>) q.execute(imgType);
			}
			detachedResults = new ArrayList<TraderImage>();
			
			for (TraderImage hull : results)
			{
				detachedResults.add(pm.detachCopy(hull));
			}
		}
		catch (Exception e)
		{
			detachedResults = null;
		}
		finally
		{
			q.closeAll();
		}
		
		return detachedResults;
	}
	
	@Override
	public Boolean addShipHull(ShipHull newShipHull)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try
		{
			pm.makePersistent(newShipHull);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
		finally
		{
			pm.close();
		}
	}

	@Override
	public Boolean updateGridObj(GridObject gridObj) 
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try
		{
			switch (gridObj.getObjType())
			{
				case GridObject.SHIP_HULL:
					pm.makePersistent((ShipHull) gridObj);
				case GridObject.SHIP_SYSTEM:
					pm.makePersistent((ShipSystem) gridObj);
			}
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
		finally
		{
			pm.close();
		}
	}

}
