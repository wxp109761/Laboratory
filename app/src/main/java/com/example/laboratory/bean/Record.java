package com.example.laboratory.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class  Record implements Serializable {


    private List<RecordListBean> recordList;

    public static Record objectFromData(String str) {

        return new Gson().fromJson(str, Record.class);
    }

    public List<RecordListBean> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<RecordListBean> recordList) {
        this.recordList = recordList;
    }

    public static class RecordListBean implements Serializable{
        /**
         * xjid : xj4
         * label : 实验11-308
         * state : 1
         * gmt_create : 1582924754000
         * gmt_update : 1583274044000
         */

        private String xjid;
        private String label;
        private String state;
        private long gmt_create;
        private long gmt_update;

        public static RecordListBean objectFromData(String str) {

            return new Gson().fromJson(str, RecordListBean.class);
        }

        public String getXjid() {
            return xjid;
        }

        public void setXjid(String xjid) {
            this.xjid = xjid;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public long getGmt_create() {
            return gmt_create;
        }

        public void setGmt_create(long gmt_create) {
            this.gmt_create = gmt_create;
        }

        public long getGmt_update() {
            return gmt_update;
        }

        public void setGmt_update(long gmt_update) {
            this.gmt_update = gmt_update;
        }
    }
}
