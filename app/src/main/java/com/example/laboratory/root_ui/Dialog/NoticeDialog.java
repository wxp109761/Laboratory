package com.example.laboratory.root_ui.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.laboratory.R;
import com.example.laboratory.root_ui.AddLaboratory.AddLabActivity;
import com.example.laboratory.root_ui.notice.NoticeActivity;

import java.util.List;

public class NoticeDialog extends Dialog {

    @BindView(R.id.dialog_title)
    TextView dialogTitle;
    @BindView(R.id.singselect_list)
    ListView mListView;
    @BindView(R.id.single_select_cancel)
    Button cancelBtn;
    @BindView(R.id.single_select_sure_btn)
    Button sureBtn;


    private List<String> mList;
    private Context mContext;
    private int posi;
    /**
     * 记录当前选中的值
     */
    private String currentSelectItemName;
    /**
     * 是否被点击了 如果被点击了 则此时可以点击确定按钮
     */
    private boolean isBeClick = false;

    public NoticeDialog(Context context, List<String> list) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.xjlab_selected);
        //点击外部区域不可取消
        setCanceledOnTouchOutside(false);
        initView();
        initListViewData();
        initEvent();
    }

    /**
     * 加载列表数据
     */
    private void initListViewData() {
        mListView.setAdapter(new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, mList));
    }

    private void initView() {
        ButterKnife.bind(this);
        dialogTitle = findViewById(R.id.dialog_title);
        dialogTitle.setText("实验室");
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        lp.y = 0;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);

    }

    private void initEvent() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentSelectItemName != null && !currentSelectItemName.equals("")) {
                    if (posi == 0) {
                        Intent intent = new Intent(mContext, NoticeActivity.class);
                        mContext.startActivity(intent);
                    } else if (posi == 1) {
                        Intent intent = new Intent(mContext, AddLabActivity.class);
                        mContext.startActivity(intent);
                    }


                    dismiss();
                } else {
                    Toast.makeText(mContext, "您当前未选择任何项", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (mList != null) {
                    currentSelectItemName = mList.get(position);
                    posi = position;
                }
            }
        });
    }
}
