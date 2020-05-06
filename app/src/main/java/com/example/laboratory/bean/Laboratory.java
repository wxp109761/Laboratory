package com.example.laboratory.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class Laboratory implements Serializable {

    /**
     * total : 2
     * rows : [{"labid":"1001","label":"实验11-309","state":"运行","function":"位展朋：实验室安全员","category":"a","safeStatus":"a","labUid":"78714725654990848","gmtCreate":1582838029000,"gmtUpdate":1582838031000},{"labid":"2001","label":"实验11-308","state":"运行","function":"位展朋：实验室安全员","category":null,"safeStatus":null,"labUid":"78714725654990848","gmtCreate":1582838194000,"gmtUpdate":1582838197000}]
     */

    private int total;
    private List<RowsBean> rows;

    public static Laboratory objectFromData(String str) {

        return new Gson().fromJson(str, Laboratory.class);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean implements Serializable {
        /**
         * labid : 1001
         * label : 实验11-309
         * state : 运行
         * function : 位展朋：实验室安全员
         * category : a
         * safeStatus : a
         * labUid : 78714725654990848
         * gmtCreate : 1582838029000
         * gmtUpdate : 1582838031000
         */

        private String labid;
        private String label;
        private String state;
        private String function;
        private String category;
        private String safeStatus;
        private String labUid;
        private String departId;

        public String getDepartId() {
            return departId;
        }

        public void setDepartId(String departId) {
            this.departId = departId;
        }

        private long gmtCreate;
        private long gmtUpdate;

        public static RowsBean objectFromData(String str) {

            return new Gson().fromJson(str, RowsBean.class);
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
    }
}
