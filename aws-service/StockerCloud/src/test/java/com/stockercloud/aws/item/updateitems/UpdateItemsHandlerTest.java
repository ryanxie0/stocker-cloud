package com.stockercloud.aws.item.updateitems;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.stockercloud.aws.api.DynamoDBHelper;
import com.stockercloud.aws.item.InventoryItem;

//use mockito runner to automatically inject mocks
@RunWith(MockitoJUnitRunner.class)
public class UpdateItemsHandlerTest {
	
	@Mock DynamoDBHelper helper;
	@Mock Table table;
	
	@InjectMocks private UpdateItemsHandler handler;
	
	private String name = "mouse";
	private int currentQuantity = 2;
	private int orderQuantity = 3;
	private int desiredQuantity = 4;
	private String supplier = "microcenter";

	@Before
	public void setUp() throws Exception {
		when(helper.getTable(anyString())).thenReturn(table);
	}
	
	@Test
	public void testWithItem() {
		UpdateItemsRequest request = new UpdateItemsRequest();
		List<InventoryItem> items = new ArrayList<>();
		items.add(createValidItem());
		request.setItems(items);
		handler.handleRequest(request, null);
		verify(table).putItem(any(Item.class)); // verifies that the putItem method is called
	}
	
	private InventoryItem createValidItem()
	{
		InventoryItem item = new InventoryItem();
		item.setName(name);
		item.setCurrentQuantity(currentQuantity);
		item.setOrderQuantity(orderQuantity);
		item.setDesiredQuantity(desiredQuantity);
		item.setSupplier(supplier);
		return item;
	}

}
