package com.example.laboratory.ui.result;

import com.example.laboratory.bean.Laboratory;
import com.example.laboratory.bean.Result;
import com.example.laboratory.ui.core.view.IPageLoadDataView;


/**
 * 用户协约类
 * author: 康栋普
 * date: 2018/3/21
 */

public interface ResultContract {
    interface IResultPresenter {
        void loadResult();
    }
    interface IResultView extends IPageLoadDataView<Result.ResultListBean> {
        String getXjd();
    }
}
