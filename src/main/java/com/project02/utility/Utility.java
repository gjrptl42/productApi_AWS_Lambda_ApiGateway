package com.project02.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project02.entity.Product;

public class Utility {

	 public static Map<String,String> createHeaders(){
	        Map<String,String> headers = new HashMap<>();
	        headers.put("Content-Type","application/json");
	        headers.put("X-amazon-author","Lipsa");
	        headers.put("X-amazon-apiVersion","v1");
	        return  headers ;
	    }
	 public static String convertObjToString(Product product, Context context){
	        String jsonBody = null;
	        try {
	            jsonBody =   new ObjectMapper().writeValueAsString(product);
	        } catch (JsonProcessingException e) {
	            context.getLogger().log( "Error while converting obj to string:::" + e.getMessage());
	        }
	        return jsonBody;
	    }
	 public static Product convertStringToObj(String jsonBody,Context context){
	        Product product = null;
	        try {
	            product =   new ObjectMapper().readValue(jsonBody,Product.class);
	        } catch (JsonProcessingException e) {
	            context.getLogger().log( "Error while converting string to obj:::" + e.getMessage());
	        }
	        return product;
	    }
	 public static String convertListOfObjToString(List<Product> products, Context context){
	        String jsonBody = null;
	        try {
	            jsonBody =   new ObjectMapper().writeValueAsString(products);
	        } catch (JsonProcessingException e) {
	            context.getLogger().log( "Error while converting obj to string:::" + e.getMessage());
	        }
	        return jsonBody;
	    }
	 
}
