package com.example.laboratory.bean;

import java.util.List;

public class HomeData {
    private BaseBean<List<Banner>> banner;

    public BaseBean<List<Banner>> getBanner() {
        return banner;
    }

    public void setBanner(BaseBean<List<Banner>> banner) {
        this.banner = banner;
    }

}
