package com.example.laboratory.inter;

import android.widget.Adapter;
import android.widget.AdapterView;

public interface OnTreeItemClickListener extends AdapterView.OnItemClickListener {
    void OnTreeItem(int position);
}
