package com.example.laboratory.ui.webguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.example.laboratory.R;
import com.example.laboratory.ui.base.BaseActivity;
import com.just.agentweb.AgentWeb;

import java.lang.reflect.Method;

public class WebWiewActivity extends BaseActivity {
    @BindView(R.id.container)
    FrameLayout  mContainer;
    private AgentWeb mAgentWeb;
    String title = "百度";
    private String url = "https://www.baidu.com/";

    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle(title);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.content_menu_setting, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.share:
                share();
                break;

            case R.id.browser:
                openInBrowser();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
        //打开浏览器

    }

    // 让菜单同时显示图标和文字
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    //分享
    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "江科大实验室巡检系统(" + title + "):" + url);
        intent.setType("text/plain");//分享文本
        startActivity(Intent.createChooser(intent, "分享"));
    }

    private void openInBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mContainer, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(R.color.black)
                .createAgentWeb()
                .ready()
                .go("https://www.baidu.com/");
    }

    @Override
    protected void getIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            title = Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            title = Html.fromHtml(title).toString();
        }
    }


    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.destroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //将事件交给AgentWeb做处理
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
