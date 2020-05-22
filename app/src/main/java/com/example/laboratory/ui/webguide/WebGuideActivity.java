package com.example.laboratory.ui.webguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.laboratory.R;
import com.example.laboratory.ui.base.BaseActivity;

public class WebGuideActivity extends BaseActivity {
    @BindView(R.id.web_aolan)
    CardView webAolan;
    @BindView(R.id.web_bishe)
    CardView webBishe;
    @BindView(R.id.web_library)
    CardView webLibrary;
    @BindView(R.id.web_banshi)
    CardView webBanshi;
    @BindView(R.id.web_jiaowu)
    CardView webJiaowu;
    @BindView(R.id.web_baidu)
    CardView webBaidu;

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
        ButterKnife.bind(this);
    }

    @OnClick({R.id.web_aolan, R.id.web_bishe, R.id.web_library, R.id.web_banshi, R.id.web_jiaowu, R.id.web_baidu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.web_aolan:
                GotoActivity("http://xsgl.just.edu.cn/LOGIN.ASPX","奥兰管理系统");
                break;
            case R.id.web_bishe:
                GotoActivity("http://bysj.just.edu.cn/","毕业设计管理系统");
                break;
            case R.id.web_library:
                GotoActivity("http://lib.just.edu.cn/","图书管理系统");
                break;
            case R.id.web_banshi:
                GotoActivity("http://ids2.just.edu.cn/cas/login?service=http%3A%2F%2Fmy.just.edu.cn%2F","一站式服务大厅");
                break;
            case R.id.web_jiaowu:
                GotoActivity("http://jwgl.just.edu.cn:8080/","教务管理系统");
                break;
            case R.id.web_baidu:
                GotoActivity("https://www.baidu.com/","百度");


                break;
        }
    }

    public void GotoActivity(String url,String title){
        Intent intent=new Intent(WebGuideActivity.this, WebViewActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("url",url);
        bundle.putString("title",title);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}