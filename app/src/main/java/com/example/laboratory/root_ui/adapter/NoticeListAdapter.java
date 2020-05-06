package com.example.laboratory.root_ui.adapter;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.laboratory.R;
import com.example.laboratory.bean.Notices;
import com.example.laboratory.common.ListDataHolder;
import com.example.laboratory.ui.adapter.BaseListAdapter;
import com.example.laboratory.utils.BadgeHelper;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.android.material.dialog.MaterialDialogs;

import java.util.ArrayList;
import java.util.List;

public class NoticeListAdapter extends BaseListAdapter<Notices.NoticesListBean> {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_send_name)
    TextView tvSendName;
    @BindView(R.id.tv_point)
    TextView tvPoint;
    OnNoticeListerer noticelistener;

    List<Integer> itemSelect=new ArrayList<>();
    public  NoticeListAdapter(OnNoticeListerer listener,List<Integer> list) {
        this.noticelistener=listener;
        this.itemSelect=list;
    }

    public interface OnNoticeListerer{
        void onNoticeRead(Integer id);
    }


    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_notice_list;
    }

    @Override
    public void bindDatas(ListDataHolder holder, Notices.NoticesListBean bean, int itemType, int position) {
        BadgeHelper badgeHelper= new BadgeHelper(holder.itemView.getContext());
        ButterKnife.bind(this, holder.itemView);
        if(itemSelect.contains(bean.getId())){
            badgeHelper.setBadgeNumber(1);
            badgeHelper .setBadgeType(BadgeHelper.Type.TYPE_POINT)
                    .setBadgeOverlap(false)
                    .bindToTargetView(holder.itemView.findViewById(R.id.tv_point));

            }

        tvName.setText(bean.getTitle());
        tvSendName.setText(bean.getSendId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog dialog=new MaterialDialog(holder.itemView.getContext());
                dialog.btnNum(1).content(bean.getNotice())
                        .btnText("OK")
                        .title(bean.getTitle())
                        .show();

                dialog.setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        if(itemSelect.contains(bean.getId())){
                            if(noticelistener!=null){
                                noticelistener.onNoticeRead(bean.getId());
                            }
                        }
                        dialog.dismiss();

                    }
                });



            }
        });

    }
}
