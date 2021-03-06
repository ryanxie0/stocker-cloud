package com.stockercloud.aws.item.getshortages;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.amazonaws.services.lambda.runtime.Context;
import com.stockercloud.aws.item.InventoryItem;
import com.stockercloud.aws.item.getitems.GetItemsHandler;
import com.stockercloud.aws.item.getitems.GetItemsRequest;
import com.stockercloud.aws.item.getitems.GetItemsResponse;

//use mockito runner to automatically inject mocks
@RunWith(MockitoJUnitRunner.class)
public class GetShortagesHandlerTest {

	@Mock GetItemsHandler getItemsHandler;
	@Mock Context context;
	
	@InjectMocks GetShortagesHandler handler;
	
	private String name = "mouse";
	
	@Before
	public void setUp() throws Exception {
		GetItemsResponse response = new GetItemsResponse();
		InventoryItem item = new InventoryItem();
		item.setName(name);
		item.setCurrentQuantity(0);
		item.setOrderQuantity(10);
		List<InventoryItem> items = new ArrayList<>();
		items.add(item);
		response.setItems(items);
		when(getItemsHandler.handleRequest(any(GetItemsRequest.class), any(Context.class))).thenReturn(response);
	}
	
	@Test
	public void test() {
		GetShortagesResponse response = handler.handleRequest(new GetShortagesRequest(), context);
		assertTrue(response.getShortages().size() == 1); // one shortage exists
		assertTrue(response.getShortages().get(0).getItems().size() == 1); // one item in the shortage
		assertEquals(name, response.getShortages().get(0).getItems().get(0).getName()); // item name is the same
	}

}
