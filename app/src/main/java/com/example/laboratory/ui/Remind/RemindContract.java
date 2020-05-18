package com.example.laboratory.ui.Remind;

import com.example.laboratory.bean.Remind;
import com.example.laboratory.ui.core.view.IListDataView;
import com.example.laboratory.ui.core.view.IPageLoadDataView;
import com.example.laboratory.ui.core.view.IView;

import java.util.List;

public interface RemindContract {
    interface IRemindPresenter {
        void getRemindsByUid(String uid);
        void addRemind(Remind.RemindListBean remindListBean);
        void updateRemind(Integer id,Remind.RemindListBean remindListBean);
        void deleteRemind(Integer id);
    }
    interface IRemindView extends IListDataView<Remind.RemindListBean> {
    }
}
