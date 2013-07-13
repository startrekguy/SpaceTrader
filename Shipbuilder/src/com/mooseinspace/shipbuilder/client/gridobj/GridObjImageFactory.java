package com.mooseinspace.shipbuilder.client.gridobj;

import java.util.ArrayList;
import java.util.List;

import com.mooseinspace.shipbuilder.shared.GridObject;

public class GridObjImageFactory 
{
	private static GridObjImageFactory instance = null;
	
	private List<GridObjImage> gridObjImages;
	
	protected GridObjImageFactory()
	{
		gridObjImages = new ArrayList<GridObjImage>();
	}
	
	public static GridObjImageFactory getInstance()
	{
		if (instance == null)
		{
			instance = new GridObjImageFactory();
		}
		
		return instance;
	}
	
	public GridObjImage getGridObjImage(GridObject gridObj)
	{
		for (GridObjImage gridImg : gridObjImages)
		{
			if (gridImg.getGridObj().getImageURL() == gridObj.getImageURL())
				return gridImg;
		}
		
		GridObjImage newGridImg = new GridObjImage(gridObj);
		gridObjImages.add(newGridImg);
		
		return newGridImg;
	}
}
