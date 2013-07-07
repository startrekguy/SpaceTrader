package com.mooseinspace.shipbuilder.server;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.mooseinspace.shipbuilder.shared.PlayerInfo;
import com.mooseinspace.shipbuilder.shared.ShipHull;
import com.mooseinspace.shipbuilder.shared.TraderImage;


@SuppressWarnings("serial")
public class FileUpload extends HttpServlet 
{
	public void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		ImagesService imageService = ImagesServiceFactory.getImagesService();
//		BlobKey blobKey = new BlobKey(request.getParameter("blob-key")); 
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
		
	    BlobKey blobKey = blobs.get("picFileUpload").get(0);
	    
	    ServingUrlOptions servURL = ServingUrlOptions.Builder.withBlobKey(blobKey);
	    String imageURL = imageService.getServingUrl(servURL);
	    
	    if (imageURL.contains("0.0.0.0"))
	    {
	    	imageURL = imageURL.replace("0.0.0.0", "127.0.0.1");
	    }
	    
//	    Image imgService = ImagesServiceFactory.makeImageFromBlob(blobKey);
//	    
	    TraderImage newImage = new TraderImage();
	    newImage.setImageName(request.getParameter("picNameText"));
	    newImage.setImageURL(imageURL);
	    newImage.setBlobKey(blobKey.getKeyString());
	    newImage.setImageType(request.getParameter("imgTypeText"));
	    newImage.setHeight(Integer.parseInt(request.getParameter("ySizeText")));
	    newImage.setWidth(Integer.parseInt(request.getParameter("xSizeText")));
//	    newImage.setHeight(imgService.getHeight());
//	    newImage.setWidth(imgService.getWidth());
	    
//	    ShipHull shipHull = new ShipHull();
//	    shipHull.setShipHullName(request.getParameter("picNameText"));
//	    shipHull.setShipImageURL(imageURL);
	    
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    
	    Entity imgType = new Entity("ImgType");
	    imgType.setProperty("imgType", newImage.getImageType());
		
	    response.setHeader("Content-Type", "text/html");
	    
		try
		{
			pm.getObjectById(TraderImage.class, newImage.getImageName());
			
			blobstoreService.delete(blobKey);
			
		    response.getWriter().write("Hull Name Used");
		}
		catch (Exception e)
		{
			try
			{	
				com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("ImgType")
					.setFilter(new FilterPredicate("imgType",
												   FilterOperator.EQUAL,
												   newImage.getImageType()));
				com.google.appengine.api.datastore.PreparedQuery pq = datastore.prepare(q);
				
				if (pq.asSingleEntity() == null)
				{
					datastore.put(imgType);
				}
				
				
				pm.makePersistent(newImage);
				
				response.getWriter().write("Upload Success");
			}
			catch (Exception e1)
			{
				blobstoreService.delete(blobKey);
				
			    response.getWriter().write("Unknown Error");
			}
		}
		finally
		{
			pm.close();
		}

		
//	    response.sendRedirect("/shipbuilder/FileUpload?gwt.codesvr=127.0.0.1:9997&shipHullName=" + shipHull.getShipHullName());
//		request.getRequestDispatcher("/shipbuilder/FileUploadResp?shipHullName=" + shipHull.getShipHullName()).forward(request, response);
	    return;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		String id = req.getParameter("shipHullName");
	    resp.setHeader("Content-Type", "text/html");
	    resp.getWriter().println(id);
	}

}
