package com.stockercloud.aws.item;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.stockercloud.aws.api.DynamoDBHelper;

public class BaseHandler {
	
	protected DynamoDBHelper helper = new DynamoDBHelper();
    public static final String DYNAMODB_TABLE_NAME = "inventory_items";

	protected Table getItemTable()
	{
        return helper.getTable(DYNAMODB_TABLE_NAME);
	}
}
