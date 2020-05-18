package com.example.laboratory.ui.user.Record;

import com.example.laboratory.bean.Record;
import com.example.laboratory.common.CommonrxObserver;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.impl.UserModel;
import com.example.laboratory.ui.core.presenter.BasePresenter;

import java.util.Date;
import java.util.List;


/**
 * 和用户相关的Presenter
 * author: 康栋普
 * date: 2018/3/21
 */

public class RecordPresenter extends BasePresenter<RecordContract.IRecordView> implements RecordContract. IRecordPresenter {
    private RecordContract.IRecordView iRecordView;
    private UserModel mUserModel;

    RecordPresenter() {
        this.mUserModel = new UserModel();
    }

    @Override
    public void loadRecordList() {
        iRecordView = getView();
        RxObserver<Record> mRecordRxPageList = new RxObserver<Record>(this) {
            @Override
            protected void onSuccess(Record data) {
                List<Record.RecordListBean> bean = data.getRecordList();
                iRecordView.setData(bean);
                if (iRecordView.getData().size() == 0)
                    iRecordView.showEmpty();
                else
                    iRecordView.showContent();
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                iRecordView.showFail(errorMsg);
            }
        };
        mUserModel.getRecordList(UserInfoManager.getUserInfo().getUid(), mRecordRxPageList);
        addDisposable(mRecordRxPageList);
    }


    @Override
    public void deleteXjRecordByXjId(String id) {
        iRecordView = getView();
        RxObserver<String> rxObserver=CommonrxObserver.rxObserver(this,iRecordView,"删除成功");
        mUserModel.deleteXjRecordByXjid(id,rxObserver);
        addDisposable(rxObserver);
    }


}
