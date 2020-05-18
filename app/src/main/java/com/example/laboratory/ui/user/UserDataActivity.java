package com.example.laboratory.ui.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.laboratory.R;
import com.example.laboratory.bean.User;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.common.CommonUtils;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.root_ui.UserInfoList.UserInfoListContact;
import com.example.laboratory.root_ui.UserInfoList.UserInfoListPresenter;
import com.example.laboratory.ui.adapter.BaseListAdapter;
import com.example.laboratory.ui.base.BaseAbListActivity;
import com.example.laboratory.ui.base.BaseActivity;
import com.example.laboratory.ui.base.BasePresenterActivity;
import com.example.laboratory.ui.core.presenter.BasePresenter;
import com.example.laboratory.ui.core.view.IView;
import com.example.laboratory.ui.login.LoginActivity;
import com.example.laboratory.ui.main.MainActivity;
import com.example.laboratory.utils.PhotoUtils;
import com.example.laboratory.utils.SPUtils;
import com.example.laboratory.utils.uploadPicUtil;
import com.example.laboratory.widget.CircleImageView;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.kekstudio.dachshundtablayout.DachshundTabLayout;
import com.kekstudio.dachshundtablayout.indicators.DachshundIndicator;
import es.dmoral.toasty.Toasty;
import jp.wasabeef.glide.transformations.BlurTransformation;
import me.drakeet.materialdialog.MaterialDialog;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


public class UserDataActivity extends BasePresenterActivity<UserInfoListPresenter> implements IView {
    private static final String TAG = "login";


    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;

    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.autograph)
    TextView autograph;
    @BindView(R.id.top_bg)
    ImageView topBg;
//    @BindView(R.id.middle_layout)
//    CoordinatorLayout middleLayout;
    @BindView(R.id.toolbar_userhead)
    CircleImageView toolbarUserhead;
    @BindView(R.id.toolbar_username)
    TextView toolbarUsername;
    @BindView(R.id.user_toolbar)
    Toolbar userToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.user_app_bar)
    AppBarLayout userAppBar;
    @BindView(R.id.tab_layout_user)
    DachshundTabLayout tabLayoutUser;
    @BindView(R.id.view_pager_user)
    ViewPager viewPagerUser;

    @BindView(R.id.user_head)
    CircleImageView userHead;

    @BindView(R.id.user_edit)
    FloatingActionButton userEdit;

    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;

    @Override
    protected void onCreate(Bundle bundle) {
        setStatusBar();
        super.onCreate(bundle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_data;
    }


    @Override
    protected UserInfoListPresenter createPresenter() {
        return new UserInfoListPresenter();
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        topBg.setImageResource(R.drawable.img_3);
        initViewPager();
        setUserDataFromBmob();
    }

    private void initViewPager() {


        List<String> titles = new ArrayList<>();
        titles.add("个人信息");
        titles.add("其他");


        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new StatisticFragment());
        fragments.add(new StatisticFragment());

        viewPagerUser.setOffscreenPageLimit(2);
        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        tabLayoutUser.setAnimatedIndicator(new DachshundIndicator(tabLayoutUser));
        viewPagerUser.setAdapter(mFragmentAdapter);
        tabLayoutUser.setupWithViewPager(viewPagerUser);
        tabLayoutUser.setTabsFromPagerAdapter(mFragmentAdapter);
    }

    /**
     * 加载用户信息
     */
    private void setUserDataFromBmob(){
        User user=UserInfoManager.getUserInfo();
        toolbarUsername.setText(user.getUsername());
        nickname.setText(user.getUsername());
        autograph.setText(CommonUtils.userRole());
        if (user != null) {
            MyTask task = new MyTask();
            task.execute(user.getAvatarUrl());
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
            bitmap= BitmapFactory.decodeStream(is);
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
            toolbarUserhead.setImageBitmap(result);
            userHead.setImageBitmap(result);
            Glide.with(getApplicationContext())
                    .load(result)
                    .apply(bitmapTransform(new BlurTransformation(25, 3)))
                    .into(topBg);
        }
    }




    /**
     * 编辑用户信息和更新头像
     * @param view
     */
    @OnClick({R.id.user_head, R.id.user_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_head:
                ChandeHeader();
                break;
            case R.id.user_edit:
                showEditDialog();
                break;
        }
    }


    public void ChandeHeader() {
        String stringItems[] = {"拍照", "从相册选择"};
        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
        dialog.isTitleShow(true).show();
        dialog.title("更换头像");
        dialog.itemTextColor(Color.parseColor("#0091ea"));
        dialog.cancelText(Color.parseColor("#0091ea"));
        // dialog.itemPressColor(Color.parseColor("#e9857d"));
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        autoObtainCameraPermission();
                        break;
                    case 1:
                        autoObtainStoragePermission();
                        break;
                }
                dialog.dismiss();
            }
        });


    }



    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toasty.info(this, "您已经拒绝过一次", Toast.LENGTH_SHORT, true).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                //通过FileProvider创建一个content类型的Uri
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(UserDataActivity.this, "com.example.laboratoryProvider", fileUri);
                }
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                Toasty.info(this, "设备没有SD卡", Toast.LENGTH_SHORT, true).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(UserDataActivity.this, "com.example.laboratoryProvider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        Toasty.info(this, "设备没有SD卡", Toast.LENGTH_SHORT, true).show();
                    }
                } else {
                    Toasty.info(this, "请允许打开相机", Toast.LENGTH_SHORT, true).show();
                }
                break;


            }
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {
                    Toasty.info(this, "请允许操作SD卡", Toast.LENGTH_SHORT, true).show();
                }
                break;
            default:
        }
    }

    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //拍照完成回调
                case CODE_CAMERA_REQUEST:
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    break;
                //访问相册完成回调
                case CODE_GALLERY_REQUEST:
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            newUri = FileProvider.getUriForFile(this, "com.example.laboratoryProvider", new File(newUri.getPath()));
                        }
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    } else {
                        Toasty.info(this, "设备没有SD卡", Toast.LENGTH_SHORT, true).show();
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    final Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        System.out.println("GGG"+cropImageUri.getEncodedPath());

                        userHead.setImageBitmap(bitmap);
                        String avatar_url = uploadPicUtil.uploadPic(cropImageUri.getEncodedPath(), UUID.randomUUID() + ".jpg");
                        User user = new User();
                        user.setAvatarUrl(avatar_url);
                        mPresenter.updateUserInfo(UserInfoManager.getUserInfo().getUid(), user);
                        userHead.setImageBitmap(bitmap);
                        Glide.with(getApplicationContext())
                                .load(bitmap)
                                .apply(bitmapTransform(new BlurTransformation(25, 3)))
                                .into(topBg);
                        break;
                    }}
        }
    }



    /**
     * 自动获取sdk权限
     */

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }

    }


    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 重写返回按钮监听刷新主界面头像
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent(UserDataActivity.this, LoginActivity.class);
            setResult(2, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    /**
     * 设置状态栏透明
     */
    private void setStatusBar(){
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }



    /**
     * 显示用户信息编辑框
     */
    protected void showEditDialog() {
//        User user = User.getCurrentUser(User.class);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View textEntryView = layoutInflater.inflate(R.layout.dialog_edit, null);
//        et_nickname = (EditText) textEntryView.findViewById(R.id.edit_nickname);
//        et_autograph = (EditText) textEntryView.findViewById(R.id.edit_autograph);
//        et_nickname.setText(user.getNickName());
//        et_autograph.setText(user.getAutograph());
        final MaterialDialog editDialog = new MaterialDialog(UserDataActivity.this);
        editDialog.setTitle("编辑信息");
        editDialog.setView(textEntryView);
        editDialog.setPositiveButton("保存", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        editDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog.dismiss();
            }
        });
        editDialog.show();// 显示对话框

    }

    @Override
    public void showResult(String msg) {

    }


//    /**
//     * Glide图片加载
//     */
//    private void glideLoad() {
//
//        RequestOptions options_1 = new RequestOptions()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .signature(new ObjectKey(SPUtils.get(UserDataActivity.this, "head_signature", "")))
//                .placeholder(R.drawable.default_photo);
//
//        RequestOptions options_2 = new RequestOptions()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .signature(new ObjectKey(SPUtils.get(UserDataActivity.this, "head_signature", "")))
//                .placeholder(R.drawable.ic_img1);
//
//        Glide.with(getApplicationContext())
//                .load(SPUtils.get(UserDataActivity.this, "path", ""))
//                .apply(options_1)
//                .into(toolbar_userhead);
//
//        Glide.with(getApplicationContext())
//                .load(SPUtils.get(UserDataActivity.this, "path", ""))
//                .apply(options_1)
//                .into(userHead);
//
//        Glide.with(getApplicationContext())
//                .load(SPUtils.get(UserDataActivity.this, "path", ""))
//                .apply(bitmapTransform(new BlurTransformation(25, 3)))
//                .apply(options_2)
//                .into(top_bg);
//    }

//    /**
//     * 用户引导
//     */
//    private void initGuide() {
//        NewbieGuide.with(this)
//                .setLabel("guide2")
//                .setShowCounts(1)//控制次数
//                .alwaysShow(false)//总是显示，调试时可以打开
//                .addGuidePage(GuidePage.newInstance()
//                        .addHighLight(userHead)
//                        .setLayoutRes(R.layout.guide_user_info))
//                .show();
//
//    }


}

