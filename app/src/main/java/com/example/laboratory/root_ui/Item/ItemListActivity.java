package com.example.laboratory.root_ui.Item;

import android.view.Menu;
import android.view.MenuItem;
import com.example.laboratory.R;
import com.example.laboratory.bean.Items;
import com.example.laboratory.common.CommonUtils;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.root_ui.Dialog.filterDialog;
import com.example.laboratory.root_ui.adapter.ItemListAdapter;
import com.example.laboratory.ui.adapter.BaseListAdapter;
import com.example.laboratory.ui.base.BaseAbListActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemListActivity extends BaseAbListActivity<ItemListPresenter, Items.ItemListBean> implements ItemListContact.ItemListView,filterDialog.OnFilterSelectListener{


    //创建Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(UserInfoManager.getUserInfo().getPermission().equals("1")){
            return false;
        }
        getMenuInflater().inflate(R.menu.right_menu_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //Menu点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_notice:
                refreshData();
                List<String> departName=new ArrayList<>();
                List<String> categorys=new ArrayList<>();
                departName.add("不限");
                departName.add("公共项目");
                categorys.add("不限");
                departName.addAll(Arrays.asList(getResources().getStringArray(R.array.departNames)));

                categorys.addAll(Arrays.asList(getResources().getStringArray(R.array.labCates)));
                filterDialog filterDialog=new filterDialog(this,this,departName,categorys,"chexk_item");
                filterDialog.show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle("待检测项目列表");
        return true;
    }

    @Override
    protected void loadDatas() {
        mPresenter.getAllItems();
    }

    @Override
    protected BaseListAdapter<Items.ItemListBean> getListAdapter() {
        return new ItemListAdapter();
    }

    @Override
    protected ItemListPresenter createPresenter() {
        return new ItemListPresenter();
    }

    @Override
    public void setData(List<Items.ItemListBean> data) {
        mListData.clear();
        if(UserInfoManager.getUserInfo().getPermission().equals("1")){
            for (int i = 0; i < data.size(); i++) {
                if(data.get(i).getBelong().equals("0")||data.get(i).getBelong().equals(UserInfoManager.getUserInfo().getDepartId()))
                    mListData.add(data.get(i));
            }
        }else {
            mListData.addAll(data);
        }

    }

    @Override
    public void showResult(String result) {

    }
    List<Items.ItemListBean> itemListBeans =new ArrayList<>();
    String departid;
    @Override
    public void getValue(String departSelect, String cateSelect) {
        itemListBeans.clear();
        if(departSelect.equals("不限")){
            itemListBeans.addAll(mListData);
        }else {
            departid= CommonUtils.getDepartId(departSelect);
            for (int i = 0; i <mListData.size() ; i++) {
                if(mListData.get(i).getBelong().equals(departid)){
                    itemListBeans.add(mListData.get(i));
                }
            }
        }
        mListData.clear();
        mListData.addAll(itemListBeans);
        mRecyclerView.notifyDataSetChanged();
        if(itemListBeans.size()==0){
            showEmpty();
        }else {
            mRecyclerView.notifyDataSetChanged();
        }

    }
}
