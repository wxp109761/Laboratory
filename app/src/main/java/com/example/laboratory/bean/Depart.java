package com.example.laboratory.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class Depart implements Serializable {

    private List<DepartListBean> departList;

    public static Depart objectFromData(String str) {

        return new Gson().fromJson(str, Depart.class);
    }

    public List<DepartListBean> getDepartList() {
        return departList;
    }

    public void setDepartList(List<DepartListBean> departList) {
        this.departList = departList;
    }

    public static class DepartListBean {
        /**
         * depart_id : 1010
         * depart_name : 深蓝学院
         * gmtCreate : 1585699200000
         * gmtUpdate : 1586217600000
         */
        private String depart_id;
        private String depart_name;
        private long gmtCreate;
        private long gmtUpdate;

        public static DepartListBean objectFromData(String str) {

            return new Gson().fromJson(str, DepartListBean.class);
        }

        public String getDepart_id() {
            return depart_id;
        }

        public void setDepart_id(String depart_id) {
            this.depart_id = depart_id;
        }

        public String getDepart_name() {
            return depart_name;
        }

        public void setDepart_name(String depart_name) {
            this.depart_name = depart_name;
        }

        public long getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(long gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public long getGmtUpdate() {
            return gmtUpdate;
        }

        public void setGmtUpdate(long gmtUpdate) {
            this.gmtUpdate = gmtUpdate;
        }


    }
}