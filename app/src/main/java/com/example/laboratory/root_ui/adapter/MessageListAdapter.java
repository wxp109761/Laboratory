package com.example.laboratory.root_ui.adapter;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.laboratory.R;
import com.example.laboratory.bean.Messages;
import com.example.laboratory.bean.Notices;
import com.example.laboratory.common.CommonUtils;
import com.example.laboratory.common.ListDataHolder;
import com.example.laboratory.ui.adapter.BaseListAdapter;
import com.example.laboratory.utils.BadgeHelper;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends BaseListAdapter<Messages.MessageListBean> {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_belong)
    TextView tvBelong;

    OnMessageListerer messagelistener;

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_pro_list;
    }
    List<String> itemSelect=new ArrayList<>();
    public MessageListAdapter(OnMessageListerer listener, List<String> list) {
        this.messagelistener=listener;
        this.itemSelect=list;
    }

    public interface OnMessageListerer{
        void onMessageRead(String id);
    }



    @Override
    public void bindDatas(ListDataHolder holder, Messages.MessageListBean bean, int itemType, int position) {
        BadgeHelper badgeHelper= new BadgeHelper(holder.itemView.getContext());
        ButterKnife.bind(this, holder.itemView);
        tvName.setText(bean.getTitle());
        tvBelong.setText(CommonUtils.getDepartName(bean.getMessage()));

        if(itemSelect.contains(bean.getId())){
            badgeHelper.setBadgeNumber(1);
            badgeHelper .setBadgeType(BadgeHelper.Type.TYPE_POINT)
                    .setBadgeOverlap(false)
                    .bindToTargetView(holder.itemView.findViewById(R.id.tv_point));

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  MaterialDialog dialog=new MaterialDialog(holder.itemView.getContext());
                dialog.btnNum(1).content(bean.getMessage())
                        .btnText("OK")
                        .title(bean.getTitle())
                        .show();

                dialog.setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        if(itemSelect.contains(bean.getId())){
                            if(messagelistener!=null){
                               messagelistener.onMessageRead(bean.getId());
                            }
                        }
                        dialog.dismiss();

                    }
                });

            }
        });
    }
}
