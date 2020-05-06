package com.example.laboratory.root_ui.adapter;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.laboratory.R;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.common.CommonUtils;
import com.example.laboratory.common.ListDataHolder;
import com.example.laboratory.inter.OnUserInfoItemClickListener;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.ui.adapter.BaseListAdapter;

public class UserInfoListAdapter extends BaseListAdapter<UserList.UserListBean> {

    @BindView(R.id.tv_job_number)
    TextView tvJobNumber;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_depart)
    TextView tvUserDepart;

    private OnUserInfoItemClickListener listener;

    public UserInfoListAdapter(OnUserInfoItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_user_info_list;
    }


    @Override
    public void bindDatas(ListDataHolder holder, UserList.UserListBean bean, int itemType, int position) {
        ButterKnife.bind(this, holder.itemView);
        tvJobNumber.setText(bean.getJobNumber());
        tvUserName.setText(bean.getUsername());
        if(UserInfoManager.getUserInfo().getPermission().equals("0")){
            tvUserDepart.setVisibility(View.VISIBLE);
            tvUserDepart.setText(CommonUtils.getDepartName(bean.getDepartId()));
        }else{
            tvUserDepart.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position, bean);
                }
            }
        });

    }
}
