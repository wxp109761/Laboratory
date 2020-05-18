package com.example.laboratory.ui.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.laboratory.R;
import com.example.laboratory.bean.LaboratoryList;
import com.example.laboratory.common.Const;
import com.example.laboratory.common.ListDataHolder;
import com.example.laboratory.ui.laboratory.LaboratoryActivity;
import com.example.laboratory.utils.DateUtils;

public class LaboratoryListAdapter extends BaseListAdapter<LaboratoryList.LabListBean> {

    @BindView(R.id.tv_tag)
    TextView tvTag;
    @BindView(R.id.tv_lab_cate)
    TextView tvLabCate;
    @BindView(R.id.ll_author)
    LinearLayout llAuthor;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_lab_name)
    TextView tvLabName;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    private OnLabListItemClickListener listener;
    String cate;
    private String labid;
    private int position;

    public LaboratoryListAdapter(OnLabListItemClickListener listener,String cate) {
        this.listener = listener;
        this.cate=cate;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_lab_list;
    }
    public interface OnLabListItemClickListener{
        void onDeleteLabsClick(int position, String labid);
    }

    @Override
    public void bindDatas(ListDataHolder holder, final LaboratoryList.LabListBean bean, int itemType, final int position) {
        ButterKnife.bind(this,holder.itemView);

        tvLabCate.setText(bean.getCategory());
        tvTime.setText(DateUtils.parseTime(bean.getGmtCreate()));
        tvState.setText(bean.getSafeStatus());
        tvTag.setText(bean.getState());

        if(cate.equals("mine")){
            imgDelete.setVisibility(View.GONE);
        }
        tvLabName.setText(bean.getLabel());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), LaboratoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Const.BUNDLE_KEY.OBJ, bean);
                intent.putExtras(bundle);
                holder.itemView.getContext().startActivity(intent);
            }
        });
        this.position=position;
        this.labid=bean.getLabid();
    }
    @OnClick(R.id.img_delete)
    public void onViewClicked() {
        if (listener != null) {
            listener.onDeleteLabsClick(position,labid);
        }
    }



}
