package com.example.laboratory.ui.user.Record;

import com.example.laboratory.bean.Laboratory;
import com.example.laboratory.bean.Record;
import com.example.laboratory.ui.core.view.IPageLoadDataView;

import java.util.Date;


/**
 * 用户协约类
 * author: 康栋普
 * date: 2018/3/21
 */

public interface RecordContract {
    interface IRecordPresenter {
        void loadRecordList();
        void deleteXjRecordByXjId(String xjid);
    }

    interface IRecordView extends IPageLoadDataView<Record.RecordListBean> {
    }
}
