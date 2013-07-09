package com.mooseinspace.shipbuilder.shared;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.mooseinspace.shipbuilder.shared.systems.*;


@PersistenceCapable(detachable="true")
public class SpaceShip implements SubGridAttachable
{
	@Persistent
	private String shipName;
	@Persistent
	private ShipHull shipHull;
	@Persistent
	private String owner;
	
	@Persistent
	private List<ShipSystem> attachedSystems;
	
	@Persistent
	Integer[][] systemLocations;
	
	@Persistent
	List<Integer> systemLocX;
	@Persistent
	List<Integer> systemLocY;
	
	public SpaceShip()
	{
		init();
	}
	
	public SpaceShip(ShipHull shipHull)
	{
		this.shipHull = shipHull;
		init();
	}
	
	private void init()
	{
		this.attachedSystems = new ArrayList<ShipSystem>();
		systemLocations = new Integer[shipHull.getImgGridWidth()][shipHull.getImgGridHeight()];
		systemLocX = new ArrayList<Integer>();
		systemLocY = new ArrayList<Integer>();
		
		for (int x = 0; x < shipHull.getImgGridWidth(); x++)
		{
			for (int y = 0; y < shipHull.getImgGridHeight(); y++)
			{
				systemLocations[x][y] = -1;
			}
		}
	}
	
	public boolean attachSubGrid(GridObject newSubGrid, int gridX, int gridY)
	{
		ShipSystem newSystem;
		
		try
		{
			newSystem = (ShipSystem) newSubGrid;
		}
		catch (ClassCastException e)
		{
			return false;
		}
				
		boolean[][] newSystemGrid = newSystem.getImgGrid();
		
		for (int x = 0; x < newSystem.getImgGridWidth(); x++)
		{
			for (int y = 0; y < newSystem.getImgGridHeight(); y++)
			{
				if (newSystemGrid[x][y])
				{
					if (systemLocations[x+gridX][y+gridY] > -1)
					{
						return false;
					}
				}
			}
		}
		
		attachedSystems.add(newSystem);
		systemLocX.add(gridX);
		systemLocY.add(gridY);
		
		int systemNum = attachedSystems.size() - 1;
		
		for (int x = 0; x < newSystem.getImgGridWidth(); x++)
		{
			for (int y = 0; y < newSystem.getImgGridHeight(); y++)
			{
				if (!newSystemGrid[x][y])
				{
					systemLocations[x+gridX][y+gridY] = systemNum;
				}
			}
		}
		
		return true;
	}
	
	@Override
	public GridObject removeSubGrid(int gridX, int gridY) 
	{
		int systemLocNum = systemLocations[gridX][gridY];
		
		systemLocX.remove(systemLocNum);
		systemLocY.remove(systemLocNum);
		
		for (int x = 0; x < shipHull.getImgGridWidth(); x++)
		{
			for (int y = 0; y < shipHull.getImgGridHeight(); y++)
			{
				if (systemLocations[x][y] == systemLocNum)
				{
					systemLocations[x][y] = -1;
				}
				
				if (systemLocations[x][y] > systemLocNum)
				{
					systemLocations[x][y] = systemLocations[x][y] - 1;
				}
			}
		}
		
		return attachedSystems.remove(systemLocNum);
	}

	@Override
	public GridObject selectSubGrid(int gridX, int gridY) 
	{
		int systemLocNum = systemLocations[gridX][gridY];
		
		return attachedSystems.get(systemLocNum);
	}
	
	public List<SystemProperty> getShipProperties()
	{
		ArrayList<SystemProperty> allProperties = new ArrayList<SystemProperty>();
		
		for (ShipSystem attachedSys : attachedSystems)
		{
			for (SystemProperty prop : attachedSys.getSystemProperties())
			{
				if (allProperties.contains(prop))
				{
					((SystemProperty) allProperties.get(allProperties.indexOf(prop))).combineProperties(prop);
				}
				else
				{
					allProperties.add(prop);
				}
			}
		}
		
		return allProperties;
	}
	
	/**
	 * 
	 * @return attachedSystems a list of all the systems attached to this ship
	 */
	public List<ShipSystem> getAttachedSystems()
	{
		return attachedSystems;
	}
	
	/**
	 * @return the shipHull
	 */
	public ShipHull getShipHull() 
	{
		return shipHull;
	}

	/**
	 * @param shipHull the shipHull to set
	 */
//	public void setShipHull(ShipHull shipHull) 
//	{
//		this.shipHull = shipHull;
//	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the shipName
	 */
	public String getShipName() {
		return shipName;
	}

	/**
	 * @param shipName the shipName to set
	 */
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	
}
