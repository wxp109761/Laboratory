package com.example.laboratory.ui.Remind;

import com.example.laboratory.bean.Remind;
import com.example.laboratory.bean.Result;
import com.example.laboratory.common.CommonUtils;
import com.example.laboratory.common.CommonrxObserver;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.impl.ReminModel;
import com.example.laboratory.ui.core.model.impl.ResultModel;
import com.example.laboratory.ui.core.presenter.BasePresenter;
import com.example.laboratory.ui.core.view.IView;
import com.example.laboratory.ui.result.ResultContract;

import java.util.List;




public class RemindPresenter extends BasePresenter<RemindContract.IRemindView> implements RemindContract.IRemindPresenter{
    private RemindContract.IRemindView remindView;
    private ReminModel mReminModel;

    public RemindPresenter() {
        this.mReminModel=new ReminModel();
    }




    @Override
    public void getRemindsByUid(String uid) {
        remindView = getView();
        RxObserver<Remind> rxObservert=new RxObserver<Remind>(this) {
            @Override
            protected void onSuccess(Remind data) {
                List<Remind.RemindListBean> bean=data.getRemindList();
                remindView.setData(bean);
                if (remindView.getData().size() == 0)
                    remindView.showEmpty();
                else
                    remindView.showContent();
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                remindView.showFail(errorMsg);
            }
        };
        mReminModel.getRemindidByUid(rxObservert,uid);
        addDisposable(rxObservert);
    }

    @Override
    public void addRemind(Remind.RemindListBean remindListBean) {
       IView iView = getView();
        RxObserver<String> rxObservert=CommonrxObserver.rxObserver(this,iView,"添加成功");
        mReminModel.addRemind(rxObservert,remindListBean);
        addDisposable(rxObservert);
    }

    @Override
    public void updateRemind(Integer id, Remind.RemindListBean remindListBean) {
        remindView = getView();
        RxObserver<String> rxObservert=CommonrxObserver.rxObserver(this,remindView,"更新成功");
        mReminModel.updateRemindid(rxObservert,id,remindListBean);
        addDisposable(rxObservert);
    }

    @Override
    public void deleteRemind(Integer id) {
        remindView = getView();
        RxObserver<String> rxObservert=CommonrxObserver.rxObserver(this,remindView,"删除成功");
        mReminModel.deleteRemindid(rxObservert,id);
        addDisposable(rxObservert);
    }
}
