package com.example.a29149.ecollaboration.model.message.newmessage.sort;

import java.io.Serializable;

public class SortModel implements Serializable {
	
	private String name;
	private String sortLetters;
	private boolean isCheck=false;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean check) {
		isCheck = check;
	}
}
