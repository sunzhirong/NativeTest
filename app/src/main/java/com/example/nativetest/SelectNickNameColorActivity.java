package com.example.nativetest;

import android.os.Bundle;

import com.example.nativetest.event.CitySelectEvent;
import com.example.nativetest.event.NicknameColorSelectEvent;
import com.example.nativetest.model.VIPConfigBean;
import com.example.nativetest.ui.activity.BaseActivity;
import com.example.nativetest.ui.adapter.NicknameRvAdapter;
import com.example.nativetest.utils.ToastUtils;
import com.example.nativetest.viewmodel.VipViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.eventbus.EventBus;

public class SelectNickNameColorActivity extends BaseActivity {
    @BindView(R.id.rv_nickname)
    RecyclerView mRvNickname;


    private VipViewModel mVipViewModel;
    private NicknameRvAdapter mNicknameRvAdapter;
    private List<VIPConfigBean> mRsData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_nickname_color;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mRvNickname.setLayoutManager(new LinearLayoutManager(mContext));
        mNicknameRvAdapter = new NicknameRvAdapter(mContext, new ArrayList<>());
        mRvNickname.setAdapter(mNicknameRvAdapter);

        initViewModel();
    }

    private void initViewModel() {
        mVipViewModel = ViewModelProviders.of(this).get(VipViewModel.class);
        mVipViewModel.getUpdateProfile().observe(this, profileInfoResult -> {
            if (profileInfoResult.RsCode == 3) {
                finish();
            }
        });


        mVipViewModel.getVipConfigResult().observe(this, result -> {
            if (result.RsCode == 3) {
                mRsData = result.RsData;
                mNicknameRvAdapter.setDatas(mRsData);

            }
        });

        mVipViewModel.vipConfigInfo();
    }

    public void onEventMainThread(NicknameColorSelectEvent event) {
        for (VIPConfigBean bean:mRsData){
            bean.setSelect(false);
        }
        mRsData.get(event.position).setSelect(event.selected);
        mNicknameRvAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
        VIPConfigBean selectBean = null;
        for (VIPConfigBean bean:mRsData){
            if(bean.isSelect()){
                selectBean = bean;
                break;
            }
        }
        if(selectBean==null){
            ToastUtils.showToast("请选择色号");
        }else {
            mVipViewModel.updateProfile(3,"NameColor",selectBean.getNameColor());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
