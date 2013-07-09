package com.mooseinspace.shipbuilder.client.gridobj;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.mooseinspace.shipbuilder.shared.GridObject;
import com.mooseinspace.shipbuilder.shared.ShipHull;
import com.mooseinspace.shipbuilder.shared.SpaceShip;
import com.mooseinspace.shipbuilder.shared.Vector;
import com.mooseinspace.shipbuilder.shared.systems.ShipSystem;

public class ShipViewCanvasControl implements MouseOverHandler, MouseMoveHandler, MouseOutHandler
{
	private Canvas 				canvas;
	private Context2d 			context;
	private GridObjHelper		gridObjHelp;
	
	private GridObjImage 		shipImg;
	private Vector				shipPos;
	
	private GridObjImage 		shipSystem;
	
	private SpaceShip			spaceShip;
	
	private int 				lastMouseGridPosX = 0;
	private int					lastMouseGridPosY = 0;
	
	private boolean				mouseHover = false;
	
	public ShipViewCanvasControl(Canvas canvas)
	{
		
		gridObjHelp = new GridObjHelper(canvas);
		shipPos = new Vector(0,0);
		
		this.canvas = canvas;
		this.context = canvas.getContext2d();
		
		canvas.addMouseOverHandler(this);
		canvas.addMouseOutHandler(this);
		canvas.addMouseMoveHandler(this);
	}
	
	public void setShipHull(SpaceShip newShip, Vector pos)
	{
		spaceShip = newShip;
		shipImg = new GridObjImage(newShip.getShipHull());
		shipPos = pos;
		
		draw();
	}
	
	public void setShipSystem(ShipSystem newSystem)
	{
		newSystem.setObjConfig(ShipSystem.SMALLER_OUTER_GRID_CONFIG);
		shipSystem = new GridObjImage(newSystem);
		
		draw();
	}
	
	public void draw()
	{
		context.clearRect(0, 0, canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceWidth());
		
		if (shipImg != null)
		{
			context.setFillStyle("#000033");
			context.fillRect(shipPos.x, shipPos.y, shipImg.getObjImage().getWidth(), shipImg.getObjImage().getHeight());
			
			gridObjHelp.draw(shipImg, shipPos, true);
			gridObjHelp.drawGrid(shipImg, shipPos, "#666666");
			
			context.setStrokeStyle("#ffffff");
			context.strokeText("x: " + (int)((lastMouseGridPosX - shipPos.x) / GridObjHelper.GRID_SIZE) +
							  " y: " + (int)((lastMouseGridPosY - shipPos.y) / GridObjHelper.GRID_SIZE), 650, 700);
		}
		
		if (shipSystem == null)
		{
			context.setFillStyle("red");
			context.fillRect(lastMouseGridPosX, lastMouseGridPosY, GridObjHelper.GRID_SIZE, GridObjHelper.GRID_SIZE);
		}
		else
		{
			if (checkGrids())
				gridObjHelp.drawGrid(shipSystem, new Vector(lastMouseGridPosX, lastMouseGridPosY), "#00ff00", "#00ff00");
			else
				gridObjHelp.drawGrid(shipSystem, new Vector(lastMouseGridPosX, lastMouseGridPosY), "#ff0000", "#ff0000");
		}
		
		
	}
	
	private boolean checkGrids()
	{
		int gridX = (int)((lastMouseGridPosX - shipPos.x) / GridObjHelper.GRID_SIZE);
		int gridY = (int)((lastMouseGridPosY - shipPos.y) / GridObjHelper.GRID_SIZE);
		
		boolean[][] shipGrid = shipImg.getGridObj().getImgGrid();
		boolean[][] systemGrid = shipSystem.getGridObj().getImgGrid();
		
		int maxWidth = shipSystem.getGridObj().getImgGridWidth() + gridX - 1;
		int maxHeight = shipSystem.getGridObj().getImgGridHeight() + gridY - 1;
		
		if (maxWidth 	> shipImg.getGridObj().getImgGridWidth() - 1 	||
			maxHeight 	> shipImg.getGridObj().getImgGridHeight() - 1 	||
			gridX		< 0												||
			gridY 		< 0)
		{
			return false;
		}
		else
		{
			for (int x = gridX; x < maxWidth; x++)
			{
				for (int y = gridY; y < maxHeight; y++)
				{
					if (!(!systemGrid[x-gridX][y-gridY] || shipGrid[x][y]))
					{
						return false;
					}
				}
			}
			return true;
		}
		
	}

	@Override
	public void onMouseOver(MouseOverEvent event) 
	{
		mouseHover = true;
	}

	@Override
	public void onMouseOut(MouseOutEvent event) 
	{
		mouseHover = false;
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) 
	{
		if (mouseHover)
		{
			if ((int)(event.getX() / GridObjHelper.GRID_SIZE) * GridObjHelper.GRID_SIZE + (int)(shipPos.x % GridObjHelper.GRID_SIZE) != lastMouseGridPosX)
			{
				lastMouseGridPosX = (int)(event.getX() / GridObjHelper.GRID_SIZE) *GridObjHelper.GRID_SIZE + (int)(shipPos.x % GridObjHelper.GRID_SIZE);
				this.draw();
			}
			
			if ((int)(event.getY() / GridObjHelper.GRID_SIZE) * GridObjHelper.GRID_SIZE + (int)(shipPos.y % GridObjHelper.GRID_SIZE) != lastMouseGridPosY)
			{
				lastMouseGridPosY = (int)(event.getY() / GridObjHelper.GRID_SIZE) * GridObjHelper.GRID_SIZE + (int)(shipPos.y % GridObjHelper.GRID_SIZE);
				this.draw();
			}
		}
	}
}
