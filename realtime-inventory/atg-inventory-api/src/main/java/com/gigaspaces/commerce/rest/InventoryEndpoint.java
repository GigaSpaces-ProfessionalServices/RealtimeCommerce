package com.gigaspaces.commerce.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.openspaces.remoting.ExecutorRemotingProxyConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gigaspaces.commerce.common.service.InventoryService;


@Controller
@EnableAutoConfiguration
public class InventoryEndpoint {
	
	private static final String DATE_FORMAT = "MM/dd/yyyy";
	
	@Autowired
	private InventoryService inventoryService;
	
	public static void main(String[] args) throws Exception {
        SpringApplication.run(InventoryEndpoint.class, args);
    }

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
	
	@Bean(name = "gigaSpace")
	public GigaSpace getGigaSpace() {
		UrlSpaceConfigurer configurer = new UrlSpaceConfigurer("jini://*/*/space?groups=ahodroj");
		return new GigaSpaceConfigurer(configurer).gigaSpace();
	}
	
	@Bean(name = "inventoryService")
	public InventoryService getInventoryService() {
		GigaSpace space = getGigaSpace();
		return new ExecutorRemotingProxyConfigurer<InventoryService>(space, InventoryService.class).proxy();
	}

	
	
}
