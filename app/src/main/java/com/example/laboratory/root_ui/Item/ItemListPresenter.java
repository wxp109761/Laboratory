package com.example.laboratory.root_ui.Item;

import com.example.laboratory.bean.Items;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.impl.ItemModel;
import com.example.laboratory.ui.core.presenter.BasePresenter;

import java.util.List;

public class ItemListPresenter  extends BasePresenter<ItemListContact.ItemListView> implements ItemListContact.ItemListPresenter{
    private ItemModel itemModel;



    private ItemListContact.ItemListView itemListView;
    public ItemListPresenter(){
        this.itemModel=new ItemModel();
    }
    @Override
    public void getAllItems() {
        itemListView=getView();
        RxObserver<Items> itemsRxObserver=new RxObserver<Items>(this) {
            @Override
            protected void onSuccess(Items data) {
                List<Items.ItemListBean> itemList=data.getItemList();

                itemListView.setData(itemList);
                if (itemListView.getData().size() == 0)
                    itemListView.showEmpty();
                else
                    itemListView.showContent();
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                itemListView.showFail(errorMsg);
            }
        };
        itemModel.getAllItems(itemsRxObserver);
        addDisposable(itemsRxObserver);
    }

    @Override
    public void addItems(Items.ItemListBean itemListBean) {
        itemListView=this.getView();
        RxObserver<String> addItemsObserver=new RxObserver<String>(this) {
            @Override
            protected void onSuccess(String data) {
                itemListView.showResult("新增成功");
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                itemListView.showFail(errorMsg);
            }
        };
        itemModel.addItems(itemListBean,addItemsObserver);
        addDisposable(addItemsObserver);
    }
}
