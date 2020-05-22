package com.example.laboratory.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.laboratory.R;
import com.example.laboratory.bean.NewsBean;
import com.example.laboratory.common.ListDataHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsAdapter extends BaseListAdapter<NewsBean.ResultBean.ListBean> {
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_summary)
    TextView txtSummary;
    @BindView(R.id.txt_media)
    TextView txtMedia;
    @BindView(R.id.txt_data)
    TextView txtData;
    @BindView(R.id.line)
    LinearLayout line;
    private Context mContext;
    private List<NewsBean.ResultBean.ListBean> list;
    public NewsAdapter(Context mContext, List<NewsBean.ResultBean.ListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.adapter_item_list;
    }

    @Override
    public void bindDatas(ListDataHolder holder, NewsBean.ResultBean.ListBean bean, int itemType, int position) {
        ButterKnife.bind(this, holder.itemView);
        if (list.get(position).getImgurl() == null || list.get(position).getImgurl().equals("")) {
            //如果没有图片，则将imageview控件隐藏
            imageView.setVisibility(View.GONE);
        } else {
            Glide
                    .with(mContext)
                    .load(list.get(position).getImgurl())
                    .into(imageView);

        }
        txtTitle.setText(list.get(position).getTitle());
        txtSummary.setText(list.get(position).getIntro());
        txtMedia.setText(list.get(position).getMedia());
        txtData.setText(list.get(position).getDatetime());
    }


}
