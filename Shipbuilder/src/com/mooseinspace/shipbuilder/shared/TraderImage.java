package com.mooseinspace.shipbuilder.shared;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class TraderImage implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6681298729067627846L;
	
	@PrimaryKey
	private String imageName;
	
	@Persistent
	private String blobKey;
	
	@Persistent
	private String imageURL;
	
	@Persistent
	private int height;
	
	@Persistent
	private int width;
	
	@Persistent
	private String imageType;
	
	public TraderImage()
	{
		
	}

	/**
	 * @return the imageName
	 */
	public String getImageName() {
		return imageName;
	}

	/**
	 * @param imageName the imageName to set
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	/**
	 * @return the blobKey
	 */
	public String getBlobKey() {
		return blobKey;
	}

	/**
	 * @param blobKey the blobKey to set
	 */
	public void setBlobKey(String blobKey) {
		this.blobKey = blobKey;
	}

	/**
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}
	
	public String getImageURL(double scale)
	{
		if (scale == 1)
			return imageURL;
		
		int imgMaxSize;
		
		if (width > height)
			imgMaxSize = width;
		else
			imgMaxSize = height;
		
		int getPixSize = (int) (imgMaxSize*scale);
		
		if (getPixSize > 1600)
			return imageURL;
		
		return imageURL + "=s" + getPixSize;
	}

	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the imageType
	 */
	public String getImageType() {
		return imageType;
	}

	/**
	 * @param imageType the imageType to set
	 */
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

}
