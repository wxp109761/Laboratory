package com.example.laboratory.ui.core.model;

import com.example.laboratory.bean.*;
import com.example.laboratory.net.callback.RxObserver;
import com.example.laboratory.net.callback.RxPageListObserver;

import java.util.List;

public interface ISeCheckModel {


    /**
     * 获得该实验室需要检查的内容
     * @param labId
     */
    void getItemsByLabId(String labId, RxObserver<Items> rxObserver);


    /**
     * 添加巡检结果
     */
    void addRecordsResult(List<Xjresult> results, RxObserver<String> callback);
    /**
     * 添加巡检记录
     */
    void addRecords(Xjrecord xjrecord, RxObserver<String> callback);


}
