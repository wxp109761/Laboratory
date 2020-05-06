package com.example.laboratory.ui.webguide;

import com.example.laboratory.ui.core.model.impl.CommonModel;
import com.example.laboratory.ui.core.presenter.BasePresenter;



public class WebViewPresenter extends BasePresenter<WebViewContract.IWebView> implements WebViewContract.IWebViewPresenter {
    private CommonModel commonModel;
    private WebViewContract.IWebView webView;

    WebViewPresenter() {
        this.commonModel = new CommonModel();
    }



}
