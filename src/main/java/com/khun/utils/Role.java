package com.khun.utils;



public enum Role {
	USER(0),ADMIN(1);
	private final int value;

	Role(int value){
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	
	 
}
