package com.mooseinspace.shipbuilder.client.gridobj;

import java.util.EventObject;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.mooseinspace.shipbuilder.client.loaders.AdminService;
import com.mooseinspace.shipbuilder.client.loaders.AdminServiceAsync;
import com.mooseinspace.shipbuilder.shared.Vector;

public class GridObjHelper
{
	private Canvas canvas;
	
	public static int GRID_SIZE = 15;
	
	public GridObjHelper (Canvas canvas)
	{
		this.canvas = canvas;
	}
	
	public void draw(final GridObjImage gridImage, final Vector position, final boolean gridOn)
	{
		
		if (gridImage.isLoaded())
		{
			if (gridImage.getGridObj().getImgGrid() == null)
			{
				calculateGrid(gridImage);
			}
			
			Context2d context = canvas.getContext2d();
			//context.clearRect(position.x, position.y, gridImage.getGridObj().getImgWidth(), gridImage.getGridObj().getImgHeight());
		
			context.drawImage(ImageElement.as(gridImage.getObjImage().getElement()), position.x, position.y);
			
			if (gridOn)
			{
				drawGrid(gridImage, position, "#666666");
			}
		}
		else
		{
			gridImage.addGridImgLoadedListener(new GridImgLoadedListener()
			{

				@Override
				public void onGridImgLoad(EventObject event) 
				{
					draw(gridImage, position, gridOn);
				}
				
			});
		}
	}
	
	public void drawGrid(GridObjImage gridImage, Vector position, String color)
	{
		drawGrid(gridImage, position, color, "#0000ff");
	}
	
	public void drawGrid(GridObjImage gridImage, Vector position, String color, String borderColor)
	{
		boolean[][] gridArray = gridImage.getGridObj().getImgGrid();
		
		Context2d context = canvas.getContext2d();
		
		context.setStrokeStyle(color);
		
		for (int x = 0; x < gridImage.getGridObj().getImgGridWidth(); x++)
		{
			for (int y = 0; y < gridImage.getGridObj().getImgGridHeight(); y++)
			{
				if (gridArray[x][y])
				{
					//context.strokeRect(x*GRID_SIZE+position.x, y*GRID_SIZE+position.y, GRID_SIZE, GRID_SIZE);
					
					
					if (y > 0 && gridArray[x][y-1])
						context.setStrokeStyle(color);
					else
					{
						context.setStrokeStyle(borderColor);
					
						//Top line
						context.beginPath();
						context.moveTo(x*GRID_SIZE+position.x, y*GRID_SIZE+position.y);
						
						context.lineTo(x*GRID_SIZE+position.x + GRID_SIZE, y*GRID_SIZE+position.y);
						context.stroke();

						
					}
					
					if (x < gridImage.getGridObj().getImgGridWidth()-1 && gridArray[x+1][y])
						context.setStrokeStyle(color);
					else
					{
						context.setStrokeStyle(borderColor);
					
						//Right Line
						context.beginPath();
						context.moveTo(x*GRID_SIZE+position.x + GRID_SIZE, y*GRID_SIZE+position.y);
						
						context.lineTo(x*GRID_SIZE+position.x + GRID_SIZE, y*GRID_SIZE+position.y + GRID_SIZE);
						context.stroke();

						
					}
					
					//Bottom Line
					context.beginPath();
					context.moveTo(x*GRID_SIZE+position.x + GRID_SIZE, y*GRID_SIZE+position.y + GRID_SIZE);
					
					if (y < gridImage.getGridObj().getImgGridHeight()-1 && gridArray[x][y+1])
						context.setStrokeStyle(color);
					else
						context.setStrokeStyle(borderColor);
					
					context.lineTo(x*GRID_SIZE+position.x, y*GRID_SIZE+position.y + GRID_SIZE);
					context.stroke();
					
					//Left Line
					context.beginPath();
					context.moveTo(x*GRID_SIZE+position.x, y*GRID_SIZE+position.y + GRID_SIZE);
					
					if (x >0 && gridArray[x-1][y])
						context.setStrokeStyle(color);
					else
						context.setStrokeStyle(borderColor);
					
					context.lineTo(x*GRID_SIZE+position.x, y*GRID_SIZE+position.y);
					context.stroke();
				}
			}
		}
	}
	
	private void calculateGrid(final GridObjImage gridImage)
	{

		//Set the canvas internal size to fit the image
		int oldCanvasHeight = canvas.getCoordinateSpaceHeight();
		int oldCanvasWidth = canvas.getCoordinateSpaceWidth();
		canvas.setCoordinateSpaceHeight(gridImage.getObjImage().getHeight());
		canvas.setCoordinateSpaceWidth(gridImage.getObjImage().getWidth());
		
		Context2d context = canvas.getContext2d();
		
		//Draw image and extract pixel data
		context.drawImage(ImageElement.as(gridImage.getObjImage().getElement()), 0, 0);
		ImageData imgData = context.getImageData(0, 0, gridImage.getObjImage().getWidth(), gridImage.getObjImage().getHeight());
//		CanvasPixelArray dataArray = imgData.getData();
		
		//TODO Move this grid makeing thing to another function
		boolean[][] gridArray = new boolean[gridImage.getObjImage().getWidth()/GRID_SIZE+1][gridImage.getObjImage().getHeight()/GRID_SIZE+1];
		int xMin = gridImage.getObjImage().getWidth();
		int xMax = 0;
		int yMin = gridImage.getObjImage().getHeight();
		int yMax = 0;
		int pixCount = 0;
		
		if (gridImage.getGridObj().getInnerGrid())
		{
			for (int x = 0; x < gridArray.length; x++)
			{
				for (int y = 0; y < gridArray[0].length; y++)
				{
					gridArray[x][y] = true;
				}
			}
		}
		
		for (int x = 0; x < gridImage.getObjImage().getWidth(); x++)
		{
			for (int y = 0; y < gridImage.getObjImage().getHeight(); y++)
			{
				if (imgData.getAlphaAt(x, y) == 0)
				{
					if (gridImage.getGridObj().getInnerGrid())
					{
						gridArray[x/GRID_SIZE][y/GRID_SIZE] = false;
					}
				}
				else
				{
					
					if (!gridImage.getGridObj().getInnerGrid())
					{
						gridArray[x/GRID_SIZE][y/GRID_SIZE] = true;
					}
					
					if (x > xMax)
						xMax = x;
					if (x < xMin)
						xMin = x;
					if (y > yMax)
						yMax = y;
					if (y < yMin)
						yMin = y;

					pixCount++;
				}
			}
		}
		
		int shipWidth = xMax - xMin;
		int shipHeight = yMax - yMin;
		
		
		gridImage.getGridObj().setImgGrid(gridArray);
		
		if (gridImage.getGridObj().getObjConfig() == 0)
		{
			gridImage.getGridObj().setNumOfPixels(pixCount);
			gridImage.getGridObj().setObjLength(shipHeight/3);
			gridImage.getGridObj().setObjWidth(shipWidth/3);
			gridImage.getGridObj().getObjImage().setHeight(gridImage.getObjImage().getHeight());
			gridImage.getGridObj().getObjImage().setWidth(gridImage.getObjImage().getWidth());
		}
		
		//Reset the canvas internal pixel size
		context.clearRect(0, 0, gridImage.getObjImage().getWidth(), gridImage.getObjImage().getHeight());
		canvas.setCoordinateSpaceHeight(oldCanvasHeight);
		canvas.setCoordinateSpaceWidth(oldCanvasWidth);
		
	}

}
