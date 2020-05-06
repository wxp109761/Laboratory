package com.example.laboratory.utils.poblist;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.laboratory.R;

import java.util.ArrayList;
import java.util.LinkedList;


public class XJLabsDialog extends Dialog {
    @BindView(R.id.dialog_title)
    TextView dialogTitle;
    @BindView(R.id.listView)
    ListView regionListView;
    @BindView(R.id.listView2)
    ListView plateListView;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;

    private ArrayList<String> groups = new ArrayList<String>();
    private LinkedList<String> labName_linklist = new LinkedList<String>();
    private LinkedList<String> labId_linklist = new LinkedList<String>();
    private SparseArray<LinkedList<String>> children = new SparseArray<LinkedList<String>>();
    private SparseArray<LinkedList<String>> children_labId = new SparseArray<LinkedList<String>>();
    private TextAdapter plateListViewAdapter;
    private TextAdapter earaListViewAdapter;
    private OnSelectListener mOnSelectListener;
    private int tEaraPosition = 0;
    private int tBlockPosition = 0;
    private String showString = "";
    private String showLabIdString = "";


    private String[] parentNames;
    private String[][] childLabName;
    private String[][] childlabId;

    public XJLabsDialog(OnSelectListener onSelectListener, Context context, String[] parentNames, String[][] childLabName, String[][] childLabId) {
        super(context);
        this.mOnSelectListener = onSelectListener;
        this.parentNames = parentNames;
        this.childLabName = childLabName;
        this.childlabId = childLabId;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_labs);
        //点击外部区域不可取消
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        lp.y = 0;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);

        //初始化下拉列表展示的数据
        for (int i = 0; i < parentNames.length; i++) {
            groups.add(parentNames[i]);
            LinkedList<String> tItem = new LinkedList<String>();
            LinkedList<String> tlab_idItem = new LinkedList<String>();
            for (int j = 0; j < childLabName[i].length; j++) {
                tItem.add(childLabName[i][j]);
                tlab_idItem.add(childlabId[i][j]);
            }
            children.put(i, tItem);
            children_labId.put(i, tlab_idItem);
        }

        earaListViewAdapter = new TextAdapter(getContext(), groups,
                R.drawable.choose_item_selected,
                R.drawable.choose_eara_item_selector, "1");
        earaListViewAdapter.setTextSize(17);
        earaListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);
        regionListView.setAdapter(earaListViewAdapter);
        earaListViewAdapter
                .setOnItemClickListener(new TextAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        //设置第二个可见
                        plateListView.setVisibility(View.VISIBLE);
                        if (position < children.size()) {
                            labName_linklist.clear();
                            labName_linklist.addAll(children.get(position));
                            labId_linklist.clear();
                            labId_linklist.addAll(children_labId.get(position));

                            plateListViewAdapter.notifyDataSetChanged();
                        }
                    }
                });
        if (tEaraPosition < children.size()) {
            labName_linklist.addAll(children.get(tEaraPosition));
            labId_linklist.addAll(children_labId.get(tEaraPosition));
        }

        plateListViewAdapter = new TextAdapter(getContext(), labName_linklist, labId_linklist,
                R.drawable.choos_item_selecter,
                R.drawable.choose_plate_item_selector);
        plateListViewAdapter.setTextSize(15);
        plateListViewAdapter.setSelectedPositionNoNotify(tBlockPosition);
        plateListView.setAdapter(plateListViewAdapter);
        plateListViewAdapter
                .setOnItemClickListener(new TextAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, final int position) {

                        showString = labName_linklist.get(position);
                        showLabIdString = labId_linklist.get(position);
                        if (mOnSelectListener != null) {
                            mOnSelectListener.getValue(showLabIdString);
                        }

                    }
                });
        if (tBlockPosition < labName_linklist.size()) {
            showString = labName_linklist.get(tBlockPosition);
            showLabIdString = labId_linklist.get(tBlockPosition);

        }

        setDefaultSelect();

    }

    public void setDefaultSelect() {
        regionListView.setSelection(tEaraPosition);
        plateListView.setSelection(tBlockPosition);
    }

    @OnClick(R.id.cancel_btn)
    public void onViewClicked() {
        this.dismiss();
    }


    public interface OnSelectListener {
        public void getValue(String showlabId);
    }


}
