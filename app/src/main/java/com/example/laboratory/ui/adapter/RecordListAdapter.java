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
import com.example.laboratory.bean.Record;
import com.example.laboratory.common.Const;
import com.example.laboratory.common.ListDataHolder;
import com.example.laboratory.ui.result.ResultActivity;
import com.example.laboratory.utils.DateUtils;

public class RecordListAdapter extends BaseListAdapter<Record.RecordListBean> {

    int position;
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
    private OnRecordListItemClickListener listener;

    String xjid;
    public interface OnRecordListItemClickListener{
        void onDeleteClick(int position,String xjid);
    }
    public RecordListAdapter(OnRecordListItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_xjrecord;
    }


    @Override
    public void bindDatas(ListDataHolder holder, final Record.RecordListBean bean, int itemType, final int positi) {

        ButterKnife.bind(this, holder.itemView);
        tvLabCate.setText("日常巡检");
        tvTime.setText(DateUtils.parseTime(bean.getGmt_create()));
        tvState.setText(bean.getState());
        tvTag.setText(bean.getState());
        tvLabName.setText(bean.getLabel());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Const.BUNDLE_KEY.OBJ, bean);
                intent.putExtras(bundle);
                holder.itemView.getContext().startActivity(intent);
            }
        });
        this.xjid=bean.getXjid();
        this.position=positi;
    }
    @OnClick(R.id.img_delete)
    public void onViewClicked() {
        if (listener != null) {
            listener.onDeleteClick(position, xjid);
        }

    }
}
