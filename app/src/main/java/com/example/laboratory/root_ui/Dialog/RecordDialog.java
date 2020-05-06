package com.example.laboratory.root_ui.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.laboratory.R;
import com.example.laboratory.bean.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class RecordDialog extends Dialog {
    @BindView(R.id.dialog_title)
    TextView dialogTitle;
    @BindView(R.id.singselect_list)
    ListView mListView;



    private Context mContext;

    private int posi;
    /**
     * 所有的数据集合
     */
    private ArrayList<TreeNode> allNodes = new ArrayList<TreeNode>();
    /**
     * 顶层元素结合
     */
    private ArrayList<TreeNode> topNodes = new ArrayList<TreeNode>();
    /**
     * 记录当前选中的值
     */
    private String currentSelectItemName;
    /**
     * 是否被点击了 如果被点击了 则此时可以点击确定按钮
     */
    private boolean isBeClick = false;
    List<String> labelList = new ArrayList<>();

    public RecordDialog(Context context, ArrayList<TreeNode> topNodes, ArrayList<TreeNode> allNodes) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.topNodes = topNodes;
        this.allNodes = allNodes;


        //this.mList=list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_record_selected);
        //点击外部区域不可取消
        setCanceledOnTouchOutside(true);
        initView();
        initListViewData();
    }

    /**
     * 加载列表数据
     */
    private void initListViewData() {
//        TreeAdapter treeViewAdapter = new TreeAdapter(mContext, topNodes, allNodes);
//        TreeItemClickListener treeViewItemClickListener = new TreeItemClickListener(treeViewAdapter);
//        mListView.setAdapter(treeViewAdapter);
//        mListView.setOnItemClickListener(treeViewItemClickListener);
    }

    private void initView() {
        ButterKnife.bind(this);
        dialogTitle.setText("巡检记录");
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        lp.y = 0;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);
    }

}