package com.stockercloud.aws.item;

//import com.amazonaws.regions.Region;
//import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

public class BaseHandler {
	
	protected DynamoDB dynamoDb;
    protected static final String DYNAMODB_TABLE_NAME = "inventory_items";
//    private static final Regions REGION = Regions.US_EAST_2;

	protected Table getItemTable()
	{
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
//      client.setRegion(Region.getRegion(REGION));
        dynamoDb = new DynamoDB(client);
		return dynamoDb.getTable(DYNAMODB_TABLE_NAME);
	}
}
