package com.example.laboratory.root_ui.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.laboratory.R;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.ui.base.BaseActivity;
import com.example.laboratory.ui.notice.NoticeFragment;
import com.example.laboratory.ui.user.FragmentAdapter;
import com.example.laboratory.ui.user.StatisticFragment;
import com.example.laboratory.utils.ViewPagerHtil;
import com.kekstudio.dachshundtablayout.DachshundTabLayout;
import com.kekstudio.dachshundtablayout.indicators.DachshundIndicator;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends BaseActivity {

    @BindView(R.id.mTabLayout)
    DachshundTabLayout mTabLayout;

    //创建Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (UserInfoManager.getUserInfo().getPermission().equals("2")) {
            return false;
        } else {
            getMenuInflater().inflate(R.menu.notice_menu_setting, menu);
            return super.onCreateOptionsMenu(menu);
        }
    }


    //Menu点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_notice:
                startActivity(new Intent(this, AddNotice.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @BindView(R.id.mViewPager)
    ViewPager mViewPager;


    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle("公告通知");
        return true;
    }

    private List<String> mTitle;
    private List<Fragment> mFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice;

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        mTitle = new ArrayList<>();
        mFragment = new ArrayList<>();
        if (UserInfoManager.getUserInfo().getPermission().equals("2")) {
            mTitle.add("所有公告");
            mTitle.add("学校公告");
            mTitle.add("学院公告");
            mFragment.add(new NoticeFragment("all"));
            mFragment.add(new NoticeFragment("collage"));
            mFragment.add(new NoticeFragment("depart"));
        } else if (UserInfoManager.getUserInfo().getPermission().equals("1")) {
            mTitle.add("所有公告");//除了其他院负责人发出的公告以外的所有公告
            mTitle.add("负责人公告");//校管理员单独对于院负责人的通知
            mTitle.add("普通公告");//包括学院负责人发出的公告及校管理员发出的公告
            mTitle.add("个人发布");//院负责人所发布的公告
            mFragment.add(new NoticeFragment("all"));
            mFragment.add(new NoticeFragment("depart_admin"));
            mFragment.add(new NoticeFragment("common"));
            mFragment.add(new NoticeFragment("mine_publish"));
        } else {
            mTitle.add("所有公告");//除了其他院负责人发出的公告以外的所有公告
            mTitle.add("个人发布");//院负责人所发布的公告
            mFragment.add(new NoticeFragment("all"));
            mFragment.add(new NoticeFragment("mine_publish"));
        }
        mViewPager.setOffscreenPageLimit(2);
        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),mFragment, mTitle);
        mTabLayout.setAnimatedIndicator(new DachshundIndicator(mTabLayout));
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mFragmentAdapter);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
