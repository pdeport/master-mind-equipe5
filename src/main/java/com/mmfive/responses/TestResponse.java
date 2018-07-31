package com.mmfive.responses;

import com.google.gson.annotations.SerializedName;
/**
 *
 * @author MM5_DevTeam
 */
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

	public TestResponse() {

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