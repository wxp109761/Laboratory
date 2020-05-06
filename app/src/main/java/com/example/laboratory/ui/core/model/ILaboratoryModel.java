package com.example.laboratory.ui.core.model;

import com.example.laboratory.bean.Items;
import com.example.laboratory.bean.Laboratory;
import com.example.laboratory.bean.LaboratoryList;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.net.callback.RxObserver;

public interface ILaboratoryModel {
    /**
     * 添加巡检记录
     */
    void addLabItemRelaations(String labId,Integer itemId, RxObserver<String> callback);
    void addLabs(Laboratory.RowsBean rowsBean, RxObserver<String> callback);
    void addItems(Items.ItemListBean itemListBean, RxObserver<String> callback);
    void getItemsByBelong(String belong, RxObserver<Items> rxObserver);
    void deleteLabByid(String labid,RxObserver<String> callback);
}
