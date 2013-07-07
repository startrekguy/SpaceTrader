package com.mooseinspace.shipbuilder.shared.systems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.persistence.Id;

import com.mooseinspace.shipbuilder.shared.GridObject;

@PersistenceCapable(detachable="true")
@Inheritance(customStrategy = "complete-table")
public class ShipSystem extends GridObject implements Serializable
{
	private static double massScale = .05;
	
	public static int SMALLER_OUTER_GRID_CONFIG = 1;
	public static int LARGE_INNER_GRID_CONFIG = 0;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Persistent
	List<String> propertyNames;
	@Persistent
	List<Integer> propertyValues;
	
	@NotPersistent
	List<SystemProperty> systemProperties;
	
	public ShipSystem()
	{
		super();
		
		this.addObjConfig();
		this.setImgScale(.5);
		this.setInnerGrid(false);
	
		propertyNames = new ArrayList<String>();
		propertyValues = new ArrayList<Integer>();
	}

	/**
	 * @return the systemName
	 */
	public String getSystemName() {
		return this.gridObjName;
	}

	/**
	 * @param systemName the systemName to set
	 */
	public void setSystemName(String systemName) {
		this.gridObjName = systemName;
	}

	/**
	 * @return the propertyNames
	 */
	public List<String> getPropertyNames() {
		return propertyNames;
	}

	/**
	 * @param propertyNames the propertyNames to set
	 */
	public void setPropertyNames(List<String> propertyNames) {
		this.propertyNames = propertyNames;
	}

	/**
	 * @return the propertyValues
	 */
	public List<Integer> getPropertyValues() {
		return propertyValues;
	}

	/**
	 * @param propertyValues the propertyValues to set
	 */
	public void setPropertyValues(List<Integer> propertyValues) {
		this.propertyValues = propertyValues;
	}

	/**
	 * @return the systemProperties
	 */
	public List<SystemProperty> getSystemProperties() {
		return systemProperties;
	}

	/**
	 * @param systemProperties the systemProperties to set
	 */
	public void setSystemProperties(List<SystemProperty> systemProperties) {
		this.systemProperties = systemProperties;
	}
	
	/**
	 * Adds a property value to the system. If it already exists, the
	 * value will be modified
	 * 
	 * @param propName Name of the property (from the SystemProperty database)
	 * @param propValue Value of the property
	 * @return Returns true if the property is new, false if it is a replacement
	 */
	public boolean addProperty(String propName, int propValue)
	{
		boolean notSameName = true;
		
		for (int i = 0; i < propertyNames.size(); i++)
		{
			if (propertyNames.get(i).equals(propName))
			{
				propertyValues.set(i, propValue);
				notSameName = false;
			}
		}
		
		if (notSameName)
		{
			propertyNames.add(propName);
			propertyValues.add(propValue);
		}
		
		return notSameName;
	}
	
	public void addProperty(SystemProperty sysProp)
	{
		addProperty(sysProp.getPropertyName(), sysProp.getPropertyValue());
	}
	
	public void removeProperty(int index)
	{
		propertyNames.remove(index);
		propertyValues.remove(index);
	}

	@Override
	public int getObjectMass() 
	{
		return (int) (this.getNumOfPixels() * massScale);
	}

	@Override
	public int getObjType() 
	{
		return GridObject.SHIP_SYSTEM;
	}
	
}
