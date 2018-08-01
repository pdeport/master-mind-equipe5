package com.mmfive.exceptions.requests;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class TestRequest {

	@SerializedName("result")
	private String result;

	@SerializedName("token")
	private String token;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	public TestRequest(String result, String token) {
		this.result = result;
		this.token = token;
	}

	@Override
 	public String toString(){
		return 
			"{" +
			"result = '" + result + '\'' +
			",token = '" + token + '\'' +
			"}";
		}
}