package com.stockercloud.aws.item.getitems;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.internal.IteratorSupport;
import com.stockercloud.aws.api.DynamoDBHelper;
import com.stockercloud.aws.item.BaseHandler;
import com.stockercloud.aws.item.InventoryItem;

import static org.mockito.Mockito.*;

// use mockito runner to automatically inject mocks
@RunWith(MockitoJUnitRunner.class)
public class GetItemsHandlerTest {

	// these are AWS API classes that shouldn't be called in a unit test, must be mocked instead
	@Mock DynamoDBHelper helper;
	@Mock Table table;
	@Mock ItemCollection<ScanOutcome> itemCollection;
	@Mock IteratorSupport<Item, ScanOutcome> iterator;
	@Mock Item dbItem;
	
	// injects mocked helper above into the handler
	@InjectMocks private GetItemsHandler handler;
	
	private String name = "mouse";

	@Before
	public void setUp() throws Exception {
		// mocking the call sequence of the handler
		when(helper.getTable(anyString())).thenReturn(table);
		when(table.scan()).thenReturn(itemCollection);
		when(itemCollection.iterator()).thenReturn(iterator);
		when(dbItem.getString("name")).thenReturn(name);
	}

	@Test
	public void testNoItems() {
		GetItemsResponse response = handler.handleRequest(new GetItemsRequest(), null);
		assertTrue(response.getItems().isEmpty());
		// checks that the handler calls the helper once
		verify(helper).getTable(BaseHandler.DYNAMODB_TABLE_NAME);
	}
	
	@Test
	public void testWithItems() {
		// mock the while loop to return a single item
		when(iterator.hasNext()).thenReturn(true, false);
		when(iterator.next()).thenReturn(dbItem);
		GetItemsResponse response = handler.handleRequest(new GetItemsRequest(), null);
		assertTrue(response.getItems().size() == 1);
		InventoryItem inventoryItem = response.getItems().get(0);
		// don't need to test every field because a unit test can be written for the map util
		assertEquals(name, inventoryItem.getName());
		// checks that the handler calls the helper once
		verify(helper).getTable(BaseHandler.DYNAMODB_TABLE_NAME);
	}
}
