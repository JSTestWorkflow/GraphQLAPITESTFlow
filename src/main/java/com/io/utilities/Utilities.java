package com.io.utilities;

import org.json.JSONObject;

public class Utilities {

	public static String graphqlToJsonString(String payload){
        JSONObject json = new JSONObject();
        json.put("query", payload);
        return  json.toString();
    }	
}

