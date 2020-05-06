package com.example.laboratory.ui.notice;

import android.util.Log;
import com.example.laboratory.bean.Notices;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.impl.NoticeModel;
import com.example.laboratory.ui.core.presenter.BasePresenter;

import java.util.List;

public class NoticePresenter extends  BasePresenter<NoticeContract.INoticeView> implements NoticeContract.INoticePresenter  {
    private NoticeModel noticeModel;
    private NoticeContract.INoticeView noticeView;
    public NoticePresenter(){
        this.noticeModel=new NoticeModel();
    }





    @Override
    public void getNoticesData(String uid, String sendDepart, String category,String send_id) {
        noticeView=getView();
        RxObserver<Notices> rxObserver=new RxObserver<Notices>(this) {
            @Override
            protected void onSuccess(Notices data) {
                List<Notices.NoticesListBean> noticesListBeans = data.getNoticesList();
                if(category.equals("getAll")||category.equals("getByDepart")||category.equals("getBySendId")||category.equals("adminGetAll")){
                    noticeView.setData(noticesListBeans);
                    if (noticeView.getData().size() == 0)
                        noticeView.showEmpty();
                    else
                        noticeView.showContent();
                }else {
                    noticeView.getUnNoticeList(noticesListBeans);
                }
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                noticeView.showFail(errorMsg);
            }
        };
        Log.d("xxx", sendDepart);
        noticeModel.getNoticesData(rxObserver,uid,sendDepart,category,send_id);
        addDisposable(rxObserver);
    }

    @Override
    public void addNotice(Notices.NoticesListBean noticesListBean, String send_id) {
        noticeView=getView();
        RxObserver<String> addObserver=new RxObserver<String>(this) {
            @Override
            protected void onSuccess(String data) {
                noticeView.showResult("发布成功");
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                noticeView.showFail(errorMsg);
            }
        };
        noticeModel.addNotice(noticesListBean,send_id,addObserver);
        addDisposable(addObserver);
    }

    @Override
    public void setRead(Integer id, String uid) {
        noticeView=this.getView();
        RxObserver<String> readObserver=new RxObserver<String>(this) {
            @Override
            protected void onSuccess(String data) {
                noticeView.showResult("已读");
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                noticeView.showFail(errorMsg);
            }
        };
        noticeModel.setRead(id,uid,readObserver);
        addDisposable(readObserver);
    }
}
