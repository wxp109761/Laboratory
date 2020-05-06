package com.example.laboratory.ui.SecureCheck;

import com.example.laboratory.bean.*;
import com.example.laboratory.ui.core.view.IListDataView;
import com.example.laboratory.ui.core.view.IPageLoadDataView;
import com.example.laboratory.ui.core.view.IView;

import java.util.List;

public interface SeCheckContact {
    interface ISeCheckPresenter{
        void getItemsData(String labId);
        void addResultList(List<Xjresult> results);
        void addRecord(Xjrecord xjrecord);
       // void addRecords();
    }
    interface ISeCheckView extends IPageLoadDataView<Items.ItemListBean> {
        void ItemList(List<Items.ItemListBean> itemListBeans);
        void showResult(String msg);

       // List<Items> getIremData();
       String getItemResult();

    }
}
