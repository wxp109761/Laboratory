package com.example.laboratory.common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.core.app.ActivityCompat;
import com.example.laboratory.manager.UserInfoManager;

import java.util.List;

public class CommonUtils {
    public static String getDepartId(String departName){
        String departId = "";
        switch(departName){
            case "公共项目":
                departId="0";
                break;
            case "计算机学院":
                departId="1070";
                break;
            case "深蓝学院":
                departId="1010";
                break;
            case "化工学院":
                departId="1020";
                break;
            case "环境学院":
                departId="1030";
                break;
            case "生物学院":
                departId="1040";
                break;
            case "人文社科学院":
                departId="1050";
                break;
            case "数理学院":
                departId="1060";
                break;

        }

        return departId;
    }
    public static String getDepartName(String departId){
        String departName = "";
        switch(departId){
            case "1070":
                departName="计算机学院";
                break;
            case "1010":
                departName="深蓝学院";
                break;
            case "1020":
                departName="化工学院";
                break;
            case "1030":
                departName="环境学院";
                break;
            case "1040":
                departName="生物学院";
                break;
            case "1050":
                departName="人文社科学院";
                break;
            case "1060":
                departName="数理学院";
                break;
            case "0":
                departName="公共项目";
                break;


        }
        return departName;
    }
    public static String userRole(){
        String role="";
        switch (UserInfoManager.getUserInfo().getPermission()){
            case "0":
                role="校管理员";
                break;
            case "1":
                role="学院负责人";
                break;
            case "2":
                role="实验室安全员";
                break;
        }
        return role;
    }
    public static int getRoleNumber(String role){
      int roleNumber=0;
        switch (role){
            case "校管理员":
                roleNumber=0;
                break;
            case "学院负责人":
                roleNumber=1;
                break;
            case "实验室安全员":
                roleNumber=2;
                break;
        }
        return roleNumber;
    }


    private static OnItemClickListener onItemClickListener;
    public static interface OnItemClickListener<E> {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
    public static String Sprinner(Context context,List<String> spinnewListData, Spinner spinner){
        final String[] check = {""};
        ArrayAdapter<String> spinner_adapter;
        spinner_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spinnewListData);
        spinner.setAdapter(spinner_adapter);




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
              check[0] = parent.getItemAtPosition(position).toString();
                Log.d("xx",parent.getItemAtPosition(position).toString());
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(parent, position);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        return check[0];
    }



    //权限请求
    public static void getPer(Context context, Activity activity)
    {
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED)
        {
            // 请求权限
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
//

    }
}
