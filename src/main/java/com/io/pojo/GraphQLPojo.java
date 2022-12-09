package com.io.pojo;

/**
 * This is complete POJO class for both query and variable.
 * @author 
 */
public class GraphQLPojo {
	
	private String query;
	private Object variables;
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public Object getVariables() {
		return variables;
	}
	public void setVariables(Object variables) {
		this.variables = variables;
	}

	
}
