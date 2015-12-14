package com.gigaspace.commerce.inventory;

import java.util.Date;

import org.openspaces.core.GigaSpace;
import org.openspaces.remoting.RemotingService;
import org.springframework.beans.factory.annotation.Autowired;

import com.gigaspaces.commerce.oracle.Inventory;
import com.gigaspaces.commerce.oracle.RealtimeInventoryService;
import com.j_spaces.core.client.SQLQuery;

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
		Inventory inventory = readByCatalogRefIdLocationId(catalogRefId, locationId);
		return updateStockLevel(inventory, -howMany);
	}

	@Override
	public long queryStockLevel(String catalogRefId, String locationId) {
		long result = 0L;
		Inventory inventory = readByCatalogRefIdLocationId(catalogRefId, locationId);
		if (inventory != null) {
			result = inventory.getStockLevel();
		}
		return result;
	}
	
	@Override
	public long queryStockThreshold(String catalogRefId, String locationId) {
		long result = 0L;
		Inventory inventory = readByCatalogRefIdLocationId(catalogRefId, locationId);
		if (inventory != null) {
			result = inventory.getStockThreshold();
		}
		return result;
	}

	@Override
	public int queryAvailabilityStatus(String catalogRefId, String locationId) {
		int result = AVAILABILITY_STATUS_OUT_OF_STOCK;
		Inventory inventory = readByCatalogRefIdLocationId(catalogRefId, locationId);
		if (inventory != null) {
			result = inventory.getAvailabilityStatus();
		}
		return result;
	}
	
	@Override
	public int decreaseStockLevel(String catalogRefId, long howMany, String locationId) {
		Inventory inventory = readByCatalogRefIdLocationId(catalogRefId, locationId);
		return updateStockLevel(inventory, -howMany);
	}
	
	@Override
	public int increaseStockLevel(String catalogRefId, long howMany, String locationId) {
		Inventory inventory = readByCatalogRefIdLocationId(catalogRefId, locationId);
		return updateStockLevel(inventory, howMany);
	}
	
	@Override
	public Date queryAvailabilityDate(String catalogRefId, String locationId) {
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
	}
	
	public void initInventory(String catalogRefId, String locationId) {
		gigaSpace.write(new Inventory(1, 100L, catalogRefId, null, 50L, 1000, null));
	}
}