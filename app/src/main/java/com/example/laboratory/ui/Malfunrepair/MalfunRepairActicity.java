package com.example.laboratory.ui.Malfunrepair;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.laboratory.R;
import com.example.laboratory.bean.User;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.ui.base.BaseActivity;
import com.example.laboratory.ui.user.UserDataActivity;
import com.example.laboratory.utils.PhotoUtils;
import com.example.laboratory.utils.uploadPicUtil;

import java.io.File;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MalfunRepairActicity extends BaseActivity {
    @BindView(R.id.lab_id)
    EditText labId;
    @BindView(R.id.repair_desc)
    EditText repairDesc;
    @BindView(R.id.img_upload)
    ImageView imgUpload;
    @BindView(R.id.submit_btn)
    Button submitBtn;
    private Uri imageUri;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;

    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle("隐患上报");
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_malfun_repair;
    }

    @Override
    protected void initViews() {

    }
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/malfun_repair.jpg");
    private Uri cropImageUri;
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_malfun_repair.jpg");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_upload, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_upload:
                autoObtainCameraPermission();
                break;
            case R.id.submit_btn:
                break;
        }
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
                    imageUri = FileProvider.getUriForFile(MalfunRepairActicity.this, "com.example.laboratoryProvider", fileUri);
                }
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                Toasty.info(this, "设备没有SD卡", Toast.LENGTH_SHORT, true).show();
            }
        }
    }
    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }


    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;
    /**
     * 拍完照回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
                case CODE_RESULT_REQUEST:
                    final Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        System.out.println("GGG"+cropImageUri.getEncodedPath());
                        imgUpload.setImageBitmap(bitmap);
                        String avatar_url = uploadPicUtil.uploadPic(cropImageUri.getEncodedPath(), UUID.randomUUID() + ".jpg");
//                        User user = new User();
//                        user.setAvatarUrl(avatar_url);
//                        mPresenter.updateUserInfo(UserInfoManager.getUserInfo().getUid(), user);
//                        userHead.setImageBitmap(bitmap);
//                        Glide.with(getApplicationContext())
//                                .load(bitmap)
//                                .apply(bitmapTransform(new BlurTransformation(25, 3)))
//                                .into(topBg);
                        break;
                    }}
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
                            imageUri = FileProvider.getUriForFile(MalfunRepairActicity.this, "com.example.laboratoryProvider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        Toasty.info(this, "设备没有SD卡", Toast.LENGTH_SHORT, true).show();
                    }
                } else {
                    Toasty.info(this, "请允许打开相机", Toast.LENGTH_SHORT, true).show();
                }
                break;
            }
            default:
        }
    }


}
