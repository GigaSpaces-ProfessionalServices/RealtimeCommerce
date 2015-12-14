package com.gigaspaces.commerce.oracle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openspaces.remoting.ExecutorProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Aliaksei Shary (EPAM Systems)
 */
@Controller
public class InventoryEndpoint {
	
	private static final String DATE_FORMAT = "MM/dd/yyyy";
	
	@ExecutorProxy(gigaSpace = "gigaSpace")
	private RealtimeInventoryService inventoryService;
	
	@ResponseBody
	@RequestMapping("/purchase")
	public String purchase(@RequestParam(value = "catalogRefId") String catalogRefId,
						   @RequestParam(value = "howMany") Long howMany,
						   @RequestParam(value = "locationId", required = false) String locationId) {
		int result = inventoryService.purchase(catalogRefId, howMany, locationId);
		return String.valueOf(result);
	}
	
	@ResponseBody
	@RequestMapping("/queryStockLevel")
	public String queryStockLevel(@RequestParam(value = "catalogRefId") String catalogRefId,
								  @RequestParam(value = "locationId", required = false) String locationId) {
		long result = inventoryService.queryStockLevel(catalogRefId, locationId);
		return String.valueOf(result);
	}
	
	@ResponseBody
	@RequestMapping("/queryStockThreshold")
	public String queryStockThreshold(@RequestParam(value = "catalogRefId") String catalogRefId,
									  @RequestParam(value = "locationId", required = false) String locationId) {
		long result = inventoryService.queryStockThreshold(catalogRefId, locationId);
		return String.valueOf(result);
	}
	
	@ResponseBody
	@RequestMapping("/queryAvailabilityStatus")
	public String queryAvailabilityStatus(@RequestParam(value = "catalogRefId") String catalogRefId,
	  									  @RequestParam(value = "locationId", required = false) String locationId) {
		int result = inventoryService.queryAvailabilityStatus(catalogRefId, locationId);
		return String.valueOf(result);
	}
	
	@ResponseBody
	@RequestMapping("/decreaseStockLevel")
	public String decreaseStockLevel(@RequestParam(value = "catalogRefId") String catalogRefId,
						   			 @RequestParam(value = "howMany") Long howMany,
						   			 @RequestParam(value = "locationId", required = false) String locationId) {
		int result = inventoryService.decreaseStockLevel(catalogRefId, howMany, locationId);
		return String.valueOf(result);
	}

	@ResponseBody
	@RequestMapping("/increaseStockLevel")
	public String increaseStockLevel(@RequestParam(value = "catalogRefId") String catalogRefId,
						   			 @RequestParam(value = "howMany") Long howMany,
						   			 @RequestParam(value = "locationId", required = false) String locationId) {
		int result = inventoryService.increaseStockLevel(catalogRefId, howMany, locationId);
		return String.valueOf(result);
	}
	
	@ResponseBody
	@RequestMapping("/queryAvailabilityDate")
	public String queryAvailabilityDate(@RequestParam(value = "catalogRefId") String catalogRefId,
	  									@RequestParam(value = "locationId", required = false) String locationId) {
		Date result = inventoryService.queryAvailabilityDate(catalogRefId, locationId);
		if (result != null) {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			return df.format(result);
		} else {
			return null;
		}
	}
	
	// For DEMO
	@ResponseBody
	@RequestMapping("/initInventory")
	public String initInventory(@RequestParam(value = "catalogRefId") String catalogRefId,
	  							@RequestParam(value = "locationId", required = false) String locationId) {
		inventoryService.initInventory(catalogRefId, locationId);
		return null;
	}
}