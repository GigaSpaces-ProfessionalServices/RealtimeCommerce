package com.gigaspaces.commerce.oracle;

import java.util.Date;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpaceRouting;
import com.gigaspaces.metadata.index.SpaceIndexType;

/*
 * Space class representing Inventory counts and allocation in Oracle ATG
 */
@SpaceClass
public class Inventory {

	private Integer id;
	
	/** The SKU ID in the Product Catalog to which this inventory item refers. */
	private String catalogRefId;

	/** The amount of stock available for purchase. The value -1 indicates that an infinite amount is available. */
	private Long stockLevel;
	
	/** If the stockLevel falls below this amount, a warning event is generated. */
	private Long stockThreshold;
	
	/** The status of this inventory item. */
	private Integer availabilityStatus;
	
	/** The date on which this item will be available if not currently available. */
	private Date availabilityDate;
	
	/** The identifier of the physical store where current inventory record belong to. */
	private String locationId;

	// Default constructor (required by XAP)
	public Inventory() {
	}

	/** TODO: Remove in future. Used for testing purposes. */
	public Inventory(Integer id, Long stockLevel, String catalogRefId, String locationId, Long stockThreshold, Integer availabilityStatus, Date availabilityDate) {
		this.id = id;
		this.stockLevel = stockLevel;
		this.catalogRefId = catalogRefId;
		this.locationId = locationId;
		this.stockThreshold = stockThreshold;
		this.availabilityStatus = availabilityStatus;
		this.availabilityDate = availabilityDate;
	}

	@SpaceRouting
	@SpaceId(autoGenerate = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getStockLevel() {
		return stockLevel;
	}

	public void setStockLevel(Long stockLevel) {
		this.stockLevel = stockLevel;
	}

	@SpaceIndex(type = SpaceIndexType.BASIC)
	public String getCatalogRefId() {
		return catalogRefId;
	}

	public void setCatalogRefId(String catalogRefId) {
		this.catalogRefId = catalogRefId;
	}
	
	public Long getStockThreshold() {
		return stockThreshold;
	}

	public void setStockThreshold(Long stockThreshold) {
		this.stockThreshold = stockThreshold;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	
	public Integer getAvailabilityStatus() {
		return availabilityStatus;
	}

	public void setAvailabilityStatus(Integer availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}
	
	public Date getAvailabilityDate() {
		return availabilityDate;
	}

	public void setAvailabilityDate(Date availabilityDate) {
		this.availabilityDate = availabilityDate;
	}

	@Override
	public String toString() {
		return "Inventory : #" + id + ", stockLevel: " + stockLevel
				+ ", SKU ID: " + catalogRefId + ", stockThreshold: " + stockThreshold
				+ ", availabilityStatus: " + availabilityStatus + ", locationId: " + locationId;
	}
}