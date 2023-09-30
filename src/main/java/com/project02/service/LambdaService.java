package com.project02.service;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.project02.entity.Product;
import com.project02.utility.Utility;

public class LambdaService {

	private DynamoDBMapper dynamoDBMapper;
	
	private String jsonBody = null;
	
	public APIGatewayProxyResponseEvent saveProduct(APIGatewayProxyRequestEvent apiGatewayRequest, Context context) {
		initDynamoDB();
		Product product = Utility.convertStringToObj(apiGatewayRequest.getBody(), context);
		dynamoDBMapper.save(product);
		jsonBody = Utility.convertObjToString(product, context);
		context.getLogger().log("Data saved successfully to dynamoDB::: "+jsonBody);
		return createAPIResponse(jsonBody, 201, Utility.createHeaders());
		
	}
	
	public APIGatewayProxyResponseEvent getProductById(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        String productId = apiGatewayRequest.getPathParameters().get("id");
        Product product =   dynamoDBMapper.load(Product.class,productId)  ;
        if(product!=null) {
            jsonBody = Utility.convertObjToString(product, context);
            context.getLogger().log("fetch product By ID:::" + jsonBody);
             return createAPIResponse(jsonBody,200,Utility.createHeaders());
        }else{
            jsonBody = "Product Not Found Exception :" + productId;
             return createAPIResponse(jsonBody,400,Utility.createHeaders());
        }
       
    }
	
	public APIGatewayProxyResponseEvent getProducts(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        List<Product> products = dynamoDBMapper.scan(Product.class,new DynamoDBScanExpression());
        jsonBody =  Utility.convertListOfObjToString(products,context);
        context.getLogger().log("fetch product List:::" + jsonBody);
        return createAPIResponse(jsonBody,200,Utility.createHeaders());
    }
    public APIGatewayProxyResponseEvent deleteProductById(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        String productId = apiGatewayRequest.getPathParameters().get("productId");
        Product product =  dynamoDBMapper.load(Product.class,productId)  ;
        if(product!=null) {
            dynamoDBMapper.delete(product);
            context.getLogger().log("data deleted successfully :::" + productId);
            return createAPIResponse("data deleted successfully." + productId,200,Utility.createHeaders());
        }else{
            jsonBody = "Product Not Found Exception :" + productId;
            return createAPIResponse(jsonBody,400,Utility.createHeaders());
        }
    }
	
	private void initDynamoDB() {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
		dynamoDBMapper = new DynamoDBMapper(client);
		
	}
	
	 private APIGatewayProxyResponseEvent createAPIResponse(String body, int statusCode, Map<String,String> headers ){
	        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
	        responseEvent.setBody(body);
	        responseEvent.setHeaders(headers);
	        responseEvent.setStatusCode(statusCode);
	        return responseEvent;
	    }
	
}
