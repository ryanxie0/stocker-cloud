package com.stockercloud.aws.api;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

public class DynamoDBHelper {
	
	protected DynamoDB dynamoDb;
//    private static final Regions REGION = Regions.US_EAST_2;

	public Table getTable(String table)
	{
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
//      client.setRegion(Region.getRegion(REGION));
        dynamoDb = new DynamoDB(client);
		return dynamoDb.getTable(table);
	}
}
