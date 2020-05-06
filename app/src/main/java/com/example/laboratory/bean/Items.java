package com.example.laboratory.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class Items implements Serializable {

    private List<ItemListBean> itemList;

    public static Items objectFromData(String str) {

        return new Gson().fromJson(str, Items.class);
    }

    public List<ItemListBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemListBean> itemList) {
        this.itemList = itemList;
    }

    public static class ItemListBean implements Serializable {

        /**
         * itemid : 1
         * itemname : 计算机是否存在问题
         * belong : 1070
         * gmtCreate : null
         * gmtUpdate : 1582837571000
         */

        private Integer itemid;
        private String itemname;
        private String belong;
        private long gmtCreate;
        private long gmtUpdate;

        public static ItemListBean objectFromData(String str) {

            return new Gson().fromJson(str, ItemListBean.class);
        }

        public Integer getItemid() {
            return itemid;
        }

        public void setItemid(Integer itemid) {
            this.itemid = itemid;
        }

        public String getItemname() {
            return itemname;
        }

        public void setItemname(String itemname) {
            this.itemname = itemname;
        }

        public String getBelong() {
            return belong;
        }

        public void setBelong(String belong) {
            this.belong = belong;
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
