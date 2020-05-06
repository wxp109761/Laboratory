package com.example.laboratory.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class Notices implements Serializable {

    private List<NoticesListBean> noticesList;

    public static Notices objectFromData(String str) {

        return new Gson().fromJson(str, Notices.class);
    }

    public List<NoticesListBean> getNoticesList() {
        return noticesList;
    }

    public void setNoticesList(List<NoticesListBean> noticesList) {
        this.noticesList = noticesList;
    }

    public static class NoticesListBean implements Serializable{
        /**
         * id : 1
         * title : 新年快乐
         * notice : 新年快乐
         * sendId : 1
         * isDel : null
         * sendDepart :
         * createTime : 1519835062000
         * updateTime : null
         */

        private Integer id;
        private String title;
        private String notice;
        private String sendId;
        private Object isDel;
        private String sendDepart;
        private long createTime;
        private Object updateTime;

        public static NoticesListBean objectFromData(String str) {

            return new Gson().fromJson(str, NoticesListBean.class);
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public String getSendId() {
            return sendId;
        }

        public void setSendId(String sendId) {
            this.sendId = sendId;
        }

        public Object getIsDel() {
            return isDel;
        }

        public void setIsDel(Object isDel) {
            this.isDel = isDel;
        }

        public String getSendDepart() {
            return sendDepart;
        }

        public void setSendDepart(String sendDepart) {
            this.sendDepart = sendDepart;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }
    }
}
