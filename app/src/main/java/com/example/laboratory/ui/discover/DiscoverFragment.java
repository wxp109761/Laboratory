package com.example.laboratory.ui.discover;


import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.laboratory.R;
import com.example.laboratory.bean.NewsBean;
import com.example.laboratory.ui.adapter.BaseListAdapter;
import com.example.laboratory.ui.adapter.NewsAdapter;
import com.example.laboratory.ui.base.BaseAbListFragment;
import com.example.laboratory.ui.base.BaseFragment;
import com.example.laboratory.ui.core.presenter.BasePresenter;
import com.example.laboratory.ui.core.view.IView;
import com.example.laboratory.ui.webguide.WebViewActivity;
import com.example.laboratory.utils.StreamUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverFragment extends BaseAbListFragment implements IView {

    String TAG = "CreaingNewsActivity";
    NewsAdapter adapter;
    int page = 0;
    @BindView(R.id.list_view)
    ListView listView;
    private String DEFAULT_MAIN_URL = "http://api.search.sina.com.cn/?q=[高校实验室]&c=news&sort=time&ie=utf-8&from=dfz_api&page=" + page;
    View rootView;

    List<NewsBean.ResultBean.ListBean> listBean;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void loadDatas() {
        MyAsyncTask myAsyncTask=new MyAsyncTask();
        myAsyncTask.execute();
    }

    @Override
    protected BaseListAdapter getListAdapter() {
        adapter= new NewsAdapter(getContext(), mListData);
        return adapter;
    }

    @Override
    public void setData(List data) {

    }


    @Override
    public void showResult(String msg) {

        mRecyclerView.notifyDataSetChanged();
    }


    class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(DEFAULT_MAIN_URL);
                HttpURLConnection openConnection = (HttpURLConnection) url
                        .openConnection();
                openConnection.setConnectTimeout(5000);
                openConnection.setReadTimeout(5000);
                int responseCode = openConnection.getResponseCode();
                if (responseCode == 200) {
                    InputStream inputStream = openConnection.getInputStream();
                    StreamUtils streamUtils = new StreamUtils();
                    String parseStream = streamUtils.parseSteam(inputStream);
                    return parseStream;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Gson gson = new Gson();
            NewsBean fromJson = gson.fromJson(result,
                    NewsBean.class);


            mListData.clear();
            mListData.addAll(fromJson.getResult().getList());
            Log.d(TAG,mListData.get(0)+"");
//
//
//
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    for (int i = 0; i < listBean.size(); i++) {
//                        if (i == position) {
//                            String url = listBean.get(i).getUrl();
//                            Intent it = new Intent(rootView.getContext(), WebViewActivity.class);
//                            it.putExtra("url", url);
//                            it.putExtra("title", listBean.get(position).getTitle());
//                            startActivity(it);
//                        }
//                    }
//                }
//            });
//        }
        }
    }


}
