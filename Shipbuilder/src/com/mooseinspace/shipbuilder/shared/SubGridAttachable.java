package com.mooseinspace.shipbuilder.shared;

public interface SubGridAttachable 
{
	public boolean attachSubGrid(GridObject newSubGrid, int gridX, int gridY);
	public GridObject removeSubGrid(int gridX, int gridY);
	public GridObject selectSubGrid(int gridX, int gridY);
}
