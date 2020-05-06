package com.example.laboratory.bean;

import java.io.Serializable;
import java.util.Date;

public class Xjrecord implements Serializable {
    private String xjid;//xjid
    private String xjrUid;//uid
    private String labid;//labid
    private String state;//状态：0，1，2，3
    private java.util.Date gmtCreate;//gmt_create
    private java.util.Date gmtUpdate;//gmt_update

    public String getXjid() {
        return xjid;
    }

    public void setXjid(String xjid) {
        this.xjid = xjid;
    }

    public String getXjrUid() {
        return xjrUid;
    }

    public void setXjrUid(String xjrUid) {
        this.xjrUid = xjrUid;
    }

    public String getLabid() {
        return labid;
    }

    public void setLabid(String labid) {
        this.labid = labid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }
}
