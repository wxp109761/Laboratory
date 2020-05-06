package com.example.laboratory.ui.main;

import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import com.example.laboratory.R;
import com.example.laboratory.bean.LaboratoryList;
import com.example.laboratory.ui.adapter.BaseListAdapter;
import com.example.laboratory.ui.adapter.LaboratoryListAdapter;
import com.example.laboratory.ui.base.BaseAbListActivity;

import java.lang.reflect.Field;
import java.util.List;
import androidx.appcompat.widget.SearchView;
/**
 * 搜索页面
 * author: 康栋普
 * date: 2018/4/5
 */

public class SearchActivity extends BaseAbListActivity<SearchPresenter, LaboratoryList.LabListBean> implements SearchContract.ISearchView, LaboratoryListAdapter.OnLabListItemClickListener {
    @Override
    protected void loadDatas() {

    }

    @Override
    protected BaseListAdapter<LaboratoryList.LabListBean> getListAdapter() {
        return new LaboratoryListAdapter(this,"mine");
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    public int getArticleId() {
        return 0;
    }

    @Override
    public String getKeyword() {
        return null;
    }

    @Override
    public void collect(boolean isCollect, String result) {

    }

    @Override
    public void setData(List<LaboratoryList.LabListBean> data) {

    }

    @Override
    public void showResult(String msg) {

    }

    @Override
    public void onDeleteLabsClick(int position, String labid) {

    }

    private SearchView.SearchAutoComplete searchAutoComplete;
    private SearchView mSearchView;
//    private SearchView.SearchAutoComplete searchAutoComplete;
    private View mHeaderView;
//    private TagFlowLayout mKeywordTagLayout, mFriendTagLayout;
//    private int position;
//    private int id;
//    private String keyword = "";
//    private List<Hotword> mHotwordDatas = new ArrayList<>();
//    private List<Friend> mFriendDatas = new ArrayList<>();

    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle(R.string.search);
        return true;
    }
    @Override
    protected View initHeaderView() {
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.search_header, mRecyclerView, false);
       // mKeywordTagLayout =  mHeaderView.findViewById(R.id.keywordTaglayout);
       // mFriendTagLayout =  mHeaderView.findViewById(R.id.friendTaglayout);
        return mHeaderView;
    }

//
//    //设置搜索的数据
//    @Override
//    public void setData(List<LaboratoryList.LabListBean> data) {
//        mRecyclerView.removeHeaderView();
//        setRefreshEnable(true);
//        setCanLoadMore(true);
//        if (state != Const.PAGE_STATE.STATE_LOAD_MORE)
//            mRecyclerView.scrollToPosition(0);
//        mListData.addAll(data);
//    }
//
//    @Override
//    public void showError() {
//        super.showError();
//        setRefreshEnable(true);
//    }
//
//
//    @Override
//    public String getKeyword() {
//        return keyword;
//    }
//
//
//    @Override
//    public int getArticleId() {
//        return id;
//    }

//    //热搜关键词
//    @Override
//    public void setHotwordData(final List<Hotword> mHotListDatas) {
//        mHotwordDatas.clear();
//        mHotwordDatas.addAll(mHotListDatas);
//        mKeywordTagLayout.setAdapter(new TagAdapter<Hotword>(mHotListDatas) {
//            @Override
//            public View getView(FlowLayout parent, int position, Hotword hotword) {
//                TextView tagView = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_search_tag, mKeywordTagLayout, false);
//                tagView.setText(hotword.getName());
//                setTagTextColor(tagView);
//                return tagView;
//            }
//        });
//        mKeywordTagLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
//            @Override
//            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                keyword = mHotListDatas.get(position).getName();
//                searchAutoComplete.setText(keyword);
//                searchAutoComplete.setSelection(keyword.length());
//                mSearchView.setQuery(keyword, true);
//                return false;
//            }
//        });
//    }
//
//    //历史网站
//    @Override
//    public void setFriendData(final List<Friend> mFriendListDatas) {
//        mFriendDatas.clear();
//        mFriendDatas.addAll(mFriendListDatas);
//        mFriendTagLayout.setAdapter(new TagAdapter<Friend>(mFriendDatas) {
//            @Override
//            public View getView(FlowLayout parent, int position, Friend friend) {
//                TextView tagView = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_search_tag, mFriendTagLayout, false);
//                tagView.setText(friend.getName());
//                setTagTextColor(tagView);
//                return tagView;
//            }
//        });
//        mFriendTagLayout.setOnTagClickListener(
//
//                new TagFlowLayout.OnTagClickListener() {
//            @Override
//            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                Friend mFriend = mFriendListDatas.get(position);
//                Article bean = new Article();
//                bean.setTitle(mFriend.getName());
//                bean.setLink(mFriend.getLink());
//                Intent intent = new Intent(SearchActivity.this, WebViewActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(Const.BUNDLE_KEY.OBJ, bean);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                return false;
//            }
//        });
//    }
//
//    //关键词颜色
//    private void setTagTextColor(TextView tagView) {
//        int red, green, blue;
//        Random mRandow = new Random();
//        red = mRandow.nextInt(255);
//        green = mRandow.nextInt(255);
//        blue = mRandow.nextInt(255);
//        int color = Color.rgb(red, green, blue);
//        tagView.setTextColor(color);
//    }
//
    //SearchView相关设置
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu_setting, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        //获取搜索框
        mSearchView = (SearchView) menuItem.getActionView();
        //设置搜索hint
        mSearchView.setQueryHint("关键词");
        mSearchView.onActionViewExpanded();
        //去除搜索框背景
        deleteSearchPlate();
        searchAutoComplete =  mSearchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setHintTextColor(ContextCompat.getColor(this, R.color._60ffffff));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ImageView mCloseView =  mSearchView.findViewById(R.id.search_close_btn);
            mCloseView.setBackground(ContextCompat.getDrawable(this, R.drawable.ripple_close));
        }

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               // keyword = query;
                refreshData();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
//                    //keyword = newText;
//                    if (mHotwordDatas.size() == 0)
//                        loadTagDatas();
                }

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
//
    //去除SearchView的输入框默认背景
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void deleteSearchPlate() {
        try {
            Class<?> cla = mSearchView.getClass();
            Field mSearchPlateField = cla.getDeclaredField("mSearchPlate");
            mSearchPlateField.setAccessible(true);
            View searchPlate = (View) mSearchPlateField.get(mSearchView);
            searchPlate.setBackground(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
//
//    @Override
//    protected SearchPresenter createPresenter() {
//        return new SearchPresenter();
//    }
//
//    @Override
//    protected boolean isCanLoadMore() {
//        return true;
//    }
//
//    @Override
//    protected void loadDatas() {
//        if (mSearchView == null) {
//            loadTagDatas();
//            return;
//        }
//
//        loadArticleListDatas();
//    }
//
//    private void loadTagDatas() {
//        setRefreshEnable(false);
//        setCanLoadMore(false);
//        mListData.clear();
//        mRecyclerView.addHeaderView(mHeaderView);
//        mListAdapter.notifyAllDatas(mListData, mRecyclerView);
//        showContent();
//        mPresenter.getHotWord();
//        mPresenter.getFriend();
//    }
//
//    private void loadArticleListDatas() {
//        mHotwordDatas.clear();
//        mFriendDatas.clear();
//        mPresenter.search();
//    }
//
//    @Override
//    protected BaseListAdapter<Article> getListAdapter() {
//        return new ArticleListAdapter(this, Const.LIST_TYPE.SEARCH);
//    }
//
//    @Override
//    public void onItemClick(int position,Article bean) {
//        Intent intent = new Intent(this, WebViewActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(Const.BUNDLE_KEY.OBJ, bean);
//        bundle.putString(Const.BUNDLE_KEY.TYPE, Const.EVENT_ACTION.SEARCH);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onDeleteCollectClick(int position, int id, int originId) {
//    }
//
//    @Override
//    public void collect(boolean isCollect, String result) {
//        notifyItemData(isCollect, result);
//    }
//
//    private void notifyItemData(boolean isCollect, String result) {
//        mListData.get(position).setCollect(isCollect);
//        mListAdapter.notifyItemDataChanged(position, mRecyclerView);
//        ToastUtils.showToast(AppContext.getContext(), result);
//    }
//
//    /**
//     * 收藏
//     * @param position
//     * @param id
//     */
//    @Override
//    public void onCollectClick(int position, int id) {
//        if (!UserInfoManager.isLogin())
//            IntentUtils.goLogin(this);
//        this.position = position;
//        this.id = id;
//        if (mListData.get(this.position).isCollect())
//            mPresenter.unCollectArticle();
//        else
//            mPresenter.collectArticle();
//    }
//
//    @Override
//    protected String registerEvent() {
//        return Const.EVENT_ACTION.SEARCH;
//    }
//
//    @Override
//    protected void receiveEvent(Object object) {
//        Event mEvent = (Event) object;
//        if (mEvent.type == Event.Type.REFRESH_ITEM) {
//            Article bean = (Article) mEvent.object;
//            for (int i = 0; i < mListData.size(); i++) {
//                if (bean.equals(mListData.get(i))) {
//                    position = i;
//                    notifyItemData(bean.isCollect(), getString(R.string.collect_success));
//                }
//            }
//        } else if (mEvent.type == Event.Type.REFRESH_LIST){
//            refreshData();
//        }
//    }

