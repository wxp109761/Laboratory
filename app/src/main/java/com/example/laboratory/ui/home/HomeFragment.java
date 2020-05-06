package com.example.laboratory.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.laboratory.R;
import com.example.laboratory.bean.Depart;
import com.example.laboratory.bean.LaboratoryList;
import com.example.laboratory.bean.UserList;
import com.example.laboratory.common.CommonUtils;
import com.example.laboratory.common.Const;
import com.example.laboratory.inter.OnCheckItemCheckListener;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.root_ui.AddLaboratory.AddLabActivity;
import com.example.laboratory.root_ui.AddUser.AddUserActivity;
import com.example.laboratory.root_ui.Item.AddItemActivity;
import com.example.laboratory.root_ui.Item.ItemListActivity;
import com.example.laboratory.root_ui.UserInfoList.UserInfoListActivity;
import com.example.laboratory.root_ui.message.MessageActivity;
import com.example.laboratory.root_ui.notice.NoticeActivity;
import com.example.laboratory.ui.Remind.remind;
import com.example.laboratory.ui.SecureCheck.SeCheckActivity;
import com.example.laboratory.ui.webguide.WebGuideActivity;
import com.example.laboratory.ui.base.BasePresenterFragment;
import com.example.laboratory.ui.user.Laboratory.LabListActivity;
import com.example.laboratory.utils.GlideImageLoader;
import com.example.laboratory.utils.IntentUtils;
import com.example.laboratory.utils.poblist.XJLabsDialog;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BasePresenterFragment<HomePresenter> implements HomeContract.IHomeView, OnCheckItemCheckListener,XJLabsDialog.OnSelectListener {
    private static List<Integer> bannerImages = new ArrayList<>();
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.malfunction_repair)
    TextView malfunctionRepair;
    @BindView(R.id.secure_check)
    TextView secureCheck;
    @BindView(R.id.remind)
    TextView mineMessage;
    @BindView(R.id.notice_and_back)
    TextView noticeAndBack;
    @BindView(R.id.web_page_guide)
    TextView webPageGuide;
    @BindView(R.id.user_manage)
    TextView userManage;
    @BindView(R.id.lab_manage)
    TextView labManage;
    @BindView(R.id.item_manage)
    TextView itemManage;
    @BindView(R.id.check_remind)
    TextView checkRemind;
    @BindView(R.id.message_remind)
    TextView messageRemind;
    private View rootView = null;

    List<LaboratoryList.LabListBean> rowsBeans=null;
    List<UserList.UserListBean> uListBeans=new ArrayList<>();

    static {
        bannerImages.add(R.drawable.banner_1);
        bannerImages.add(R.drawable.banner_3);
        bannerImages.add(R.drawable.banner_4);
    }


    @Override
    protected void initViews(View view) {
        this.rootView = view;
        ButterKnife.bind(this,rootView);
//        mPresenter.getDepartList();

        if (UserInfoManager.isLogin()) {
            if (UserInfoManager.getUserInfo().getPermission().equals("2")) {
                userManage.setVisibility(View.GONE);
                labManage.setVisibility(View.GONE);
                itemManage.setVisibility(View.GONE);
            }else {
                userManage.setVisibility(View.VISIBLE);
                labManage.setVisibility(View.VISIBLE);
                itemManage.setVisibility(View.VISIBLE);

            }
        }
        BannerLooper();
        if(UserInfoManager.getUserInfo().getPermission().equals("0")){
            mPresenter.getAllUsers();
        }else {
            mPresenter.getUserListPermissionNot("0");
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        initViews(rootView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    private void BannerLooper() {
        Banner banner = (Banner) rootView.findViewById(R.id.banner);
        //设置banner样式
        //banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(bannerImages);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        // banner.setBannerTitles(titles);
        // banner.setti
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }



    @OnClick({R.id.malfunction_repair, R.id.secure_check, R.id.remind, R.id.notice_and_back, R.id.web_page_guide, R.id.user_manage, R.id.lab_manage, R.id.item_manage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.malfunction_repair:
                break;
            case R.id.secure_check:

               SecureCheck();
                break;
            case R.id.remind:
                remind();
                break;
            case R.id.notice_and_back:
                NoticeBackListener();
                break;
            case R.id.web_page_guide:
                WebGuideListener();
                break;
            case R.id.user_manage:
                UserManageListener();
                break;
            case R.id.lab_manage:
                LabManageListener();
                break;
            case R.id.item_manage:
                ProItemeListener();
                break;
        }
    }

    List<Depart.DepartListBean> departListeans=new ArrayList<>();

    @Override
    public void getDepartList(List<Depart.DepartListBean> departListeans) {
        this.departListeans=departListeans;

    }

    /**
     * 提醒待办
     */
    private void remind(){
        Intent intent=new Intent(rootView.getContext(), remind.class);
        startActivity(intent);
    }


    /***
     * 网址导航
     */
    private void WebGuideListener(){
        Intent intent=new Intent(rootView.getContext(), WebGuideActivity.class);
        startActivity(intent);
    }

    /**
     * 安全巡检
     */
    private void SecureCheck() {
        if(UserInfoManager.getUserInfo().getPermission().equals("2")){
            mPresenter.getLabsData(UserInfoManager.getUserInfo().getUid(),"uid");
        }else if(UserInfoManager.getUserInfo().getPermission().equals("1")) {
            mPresenter.getLabsData("1070","depart_id");
        }else{
            mPresenter.getLabsData("","all");
        }

    }
    XJLabsDialog xjLabsDialog;
    @Override
    public void getLabList(List<LaboratoryList.LabListBean> labListBeans) {
        this.rowsBeans=labListBeans;
        if(UserInfoManager.getUserInfo().getPermission().equals("1")||UserInfoManager.getUserInfo().getPermission().equals("2")){

            List<String> itemmStrings=new ArrayList<>();
            for (int i = 0; i <rowsBeans.size() ; i++) {
                System.out.println("ZZZ"+rowsBeans.get(i).getLabel()+"");
                itemmStrings.add(rowsBeans.get(i).getLabel());
            }
            String[] itemNames=itemmStrings.toArray(new String[itemmStrings.size()]);
            ActionSheetDialog dialog = actionSheetDialog(itemNames,"待检测实验室列表");
            dialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (!UserInfoManager.isLogin()) {
                        IntentUtils.goLogin(getActivity());
                    } else {
                        Intent intent= new Intent(getContext(), SeCheckActivity.class) ;
                        Bundle bundle = new Bundle();
                        bundle.putString("labId", rowsBeans.get(position).getLabid());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    dialog.dismiss();
                }
            });
        }else {
            List<String> departName=new ArrayList<>();
            for (int i = 0; i <departListeans.size() ; i++) {
                departName.add(departListeans.get(i).getDepart_name());
            }
            String[] depart=departName.toArray(new String[departName.size()]);
            String[][] labs=new String[depart.length][];
            String[][] childlabsId=new String[depart.length][];
            for (int i = 0; i <depart.length ; i++) {
                List<String> lablistTemp=new ArrayList<>();
                List<String> labIdTemp=new ArrayList<>();
                for (int j = 0; j <rowsBeans.size() ; j++) {

                    System.out.println(rowsBeans.get(i).getLabel()+"CC");
                    if (CommonUtils.getDepartId(depart[i]).equals(rowsBeans.get(j).getDepartId())){
                        lablistTemp.add(rowsBeans.get(j).getLabel());
                        labIdTemp.add(rowsBeans.get(j).getLabid());
                    }
                    labs[i]=lablistTemp.toArray(new String[lablistTemp.size()]);
                    childlabsId[i]=labIdTemp.toArray(new String[labIdTemp.size()]);
                }
            }


            xjLabsDialog=new XJLabsDialog(this,getContext(),depart,labs,childlabsId);
            xjLabsDialog.show();
        }

    }




    ActionSheetDialog actionSheetDialog(String []stringItems,String title){
        final ActionSheetDialog dialog = new ActionSheetDialog(rootView.getContext(), stringItems, null);
        dialog.isTitleShow(true).show();
        dialog.title(title);
        dialog.itemTextColor(Color.parseColor("#0091ea"));
        dialog.cancelText(Color.parseColor("#0091ea"));
        // dialog.itemPressColor(Color.parseColor("#e9857d"));
        return dialog;
    }



    /**
     * 安全员管理
     */
    private void UserManageListener() {
        String stringItems[];
        if(UserInfoManager.getUserInfo().getPermission().equals("0")){

            stringItems= new String[]{"学院负责人信息", "新增学院负责人"};
        }else{
            stringItems= new String[]{"实验室安全员信息", "新增实验室安全员"};
        }

        ActionSheetDialog dialog = actionSheetDialog(stringItems,"人员管理");
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (!UserInfoManager.isLogin()) {
                            IntentUtils.goLogin(getActivity());
                        } else {
                           startActivity(new Intent(getContext(), UserInfoListActivity.class));
                        }
                        break;
                    case 1:
                        if (!UserInfoManager.isLogin()) {
                            IntentUtils.goLogin(getActivity());
                        } else {
                            startActivity(new Intent(getContext(),  AddUserActivity.class));
                        }
                        break;
                }
                dialog.dismiss();
            }
        });
    }
    /**
     * 实验室管理
     */
    private void LabManageListener() {

        String stringItems[] = {"实验室列表", "新增实验室"};
        ActionSheetDialog dialog = actionSheetDialog(stringItems,"实验室管理");
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (!UserInfoManager.isLogin()) {
                            IntentUtils.goLogin(getActivity());
                        } else {
                            Intent intent= new Intent(getContext(), LabListActivity.class) ;
                            Bundle bundle = new Bundle();
                            bundle.putString("mine","");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        break;
                    case 1:
                        if (!UserInfoManager.isLogin()) {
                            IntentUtils.goLogin(getActivity());
                        } else {

                            for (int i = 0; i <uListBeans.size() ; i++) {
                                System.out.println("ZZZ"+uListBeans.get(i).getUsername());
                            }

                            Intent intent= new Intent(getContext(), AddLabActivity.class) ;
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(Const.BUNDLE_KEY.OBJ, (Serializable) uListBeans);

                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        break;
                }
                dialog.dismiss();
            }
        });


    }

    /**
     * 待巡检条目管理
     */
    private void ProItemeListener() {
        String stringItems[] = {"待检测条目列表", "新增检测条目"};
        ActionSheetDialog dialog = actionSheetDialog(stringItems,"检测条目管理");
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (!UserInfoManager.isLogin()) {
                            IntentUtils.goLogin(getActivity());
                        } else {
                            startActivity(new Intent(getContext(), ItemListActivity.class));
                        }
                        break;
                    case 1:
                        if (!UserInfoManager.isLogin()) {
                            IntentUtils.goLogin(getActivity());
                        } else {
                            startActivity(new Intent(getContext(),  AddItemActivity.class));
                        }
                        break;
                }
                dialog.dismiss();
            }
        });
    }


    private void NoticeBackListener() {
        String stringItems[] = {"公告通知", "站内短信  "};
        ActionSheetDialog dialog = actionSheetDialog(stringItems,"公告信箱");
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(rootView.getContext(), NoticeActivity.class);
                        rootView.getContext().startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(rootView.getContext(), MessageActivity.class);
                        rootView.getContext().startActivity(intent2);
                        break;
                }
                dialog.dismiss();
            }
        });

    }





    @Override
    public void getUserList(List<UserList.UserListBean> userListBeans) {
        uListBeans.clear();
        if(UserInfoManager.getUserInfo().getPermission().equals("0")){
            uListBeans.addAll(userListBeans);
        }else {
            for (int i = 0; i <userListBeans.size() ; i++) {
                if(userListBeans.get(i).getDepartId().equals(UserInfoManager.getUserInfo().getDepartId())){
                    uListBeans.add(userListBeans.get(i));
                }
            }
        }
    }


    @Override
    public void showResult(String msg) {

    }


    @Override
    public void onDepartClick(String departId) {

    }

    @Override
    public void getValue(String labId) {
        if (!UserInfoManager.isLogin()) {
            IntentUtils.goLogin(getActivity());
        } else {
            xjLabsDialog.dismiss();
            Intent intent= new Intent(getContext(), SeCheckActivity.class) ;
            Bundle bundle = new Bundle();
            bundle.putString("labId", labId);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }
}

