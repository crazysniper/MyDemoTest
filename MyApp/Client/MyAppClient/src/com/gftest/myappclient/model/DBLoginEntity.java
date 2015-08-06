package com.gftest.myappclient.model;

public class DBLoginEntity {
	private int ID;// 数据库中的ID
	private String UserName;// 账号
	private String Password;// 密码

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
}
