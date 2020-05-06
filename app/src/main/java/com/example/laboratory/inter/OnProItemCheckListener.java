package com.example.laboratory.inter;

import android.view.View;
import com.example.laboratory.bean.Items;
import com.example.laboratory.bean.UserList;

import java.util.List;

public interface OnProItemCheckListener extends OnItemClickListener{
    void getCheckData(List<Items.ItemListBean> mCheckData);
     void onClick(View view,String item_name,boolean isCheck);
}
