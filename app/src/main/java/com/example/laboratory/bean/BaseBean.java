package com.example.laboratory.bean;

/**
 * 实体基类
 * Created by 位展朋 on 2020/2/24
 */

public class BaseBean<T> {
    /**
     * 服务器返回的错误码
     */
    public boolean flag;
    /**
     * 服务器返回的成功或失败的提示
     */
    public int code;
    public String message;
    /**
     * 服务器返回的数据
     */
    public T data;

    public BaseBean(boolean flag, int code, String message, T data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "flag=" + flag +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}