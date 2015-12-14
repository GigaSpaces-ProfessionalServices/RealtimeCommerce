package com.gigaspaces.commerce.oracle;

import java.util.Date;

/** 
 * Oracle ATG Inventory API
 * TODO: ATG-specific identifiers need to be moved out of this interface. 
 * 
 * @author ahodroj
 *
 */
public interface RealtimeInventoryService {
	
	public static final int INVENTORY_STATUS_SUCCEED = 1;
	public static final int INVENTORY_STATUS_FAIL = -1;
	public static final int INVENTORY_STATUS_INSUFFICIENT_SUPPLY = -2;
	public static final int INVENTORY_STATUS_ITEM_NOT_FOUND = -3;
	
	public static final int AVAILABILITY_STATUS_IN_STOCK = 1000;
	public static final int AVAILABILITY_STATUS_OUT_OF_STOCK = 1001;
	public static final int AVAILABILITY_STATUS_PREORDERABLE = 1002;
	public static final int AVAILABILITY_STATUS_BACKORDERABLE = 1003;
	public static final int AVAILABILITY_STATUS_DERIVED = 1004;
	public static final int AVAILABILITY_STATUS_DISCONTINUED = 1005;
	
	int purchase(String catalogRefId, long howMany, String locationId);
	
	long queryStockLevel(String catalogRefId, String locationId);
	
	long queryStockThreshold(String catalogRefId, String locationId);
	
	int queryAvailabilityStatus(String catalogRefId, String locationId);
	
	int decreaseStockLevel(String catalogRefId, long howMany, String locationId);
	
	int increaseStockLevel(String catalogRefId, long howMany, String locationId);
	
	Date queryAvailabilityDate(String catalogRefId, String locationId);
	
	void initInventory(String catalogRefId, String locationId);

}