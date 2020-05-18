package com.example.laboratory.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.*;
import android.widget.*;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.example.laboratory.R;
import com.example.laboratory.application.AppContext;
import com.example.laboratory.bean.User;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.common.CommonUtils;
import com.example.laboratory.common.Const;
import com.example.laboratory.event.Event;
import com.example.laboratory.event.RxEvent;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.root_ui.UserInfoList.UserInfoListContact;
import com.example.laboratory.root_ui.UserInfoList.UserInfoListPresenter;
import com.example.laboratory.test.RemindActivity;
import com.example.laboratory.ui.Remind.NewRemindActivity;
import com.example.laboratory.ui.base.BaseActivity;
import com.example.laboratory.ui.base.BasePresenterActivity;
import com.example.laboratory.ui.discover.DiscoverFragment;
import com.example.laboratory.ui.home.HomeFragment;


import com.example.laboratory.ui.login.LoginActivity;
import com.example.laboratory.ui.user.Laboratory.LabListActivity;
import com.example.laboratory.ui.user.Record.RecordListActivity;
import com.example.laboratory.ui.user.UserDataActivity;
import com.example.laboratory.ui.user.UserInfoActivity;
import com.example.laboratory.utils.IntentUtils;
import com.example.laboratory.utils.PreUtils;
import com.example.laboratory.utils.ToastUtils;
import com.example.laboratory.utils.uploadPicUtil;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.android.material.navigation.NavigationView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import top.wefor.circularanim.CircularAnim;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


/**
 * 管理首页Tab的Activity
 */
public class MainActivity extends BasePresenterActivity<UserInfoListPresenter> implements View.OnClickListener{
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TextView mNameView;
    private ImageView navBg;
    private TextView role;
    private ImageView mAvatarView;
    private Button[] btns;
    private Fragment[] fragments;
    private int currentPosition;
    private int index;

    //创建Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Menu点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_main;
    }
//
    @Override
    protected boolean initToolbar() {

        mToolbar.setTitle(R.string.app_name);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        return true;
    }


    public void setCurrentTitle() {
        if (currentPosition == 0)
            mToolbar.setTitle(R.string.app_name);
        else if (currentPosition == 1)
            mToolbar.setTitle(R.string.system);
    }

    @Override
    protected void initViews() {
        CommonUtils.getPer(this,MainActivity.this);
        mDrawerLayout =  findViewById(R.id.drawerLayout);
        mNavigationView =  findViewById(R.id.nav_view);
        btns = new Button[2];
        btns[0] =  findViewById(R.id.btn_main);
        btns[1] =  findViewById(R.id.btn_system);
        btns[0].setSelected(true);
        for (int i = 0; i < btns.length; i++) {
            btns[i].setOnClickListener(this);
            if (i != currentPosition) {
                btns[i].setScaleX(0.9f);
                btns[i].setScaleY(0.9f);
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main:
                index = 0;
                break;
            case R.id.btn_system:
                index = 1;
                break;

            default:
        }
        showCurrentFragment(index);
    }


    private View.OnClickListener onScrollTopListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String action="";
            switch (currentPosition) {
                case 0:
                    action = Const.EVENT_ACTION.HOME;
                    break;
                case 1:
                    action = Const.EVENT_ACTION.SYSTEM;
                    break;
            }
            RxEvent.getInstance().postEvent(action,new Event(Event.Type.SCROLL_TOP));
        }
    };

    @Override
    protected void onCreate(Bundle bundle) {
      //  setStatusBar();
        super.onCreate(bundle);

        //设置Home旋转开关按钮
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
        //侧滑菜单
        initNavigationHeaderView();
        initFragments();
    }
    private void initFragments() {
        fragments = new Fragment[]{new HomeFragment(), new DiscoverFragment()};
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, fragments[0]).show(fragments[0]).commitAllowingStateLoss();
    }

    @Override
    protected UserInfoListPresenter createPresenter() {
        return new UserInfoListPresenter();
    }

    private String path = Environment.getExternalStorageDirectory().getAbsolutePath();

    private void initNavigationHeaderView() {
        View mHeaderView = mNavigationView.getHeaderView(0);
        mAvatarView =  mHeaderView.findViewById(R.id.user_image);
        navBg =  mHeaderView.findViewById(R.id.nav_bg);
        mNameView =  mHeaderView.findViewById(R.id.tv_name);
        role =  mHeaderView.findViewById(R.id.user_role);
        mAvatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CircularAnim.fullActivity(MainActivity.this,mAvatarView)
                        .go(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {
                                Intent intent = new Intent(MainActivity.this, UserDataActivity.class);
                                startActivityForResult(intent,1);
                            }
                        });


            }
        });
    }



    /**

     * 根据URL获取Bitmap

     * */

    public Bitmap getHttpBitmap(String url){
        Bitmap bitmap=null;
        URL myUrl;
        try {
            myUrl=new URL(url);
            HttpURLConnection conn=(HttpURLConnection)myUrl.openConnection();
            conn.setConnectTimeout(5000);
            conn.connect();
            InputStream is=conn.getInputStream();
            bitmap=BitmapFactory.decodeStream(is);
            //把bitmap转成圆形
            //bitmap=toRoundBitmap(bitmap);
            is.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //返回圆形bitmap
        return bitmap;
    }

    @Override
    public void showResult(String msg) {
    }


    class MyTask extends AsyncTask<String, String, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            String url=arg0[0];
            Bitmap bm=getHttpBitmap(url);
            return bm;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            mAvatarView.setImageBitmap(result);
            Glide.with(getApplicationContext())
                    .load(result)
                    .apply(bitmapTransform(new BlurTransformation(25, 3)))
                    .into(navBg);

        }

    }



    private void setUserData() {
        if (UserInfoManager.isLogin()) {
            User user = UserInfoManager.getUserInfo();
            if (user != null) {
                MyTask task = new MyTask();
                if(user.getAvatarUrl()==null||user.getAvatarUrl().equals("")){
                    mAvatarView.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.img_3));
                    Glide.with(getApplicationContext())
                            .load(getBaseContext().getResources().getDrawable(R.drawable.img_3))
                            .apply(bitmapTransform(new BlurTransformation(25, 3)))
                            .into(navBg);
                }else{
                    task.execute(user.getAvatarUrl());

                }

            }
            mNameView.setText(user.getUsername());
            role.setText(new CommonUtils().userRole());
        }
        else {
            mNameView.setText("未登录");
        }
    }

    //回调刷新
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 2){
            finish();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);

        }
        if (resultCode ==3){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }



    //设置侧滑item click
    private NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_mine_labs:
                    if (!UserInfoManager.isLogin()) {
                        IntentUtils.goLogin(MainActivity.this);
                    } else {
                        Intent intent = new Intent(MainActivity.this,LabListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("mine","mine");
                        intent.putExtras(bundle);
                        startActivity(intent);   }

                    break;
                case R.id.menu_mine_record:
                    if (!UserInfoManager.isLogin()) {
                        IntentUtils.goLogin(MainActivity.this);
                    } else {
                        startActivity(new Intent(MainActivity.this, RecordListActivity.class));
                    }

                    break;
                case R.id.menu_exit:
                    exitToLogin();
                    break;
            }
            return true;
        }
    };



    //退出登录
    private void exitToLogin() {
        IntentUtils.goLogin(this);
        PreUtils.clearAll();

        //刷新首页数据
       RxEvent.getInstance().postEvent(Const.EVENT_ACTION.HOME, new Event(Event.Type.REFRESH_LIST));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserData();
    }


    /**
     * 切换显示当前Fragment
     *
     * @param index
     */
    private void showCurrentFragment(int index) {
        if (currentPosition != index) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(fragments[currentPosition]);
            if (!fragments[index].isAdded()) {
                ft.add(R.id.container, fragments[index]);
            }
            ft.show(fragments[index]).commit();
            btns[currentPosition].setSelected(false);
            btns[index].setSelected(true);
            scaleView();
            currentPosition = index;
            setCurrentTitle();
        }
    }

    /**
     * view放大缩小
     */
    private void scaleView() {
        btns[currentPosition].animate().scaleX(0.9f).scaleY(0.9f)
                .setDuration(150).start();
        btns[index].animate().scaleX(1.0f).scaleY(1.0f)
                .setDuration(150).start();
    }

    private long mExitTime;

    @SuppressLint("WrongConstant")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                mDrawerLayout.closeDrawer(Gravity.START);
                return true;
            }

            if (System.currentTimeMillis() - mExitTime < 2000) {
                finish();
            } else {
                mExitTime = System.currentTimeMillis();
                ToastUtils.showToast(AppContext.getContext(), "请再按一次退出程序");
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




    @Override
    protected String registerEvent() {
        return Const.EVENT_ACTION.MAIN;
    }

}