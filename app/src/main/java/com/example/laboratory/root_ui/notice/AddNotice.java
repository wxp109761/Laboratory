package com.example.laboratory.root_ui.notice;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.refactor.lib.colordialog.PromptDialog;
import com.example.laboratory.R;
import com.example.laboratory.bean.Notices;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.ui.base.BasePresenterActivity;
import com.example.laboratory.ui.notice.NoticeContract;
import com.example.laboratory.ui.notice.NoticePresenter;

import java.util.List;

public class AddNotice extends BasePresenterActivity<NoticePresenter> implements NoticeContract.INoticeView {
    @BindView(R.id.et_notice_title)
    EditText etNoticeTitle;
    @BindView(R.id.et_notice_content)
    EditText etNoticeContent;
    @BindView(R.id.publish_notice)
    Button publishNotice;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.tv_receive_usr_cate)
    TextView tvReceiveUsrCate;
    String notice_cate;
    RadioButton rbtn;
    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle("发布公告");
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish_notice;
    }

    @Override
    protected NoticePresenter createPresenter() {
        return new NoticePresenter();
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        if (UserInfoManager.getUserInfo().getPermission().equals("0")) {
            radioGroup.setVisibility(View.VISIBLE);
            tvReceiveUsrCate.setVisibility(View.VISIBLE);
            rbtn =radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
            if(rbtn.getText().equals("所有用户")){
                notice_cate="0";
                System.out.println("EEE"+notice_cate);
            }else {
                notice_cate="1";
                System.out.println("EEE"+notice_cate);
            }
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton radioButton= (RadioButton) group.findViewById(checkedId);
                    if(radioButton.getText().equals("所有用户")){
                        notice_cate="0";
                        System.out.println("EEE"+notice_cate);
                    }else {
                        notice_cate="1";
                        System.out.println("EEE"+notice_cate);
                    }
                }
            });
        }
    }


    @Override
    public void showResult(String msg) {
        if (msg.equals("发布成功")) {
            new PromptDialog(this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                    .setAnimationEnable(true)
                    .setTitleText("SUCCESS")
                    .setContentText(msg)
                    .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                            etNoticeTitle.setText("");
                            etNoticeContent.setText("");
                        }
                    }).show();
        }
    }


    @OnClick(R.id.publish_notice)
    public void onViewClicked() {
        Notices.NoticesListBean notices = new Notices.NoticesListBean();
        notices.setTitle(etNoticeTitle.getText().toString());
        notices.setNotice(etNoticeContent.getText().toString());
        if(UserInfoManager.getUserInfo().getPermission().equals("1")){
            notices.setSendDepart(UserInfoManager.getUserInfo().getDepartId());
        }else {
            notices.setSendDepart(notice_cate);
        }

        mPresenter.addNotice(notices, UserInfoManager.getUserInfo().getUid());
    }

    @Override
    public void setData(List<Notices.NoticesListBean> data) {

    }

    @Override
    public List<Notices.NoticesListBean> getData() {
        return null;
    }

    @Override
    public void showContent() {

    }

    @Override
    public void getUnNoticeList(List<Notices.NoticesListBean> noticesListBeans) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
