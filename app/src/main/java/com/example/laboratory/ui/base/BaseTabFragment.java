package com.example.laboratory.ui.base;

import android.view.View;
import androidx.viewpager.widget.ViewPager;
import com.example.laboratory.R;
import com.example.laboratory.ui.core.presenter.BasePresenter;
import com.google.android.material.tabs.TabLayout;

/***
 * @author kdp
 * @date 2019/3/25 16:14
 * @description
 */
public abstract class BaseTabFragment<P extends BasePresenter> extends BasePresenterFragment<P> {
    protected TabLayout tabLayout;
    protected ViewPager viewPager;
    @Override
    protected int getLayoutId() {
        return R.layout.base_tab_layout;
    }

    @Override
    protected void initViews(View view) {
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
    }
}
