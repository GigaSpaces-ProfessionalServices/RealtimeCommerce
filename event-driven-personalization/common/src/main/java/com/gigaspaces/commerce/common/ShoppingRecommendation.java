package com.gigaspaces.commerce.common;

import java.util.ArrayList;
import java.util.List;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

@SpaceClass
public class ShoppingRecommendation {

		private String sessionId;
		
		private String userId;
		
		private List<ProductSummary> recommendedProducts;
		
		public ShoppingRecommendation() {
			this.recommendedProducts = new ArrayList<ProductSummary>();
		}
		
		@SpaceId(autoGenerate = false)
		public String getSessionId() {
			return sessionId;
		}
		
		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}
		
		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public List<ProductSummary> getRecommendedProducts() {
			return recommendedProducts;
		}

		public void setRecommendedProducts(List<ProductSummary> recommendedProducts) {
			this.recommendedProducts = recommendedProducts;
		}
		
		
}
