package com.example.laboratory.root_ui.message;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.laboratory.R;
import com.example.laboratory.root_ui.UserInfoList.UserListFragment;
import com.example.laboratory.ui.base.BaseActivity;
import com.example.laboratory.ui.messages.MessageFragment;
import com.example.laboratory.utils.ViewPagerHtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends BaseActivity {
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;

    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle("站内短信");
        return true;
    }
    private List<String> mTitle;
    private List<Fragment> mFragment;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        mTitle = new ArrayList<>();
        mTitle.add("消息");
        mTitle.add("联系人");
        mFragment = new ArrayList<>();
        mFragment.add(new MessageFragment());
        mFragment.add(new UserListFragment());
        ViewPagerHtil.addTab(mTabLayout, mViewPager, mFragment, mTitle, getSupportFragmentManager());
    }

}
