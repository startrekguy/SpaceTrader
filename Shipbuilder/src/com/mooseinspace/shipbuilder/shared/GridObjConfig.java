package com.mooseinspace.shipbuilder.shared;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class GridObjConfig implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2157817517129870516L;
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
	
	@Persistent
	public boolean innerGrid;
	@Persistent
	public double imgScale;
	@Persistent(serialized = "true")
	public boolean[][] imgGrid;
	@Persistent
	public boolean canAttachSubGrids;
}
