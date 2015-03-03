package com.gigaspaces.commerce.personalization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;

import com.gigaspaces.httpsession.models.SpaceSessionByteArray;
import com.gigaspaces.httpsession.serialize.CompressUtils;
import com.gigaspaces.httpsession.serialize.SerializeUtils;
import com.gigaspaces.httpsession.sessions.FullStoreMode;

@EventDriven
@Notify
public class PersonalizationProcessor {
	
		@EventTemplate
		public SpaceSessionByteArray spaceSessionTemplate() {
			return new SpaceSessionByteArray();
		}
		
		@SpaceDataEvent
		public void onSessionChanges(SpaceSessionByteArray spaceSessionByteArray) {
			 
			
            
		}
		
		private List<ShoppingCartItem> getShoppingCartItems(SpaceSessionByteArray ssba) {
			CompressUtils.register(null);
            SerializeUtils.register(null);
           
            FullStoreMode fsm = new FullStoreMode();
       
            Map<Object, Object> map = fsm.getAttribures(ssba, null);
            
           return (ArrayList<ShoppingCartItem>) map.get("cart");
		}
	
}
