package com.example.laboratory.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class UserList implements Serializable {

    private List<UserListBean> userList;

    public static UserList objectFromData(String str) {

        return new Gson().fromJson(str, UserList.class);
    }

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    public static class UserListBean implements Serializable{
        /**
         * uid : 78714725654990848
         * jobNumber : 708125
         * username : 位展朋
         * password : $2a$10$tr/a9lIgNzbIq5vbdCNb/OnmEYfCj5obiQ2fA0JUmvrAlmlmH.eW6
         * departId : 1070
         * tel : 15262910090
         * permission : 1
         * gmtCreate : 1582721704754
         * gmtUpdate : 1582721704754
         */

        private String uid;
        private String jobNumber;
        private String username;
        private String password;
        private String departId;
        private String tel;
        private String permission;
        private long gmtCreate;
        private long gmtUpdate;

        public static UserListBean objectFromData(String str) {

            return new Gson().fromJson(str, UserListBean.class);
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getJobNumber() {
            return jobNumber;
        }

        public void setJobNumber(String jobNumber) {
            this.jobNumber = jobNumber;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDepartId() {
            return departId;
        }

        public void setDepartId(String departId) {
            this.departId = departId;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
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
