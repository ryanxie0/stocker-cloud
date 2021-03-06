package com.stockercloud.aws.item.updateitems;

import java.util.List;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.stockercloud.aws.item.BaseHandler;
import com.stockercloud.aws.item.InventoryItem;
import com.stockercloud.aws.item.utils.ItemUtil;

public class UpdateItemsHandler extends BaseHandler implements RequestHandler<UpdateItemsRequest, UpdateItemsResponse>{

	public UpdateItemsResponse handleRequest(UpdateItemsRequest request, Context context)
	{
		List<InventoryItem> items = request.getItems();
		Table dbTable = super.getItemTable();
		Item dbItem;
		for (InventoryItem item : items)
		{
			ItemUtil.validateInput(item);
			dbItem = ItemUtil.mapToDBItem(item, item.getId());
			dbTable.putItem(dbItem);
		}		
		return new UpdateItemsResponse();
	}
}
