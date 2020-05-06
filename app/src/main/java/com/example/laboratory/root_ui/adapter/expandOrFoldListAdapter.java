package com.example.laboratory.root_ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.laboratory.R;

import java.util.List;

public class expandOrFoldListAdapter extends BaseAdapter {
    List<String> mList;
    private Context mContext;
    boolean isExpand;
    public expandOrFoldListAdapter(Context context, List<String> mList,boolean isExpand) {

        this.mList= mList;
        mContext = context;
        this.isExpand=isExpand;
    }

    @Override
    public int getCount() {
        if(isExpand){
            return mList.size();
        }else {
            return 0;
        }


    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.sublist_item, null);
            viewHolder = new ItemViewHolder();
            viewHolder.textView = (TextView) convertView
                    .findViewById(R.id.textview1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder=(ItemViewHolder)convertView.getTag();
        }
        viewHolder.textView.setText(mList.get(position));
        viewHolder.textView.setTextColor(mContext.getResources().getColor(R.color._767676));
        //因为数据没变化
        convertView.setBackgroundColor(mContext.getResources().getColor(R.color.shallow_white));//对
        //
        return convertView;
    }
    public static class ItemViewHolder {
        public TextView textView;
    }
}
