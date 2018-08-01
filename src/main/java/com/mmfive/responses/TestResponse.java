package com.mmfive.responses;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class TestResponse{

	@SerializedName("good")
	private int good;

	@SerializedName("wrong_place")
	private int wrongPlace;

	public void setGood(int good){
		this.good = good;
	}

	public int getGood(){
		return good;
	}

	public void setWrongPlace(int wrongPlace){
		this.wrongPlace = wrongPlace;
	}

	public int getWrongPlace(){
		return wrongPlace;
	}

	@Override
 	public String toString(){
		return 
			"TestResponse{" +
			"good = '" + good + '\'' +
			",wrong_place = '" + wrongPlace + '\'' +
			"}";
		}
}