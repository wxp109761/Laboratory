package com.example.laboratory.ui.main;

import com.example.laboratory.bean.LaboratoryList;
import com.example.laboratory.ui.core.view.IPageLoadDataView;


import java.util.List;

/**
 * 搜索协约类
 * author: 康栋普
 * date: 2018/4/5
 */

public class SearchContract {
    interface ISearchPresenter {
        void search();

        void getHotWord();

        void getFriend();

        void collectArticle();

        void unCollectArticle();
    }

    interface ISearchView extends IPageLoadDataView<LaboratoryList.LabListBean> {
        int getArticleId();//文章id

        String getKeyword();

//        void setHotwordData(List<Hotword> mHotwordDatas);
//
//        void setFriendData(List<Friend> mFriendListDatas);

        void collect(boolean isCollect, String result);
    }
}
