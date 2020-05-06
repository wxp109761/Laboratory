package com.example.laboratory.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class LaboratoryList implements Serializable {

    private List<LabListBean> labList;

    public static LaboratoryList objectFromData(String str) {

        return new Gson().fromJson(str, LaboratoryList.class);
    }

    public List<LabListBean> getLabList() {
        return labList;
    }

    public void setLabList(List<LabListBean> labList) {
        this.labList = labList;
    }

    public static class LabListBean implements Serializable{
        /**
         * labid : 2001
         * label : 实验B-312
         * state : 运行中
         * departId : 1070
         * function : 负责人：许琦
         * category : 个人办公室
         * safeStatus : 安全
         * labUid : 78714725654990857
         * gmtCreate : 1586226139000
         * gmtUpdate : 1588126943000
         * items : [{"itemid":1,"itemname":"用水是否安全","belong":"0","gmtCreate":1586444659000,"gmtUpdate":1586271863000},{"itemid":3,"itemname":"门窗是否完好","belong":"0","gmtCreate":1582837650000,"gmtUpdate":1582837653000},{"itemid":4,"itemname":"用电是否安全","belong":"0","gmtCreate":1582837510000,"gmtUpdate":1582837514000}]
         */

        private String labid;
        private String label;
        private String state;
        private String departId;
        private String function;
        private String category;
        private String safeStatus;
        private String labUid;
        private long gmtCreate;
        private long gmtUpdate;
        private List<ItemsBean> items;

        public static LabListBean objectFromData(String str) {

            return new Gson().fromJson(str, LabListBean.class);
        }

        public String getLabid() {
            return labid;
        }

        public void setLabid(String labid) {
            this.labid = labid;
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

        public String getDepartId() {
            return departId;
        }

        public void setDepartId(String departId) {
            this.departId = departId;
        }

        public String getFunction() {
            return function;
        }

        public void setFunction(String function) {
            this.function = function;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSafeStatus() {
            return safeStatus;
        }

        public void setSafeStatus(String safeStatus) {
            this.safeStatus = safeStatus;
        }

        public String getLabUid() {
            return labUid;
        }

        public void setLabUid(String labUid) {
            this.labUid = labUid;
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

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean implements Serializable{
            /**
             * itemid : 1
             * itemname : 用水是否安全
             * belong : 0
             * gmtCreate : 1586444659000
             * gmtUpdate : 1586271863000
             */

            private int itemid;
            private String itemname;
            private String belong;
            private long gmtCreate;
            private long gmtUpdate;

            public static ItemsBean objectFromData(String str) {

                return new Gson().fromJson(str, ItemsBean.class);
            }

            public int getItemid() {
                return itemid;
            }

            public void setItemid(int itemid) {
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
}
