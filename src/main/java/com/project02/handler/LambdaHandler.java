package com.project02.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.project02.service.LambdaService;
import com.project02.service.ProductService;

public class LambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>{

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayRequest, Context context) {
		
		LambdaService lambdaService = new LambdaService();
		
		switch(apiGatewayRequest.getHttpMethod()) {
			case "POST":
				lambdaService.saveProduct(apiGatewayRequest, context);
			case "GET":
				if(apiGatewayRequest.getPathParameters()!=null) {
					
					lambdaService.getProductById(apiGatewayRequest, context);
				}
			
				lambdaService.getProducts(apiGatewayRequest, context);
			case "DELETE":
				if(apiGatewayRequest.getPathParameters()!=null) {
					
					lambdaService.deleteProductById(apiGatewayRequest, context);
				}
			default:
				
				throw new Error("Unsupported Methods :::" + apiGatewayRequest.getHttpMethod());
			
		
		}
		
	
	}

}
