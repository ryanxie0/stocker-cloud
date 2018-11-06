package com.stockercloud.aws.item.getshortages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.stockercloud.aws.item.BaseHandler;
import com.stockercloud.aws.item.InventoryItem;
import com.stockercloud.aws.item.getitems.GetItemsHandler;
import com.stockercloud.aws.item.getitems.GetItemsRequest;
import com.stockercloud.aws.item.getitems.GetItemsResponse;

public class GetShortagesHandler extends BaseHandler implements RequestHandler<GetShortagesRequest, GetShortagesResponse>{
	
	public GetShortagesResponse handleRequest(GetShortagesRequest request, Context context)
	{
    	Map<String, List<InventoryItem>> shortageMap = new HashMap<>();
    	GetItemsHandler getItemsHandler = new GetItemsHandler();
    	GetItemsResponse getItemsResponse = getItemsHandler.handleRequest(new GetItemsRequest(), context);
    	for (InventoryItem item: getItemsResponse.getItems())
    	{
    		if (item.getCurrentQuantity() <= item.getOrderQuantity())
    		{
    			if (shortageMap.get(item.getSupplier()) == null)
    			{
    				shortageMap.put(item.getSupplier(), new ArrayList<InventoryItem>());
    			}
    			shortageMap.get(item.getSupplier()).add(item);
    		}
    	}
		GetShortagesResponse response = new GetShortagesResponse();
		List<Shortage> shortages = new ArrayList<>();
    	Shortage shortage;
		for (String supplier : shortageMap.keySet())
		{
			shortage = new Shortage();
			shortage.setSupplier(supplier);
			shortage.setItems(shortageMap.get(supplier));
			shortages.add(shortage);
		}
		response.setShortages(shortages);
		return response;
	}
}
