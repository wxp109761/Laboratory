package com.example.laboratory.bean;


import com.google.gson.Gson;

import java.io.Serializable;

/**
 * 当前用户
 */
public class User implements Serializable {


    /**
     * gmt_create : 1582721704754
     * uid : 78714725654990857
     * password : $2a$10$tr/a9lIgNzbIq5vbdCNb/OnmEYfCj5obiQ2fA0JUmvrAlmlmH.eW6
     * gmt_update : 1582721704754
     * avatarUrl : http://q94nddme1.bkt.clouddn.com/image/i1.jpg
     * roles : user
     * departId : 1070
     * permission : 1
     * tel : 15262910090
     * jobNumber : 708127
     * token : eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI3ODcxNDcyNTY1NDk5MDg1NyIsInN1YiI6IjcwODEyNyIsImlhdCI6MTU4ODc3MTM3Niwicm9sZXMiOiJ1c2VyIiwiZXhwIjoxNTg4Nzc0OTc2fQ.1A4LH0xrU1tQ0vLTvVWod2XbUpi9SOK7aKgwlKE-GeI
     * username : 许琦
     */


    private long gmt_create;
    private String uid;
    private String password;
    private long gmt_update;
    private String roles;
    private String departId;
    private String permission;
    private String tel;
    private String jobNumber;
    private String token;
    private String username;
    private String avatarUrl;




    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public static User objectFromData(String str) {

        return new Gson().fromJson(str, User.class);
    }

    public long getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(long gmt_create) {
        this.gmt_create = gmt_create;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getGmt_update() {
        return gmt_update;
    }

    public void setGmt_update(long gmt_update) {
        this.gmt_update = gmt_update;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
