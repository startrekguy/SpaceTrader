package com.mooseinspace.shipbuilder.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public abstract class GridObject implements Serializable
{
	// All the types of classes that implement GridObject
	public static final int SHIP_HULL = 1;
	public static final int SHIP_SYSTEM = 2;
	public static final int SYSTEM_SUBSYSTEM = 3;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8713228282146174305L;
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
//    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	protected String gridObjName;
	
	@Persistent
	private String objImageName;
	
	@Persistent
	private int objLength;
	
	@Persistent
	private int objWidth;
	
	@Persistent
	private int numOfPixels;
	
	@Persistent(defaultFetchGroup = "true")
	@Order(extensions = @Extension(vendorName="datanucleus",key="list-ordering", value="imgScale desc"))
	private List<GridObjConfig> objConfigs;
	
	@NotPersistent
	private int objConfig;
	
	@NotPersistent
	private TraderImage objImage;

	public GridObject()
	{
		this.objConfigs = new ArrayList<GridObjConfig>();
		
		addObjConfig();
	}
	
	abstract public int getObjType();
	
	/**
	 * Adds a new configuration to this grid object
	 * 
	 * @return the object's new configuration number
	 */
	protected int addObjConfig()
	{
		objConfigs.add(new GridObjConfig());
		objConfig = objConfigs.size() - 1;
		objConfigs.get(objConfig).imgScale = 1;
		objConfigs.get(objConfig).innerGrid = true;
		return objConfig;
	}
	
	protected int getNumOfConfigs()
	{
		return objConfigs.size();
	}
	
	/**
	 * @return the objConfig
	 */
	public int getObjConfig() {
		return objConfig;
	}

	/**
	 * @param objConfig the objConfig to set
	 */
	public boolean setObjConfig(int objConfig) 
	{
		if (objConfig >= 0 && objConfig < objConfigs.size())
		{
			this.objConfig = objConfig;
			return true;
		}
		else
		{
			return false;
		}
		
	}

	/**
	 * @return the objImage
	 */
	public TraderImage getObjImage() {
		return objImage;
	}

	/**
	 * @param objImage the objImage to set
	 */
	public void setObjImage(TraderImage objImage) {
		this.objImage = objImage;
		this.objImageName = objImage.getImageName();
	}

	/**
	 * @return the objImageName
	 */
	public String getObjImageName() {
		return objImageName;
	}

	/**
	 * @return the objLength
	 */
	public int getObjLength() {
		return objLength;
	}

	/**
	 * @param objLength the objLength to set
	 */
	public void setObjLength(int objLength) {
		this.objLength = objLength;
	}

	/**
	 * @return the objWidth
	 */
	public int getObjWidth() {
		return objWidth;
	}

	/**
	 * @param objWidth the objWidth to set
	 */
	public void setObjWidth(int objWidth) {
		this.objWidth = objWidth;
	}

	/**
	 * @return the numOfPixels
	 */
	public int getNumOfPixels() {
		return numOfPixels;
	}

	/**
	 * @param numOfPixels the numOfPixels to set
	 */
	public void setNumOfPixels(int numOfPixels) {
		this.numOfPixels = numOfPixels;
	}

	/**
	 * @return the shipMass
	 */
	abstract public int getObjectMass();
	
	/**
	 * @return the imgGrid
	 */
	public boolean[][] getImgGrid() {
		return objConfigs.get(objConfig).imgGrid;
	}
	
	/**
	 * @param imgGrid the imgGrid to set
	 */
	public void setImgGrid(boolean[][] imgGrid) {
		objConfigs.get(objConfig).imgGrid = imgGrid;
	}
	
	public int getImgGridWidth()
	{
		return objConfigs.get(objConfig).imgGrid.length;
	}
	
	public int getImgGridHeight()
	{
		return objConfigs.get(objConfig).imgGrid[0].length;
	}
	
	public void setImgScale(double imgScale)
	{
		objConfigs.get(objConfig).imgScale = imgScale;
	}
	
	public double getImgScale()
	{
		return objConfigs.get(objConfig).imgScale;
	}
	
	public void setInnerGrid(boolean innerGrid)
	{
		objConfigs.get(objConfig).innerGrid = innerGrid;
	}
	
	public boolean getInnerGrid()
	{
		return objConfigs.get(objConfig).innerGrid;
	}

	public String getImageURL()
	{
		return this.objImage.getImageURL(getImgScale());
	}
	
	public int getImgWidth()
	{
		return (int) (this.objImage.getWidth()*getImgScale());
	}
	
	public int getImgHeight()
	{
		return (int) (this.objImage.getHeight()*getImgScale());
	}
	
}
