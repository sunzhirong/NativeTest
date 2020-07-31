package com.example.nativetest.ui.activity;

import android.os.Bundle;

import com.example.nativetest.R;
import com.example.nativetest.common.NetConstant;
import com.example.nativetest.event.AddFollowCompleteEvent;
import com.example.nativetest.event.CitySelectEvent;
import com.example.nativetest.event.FollowEvent;
import com.example.nativetest.model.FollowRequestInfo;
import com.example.nativetest.ui.adapter.FriendsRequestRvAdapter;
import com.example.nativetest.viewmodel.UserInfoViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.eventbus.EventBus;

public class FriendsRequestListActivity extends BaseActivity {
    @BindView(R.id.rv_request)
    RecyclerView mRvRequest;
    private UserInfoViewModel mUserInfoViewModel;
    private ArrayList<FollowRequestInfo> mBeans;
    private int pos;
    private FriendsRequestRvAdapter mFriendsRequestRvAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friends_request;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            mBeans = (ArrayList<FollowRequestInfo>) bundle.getSerializable("beans");
            if(mBeans !=null) {
                mRvRequest.setLayoutManager(new LinearLayoutManager(mContext));
                mFriendsRequestRvAdapter = new FriendsRequestRvAdapter(mContext, mBeans);
                mRvRequest.setAdapter(mFriendsRequestRvAdapter);
            }
        }

        initViewModel();
    }

    private void initViewModel() {
        mUserInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        mUserInfoViewModel.getAddFollowingsResult().observe(this,result->{
            if(result.RsCode==NetConstant.REQUEST_SUCCESS_CODE) {
                EventBus.getDefault().post(new AddFollowCompleteEvent(mBeans.get(pos).getUID()));
                mBeans.remove(pos);
                mFriendsRequestRvAdapter.notifyDataSetChanged();
            }
        });
    }

    public void onEventMainThread(FollowEvent event) {
        pos  = event.position;
        mUserInfoViewModel.addFollowings(mBeans.get(event.position).getUID());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
