package com.example.laboratory.ui.adapter;

import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.laboratory.R;
import com.example.laboratory.bean.Result;
import com.example.laboratory.common.ListDataHolder;

public class ResultListAdapter extends BaseListAdapter<Result.ResultListBean> {

    @BindView(R.id.tv_item_name)
    TextView tvItemName;
    @BindView(R.id.tv_item_result)
    TextView tvItemResult;
    @BindView(R.id.tv_description)
    TextView tvDescription;

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_result;
    }


    @Override
    public void bindDatas(ListDataHolder holder, final Result.ResultListBean bean, int itemType, final int position) {
        ButterKnife.bind(this,holder.itemView);
        tvItemName.setText(bean.getItemname());
        tvItemResult.setText(bean.getResullt());
        tvDescription.setText(bean.getDescription());
    }
}
