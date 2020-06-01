package com.example.laboratory.ui.discover;


import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laboratory.R;
import com.example.laboratory.bean.NewsBean;
import com.example.laboratory.ui.adapter.NewsAdapter;
import com.example.laboratory.ui.base.BaseFragment;
import com.example.laboratory.utils.StreamUtils;
import com.example.laboratory.widget.LMRecyclerView;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import butterknife.BindView;

public class DiscoverFragment extends BaseFragment {

    String TAG = "CreaingNewsActivity";
    int page = 0;
    @BindView(R.id.news_list)
    LMRecyclerView newsList;

    private String DEFAULT_MAIN_URL = "http://api.search.sina.com.cn/?q=[高校实验室]&c=news&sort=time&ie=utf-8&from=dfz_api&page=" + page;
    View rootView;

    List<NewsBean.ResultBean.ListBean> listBean;

    @Override
    protected void initViews(View view) {
        rootView = view;
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext());
       // newsList.setLayoutManager(linearLayoutManager);
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
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
            listBean = fromJson.getResult().getList();

            NewsAdapter newsAdapter = new NewsAdapter(rootView.getContext(), listBean);
            newsList.setAdapter(newsAdapter);
            Log.d(TAG, listBean.get(0).getTitle());
            //listView.addI

////
////
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
//
        }
    }
}
