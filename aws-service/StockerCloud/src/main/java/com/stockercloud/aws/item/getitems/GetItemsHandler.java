package com.stockercloud.aws.item.getitems;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.stockercloud.aws.item.BaseHandler;
import com.stockercloud.aws.item.InventoryItem;
import com.stockercloud.aws.item.utils.MapUtil;

public class GetItemsHandler extends BaseHandler implements RequestHandler<GetItemsRequest, GetItemsResponse> {
	
	@Override
    public GetItemsResponse handleRequest(GetItemsRequest request, Context context)
    {
    	ItemCollection<ScanOutcome> itemCollection = super.getItemTable().scan();
    	Iterator<Item> iterator = itemCollection.iterator();    	
    	List<InventoryItem> items = new ArrayList<>();
    	Item dbItem;
    	InventoryItem item;    	
    	while (iterator.hasNext())
    	{
    		dbItem = iterator.next();
    		item = MapUtil.mapToInventoryItem(dbItem);
    		items.add(item);
    	}    	
    	GetItemsResponse response = new GetItemsResponse();
    	response.setItems(items);
    	return response;
    }
}