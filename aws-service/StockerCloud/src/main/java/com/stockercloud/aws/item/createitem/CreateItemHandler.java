package com.stockercloud.aws.item.createitem;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.stockercloud.aws.item.BaseHandler;
import com.stockercloud.aws.item.InventoryItem;
import com.stockercloud.aws.item.utils.MapUtil;

public class CreateItemHandler extends BaseHandler implements RequestHandler<CreateItemRequest, CreateItemResponse>  {
	
	public CreateItemResponse handleRequest(CreateItemRequest request, Context context)
	{
		InventoryItem item = request.getItem();		
		long id = generateItemId();
		Item dbItem = MapUtil.mapToDBItem(item, id);
		super.getItemTable().putItem(dbItem);		
		CreateItemResponse response = new CreateItemResponse();
		response.setId(id);
		return response;
	}
	
	private long generateItemId()
	{
		return System.currentTimeMillis();	// mostly for simplicity to try and guarantee the item id is unique
	}
}
