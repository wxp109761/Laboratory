package com.example.laboratory.root_ui.adapter;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.laboratory.R;
import com.example.laboratory.bean.Items;
import com.example.laboratory.common.CommonUtils;
import com.example.laboratory.common.ListDataHolder;
import com.example.laboratory.inter.OnUserInfoItemClickListener;
import com.example.laboratory.ui.adapter.BaseListAdapter;

public class ItemListAdapter extends BaseListAdapter<Items.ItemListBean> {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_belong)
    TextView tvBelong;

    private OnUserInfoItemClickListener listener;

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_pro_list;
    }


    @Override
    public void bindDatas(ListDataHolder holder, Items.ItemListBean bean, int itemType, int position) {
        ButterKnife.bind(this, holder.itemView);
        tvName.setText(bean.getItemname());
        tvBelong.setText(CommonUtils.getDepartName(bean.getBelong()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}