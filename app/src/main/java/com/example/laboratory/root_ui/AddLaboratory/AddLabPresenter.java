package com.example.laboratory.root_ui.AddLaboratory;

import com.example.laboratory.bean.Items;
import com.example.laboratory.bean.Laboratory;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.ui.core.model.impl.LaboratoryModel;
import com.example.laboratory.ui.core.presenter.BasePresenter;
import java.util.List;



public class AddLabPresenter extends BasePresenter<AddLabContact.IAddLabsView> implements AddLabContact.IAddLabsPresenter{
    private LaboratoryModel labModel;
    private AddLabContact.IAddLabsView iAddLabsView;

    public AddLabPresenter(){
        this.labModel=new LaboratoryModel();
    }
    @Override
    public void addLabs(Laboratory.RowsBean labs) {
        iAddLabsView=this.getView();
        RxObserver<String> addLabsObserver=new RxObserver<String>(this) {
            @Override
            protected void onSuccess(String data) {
                iAddLabsView.showResult("新增成功");
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                iAddLabsView.showFail(errorMsg);
            }
        };
        labModel.addLabs(labs,addLabsObserver);
        addDisposable(addLabsObserver);
    }

    @Override
    public void addItems(Items.ItemListBean itemListBean) {
        RxObserver<String> addItemsObserver=new RxObserver<String>(this) {
            @Override
            protected void onSuccess(String data) {
                iAddLabsView.showResult("条目增加成功");
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                iAddLabsView.showFail(errorMsg);
            }
        };
        labModel.addItems(itemListBean,addItemsObserver);
        addDisposable(addItemsObserver);
    }

    @Override
    public void addLabItemRelations(String labId, Integer itemId) {
        iAddLabsView=this.getView();
        RxObserver<String> relationsObserver=new RxObserver<String>(this) {
            @Override
            protected void onSuccess(String data) {

            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                iAddLabsView.showFail(errorMsg);
            }
        };
        labModel.addLabItemRelaations(labId,itemId,relationsObserver);
        addDisposable(relationsObserver);
    }

    @Override
    public void getItemsData(String belong) {
        iAddLabsView=getView();
        RxObserver<Items> itemsRxObserver=new RxObserver<Items>(this) {
            @Override
            protected void onSuccess(Items data) {
                List<Items.ItemListBean> itemList=data.getItemList();
                iAddLabsView.setItemList(itemList);
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                iAddLabsView.showFail(errorMsg);
            }
        };
       labModel.getItemsByBelong(belong,itemsRxObserver);
        addDisposable(itemsRxObserver);
    }




}
