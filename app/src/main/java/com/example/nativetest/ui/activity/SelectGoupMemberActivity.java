package com.example.nativetest.ui.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nativetest.R;
import com.example.nativetest.common.NetConstant;
import com.example.nativetest.db.model.ProfileHeadInfo;
import com.example.nativetest.event.SelectFriendEvent;
import com.example.nativetest.model.FriendInfo;
import com.example.nativetest.model.GroupDataReq;
import com.example.nativetest.ui.adapter.FollowRvAdapter;
import com.example.nativetest.ui.adapter.FriendRvAdapter;
import com.example.nativetest.viewmodel.UserInfoViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class SelectGoupMemberActivity extends BaseActivity {
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_complete)
    TextView mTvComplete;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.rv_group)
    RecyclerView mRvGroup;
    private UserInfoViewModel mUserInfoViewModel;
    private FriendRvAdapter mFriendRvAdapter;
    private List<FriendInfo> mRsData;
    private List<FriendInfo> mList;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_group_member;
    }

    @Override
    protected void initView() {
        mList = new ArrayList<>();

        mRvGroup.setLayoutManager(new LinearLayoutManager(mContext));
        mFriendRvAdapter = new FriendRvAdapter(mContext, new ArrayList<>());
        mRvGroup.setAdapter(mFriendRvAdapter);

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString().trim();
                if(TextUtils.isEmpty(content)){
                    mFriendRvAdapter.setDatas(mRsData);
                }else {
                    List<FriendInfo> infos = new ArrayList<>();
                    for(FriendInfo info :mRsData){
                        if(info.getName().contains(content)){
                            infos.add(info);
                        }
                    }
                    mFriendRvAdapter.setDatas(infos);
                }
            }
        });
        initViewModel();
    }

    private void initViewModel() {
        mUserInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        mUserInfoViewModel.getCreateGroupResult().observe(this, result -> {
            if (result.RsCode == NetConstant.REQUEST_SUCCESS_CODE) {

                finish();
            }
        });

        mUserInfoViewModel.getFriendListResult().observe(this,result->{
            if (result.RsCode == NetConstant.REQUEST_SUCCESS_CODE) {
                mRsData = result.RsData;
                mFriendRvAdapter.setDatas(mRsData);
            }
        });
        mUserInfoViewModel.getFriendList(NetConstant.SKIP,NetConstant.TAKE);

    }

    @OnClick({R.id.tv_cancel, R.id.tv_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_complete:
                if(mList.size()!=0) {
                    createGroup();
                }
                break;
        }
    }

    private void createGroup() {
        GroupDataReq groupDataReq = new GroupDataReq();
        groupDataReq.setChatGrpID(0);
        groupDataReq.setTitle("Niko 测试群聊");
        List<Integer> integers = new ArrayList<>();
        for(FriendInfo info : mList){
            integers.add(info.getUID());
        }
        groupDataReq.setUIDs(integers);
        mUserInfoViewModel.createGroup(groupDataReq);
    }


    public void onEventMainThread(SelectFriendEvent event) {
        if(!mList.contains(event.bean)) {
            mList.add(event.bean);
            mTvComplete.setText("创建" + mList.size());
        }else {
            mList.remove(event.bean);
            if(mList.size()==0){
                mTvComplete.setText("创建");
            }else {
                mTvComplete.setText("创建" + mList.size());
            }
        }
    }
}
