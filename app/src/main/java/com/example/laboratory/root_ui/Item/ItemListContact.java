package com.example.laboratory.root_ui.Item;

import com.example.laboratory.bean.Items;
import com.example.laboratory.ui.core.view.IListDataView;
import com.example.laboratory.ui.core.view.IPageLoadDataView;
import com.example.laboratory.ui.core.view.IView;

public interface ItemListContact {
    interface ItemListPresenter{
        void getAllItems();
        void addItems(Items.ItemListBean itemListBean);
    }
    interface ItemListView extends IListDataView<Items.ItemListBean> {
        void showResult(String result);
    }
    interface ItemView extends IView {
        void showResult(String result);
    }
}
