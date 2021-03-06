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
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.mooseinspace.shipbuilder.shared.GridObject;
import com.mooseinspace.shipbuilder.shared.ShipHull;
import com.mooseinspace.shipbuilder.shared.SpaceShip;
import com.mooseinspace.shipbuilder.shared.Vector;
import com.mooseinspace.shipbuilder.shared.systems.ShipSystem;

public class ShipViewCanvasControl implements MouseOverHandler, MouseMoveHandler, MouseOutHandler, MouseUpHandler
{
	private Canvas 				canvas;
	private Context2d 			shipContext;
	
	private GridObjHelper		shipObjHelp;
	
	private GridObjImage 		shipImg;
	private Vector				shipPos;
	
	private GridObjImage 		shipSystem;
	
	private SpaceShip			spaceShip;
	
	private int 				lastMouseGridPosX = 0;
	private int					lastMouseGridPosY = 0;
	
	private boolean				mouseHover = false;
	
	public ShipViewCanvasControl(Canvas shipCanvas, AbsolutePanel picArea)
	{
		
		shipObjHelp = new GridObjHelper(shipCanvas);
		
		shipPos = new Vector(0,0);
		
		this.canvas = shipCanvas;
		this.shipContext = shipCanvas.getContext2d();
		
		shipCanvas.addMouseOverHandler(this);
		shipCanvas.addMouseOutHandler(this);
		shipCanvas.addMouseMoveHandler(this);
		shipCanvas.addMouseUpHandler(this);
	}
	
	public void setShipHull(SpaceShip newShip, Vector pos)
	{
		spaceShip = newShip;
		shipImg = GridObjImageFactory.getInstance().getGridObjImage(newShip.getShipHull());
		shipPos = pos;
		
		draw();
	}
	
	public void setShipSystem(ShipSystem newSystem)
	{
		newSystem.setObjConfig(ShipSystem.SMALLER_OUTER_GRID_CONFIG);
		shipSystem = GridObjImageFactory.getInstance().getGridObjImage(newSystem);
		
		draw();
	}
	
	public void draw()
	{
		shipContext.clearRect(0, 0, canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceWidth());
		
		if (shipImg != null)
		{
			//shipContext.setFillStyle("#000033");
			//shipContext.fillRect(shipPos.x, shipPos.y, shipImg.getObjImage().getWidth(), shipImg.getObjImage().getHeight());
			
			shipObjHelp.draw(shipImg, shipPos, false);
			
			List<ShipSystem> attachedSystems = spaceShip.getAttachedSystems();
			Vector systemPos = new Vector();
			GridObjImage attachedImg;
			GridObjImageFactory imgFact = GridObjImageFactory.getInstance();
			
			for (int i = 0; i < attachedSystems.size(); i++)
			{
				systemPos.x = spaceShip.getSystemLocX().get(i) * GridObjHelper.GRID_SIZE + shipPos.x;
				systemPos.y = spaceShip.getSystemLocY().get(i) * GridObjHelper.GRID_SIZE + shipPos.y;
				
				attachedImg = imgFact.getGridObjImage(attachedSystems.get(i));
				
				shipObjHelp.draw(attachedImg, systemPos, false);
			}
			
			shipObjHelp.drawGrid(shipImg, shipPos, "#666666");
			
			shipContext.setStrokeStyle("#ffffff");
			shipContext.strokeText("x: " + (int)((lastMouseGridPosX - shipPos.x) / GridObjHelper.GRID_SIZE) +
							  " y: " + (int)((lastMouseGridPosY - shipPos.y) / GridObjHelper.GRID_SIZE), 650, 700);
		}
		
		if (shipSystem == null)
		{
			shipContext.setFillStyle("red");
			shipContext.fillRect(lastMouseGridPosX, lastMouseGridPosY, GridObjHelper.GRID_SIZE, GridObjHelper.GRID_SIZE);
		}
		else
		{
			if (checkGrids())
				shipObjHelp.drawGrid(shipSystem, new Vector(lastMouseGridPosX, lastMouseGridPosY), "#00ff00", "#00ff00");
			else
				shipObjHelp.drawGrid(shipSystem, new Vector(lastMouseGridPosX, lastMouseGridPosY), "#ff0000", "#ff0000");
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
			if (!spaceShip.checkNewSubgrid(shipSystem.getGridObj(), gridX, gridY))
				return false;
			
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

	@Override
	public void onMouseUp(MouseUpEvent event) 
	{
		if (mouseHover)
		{
			if (checkGrids())
			{
				int gridX = (int)((lastMouseGridPosX - shipPos.x) / GridObjHelper.GRID_SIZE);
				int gridY = (int)((lastMouseGridPosY - shipPos.y) / GridObjHelper.GRID_SIZE);
				
				spaceShip.attachSubGrid(shipSystem.getGridObj(), gridX, gridY);
				
				this.draw();
			}
		}
		
	}
}
