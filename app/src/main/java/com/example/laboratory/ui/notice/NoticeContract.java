package com.example.laboratory.ui.notice;

import com.example.laboratory.bean.Notices;
import com.example.laboratory.ui.core.view.IListDataView;
import com.example.laboratory.ui.core.view.IView;

import java.util.List;

public interface NoticeContract {

    interface INoticePresenter {

        void getNoticesData(String uid,String sendDepart,String category,String send_id);
        void addNotice(Notices.NoticesListBean noticesListBean,String send_id);
        void setRead(Integer id,String uid);
    }
    interface INoticeView extends IListDataView<Notices.NoticesListBean> {
        void getUnNoticeList(List<Notices.NoticesListBean> noticesListBeans);
    }
}
