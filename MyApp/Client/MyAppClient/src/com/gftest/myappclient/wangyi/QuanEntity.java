package com.gftest.myappclient.wangyi;

import java.io.Serializable;

public class QuanEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 圈子id */
	private String id;
	/** 圈子名称 */
	private String name;

	public QuanEntity() {

	}

	/**
	 * 
	 * @param id
	 *            圈子id
	 * @param name
	 *            圈子名称
	 */
	public QuanEntity(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
