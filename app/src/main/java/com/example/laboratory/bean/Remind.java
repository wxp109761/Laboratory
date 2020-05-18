package com.example.laboratory.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class Remind implements Serializable {
    private List<RemindListBean> remindList;

    public static Remind objectFromData(String str) {

        return new Gson().fromJson(str, Remind.class);
    }

    public List<RemindListBean> getRemindList() {
        return remindList;
    }

    public void setRemindList(List<RemindListBean> remindList) {
        this.remindList = remindList;
    }

    public static class RemindListBean {
        /**
         * id : 1
         * title : 123456
         * content : 放松放松
         * isDone : 0
         * createTime : 1588865861000
         * remindTime : 1588865861000
         * uid : 78714725654990857
         */

        private int id;

        public int getImgId() {
            return imgId;
        }

        public void setImgId(int imgId) {
            this.imgId = imgId;
        }

        private int imgId;
        private String title;
        private String content;
        private String isDone;
        private long createTime;
        private long remindTime;
        private String uid;

        public static RemindListBean objectFromData(String str) {

            return new Gson().fromJson(str, RemindListBean.class);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIsDone() {
            return isDone;
        }

        public void setIsDone(String isDone) {
            this.isDone = isDone;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getRemindTime() {
            return remindTime;
        }

        public void setRemindTime(long remindTime) {
            this.remindTime = remindTime;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
