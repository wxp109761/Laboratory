package com.example.laboratory.ui.user.Laboratory;

import com.example.laboratory.bean.LaboratoryList;
import com.example.laboratory.common.CommonrxObserver;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.impl.LaboratoryModel;
import com.example.laboratory.ui.core.presenter.BasePresenter;

import java.util.List;


/**
 * 和用户相关的Presenter
 * author: 康栋普
 * date: 2018/3/21
 */

public class  LabPresenter extends BasePresenter<LabContract.ILabView> implements LabContract.ILabPresenter {
    private LabContract.ILabView iLabView;
    private LaboratoryModel laboratoryModel;

    public LabPresenter() {
        this.laboratoryModel = new LaboratoryModel();
    }

    @Override
    public void getLabsData(String id, String cate) {

        iLabView = getView();
        RxObserver<LaboratoryList> rxObserver=new RxObserver<LaboratoryList>(this) {
            @Override
            protected void onSuccess(LaboratoryList data) {
                List<LaboratoryList.LabListBean> labList=data.getLabList();
                iLabView.setData(labList);
                    if (iLabView.getData().size() == 0)
                        iLabView.showEmpty();
                    else
                        iLabView.showContent();
            }
            @Override
            protected void onFail(int errorCode, String errorMsg) {
                iLabView.showFail(errorMsg);
            }
        };
        laboratoryModel.getLabsData(rxObserver,id,cate);
        addDisposable(rxObserver);

    }

    @Override
    public void deleteLabById(String id) {
        iLabView = getView();
        RxObserver<String> rxObserver= CommonrxObserver.rxObserver(this,iLabView,"删除成功");
        laboratoryModel.deleteLabByid(id,rxObserver);
        addDisposable(rxObserver);
    }

}
