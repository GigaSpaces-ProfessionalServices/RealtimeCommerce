package com.gigaspaces.commerce.personalization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.openspaces.events.notify.NotifyType;

import com.gigaspaces.commerce.common.ProductSummary;
import com.gigaspaces.commerce.common.ShoppingCartItem;
import com.gigaspaces.commerce.common.ShoppingRecommendation;
import com.gigaspaces.httpsession.models.SpaceSessionByteArray;
import com.gigaspaces.httpsession.serialize.CompressUtils;
import com.gigaspaces.httpsession.serialize.SerializeUtils;
import com.gigaspaces.httpsession.sessions.FullStoreMode;
import com.j_spaces.core.client.SQLQuery;

@EventDriven
@Notify
@NotifyType(write = true, update = true)
public class PersonalizationProcessor {
	
		@GigaSpaceContext
		private GigaSpace gigaSpace;
	
		@EventTemplate
		public SQLQuery<SpaceSessionByteArray> spaceSessionTemplate() {
			SQLQuery<SpaceSessionByteArray> template = new SQLQuery<SpaceSessionByteArray>(SpaceSessionByteArray.class, "version > 0");
			return template;
		}
		
		@SpaceDataEvent
		public void onSessionChanges(SpaceSessionByteArray spaceSessionByteArray) {
			 List<ShoppingCartItem> items = getShoppingCartItems(spaceSessionByteArray);
			 
			 
			 // Create dummy recommendations
			 //		Customers who bought this, also bought...etc
			 ShoppingRecommendation sr = new ShoppingRecommendation();
			 sr.setSessionId(spaceSessionByteArray.getId());
			 sr.setUserId("ali");
			 
			 
			 for(ShoppingCartItem item : items) {
				 ProductSummary ps = new ProductSummary();
				 ps.setTitle("New and Improved " + item.getName());
				 ps.setSkuId(item.getSkuId() + "0");
				 
				 sr.getRecommendedProducts().add(ps);
			 }
			 
			 // Create recommendation
			 gigaSpace.write(sr);
			 
			 
		}
		
		private List<ShoppingCartItem> getShoppingCartItems(SpaceSessionByteArray ssba) {
			CompressUtils.register(null);
            SerializeUtils.register(null);
           
            FullStoreMode fsm = new FullStoreMode();
       
            Map<Object, Object> map = fsm.getAttribures(ssba, null);
            
           return (ArrayList<ShoppingCartItem>) map.get("cart");
		}
	
}
