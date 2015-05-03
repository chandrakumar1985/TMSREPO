package com.accion.tms.entity;



import java.io.Serializable;
import java.util.Date;

public class PieChartVo implements Serializable{

	private static final long serialVersionUID = 8768660950428669900L;
	private String key;
	private int count;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	

}
