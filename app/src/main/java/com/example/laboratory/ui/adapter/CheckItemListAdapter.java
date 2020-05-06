package com.example.laboratory.ui.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.laboratory.R;
import com.example.laboratory.bean.Items;
import com.example.laboratory.bean.Result;
import com.example.laboratory.bean.Xjresult;
import com.example.laboratory.common.ListDataHolder;
import com.example.laboratory.inter.OnXJResultItemClickListener;
import com.example.laboratory.widget.LMRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CheckItemListAdapter extends BaseListAdapter<Items.ItemListBean> {


    @Override
    public void notifyAllDatas(List<Items.ItemListBean> mList, LMRecyclerView recyclerView) {
        super.notifyAllDatas(mList, recyclerView);
        results.clear();
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_cehck_list;
    }
    RadioButton radbtn;
    private  static List<Xjresult> results=new ArrayList<>();

    @Override
    public void bindDatas(final ListDataHolder holder, final Items.ItemListBean bean, int itemType, final int position) {

        TextView tv_question = holder.getView(R.id.tv_question);
        final EditText et_description = holder.getView(R.id.et_description);
        RadioGroup radioGroup = holder.getView(R.id.radio_group);
        RadioButton radioButton=radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        tv_question.setText(bean.getItemname());

        Xjresult xjresult=new Xjresult();
        xjresult.setItemid(bean.getItemid()+"");
        xjresult.setRid(UUID.randomUUID()+""+position);

        holder.itemView.setClickable(false);
        xjresult.setResullt(radioButton.getText().toString());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radbtn= (RadioButton) group.findViewById(checkedId);
                xjresult.setResullt(radbtn.getText().toString());

            }
        });

        et_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(et_description.getText().equals("描述")){
                    xjresult.setDescription("无");
                }else {
                    xjresult.setDescription(""+et_description.getText().toString());
                }

            }
        });
        results.add(xjresult);


    }
    public static List<Xjresult> getResut(){
        return results;
    }

}
