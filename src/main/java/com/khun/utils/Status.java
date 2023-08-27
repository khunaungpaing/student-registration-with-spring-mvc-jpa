package com.khun.utils;

public enum Status {
	DISABLE(0),ACTIVE(1);
	private final int value;

	Status(int value){
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	
	 
}
