package com.mmfive.responses;

import com.google.gson.annotations.SerializedName;
public class StartResponse {

	@SerializedName("size")
	private int size;

	@SerializedName("name")
	private String name;

	@SerializedName("quizz_id")
	private int quizzId;

	public void setSize(int size){
		this.size = size;
	}

	public int getSize(){
		return size;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setQuizzId(int quizzId){
		this.quizzId = quizzId;
	}

	public int getQuizzId(){
		return quizzId;
	}

	public StartResponse() {
	}

	@Override
 	public String toString(){
		return 
			"StartResponse{" +
			"size = '" + size + '\'' + 
			",name = '" + name + '\'' + 
			",quizz_id = '" + quizzId + '\'' + 
			"}";
		}
}