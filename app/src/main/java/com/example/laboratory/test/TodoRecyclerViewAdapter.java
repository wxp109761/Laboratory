package com.example.laboratory.test;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.laboratory.R;
import com.example.laboratory.bean.Remind;
import com.example.laboratory.common.ListDataHolder;
import com.example.laboratory.ui.adapter.BaseListAdapter;
import com.example.laboratory.utils.DateUtils;
import com.github.vipulasri.timelineview.TimelineView;
import me.drakeet.materialdialog.MaterialDialog;

import java.util.Collections;
import java.util.List;


public class TodoRecyclerViewAdapter extends BaseListAdapter<Remind.RemindListBean> implements ItemTouchHelperAdapter{

    @BindView(R.id.time_marker)
    TimelineView timeMarker;
    @BindView(R.id.card_bg)
    ImageView cardBg;
    @BindView(R.id.todo_date)
    AppCompatTextView todoDate;
    @BindView(R.id.todo_title)
    AppCompatTextView todoTitle;
    @BindView(R.id.todo_desc)
    AppCompatTextView todoDesc;
    @BindView(R.id.isRepeat)
    AppCompatTextView isRepeat;



    private List<Remind.RemindListBean> todosList;
    private Context context;
    private MaterialDialog dialog;
    private int truePosition, itemPosition;

    OnDeleteRemindListener listener;

    interface OnDeleteRemindListener {

        void onDeleteRemindListener(Integer id);
    }

    public TodoRecyclerViewAdapter(OnDeleteRemindListener listener,Context context ,List<Remind.RemindListBean> remindList) {
        this.listener = listener;
        this.todosList=remindList;
        this.context=context;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_todo;
    }


    @Override
    public void bindDatas(ListDataHolder holder, Remind.RemindListBean bean, int itemType, int position) {
        ButterKnife.bind(this, holder.itemView);
        todoTitle.setText(bean.getTitle());
        todoDesc.setText(bean.getContent());
        todoDate.setText(DateUtils.parseTime(bean.getRemindTime()));
//        cardBg.setImageBitmap(BitmapUtils.readBitMap(holder.itemView.getContext(),bean.getImgId()));


        isRepeat.setText("单次");
        isRepeat.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        if (bean.getRemindTime() <= System.currentTimeMillis()) {
            timeMarker.setMarker(holder.itemView.getResources().getDrawable(R.drawable.ic_marker));
        } else {
            timeMarker.setMarker(holder.itemView.getResources().getDrawable(R.drawable.round));
        }


    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(todosList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        notifyItemRangeChanged(fromPosition,toPosition);
        return true;
    }

    public void removeItem(int position) {

        truePosition = todosList.size()-1-position;
        itemPosition = position;
        popAlertDialog();
    }

    private void popAlertDialog() {

        if (dialog == null) {

            dialog = new MaterialDialog(context);
            dialog.setMessage("确定删除？")
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Remind.RemindListBean remindListBean = todosList.get(truePosition);
                            //删除
                            if (listener != null) {
                                listener.onDeleteRemindListener(todosList.get(truePosition).getId());
                            }
                            todosList.remove(truePosition);
                            notifyItemRemoved(itemPosition);
                            notifyItemRangeChanged(itemPosition,todosList.size());
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消", new View.OnClickListener() {
                        public void onClick(View view) {
                            notifyItemChanged(itemPosition);
                            dialog.dismiss();
                        }
                    });

        }

        dialog.show();

    }


}