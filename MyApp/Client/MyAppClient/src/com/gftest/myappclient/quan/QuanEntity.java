package com.gftest.myappclient.quan;

import java.io.Serializable;

public class QuanEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 圈子id */
	private String id;
	/** 圈子名称 */
	private String name;
	/** 圈子顺序 */
	private int orderId;

	public QuanEntity() {

	}

	/**
	 * 
	 * @param id
	 *            圈子id
	 * @param name
	 *            圈子名称
	 * @param orderId
	 *            圈子顺序
	 */
	public QuanEntity(String id, String name, int orderId) {
		super();
		this.id = id;
		this.name = name;
		this.orderId = orderId;
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

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

}
