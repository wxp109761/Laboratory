package com.example.laboratory.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.laboratory.R;
import com.example.laboratory.bean.User;
import com.example.laboratory.common.CommonUtils;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.utils.DateUtils;


public class StatisticFragment extends Fragment {

    @BindView(R.id.job_number)
    TextView jobNumber;
    @BindView(R.id.depart_name)
    TextView departName;
    @BindView(R.id.telphone)
    TextView telphone;
    @BindView(R.id.register_data)
    TextView registerData;
    @BindView(R.id.update_date)
    TextView updateDate;

    public StatisticFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        ButterKnife.bind(this,view);
        User user=  UserInfoManager.getUserInfo();
        if (user!=null) {
            jobNumber.setText(user.getJobNumber());
            departName.setText(CommonUtils.getDepartName(user.getDepartId()));
            telphone.setText(user.getTel());
            registerData.setText(DateUtils.parseTime(user.getGmt_create()));
            updateDate.setText(DateUtils.parseTime(user.getGmt_update()));
        }


        return view;
    }

    public void setData() {


        new Thread(new Runnable() {
            //开启一个线程处理逻辑，然后在线程中在开启一个UI线程，当子线程中的逻辑完成之后，
            //就会执行UI线程中的操作，将结果反馈到UI界面。
            @Override
            public void run() {
                // 模拟耗时的操作，在子线程中进行。
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 更新主线程ＵＩ，跑在主线程。
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });


            }
        });
    }
}
