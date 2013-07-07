package com.mooseinspace.shipbuilder.shared.systems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class SystemProperty implements Serializable
{

	@PrimaryKey
	private String propertyName;
	@Persistent
	private String unitName;
	@Persistent
	private String unitAbv;
	
	@NotPersistent
	private List<Integer> propertyValue;
	
	public SystemProperty()
	{
		this.propertyValue = new ArrayList<Integer>();
	}
	
	@Override
	public boolean equals(Object o)
	{
		try
		{
			if (((SystemProperty) o).getPropertyName().equals(this.propertyName))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (ClassCastException e)
		{
			return false;
		}
		
	}
	
	public void combineProperties(SystemProperty newSystem)
	{
		propertyValue.addAll(newSystem.getAllPropertyValues());
	}

	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @param propertyName the propertyName to set
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * @return the unitName
	 */
	public String getUnitName() {
		return unitName;
	}

	/**
	 * @param unitName the unitName to set
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	/**
	 * @return the unitAbv
	 */
	public String getUnitAbv() {
		return unitAbv;
	}

	/**
	 * @param unitAbv the unitAbv to set
	 */
	public void setUnitAbv(String unitAbv) {
		this.unitAbv = unitAbv;
	}

	public List<Integer> getAllPropertyValues()
	{
		return propertyValue;
	}

	/**
	 * @return the propertyValue
	 */
	public int getPropertyValue() 
	{
		int finalValue = 0;
		
		for(int value : propertyValue)
		{
			finalValue += value;
		}
		
		return finalValue;
	}

	/**
	 * @param propertyValue the propertyValue to set
	 */
	public void setPropertyValue(int propertyValue) {
		this.propertyValue.set(0, propertyValue);
	}
}
