package com.gigaspaces.commerce.common;

import java.io.Serializable;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

/*
 * Space class which represents a product catalog summary
 */
@SpaceClass
public class ProductSummary implements Serializable {

	private String skuId;
	private String title;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@SpaceId(autoGenerate = false)
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
	
}
