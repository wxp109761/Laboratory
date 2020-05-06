package com.example.laboratory.inter;

import com.example.laboratory.bean.Items;
import com.example.laboratory.bean.Result;
import com.example.laboratory.bean.Xjresult;

import java.util.List;

public interface OnXJResultItemClickListener extends OnItemClickListener<Result> {

    public void getResults(List<Xjresult> results);
}
