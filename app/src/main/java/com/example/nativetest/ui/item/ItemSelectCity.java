package com.example.nativetest.ui.item;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.nativetest.AreaBean;
import com.example.nativetest.R;
import com.example.nativetest.ui.adapter.BaseItemView;

import butterknife.BindView;

public class ItemSelectCity extends BaseItemView {


    @BindView(R.id.tv_city)
    TextView mTvCity;

    public ItemSelectCity(Context context) {
        super(context);
    }

    public ItemSelectCity(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_select_city;
    }


    public void bindData(AreaBean areaBean) {
        mTvCity.setText(areaBean.getState_name());
    }

    public void bindData(AreaBean.ProvinceBean areaBean) {
        mTvCity.setText(areaBean.getProvince_name());
    }

    public void bindData(AreaBean.ProvinceBean.CityBean areaBean) {
        mTvCity.setText(areaBean.getCity_name());
    }
}
