package com.mmfive.responses;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ErrorResponse{

	@SerializedName("Error")
	private String error;

	public void setError(String error){
		this.error = error;
	}

	public String getError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"ErrorResponse{" + 
			"error = '" + error + '\'' + 
			"}";
		}
}