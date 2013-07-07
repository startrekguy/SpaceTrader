package com.mooseinspace.shipbuilder.shared;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Id;

@PersistenceCapable(detachable="true")
@Inheritance(customStrategy = "complete-table")
public class ShipHull extends GridObject implements Serializable
{
	private static final double massScale = .1;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5080003842304294606L;
	
//	@PrimaryKey
////	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
//	private String shipHullName;
	
	public ShipHull()
	{
		super();
	}
	
	/**
	 * @return the shipHullName 
	 */
	public String getShipHullName() {
		return this.gridObjName;
	}

	/**
	 * @param shipHullName the shipHullName to set
	 */
	public void setShipHullName(String shipHullName) {
		this.gridObjName = shipHullName;
	}

	@Override
	public int getObjectMass() 
	{
		return (int) (this.getNumOfPixels()*massScale);
	}

	@Override
	public int getObjType() 
	{
		return GridObject.SHIP_HULL;
	}
}
