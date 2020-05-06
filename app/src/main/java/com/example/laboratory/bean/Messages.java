package com.example.laboratory.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class Messages implements Serializable {

    private List<MessageListBean> messageList;

    public static Messages objectFromData(String str) {

        return new Gson().fromJson(str, Messages.class);
    }

    public List<MessageListBean> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<MessageListBean> messageList) {
        this.messageList = messageList;
    }

    public static class MessageListBean {
        /**
         * id : 10
         * title : 渣渣
         * message : 渣渣渣渣渣渣
         * sendId : 8
         * receiveId : 1
         * isDel : 否
         * createTime : 1519726253000
         * updateTime : 1588801062000
         */

        private String id;
        private String title;
        private String message;
        private String sendId;
        private String receiveId;
        private String isDel;
        private long createTime;
        private long updateTime;

        public static MessageListBean objectFromData(String str) {

            return new Gson().fromJson(str, MessageListBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getSendId() {
            return sendId;
        }

        public void setSendId(String sendId) {
            this.sendId = sendId;
        }

        public String getReceiveId() {
            return receiveId;
        }

        public void setReceiveId(String receiveId) {
            this.receiveId = receiveId;
        }

        public String getIsDel() {
            return isDel;
        }

        public void setIsDel(String isDel) {
            this.isDel = isDel;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
