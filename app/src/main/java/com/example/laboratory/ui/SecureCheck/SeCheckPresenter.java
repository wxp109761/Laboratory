package com.example.laboratory.ui.SecureCheck;

import com.example.laboratory.R;
import com.example.laboratory.application.AppContext;
import com.example.laboratory.bean.*;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.base.BaseActivity;
import com.example.laboratory.ui.core.model.impl.SeCheckModel;
import com.example.laboratory.ui.core.presenter.BasePresenter;

import java.util.List;

public class SeCheckPresenter extends BasePresenter<SeCheckContact.ISeCheckView> implements SeCheckContact.ISeCheckPresenter {


    private SeCheckModel seCheckModel;
    private SeCheckContact.ISeCheckView mSeCheckView;
    public SeCheckPresenter(){
        this.seCheckModel=new SeCheckModel();
    }


    @Override
    public void getItemsData(String labId) {
        mSeCheckView=getView();
        RxObserver<Items> itemsRxObserver=new RxObserver<Items>(this) {
            @Override
            protected void onSuccess(Items data) {
                mSeCheckView.ItemList(data.getItemList());

                List<Items.ItemListBean> rowsBeans=data.getItemList();
                mSeCheckView.setData(rowsBeans);
                if (mSeCheckView.getData().size() == 0)
                    mSeCheckView.showEmpty();
                else
                    mSeCheckView.showContent();
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mSeCheckView.showFail(errorMsg);
            }
        };
        seCheckModel.getItemsByLabId(labId,itemsRxObserver);
        addDisposable(itemsRxObserver);
    }

    @Override
    public void addResultList(List<Xjresult> results) {
        RxObserver<String> mRxObserver = new RxObserver<String>(this) {
            @Override
            protected void onStart() {
                mSeCheckView.showLoading(AppContext.getContext().getString(R.string.isSubmitting));
            }

            @Override
            protected void onSuccess(String data) {
                mSeCheckView.showResult(AppContext.getContext().getString(R.string.submit_success));
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mSeCheckView.showFail(errorMsg);
            }
        };
       seCheckModel.addRecordsResult(results,mRxObserver);
        addDisposable(mRxObserver);
    }

    @Override
    public void addRecord(Xjrecord xjrecord) {
        RxObserver<String> mRecordObserver=new RxObserver<String>(this) {
            @Override
            protected void onSuccess(String data) {
                //mSeCheckView.showResult(AppContext.getContext().getString(R.string.submit_success));
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mSeCheckView.showFail(errorMsg);
            }
        };
        seCheckModel.addRecords(xjrecord,mRecordObserver);
        addDisposable(mRecordObserver);
    }


}
