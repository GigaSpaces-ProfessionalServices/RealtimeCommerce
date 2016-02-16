package com.gigaspace.commerce.inventory;

import java.util.Date;

import org.openspaces.core.GigaSpace;
import org.openspaces.remoting.RemotingService;
import org.springframework.beans.factory.annotation.Autowired;

import com.gigaspaces.commerce.oracle.Inventory;
import com.gigaspaces.commerce.oracle.RealtimeInventoryService;
import com.j_spaces.core.client.SQLQuery;
import com.mycompany.model.InventoryDemand;
/**
 * @author Aliaksei Shary (EPAM Systems)
 */
@RemotingService
public class RealtimeInventoryServiceImpl implements RealtimeInventoryService {

	@Autowired
	private GigaSpace gigaSpace;

	@Override
	public int purchase(String catalogRefId, long howMany, String locationId) {
		// TODO: Wrap each method in transaction from InventoryEndpoint
		// TODO: Pessimistic Inventory Locking for updates - EXCLUSIVE_READ_LOCK
		System.out.println("RealtimeInventoryServiceImpl purchase: " + catalogRefId + ", Location: " + locationId + ", howMany: " + howMany);
    	
    	Inventory inventory = readByCatalogRefIdLocationId(catalogRefId, locationId);
		InventoryDemand inventoryDemand = readInventoryDemandByCatalogRefIdLocationId(catalogRefId, locationId);
		
		int result = validateInventoryRequest(inventory, inventoryDemand, -howMany);
		
		System.out.println("RealtimeInventoryServiceImpl purchase result :" + result);
		
		return result;
		 
	}

	@Override
	public long queryStockLevel(String catalogRefId, String locationId) {
		System.out.println("RealtimeInventoryServiceImpl queryStockLevel: " + catalogRefId + ", Location: " + locationId );
		
		Inventory inventory = readByCatalogRefIdLocationId(catalogRefId, locationId);
		InventoryDemand inventoryDemand = readInventoryDemandByCatalogRefIdLocationId(catalogRefId, locationId);
		
		long inventoryTotal = (inventory != null)?inventory.getStockLevel():0L;
		long inventoryDemandTotal = (inventoryDemand != null)?inventoryDemand.getStockLevel():0L;
		
		long result = inventoryTotal - inventoryDemandTotal;
		
		System.out.println("RealtimeInventoryServiceImpl queryStockLevel result :" + result);
		
		return result;
	}
	
	@Override
	public long queryStockThreshold(String catalogRefId, String locationId) {
		System.out.println("RealtimeInventoryServiceImpl queryStockThreshold: " + catalogRefId + ", Location: " + locationId );
		
		long result = 0L;
		
		Inventory inventory = readByCatalogRefIdLocationId(catalogRefId, locationId);
		if (inventory != null) {
			result = inventory.getStockThreshold();
		}
		
		System.out.println("RealtimeInventoryServiceImpl queryStockThreshold result :" + result);
		
		return result;
	}

	@Override
	public int queryAvailabilityStatus(String catalogRefId, String locationId) {
		System.out.println("RealtimeInventoryServiceImpl queryAvailabilityStatus: " + catalogRefId + ", Location: " + locationId );
		
		int result = AVAILABILITY_STATUS_OUT_OF_STOCK;
		
		Inventory inventory = readByCatalogRefIdLocationId(catalogRefId, locationId);
		if (inventory != null) {
			result = inventory.getAvailabilityStatus();
		}
		
		System.out.println("RealtimeInventoryServiceImpl queryAvailabilityStatus result :" + result);
		
		return result;
	}
	
	@Override
	public int decreaseStockLevel(String catalogRefId, long howMany, String locationId) {
		System.out.println("RealtimeInventoryServiceImpl decreaseStockLevel: " + catalogRefId + ", Location: " + locationId + ", howMany: " + howMany);
		
    	Inventory inventory = readByCatalogRefIdLocationId(catalogRefId, locationId);
		InventoryDemand inventoryDemand = readInventoryDemandByCatalogRefIdLocationId(catalogRefId, locationId);
		
		int result = validateInventoryRequest(inventory, inventoryDemand, -howMany);
		
		System.out.println("RealtimeInventoryServiceImpl decreaseStockLevel result :" + result);
		
		return result;
	}
	
	@Override
	public int increaseStockLevel(String catalogRefId, long howMany, String locationId) {
		System.out.println("RealtimeInventoryServiceImpl increaseStockLevel catalogRefId: " + catalogRefId + ", Location: " + locationId + ", howMany: " + howMany);
		
    	Inventory inventory = readByCatalogRefIdLocationId(catalogRefId, locationId);
		InventoryDemand inventoryDemand = readInventoryDemandByCatalogRefIdLocationId(catalogRefId, locationId);
		
		int result = validateInventoryRequest(inventory, inventoryDemand, howMany);
		
		System.out.println("RealtimeInventoryServiceImpl increaseStockLevel result :" + result);
		
		return result;
	}
	
	@Override
	public Date queryAvailabilityDate(String catalogRefId, String locationId) {
		System.out.println("Gigaspace connector queryAvailabilityDate: " + catalogRefId + ", Location: " + locationId );
		
    	Date result = null;
		Inventory inventory = readByCatalogRefIdLocationId(catalogRefId, locationId);
		if (inventory.getAvailabilityStatus() != AVAILABILITY_STATUS_IN_STOCK) {
			result = inventory.getAvailabilityDate();
		}
		
		
		return result;
	}
	
	private Inventory readByCatalogRefIdLocationId(String catalogRefId, String locationId) {
		// TODO: Implement proper logging
		System.out.println("In Remote InventoryService - ID: " + catalogRefId + ", Location: " + locationId);
		SQLQuery<Inventory> query;
		if (locationId != null) {
			query = new SQLQuery<Inventory>(Inventory.class, "catalogRefId = ? and locationId = ?")
					.setParameter(1, catalogRefId)
					.setParameter(2, locationId);
		} else {
			query = new SQLQuery<Inventory>(Inventory.class, "catalogRefId = ? and locationId is null")
					.setParameter(1, catalogRefId);
		}
		Inventory inventory = gigaSpace.read(query);
		// TODO: Think of quering by ID and using concatination of catalogRefId & locationId as ID for Inventory object.
		return inventory;
	}
	private InventoryDemand readInventoryDemandByCatalogRefIdLocationId(String catalogRefId, String locationId) {
		// TODO: Implement proper logging
    	System.out.println("RealtimeInventory readInventoryDemandByCatalogRefIdLocationId: " + catalogRefId + ", Location: " + locationId );
		
		SQLQuery<InventoryDemand> query;
		if (locationId != null) {
			query = new SQLQuery<InventoryDemand>(InventoryDemand.class, "catalogRefId = ? and locationId = ?")
					.setParameter(1, catalogRefId)
					.setParameter(2, locationId);
		} else {
			query = new SQLQuery<InventoryDemand>(InventoryDemand.class, "catalogRefId = ? and locationId is null")
					.setParameter(1, catalogRefId);
		}
		
		InventoryDemand inventory = gigaSpace.read(query);
		
		return inventory;
	}
	 	private int validateInventoryRequest(Inventory inventory, InventoryDemand inventoryDemand, Long howMany) {
			// TODO: Implement proper logging
	    	System.out.println("RealtimeInventory validateInventoryRequest updateStockLevel howMany: " + howMany);
	    	
			if (inventory == null) {
				return INVENTORY_STATUS_ITEM_NOT_FOUND;
			}
			
			long inventoryTotal = (inventory != null)?inventory.getStockLevel():0L;
			long inventoryDemandTotal = (inventoryDemand != null)?inventoryDemand.getStockLevel():0L;
			
			long stockLevel = inventoryTotal - inventoryDemandTotal;
			
			System.out.println("validateInventoryRequest updateStockLevel stocklevel :" + stockLevel);
			
			if (howMany >= 0 || (howMany < 0 && stockLevel >= Math.abs(howMany))) {
				//inventory.setStockLevel(stockLevel + howMany);
				//gigaSpaceRemoteProxy.write(inventory);
				return INVENTORY_STATUS_SUCCEED;
			} else {
				return INVENTORY_STATUS_INSUFFICIENT_SUPPLY;
			}
		}
	/*
	private int updateStockLevel(Inventory inventory, Long howMany) {
		// TODO: Implement proper logging
		System.out.println("In Remote InventoryService - ID: " + inventory + ", HowMany: " + howMany);
		if (inventory == null) {
			return INVENTORY_STATUS_ITEM_NOT_FOUND;
		}
		long stockLevel = inventory.getStockLevel();
		if (howMany >= 0 || (howMany < 0 && stockLevel >= Math.abs(howMany))) {
			inventory.setStockLevel(stockLevel + howMany);
			gigaSpace.write(inventory);
			return INVENTORY_STATUS_SUCCEED;
		} else {
			return INVENTORY_STATUS_INSUFFICIENT_SUPPLY;
		}
	}*/
	
	public void initInventory(String catalogRefId, String locationId) {
		System.out.println("RealtimeInventory catalogRefId: " + catalogRefId + ", locationId: " + locationId );
		
		gigaSpace.write(new Inventory(1, 100L, catalogRefId +"1", "NY", 51L, 1000, new Date()));
		gigaSpace.write(new Inventory(2, 100L, catalogRefId +"2", "NY", 52L, 1000, new Date()));
		gigaSpace.write(new Inventory(3, 100L, catalogRefId +"3", "CA", 53L, 1000, new Date()));
		gigaSpace.write(new Inventory(4, 100L, catalogRefId +"4", "CA", 54L, 1000, new Date()));
		gigaSpace.write(new Inventory(5, 100L, catalogRefId+ "5", "FL", 55L, 1000, new Date()));
	}
}