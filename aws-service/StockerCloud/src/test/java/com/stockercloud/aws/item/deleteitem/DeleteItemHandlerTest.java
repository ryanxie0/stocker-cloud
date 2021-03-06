package com.stockercloud.aws.item.deleteitem;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.stockercloud.aws.api.DynamoDBHelper;

//use mockito runner to automatically inject mocks
@RunWith(MockitoJUnitRunner.class)
public class DeleteItemHandlerTest {
	
	@Mock DynamoDBHelper helper;
	@Mock Table table;
	
	@InjectMocks private DeleteItemHandler handler;
	
	private long id = System.currentTimeMillis();

	@Before
	public void setUp() throws Exception {
		when(helper.getTable(anyString())).thenReturn(table);
	}

	@Test
	public void testWithId() {
		DeleteItemRequest request = new DeleteItemRequest();
		request.setId(id);
		DeleteItemResponse response = handler.handleRequest(request, null);
		assertEquals(id, response.getId());
		verify(table).deleteItem(any(PrimaryKey.class)); // verifies that the putItem method is called
	}

}
