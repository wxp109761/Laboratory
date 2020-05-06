package com.example.laboratory.root_ui.message;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.refactor.lib.colordialog.PromptDialog;
import com.example.laboratory.R;
import com.example.laboratory.bean.Messages;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.ui.base.BasePresenterActivity;
import com.example.laboratory.ui.messages.MessageContract;
import com.example.laboratory.ui.messages.MessagePresenter;

import java.util.List;

public class SendMessage extends BasePresenterActivity<MessagePresenter> implements MessageContract.IMessageView {


    @BindView(R.id.et_message_title)
    EditText etMessageTitle;
    @BindView(R.id.et_message_content)
    EditText etMessageContent;
    @BindView(R.id.send_message)
    Button sendMessage;
    String receive_id;

    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle("发送短信");
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_message;
    }

    @Override
    protected MessagePresenter createPresenter() {
        return new MessagePresenter();
    }

    @Override
    protected void initViews() {
        Bundle bundle = this.getIntent().getExtras();
        receive_id= bundle.getString("receive_id");
        ButterKnife.bind(this);
        Log.d("XXX",receive_id);
    }


    @Override
    public void showResult(String msg) {
        if (msg.equals("发送成功")) {
            new PromptDialog(this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                    .setAnimationEnable(true)
                    .setTitleText("SUCCESS")
                    .setContentText(msg)
                    .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                            etMessageTitle.setText("");
                            etMessageContent.setText("");
                        }
                    }).show();
        }
    }





    @Override
    public void setData(List<Messages.MessageListBean> data) {

    }

    @Override
    public List<Messages.MessageListBean> getData() {
        return null;
    }


    @Override
    public void showContent() {

    }


    @OnClick(R.id.send_message)
    public void onViewClicked() {
       Messages.MessageListBean messages = new Messages.MessageListBean();
        messages.setTitle(etMessageTitle.getText().toString());
        messages.setMessage(etMessageContent.getText().toString());

        mPresenter.sendMessage(messages, UserInfoManager.getUserInfo().getUid(),receive_id);
    }

    @Override
    public void setUnMessList(List<Messages.MessageListBean> messageList) {

    }
}
