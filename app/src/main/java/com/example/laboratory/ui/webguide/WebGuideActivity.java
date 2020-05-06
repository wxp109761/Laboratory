package com.example.laboratory.ui.webguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.laboratory.R;
import com.example.laboratory.ui.base.BaseActivity;

public class WebGuideActivity extends BaseActivity {
    @BindView(R.id.web_aolan)
    TextView webAolan;
    @BindView(R.id.web_beshe)
    TextView webBeshe;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_guide;
    }

    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle("网址导航");
        return true;
    }


    @Override
    protected void initViews() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.web_aolan, R.id.web_beshe})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.web_aolan:
                startActivity(new Intent(WebGuideActivity.this,WebWiewActivity.class));
                break;
            case R.id.web_beshe:
                break;
        }
    }
}