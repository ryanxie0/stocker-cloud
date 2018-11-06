package com.stockercloud.aws.item.deleteitem;

import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.stockercloud.aws.item.BaseHandler;

public class DeleteItemHandler extends BaseHandler implements RequestHandler<DeleteItemRequest, DeleteItemResponse>{
	
	public DeleteItemResponse handleRequest(DeleteItemRequest request, Context context)
	{
		super.getItemTable().deleteItem(new PrimaryKey("id", request.getId()));		
		DeleteItemResponse response = new DeleteItemResponse();
		response.setId(request.getId());
		return response;
	}
}
