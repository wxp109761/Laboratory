package com.example.laboratory.ui.user.Laboratory;

import com.example.laboratory.bean.Laboratory;
import com.example.laboratory.bean.LaboratoryList;
import com.example.laboratory.bean.Record;
import com.example.laboratory.ui.core.view.IPageLoadDataView;
import com.example.laboratory.ui.core.view.IView;

import java.util.List;


/**
 * 用户协约类
 * author: 康栋普
 * date: 2018/3/21
 */

public interface LabContract {
    interface ILabPresenter {
        void getLabsData(String uid,String cate);
        void deleteLabById(String id);

    }
    interface ILabView extends IPageLoadDataView<LaboratoryList.LabListBean> {
    }
}
