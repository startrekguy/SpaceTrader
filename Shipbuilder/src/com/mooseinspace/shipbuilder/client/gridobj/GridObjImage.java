package com.mooseinspace.shipbuilder.client.gridobj;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.mooseinspace.shipbuilder.shared.GridObject;

/**
 * 
 * @author startrekguy
 *
 */
public class GridObjImage implements LoadHandler
{
	private boolean loaded;
	private GridObject gridObj;
	private Image objImage;
	private int gridObjConfig;
	
	private List<GridImgLoadedListener> gridImgLoadedEventListeners = new ArrayList<GridImgLoadedListener>();
	
	public GridObjImage(GridObject gridObj) 
	{
		this.gridObj = gridObj;
		this.gridObjConfig = gridObj.getObjConfig();
		objImage = new Image();
		objImage.addLoadHandler(this);
		objImage.setUrl(gridObj.getImageURL());
		
//		objImage = new Image(gridObj.getImageURL());
		
		objImage.setVisible(false);
		RootPanel.get().add(objImage);
		
		if (objImage.getHeight() != 0 && !loaded)
		{
			imageLoaded();
		}
	}

	@Override
	public void onLoad(LoadEvent event) 
	{
		imageLoaded();
	}
	
	private void imageLoaded()
	{
		this.loaded = true;
		fireLoadedEvent();
	}
	
	/**
	 * @return the loaded
	 */
	public boolean isLoaded() {
		return loaded;
	}

	/**
	 * @return the gridObj
	 */
	public GridObject getGridObj() 
	{
		gridObj.setObjConfig(gridObjConfig);
		return gridObj;
	}

	/**
	 * @return the objImage
	 */
	public Image getObjImage() {
		return objImage;
	}
	
	public synchronized void addGridImgLoadedListener(GridImgLoadedListener listner)
	{
		gridImgLoadedEventListeners.add(listner);
	}
	
	public synchronized void removeGridImgLoadedListener(GridImgLoadedListener listner)
	{
		gridImgLoadedEventListeners.remove(listner);
	}
	
	private synchronized void fireLoadedEvent()
	{
		GridImgLoadedEvent event = new GridImgLoadedEvent(this);
		Iterator<GridImgLoadedListener> i = gridImgLoadedEventListeners.iterator();
		while(i.hasNext())  
		{
			i.next().onGridImgLoad(event);
		}
	}
}
