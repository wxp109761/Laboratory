package com.example.laboratory.bean;
import java.io.Serializable;

/**
 * xjresult实体类
 * @author Administrator
 *
 */

public class Xjresult implements Serializable{
	private String rid;//rid

	private String xjid;//xjid
	private String itemid;//itemid
	private String resullt;//resullt
	private String description;//description
	private java.util.Date gmtCreate;//gmt_create
	private java.util.Date gmtUpdate;//gmt_update

	
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getXjid() {
		return xjid;
	}
	public void setXjid(String xjid) {
		this.xjid = xjid;
	}

	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getResullt() {
		return resullt;
	}
	public void setResullt(String resullt) {
		this.resullt = resullt;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public java.util.Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(java.util.Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public java.util.Date getGmtUpdate() {
		return gmtUpdate;
	}
	public void setGmtUpdate(java.util.Date gmtUpdate) {
		this.gmtUpdate = gmtUpdate;
	}
}
