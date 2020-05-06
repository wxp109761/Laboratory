package com.example.laboratory.ui.laboratory;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.laboratory.R;
import com.example.laboratory.bean.LaboratoryList;
import com.example.laboratory.common.Const;
import com.example.laboratory.root_ui.adapter.expandOrFoldListAdapter;
import com.example.laboratory.ui.base.BaseActivity;
import com.example.laboratory.utils.DateUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LaboratoryActivity extends BaseActivity {
    @BindView(R.id.lab_id)
    TextView labId;
    @BindView(R.id.lab_label)
    TextView labLabel;
    @BindView(R.id.lab_function)
    TextView labFunction;
    @BindView(R.id.lab_state)
    TextView labState;
    @BindView(R.id.lab_category)
    TextView labCategory;
    @BindView(R.id.lab_safe_status)
    TextView labSafeStatus;
    @BindView(R.id.lab_create_date)
    TextView labCreateDate;
    @BindView(R.id.lab_update_date)
    TextView labUpdateDate;
    @BindView(R.id.click_expand)
    TextView clickExpand;

    @BindView(R.id.list_view)
    ListView listView;

    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle(R.string.lab_info);
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lab_info;
    }

    boolean isExpand=true;
    List<String> list=new ArrayList<>();
    expandOrFoldListAdapter adapter;
    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        Bundle bundle = this.getIntent().getExtras();
        LaboratoryList.LabListBean bean = (LaboratoryList.LabListBean) bundle.getSerializable(Const.BUNDLE_KEY.OBJ);
        labId.setText(bean.getLabid());
        labLabel.setText(bean.getLabel());
        labFunction.setText(bean.getFunction());
        labCategory.setText(bean.getCategory());
        labSafeStatus.setText(bean.getSafeStatus());
        labState.setText(bean.getState());
        labCreateDate.setText(DateUtils.parseTime(bean.getGmtCreate()));
        labUpdateDate.setText(DateUtils.parseTime(bean.getGmtUpdate()));
        for (int i = 0; i <bean.getItems().size() ; i++) {
            list.add(bean.getItems().get(i).getItemname());
        }
        adapter =new expandOrFoldListAdapter(this, list,isExpand);
        listView.setAdapter(adapter);

    }


    @OnClick(R.id.click_expand)
    public void onViewClicked() {
        isExpand=!isExpand;
        list =Arrays.asList(getResources().getStringArray(R.array.departNames));
        System.out.println(""+isExpand);
        adapter.notifyDataSetChanged();


    }

}
