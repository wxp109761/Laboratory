package com.example.laboratory.ui.core.view;

/**
 * view基类
 * Create by 位展朋 on 2020.2.24
 */


public interface IView {
    /**
     * 显示进度条
     *
     */
    void showLoading(String msg);

    /**
     * 隐藏进度条
     */
    void hideLoading();

    /**
     * 失败
     *
     * @param msg
     */
    void showFail(String msg);

    /**
     * 错误
     */
    void showError();

    /**
     * 没有数据
     */
    void showEmpty();//没有数据
    void showResult(String msg);//没有数据
}