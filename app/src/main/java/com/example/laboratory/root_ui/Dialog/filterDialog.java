package com.example.laboratory.root_ui.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.laboratory.R;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.ui.adapter.ConstellationAdapter;

import java.util.List;

public class filterDialog extends Dialog {


    @BindView(R.id.constellation)
    GridView constellation;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    List<String> departName;
    List<String> category;
    ConstellationAdapter departAdapter;
    ConstellationAdapter categoryAdpater;
    @BindView(R.id.constellation2)
    GridView constellation2;
    @BindView(R.id.sure_btn)
    Button sureBtn;
    String activity_cate;
    String departSelect = "不限";
    String cateSelect = "不限";
    @BindView(R.id.depart_tag)
    TextView departTag;
    @BindView(R.id.lab_category_tag)
    TextView labCategoryTag;

    @OnClick({R.id.sure_btn, R.id.cancel_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sure_btn:
                if (listener != null) {
                    listener.getValue(departSelect, cateSelect);
                }
                dismiss();
                break;
            case R.id.cancel_btn:
                dismiss();
                break;
        }
    }

    public interface OnFilterSelectListener {
        public void getValue(String departSelect, String cateSelect);
    }

    OnFilterSelectListener listener;

    public filterDialog(OnFilterSelectListener listener, Context context, List<String> departName, List<String> category, String activity_cate) {
        super(context, R.style.DialogRight);
        this.departName = departName;
        this.listener = listener;
        this.category = category;
        this.activity_cate = activity_cate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_filter);
        //点击外部区域不可取消
        setCanceledOnTouchOutside(true);
        initView();

    }

    private void initView() {
        ButterKnife.bind(this);

        Window window = this.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.RIGHT;
        wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        if (activity_cate.equals("laboratory")) {
            if (UserInfoManager.getUserInfo().getPermission().equals("1")) {
                constellation.setVisibility(View.GONE);
                departTag.setVisibility(View.GONE);
            }
        } else if (activity_cate.equals("user")) {
            constellation2.setVisibility(View.GONE);
            labCategoryTag.setVisibility(View.GONE);
        } else if (activity_cate.equals("chexk_item")) {
            constellation2.setVisibility(View.GONE);
            labCategoryTag.setVisibility(View.GONE);
        }


        constellation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return MotionEvent.ACTION_MOVE == event.getAction() ? true
                        : false;
            }
        });


        constellation2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return MotionEvent.ACTION_MOVE == event.getAction() ? true
                        : false;
            }
        });
        departAdapter = new ConstellationAdapter(getContext(), departName);
        constellation.setAdapter(departAdapter);

        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                departAdapter.setCheckItem(position);
                System.out.println(departName.get(position));
                departSelect = departName.get(position);

            }
        });

        categoryAdpater = new ConstellationAdapter(getContext(), category);
        constellation2.setAdapter(categoryAdpater);

        constellation2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryAdpater.setCheckItem(position);
                cateSelect = category.get(position);
                System.out.println("jjj" + cateSelect);
            }
        });
    }


}