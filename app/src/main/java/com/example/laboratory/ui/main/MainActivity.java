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
import com.example.laboratory.R;
import com.example.laboratory.application.AppContext;
import com.example.laboratory.bean.User;
import com.example.laboratory.common.CommonUtils;
import com.example.laboratory.common.Const;
import com.example.laboratory.event.Event;
import com.example.laboratory.event.RxEvent;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.ui.base.BaseActivity;
import com.example.laboratory.ui.discover.DiscoverFragment;
import com.example.laboratory.ui.home.HomeFragment;


import com.example.laboratory.ui.user.Laboratory.LabListActivity;
import com.example.laboratory.ui.user.Record.RecordListActivity;
import com.example.laboratory.ui.user.UserInfoActivity;
import com.example.laboratory.utils.IntentUtils;
import com.example.laboratory.utils.PreUtils;
import com.example.laboratory.utils.ToastUtils;
import com.example.laboratory.utils.uploadPicUtil;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.android.material.navigation.NavigationView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.UUID;


/**
 * 管理首页Tab的Activity
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TextView mNameView;
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
        mNavigationView =  findViewById(R.id.navigation_view);
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

    private String path = Environment.getExternalStorageDirectory().getAbsolutePath();

    private void initNavigationHeaderView() {
        View mHeaderView = mNavigationView.getHeaderView(0);
        mAvatarView =  mHeaderView.findViewById(R.id.img_avatar);
        mNameView =  mHeaderView.findViewById(R.id.tv_name);

        mAvatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeHeaderImg();
            }
        });
    }

    String domin="http://q94nddme1.bkt.clouddn.com";




    private Bitmap head;// 头像Bitmap
    private Bitmap headbit;// 头像Bitmap

    //调用照相机返回图片文件
    private File tempFile;






    private void changeHeaderImg() {
        String stringItems[] = {"拍照", "从相册选择"};
        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
        dialog.isTitleShow(true).show();
        dialog.title("更换头像");
        dialog.itemTextColor(Color.parseColor("#e9857d"));
        dialog.cancelText(Color.parseColor("#e9857d"));
        // dialog.itemPressColor(Color.parseColor("#e9857d"));
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        initCamera();
                        break;
                    case 1:
                        initLocal();
                        break;
                }
                dialog.dismiss();
            }
        });

    }

    public void initCamera() {

        //最好用try/catch包裹一下，防止因为用户未给应用程序开启相机权限，而使程序崩溃
        try {
            Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//开启相机应用程序获取并返回图片（capture：俘获）
            intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                    "head.jpg")));//指明存储图片或视频的地址URI
            startActivityForResult(intent2, 2);//采用ForResult打开
        } catch (Exception e) {
            Toast.makeText(this, "相机无法启动，请先开启相机权限", Toast.LENGTH_LONG).show();
        }
    }

    public void initLocal() {
        Intent intent1 = new Intent(Intent.ACTION_PICK, null);//返回被选中项的URI
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//得到所有图片的URI
//                System.out.println("MediaStore.Images.Media.EXTERNAL_CONTENT_URI  ------------>   "
//                        + MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//   content://media/external/images/media
        startActivityForResult(intent1, 1);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //从相册里面取相片的返回结果
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }

                break;
            //相机拍照后的返回结果
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }

                break;
            //调用系统裁剪图片后

            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);//保存在SD卡中
                        uploadPicUtil.uploadPic(Environment.getExternalStorageDirectory() + "/head.jpg", UUID.randomUUID()+".jpg");

                        mAvatarView.setImageBitmap(head);//用ImageView显示出来

                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    ;

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        //找到指定URI对应的资源图片
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);

        //进入系统裁剪图片的界面
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd卡是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建以此File对象为名（path）的文件夹
        String fileName = path + "/head.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件（compress：压缩）

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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

        }

    }



    private void setUserData() {
        if (UserInfoManager.isLogin()) {
            User user = UserInfoManager.getUserInfo();
            if (user != null) {
                MyTask task=new MyTask();
                task.execute(user.getAvatarUrl());
                Log.d("Xxx","请求成功");
            }
//                mNameView.setText(new CommonUtils().userRole()+":"+user.getUsername());
//                Bitmap bt = BitmapFactory.decodeFile(path + "/head.jpg");// 从SD卡中找头像，转换成Bitmap
//                if (bt != null) {
//                    @SuppressWarnings("deprecation")
//                    Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
//                    mAvatarView.setImageDrawable(drawable);
//                } else {
//
//
//                    }
//                }
            }
        else {
            mNameView.setText("未登录");
        }
    }


    private void initFragments() {
        fragments = new Fragment[]{new HomeFragment(), new DiscoverFragment()};
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, fragments[0]).show(fragments[0]).commitAllowingStateLoss();
    }


    //设置侧滑item click
    private NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
//                case R.id.menu_favorite_article: {
//                    if (!UserInfoManager.isLogin()) {
//                        IntentUtils.goLogin(MainActivity.this);
//                    } else {
//                        startActivity(new Intent(MainActivity.this, ResultActivity.class));
//                    }
//                }
//                break;
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
                case R.id.menu_mine_info:
                    if (!UserInfoManager.isLogin()) {
                        finish();
                        IntentUtils.goLogin(MainActivity.this);
                    } else {
                        Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("cate","2");
                        intent.putExtras(bundle);
                        startActivity(intent);
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