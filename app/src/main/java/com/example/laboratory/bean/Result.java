package com.example.laboratory.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {

    private List<ResultListBean> resultList;

    public static Result objectFromData(String str) {

        return new Gson().fromJson(str, Result.class);
    }

    public List<ResultListBean> getResultList() {
        return resultList;
    }

    public void setResultList(List<ResultListBean> resultList) {
        this.resultList = resultList;
    }

    public static class ResultListBean implements Serializable{
        /**
         * resullt : 否
         * belong : 0
         * itemname : 门窗是否损坏
         * itemid : 4
         * description : 无
         */
        private String rid;
        private String xjid;
        private String resullt;
        private String belong;
        private String itemname;
        private int itemid;
        private String description;

        public static ResultListBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultListBean.class);
        }

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

        public String getResullt() {
            return resullt;
        }

        public void setResullt(String resullt) {
            this.resullt = resullt;
        }

        public String getBelong() {
            return belong;
        }

        public void setBelong(String belong) {
            this.belong = belong;
        }

        public String getItemname() {
            return itemname;
        }

        public void setItemname(String itemname) {
            this.itemname = itemname;
        }

        public int getItemid() {
            return itemid;
        }

        public void setItemid(int itemid) {
            this.itemid = itemid;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
