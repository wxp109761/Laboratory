package com.example.laboratory.test;

import android.content.Intent;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.laboratory.R;
import com.example.laboratory.ui.Remind.NewRemindActivity;
import com.example.laboratory.ui.base.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import top.wefor.circularanim.CircularAnim;


public class RemindActivity extends BaseActivity {
    @BindView(R.id.fg_container)
    FrameLayout fgContainer;

    RemindListFragment remindListFragment = new RemindListFragment();
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle("提醒待办");
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_todo;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction().add(R.id.fg_container, remindListFragment).commit();
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        CircularAnim.fullActivity(RemindActivity.this,fab.getRootView())
                .go(new CircularAnim.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {
                        Intent intent = new Intent(RemindActivity.this, NewRemindActivity.class);
                        startActivityForResult(intent,1);
                    }
                });
    }
}