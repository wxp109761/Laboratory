package com.example.laboratory.root_ui.AddLaboratory;

import android.app.Application;
import com.example.laboratory.bean.Items;
import com.example.laboratory.bean.Laboratory;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.ui.core.view.IListDataView;
import com.example.laboratory.ui.core.view.ILoadView;
import com.example.laboratory.ui.core.view.IPageLoadDataView;
import com.example.laboratory.ui.core.view.IView;

import java.util.List;

public interface AddLabContact {
    interface IAddLabsPresenter{
        void addLabs(Laboratory.RowsBean labs);
        void addItems(Items.ItemListBean itemListBean);
        void addLabItemRelations(String labId,Integer itemId);
        void getItemsData(String belong);
    }
    interface IAddLabsView extends IView{

        void setItemList(List<Items.ItemListBean> itemList);

    }
}
