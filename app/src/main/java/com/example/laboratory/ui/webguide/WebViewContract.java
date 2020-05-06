package com.example.laboratory.ui.webguide;


import com.example.laboratory.ui.core.view.IView;



public class WebViewContract {
    interface IWebViewPresenter{

    }

    interface IWebView extends IView {
        int getArticleId();
        void collect(boolean isCollect, String result);
    }
}
