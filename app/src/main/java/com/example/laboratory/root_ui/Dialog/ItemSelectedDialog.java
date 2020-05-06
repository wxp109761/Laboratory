package com.example.laboratory.root_ui.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.laboratory.R;
import com.example.laboratory.bean.Items;
import com.example.laboratory.inter.OnProItemCheckListener;
import com.example.laboratory.root_ui.adapter.ItemCheckDialogAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Created by lzl on 2017/6/17.
 * @function: 自定义单选列表dialog
 * @description:
 */

public class ItemSelectedDialog extends Dialog {


    @BindView(R.id.checkbox_list)
    ListView mListView;
    @BindView(R.id.add_item_name)
    EditText addItemName;
    @BindView(R.id.add_item_btn)
    Button addItemBtn;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    @BindView(R.id.sure_btn)
    Button sureBtn;
    @BindView(R.id.is_common)
    CheckBox isCommon;
    private Context mContext;

    private List<Items.ItemListBean> mList;
    private SparseBooleanArray stateCheckedMap = new SparseBooleanArray();//用来存放CheckBox的选中状态，true为选中,false为没有选中
    private ItemCheckDialogAdapter itemCheckDialogAdapter;
    private List<Items.ItemListBean> mCheckedData = new ArrayList<>();//将选中数据放入里面

    private int posi;
    /**
     * 记录当前选中的值
     */
    private String currentSelectItemName;
    /**
     * 是否被点击了 如果被点击了 则此时可以点击确定按钮
     */
    private boolean isBeClick = false;

    public ItemSelectedDialog(Context context, List<Items.ItemListBean> list) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.mList = list;
    }

    OnProItemCheckListener listener = null;

    public void OnProItemCheckListener(OnProItemCheckListener listener) {
        this.listener = listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pro_item_selected);
        //点击外部区域不可取消
        setCanceledOnTouchOutside(false);
        initView();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            list.add(mList.get(i).getItemname());
        }
        itemCheckDialogAdapter = new ItemCheckDialogAdapter(mContext, list, stateCheckedMap);
        mListView.setAdapter(itemCheckDialogAdapter);
        setOnListViewItemClickListener();
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }


    private void initView() {
        ButterKnife.bind(this);
        mListView = (ListView) findViewById(R.id.checkbox_list);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        lp.y = 0;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);

    }

    @OnClick({R.id.add_item_btn, R.id.cancel_btn, R.id.sure_btn})
    public void onViewClicked(View view) {

        listener.onClick(view, addItemName.getText().toString(),isCommon.isChecked() );
        switch (view.getId()) {
            case R.id.add_item_btn:
                addItemName.setHint("item_name");

                break;
            case R.id.cancel_btn:
                dismiss();
                break;
            case R.id.sure_btn:
                if (listener != null) {
                    listener.getCheckData(mCheckedData);
                }
                dismiss();
                break;
        }
    }


    private void setOnListViewItemClickListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateCheckBoxStatus(view, position);
            }
        });
    }

    private void updateCheckBoxStatus(View view, int position) {
        ItemCheckDialogAdapter.ViewHolder holder = (ItemCheckDialogAdapter.ViewHolder) view.getTag();
        holder.checkBox.toggle();//反转CheckBox的选中状态

        mListView.setItemChecked(position, holder.checkBox.isChecked());//长按ListView时选中按的那一项
        stateCheckedMap.put(position, holder.checkBox.isChecked());//存放CheckBox的选中状态
        if (holder.checkBox.isChecked()) {
            mCheckedData.add(mList.get(position));//CheckBox选中时，把这一项的数据加到选中数据列表
        } else {
            mCheckedData.remove(mList.get(position));
            //CheckBox未选中时，把这一项的数据从选中数据列表移除
        }
        itemCheckDialogAdapter.notifyDataSetChanged();
    }


}