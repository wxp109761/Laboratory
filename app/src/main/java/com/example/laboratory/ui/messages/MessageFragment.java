package com.example.laboratory.ui.messages;

import android.annotation.SuppressLint;
import com.example.laboratory.bean.Messages;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.root_ui.adapter.MessageListAdapter;
import com.example.laboratory.ui.adapter.BaseListAdapter;
import com.example.laboratory.ui.base.BaseAbListFragment;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class MessageFragment extends BaseAbListFragment<MessagePresenter, Messages.MessageListBean> implements MessageContract.IMessageView,MessageListAdapter.OnMessageListerer {

    List<String> listMessUnRead=new ArrayList<>();
    List<Messages.MessageListBean> messageListBeans=null;
    @Override
    protected MessagePresenter createPresenter() {

        return new MessagePresenter();
    }

    @Override
    protected void loadDatas() {
        mPresenter.getUnReadMessages(UserInfoManager.getUserInfo().getUid());
        mPresenter.getAllMessage(UserInfoManager.getUserInfo().getUid());

    }

    @Override
    protected BaseListAdapter<Messages.MessageListBean> getListAdapter() {
        return new MessageListAdapter(this,listMessUnRead);
    }


    @Override
    public void showResult(String result) {

    }

    @Override
    public void setData(List<Messages.MessageListBean> data) {
        mListData.clear();
        mListData.addAll(data);
    }

    public void showContent() {
        super.showContent();
        for(int i=0;i<mListData.size();i++){
            for(int k=0;k<messageListBeans.size();k++){
                if(messageListBeans.get(k).getId().equals(mListData.get(i).getId())){
                    listMessUnRead.add(mListData.get(i).getId());
                    break;
                }

            }
        }

    }

    @Override
    public void onMessageRead(String id) {
        listMessUnRead.remove(id);
        mPresenter.setMessageRead(id,UserInfoManager.getUserInfo().getUid());

        mRecyclerView.notifyDataSetChanged();
    }


    @Override
    public void setUnMessList(List<Messages.MessageListBean> messageList) {
       // System.out.println(messageList.size()+" ss");
}
}
