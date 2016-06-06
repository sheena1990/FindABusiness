package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class Attribute {
	
	private int attribut_bit;
	private String attributeName;
	

	public int getAttribute_bit() {
		return attribut_bit;
	}
	public void setAttribute_bit(int attribute_bit) {
		this.attribut_bit = attribute_bit;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	public Attribute() {}
	
	public Attribute(int attributeBit, String attributeName) {
		this.attribut_bit = attributeBit;
		this.attributeName = attributeName;
	}
	
	public Attribute(String attributeName) {
		this.attributeName = attributeName;
	}
	
	
}

