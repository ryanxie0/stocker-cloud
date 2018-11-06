package com.stockercloud.aws.item.updateitems;

import java.util.ArrayList;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.stockercloud.aws.item.BaseHandler;
import com.stockercloud.aws.item.InventoryItem;
import com.stockercloud.aws.item.utils.MapUtil;

public class UpdateItemsHandler extends BaseHandler implements RequestHandler<UpdateItemsRequest, UpdateItemsResponse>{

	public UpdateItemsResponse handleRequest(UpdateItemsRequest request, Context context)
	{
		ArrayList<InventoryItem> items = request.getItems();
		Table dbTable = super.getItemTable();
		Item dbItem;
		for (InventoryItem item : items)
		{
			dbItem = MapUtil.mapToDBItem(item, item.getId());
			dbTable.putItem(dbItem);
		}		
		UpdateItemsResponse response = new UpdateItemsResponse();
		return response;
	}
}
