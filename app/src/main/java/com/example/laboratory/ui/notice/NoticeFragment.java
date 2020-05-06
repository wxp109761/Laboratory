package com.example.laboratory.ui.notice;

import android.annotation.SuppressLint;
import android.util.Log;
import com.example.laboratory.bean.Notices;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.root_ui.adapter.NoticeListAdapter;
import com.example.laboratory.ui.adapter.BaseListAdapter;
import com.example.laboratory.ui.base.BaseAbListFragment;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class NoticeFragment extends BaseAbListFragment<NoticePresenter, Notices.NoticesListBean> implements NoticeContract.INoticeView,NoticeListAdapter.OnNoticeListerer {


    List<Integer> listUnRead=new ArrayList<>();
    List<Notices.NoticesListBean> unReadNoticeList=new ArrayList<>();

    String get_cate;

    @SuppressLint("ValidFragment")
    public NoticeFragment(String all) {
        this.get_cate=all;
    }

    @Override
    protected NoticePresenter createPresenter() {

        return new NoticePresenter();
    }


    @Override
    public void showContent() {
        super.showContent();

        for(int i=0;i<mListData.size();i++){
            for(int k=0;k<unReadNoticeList.size();k++){
                if(unReadNoticeList.get(k).getId().equals(mListData.get(i).getId())){
                    listUnRead.add(mListData.get(i).getId());
                    break;
                }

            }
            // System.out.println("eeee"+la.get(i).getId()+"-----"+la.get(i).getCh());
        }

    }

    @Override
    protected void loadDatas() {
        mPresenter.getNoticesData(UserInfoManager.getUserInfo().getUid(),"","getUnRead","");

        if(UserInfoManager.getUserInfo().getPermission().equals("2")){
            if(get_cate.equals("all")){
                Log.d("xxx",UserInfoManager.getUserInfo().getDepartId());
                mPresenter.getNoticesData("",UserInfoManager.getUserInfo().getDepartId(),"getAll","");
            }else  if(get_cate.equals("collage")){
                mPresenter.getNoticesData("","0","getAll","");
            }else if(get_cate.equals("depart")){
                mPresenter.getNoticesData("",UserInfoManager.getUserInfo().getDepartId(),"getByDepart","");
            }
        }else if(UserInfoManager.getUserInfo().getPermission().equals("1")){
            if(get_cate.equals("all")){
                mPresenter.getNoticesData("",UserInfoManager.getUserInfo().getDepartId(),"getAll","");
            }else  if(get_cate.equals("depart_admin")){
                mPresenter.getNoticesData("","1","getByDepart","");
            }else if(get_cate.equals("common")){
                //只要是本学院负责人发布的公告
                mPresenter.getNoticesData("",UserInfoManager.getUserInfo().getDepartId(),"getByDepart","");
            } if(get_cate.equals("mine_publish")){
                mPresenter.getNoticesData("","","getBySendId",UserInfoManager.getUserInfo().getUid());
            }
        }else {
            if(get_cate.equals("all")) {
                mPresenter.getNoticesData("",UserInfoManager.getUserInfo().getDepartId(),"adminGetAll",UserInfoManager.getUserInfo().getUid());
            }else if(get_cate.equals("mine_publish")){
                mPresenter.getNoticesData("","","getBySendId",UserInfoManager.getUserInfo().getUid());
            }

        }



    }

    @Override
    protected BaseListAdapter<Notices.NoticesListBean> getListAdapter() {

        return new NoticeListAdapter(this,listUnRead);
    }


    @Override
    public void showResult(String result) {

    }

    @Override
    public void setData(List<Notices.NoticesListBean> data) {
        mListData.clear();
        mListData.addAll(data);


    }
    @Override
    public void getUnNoticeList(List<Notices.NoticesListBean> noticesListBeans) {

        unReadNoticeList.clear();
        unReadNoticeList.addAll(noticesListBeans);
    }


    @Override
    public void onNoticeRead(Integer id) {
        listUnRead.remove(id);
        mPresenter.setRead(id,UserInfoManager.getUserInfo().getUid());

        mRecyclerView.notifyDataSetChanged();
    }
}
