package com.xin.pojo;

import java.util.Date;

public class User{
	private int uid;
	private String name;
	private String psw;
	private Date createdate;

	public User() {
		super();
	}

	public User(int uid, String name, String psw) {
		super();
		this.uid = uid;
		this.name = name;
		this.psw = psw;
	}

	public User(String name, String psw) {
		super();
		this.name = name;
		this.psw = psw;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

}