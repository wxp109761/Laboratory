package com.example.laboratory.ui.core.model;

import com.example.laboratory.bean.Items;
import com.example.laboratory.bean.Laboratory;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.net.callback.RxObserver;

public interface IItemModel {

    void getAllItems(RxObserver<Items> rxObserver);
    void addItems(Items.ItemListBean itemListBean, RxObserver<String> callback);



}
