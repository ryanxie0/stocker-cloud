package com.stockercloud.aws.item.createitem;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
import com.stockercloud.aws.item.ValidationException;

//use mockito runner to automatically inject mocks
@RunWith(MockitoJUnitRunner.class)
public class CreateItemHandlerTest {
	
	@Mock DynamoDBHelper helper;
	@Mock Table table;
	
	@InjectMocks private CreateItemHandler handler;
	
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
	public void testHandleRequestValidInputs() {
		CreateItemRequest request = new CreateItemRequest();
		request.setItem(createValidItem());
		CreateItemResponse response = handler.handleRequest(request, null);
		assertNotNull(response.getId()); // just check that the response is given a long
		verify(table).putItem(any(Item.class)); // verifies that the putItem method is called
	}
	
	@Test
	public void testInvalidName() {
		InventoryItem item = createValidItem();
		item.setName("");
		checkInvalidInput("Name cannot be empty", item);
	}
	
	@Test
	public void testInvalidCurrentQuantity() {
		InventoryItem item = createValidItem();
		item.setCurrentQuantity(-5);
		checkInvalidInput("Current quantity must be non-negative", item);
	}
	
	@Test
	public void testInvalidOrderQuantity() {
		InventoryItem item = createValidItem();
		item.setOrderQuantity(-4);
		checkInvalidInput("Order quantity must be non-negative", item);
	}
	
	@Test
	public void testDesiredQuantity() {
		InventoryItem item = createValidItem();
		item.setDesiredQuantity(-3);
		checkInvalidInput("Desired quantity must be non-negative", item);
	}
	
	@Test
	public void testInvalidSupplier() {
		InventoryItem item = createValidItem();
		item.setSupplier(null);
		checkInvalidInput("Supplier cannot be empty", item);
	}
	
	@Test
	public void testQuantityConstraint() {
		InventoryItem item = createValidItem();
		item.setOrderQuantity(5);
		item.setDesiredQuantity(3);
		checkInvalidInput("Order quantity must be less than desired quantity", item);
	}
	
	private void checkInvalidInput(String expected, InventoryItem item)
	{
		CreateItemRequest request = new CreateItemRequest();
		request.setItem(item);
		try {
			handler.handleRequest(request, null);
			fail("Test should have failed");
		}
		catch(ValidationException e) {
			assertEquals(expected, e.getMessage());
		}
		verify(table, never()).putItem(any(Item.class)); // verifies that the putItem method is never called
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
