package com.example.laboratory.ui.SecureCheck;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import com.example.laboratory.R;
import com.example.laboratory.application.AppContext;
import com.example.laboratory.bean.*;
import com.example.laboratory.common.Const;
import com.example.laboratory.event.Event;
import com.example.laboratory.event.RxEvent;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.ui.adapter.BaseListAdapter;
import com.example.laboratory.ui.adapter.CheckItemListAdapter;
import com.example.laboratory.ui.base.BaseAbListActivity;
import com.example.laboratory.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SeCheckActivity extends BaseAbListActivity<SeCheckPresenter,Items.ItemListBean> implements SeCheckContact.ISeCheckView{
    List<Xjresult> results=new ArrayList<>();
    Laboratory.RowsBean bean;
    String labId;
    String xjid="";
    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle(R.string.lab_items);
        return true;
    }



    @Override
    protected SeCheckPresenter createPresenter() {

        Button commitBtn=new Button(this);
        commitBtn.setText("提交");

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.BOTTOM;
        addContentView(commitBtn,lp);//这个是重点
        commitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                xjid= UUID.randomUUID()+"";
               Xjrecord xjrecord=new Xjrecord();
               xjrecord.setXjid(xjid);
               xjrecord.setLabid(labId);
               xjrecord.setXjrUid(UserInfoManager.getUserInfo().getUid());
//                for (int i = 0; i <results.size() ; i++) {
//                    if(results.)
//                }
               xjrecord.setState("1");
               mPresenter.addRecord(xjrecord);
                results=CheckItemListAdapter.getResut();
                for (int i = 0; i <results.size() ; i++) {
                    results.get(i).setXjid(xjrecord.getXjid());
                }
                mPresenter.addResultList(results);
            }
        });
        btn_scroll_top.setVisibility(View.GONE);
            return new SeCheckPresenter();

    }

    @Override
    protected void loadDatas() {
        Bundle bundle = this.getIntent().getExtras();
        labId=  bundle.getString("labId");

        mPresenter.getItemsData(labId);
    }


    @Override
    public void setData(List<Items.ItemListBean> data) {
        mListData.clear();
        mListData.addAll(data);
    }

    @Override
    protected BaseListAdapter<Items.ItemListBean> getListAdapter() {
        return new CheckItemListAdapter();
    }





    @Override
    public void ItemList(List<Items.ItemListBean> itemListBeans) {


    }

    @Override
    public void showResult(String msg) {
        ToastUtils.showToast(AppContext.getContext(), msg);
        RxEvent.getInstance().postEvent(Const.EVENT_ACTION.HOME, new Event(Event.Type.REFRESH_LIST));
        finish();
    }

    @Override
    public String getItemResult() {
        return mListData.get(0).getItemid()+"";
    }

}
