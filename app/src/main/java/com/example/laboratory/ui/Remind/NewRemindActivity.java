package com.example.laboratory.ui.Remind;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.laboratory.R;
import com.example.laboratory.test.ClockManager;
import com.example.laboratory.test.ClockService;
import com.example.laboratory.utils.DateTimeUtil;
import com.example.laboratory.bean.Remind;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.ui.base.BasePresenterActivity;
import com.example.laboratory.ui.core.view.IView;
import com.example.laboratory.utils.PermissionPageUtils;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.dmoral.toasty.Toasty;
import me.drakeet.materialdialog.MaterialDialog;

import java.text.SimpleDateFormat;
import java.util.*;


public class NewRemindActivity extends BasePresenterActivity<RemindPresenter> implements IView {
    @BindView(R.id.new_bg)
    ImageView newBg;
    @BindView(R.id.new_toolbar)
    Toolbar newToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.new_todo_title)
    EditText newTodoTitle;

    @BindView(R.id.new_todo_dsc)
    EditText newTodoDsc;

    @BindView(R.id.new_todo_date)
    TextView newTodoDate;

    @BindView(R.id.fab_ok)
    FloatingActionButton fabOk;
    @BindView(R.id.fabProgressCircle)
    FABProgressCircle fabProgressCircle;
    private PermissionPageUtils permissionPageUtils;
    long triiTime;
    private static int[] imageArray = new int[]{R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3,
            R.drawable.img_4,
            R.drawable.img_5,
            R.drawable.img_6,
            R.drawable.img_7,
            R.drawable.img_8,};
    private ClockManager mClockManager = ClockManager.getInstance();
    private int imgId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setStatusBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);
        ButterKnife.bind(this);
        initHeadImage();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_todo;
    }
    private ImageView new_bg;
    @Override
    protected RemindPresenter createPresenter() {
        return new RemindPresenter();
    }

    private String todoDate = null;


    private void initHeadImage() {
        Random random = new Random();
        imgId = imageArray[random.nextInt(8)];
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true);
        Glide.with(getApplicationContext())
                .load(imgId)
                .apply(options)
                .into(newBg);
    }

    @OnClick({R.id.new_todo_date, R.id.fab_ok})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        //  intent.putExtra(ClockReceiver.EXTRA_EVENT_ID, id);
        intent.setClass(this, ClockService.class);
        PendingIntent pendingIntent= PendingIntent.getService(this, 0x001, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        switch (view.getId()) {
            case R.id.new_todo_date:
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        todoDate = simpleDateFormat.format(date);
                        newTodoDate.setText(todoDate);
                    }
                })
                        .setType(new boolean[]{true, true, true, true, true, false})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(18)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        .setTitleText("日期时间选择")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.parseColor("#0091ea"))//确定按钮文字颜色
                        .setCancelColor(Color.parseColor("#0091ea"))//取消按钮文字颜色
                        .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                        //    .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                        //    .setRangDate(startDate,endDate)//起始终止年月日设定
                        //.setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        //.isDialog(true)//是否显示为对话框样式
                        .build();

                pvTime.show();
                break;
            case R.id.fab_ok:
                if (todoDate == null) {
                    Toasty.info(NewRemindActivity.this, "没有设置日期--时间", Toast.LENGTH_SHORT, true).show();
                } else if (newTodoDsc.getText().toString().equals("") || newTodoTitle.getText().toString().equals("")) {
                    Toasty.info(NewRemindActivity.this, "待办标题及描述不能为空", Toast.LENGTH_SHORT, true).show();
                } else {
                    mClockManager.addAlarm(pendingIntent, DateTimeUtil.str2Date(todoDate));
                    fabProgressCircle.show();
                    Remind.RemindListBean remind = new Remind.RemindListBean();
                    remind.setContent(newTodoDsc.getText().toString());
                    remind.setTitle(newTodoTitle.getText().toString());
                    remind.setImgId(newBg.getId());
                    remind.setRemindTime(DateTimeUtil.str2Date(todoDate).getTime());
                    remind.setUid(UserInfoManager.getUserInfo().getUid());
                    remind.setIsDone("0");
                    mPresenter.addRemind(remind);
                    finish();
                }
                break;
        }
    }

    @Override
    public void showResult(String msg) {

    }

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


}
