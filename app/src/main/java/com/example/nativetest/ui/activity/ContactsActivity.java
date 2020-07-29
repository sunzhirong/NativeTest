package com.example.nativetest.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.nativetest.R;
import com.example.nativetest.common.NetConstant;
import com.example.nativetest.db.model.ProfileHeadInfo;
import com.example.nativetest.event.AddFollowCompleteEvent;
import com.example.nativetest.event.FollowEvent;
import com.example.nativetest.model.FollowRequestInfo;
import com.example.nativetest.ui.adapter.MembersAdapter;
import com.example.nativetest.utils.log.SLog;
import com.example.nativetest.viewmodel.UserInfoViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.eventbus.EventBus;
import io.rong.imkit.mention.SideBar;
import io.rong.imkit.tools.CharacterParser;
import io.rong.imkit.widget.AsyncImageView;
import io.rong.imlib.model.Conversation;

public class ContactsActivity extends BaseActivity implements MembersAdapter.OnDeleteClickListener {

    @BindView(R.id.tv_count)
    TextView mTvCount;
    private UserInfoViewModel mUserInfoViewModel;

    private int requestCount;
    private ArrayList<FollowRequestInfo> mRsData;
    private int deletePos;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        EditText searchBar = (EditText) findViewById(R.id.rc_edit_text);
        mListView = (ListView) findViewById(R.id.rc_list);
        SideBar mSideBar = (SideBar) findViewById(R.id.rc_sidebar);
        TextView letterPopup = (TextView) findViewById(R.id.rc_popup_bg);
        mSideBar.setTextView(letterPopup);


        mAdapter = new MembersAdapter();
        mListView.setAdapter(mAdapter);
        mAllMemberList = new ArrayList<>();

        mTargetId = getIntent().getStringExtra("targetId");
        mConversationType = (Conversation.ConversationType) getIntent().getSerializableExtra("conversationType");

        mAdapter.setOnDeleteClickListener(this);

        initViewModel();


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent();
//                intent.putExtra("contact", mAdapter.getItem(position).userInfo);
//                setResult(Activity.RESULT_OK, intent);
//                finish();
            }
        });

        //设置右侧触摸监听
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mListView.setSelection(position);
                }
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                List<MembersAdapter.MemberInfo> filterDataList = new ArrayList<>();

                if (TextUtils.isEmpty(s.toString())) {
                    filterDataList = mAllMemberList;
                } else {
                    filterDataList.clear();
                    for (MembersAdapter.MemberInfo member : mAllMemberList) {
                        String name = member.userInfo.getName();
                        if (name != null) {
                            if (name.contains(s) || CharacterParser.getInstance().getSelling(name).startsWith(s.toString())) {
                                filterDataList.add(member);
                            }
                        }
                    }
                }
                // 根据a-z进行排序
                Collections.sort(filterDataList, MembersAdapter.PinyinComparator.getInstance());
                mAdapter.setData(filterDataList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initViewModel() {
        mUserInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        mUserInfoViewModel.getFollowerListResult().observe(this, result -> {
            if (result.RsCode == NetConstant.REQUEST_SUCCESS_CODE) {
                SLog.e("UserInfoViewModel","刷新联系人");
                mAllMemberList.clear();
                List<ProfileHeadInfo> rsData = result.getRsData();
//                mFollowRvAdapter.setDatas(rsData);

                for (int i = 0; i < rsData.size(); i++) {
                    ProfileHeadInfo profileHeadInfo = rsData.get(i);
                    MembersAdapter.MemberInfo memberInfo = new MembersAdapter.MemberInfo(profileHeadInfo);
                    String sortString = "#";
                    //汉字转换成拼音
                    String pinyin = CharacterParser.getInstance().getSelling(profileHeadInfo.getName());

                    if (pinyin != null) {
                        if (pinyin.length() > 0) {
                            sortString = pinyin.substring(0, 1).toUpperCase();
                        }
                    }
                    // 正则表达式，判断首字母是否是英文字母
                    if (sortString.matches("[A-Z]")) {
                        memberInfo.setLetter(sortString.toUpperCase());
                    } else {
                        memberInfo.setLetter("#");
                    }
                    mAllMemberList.add(memberInfo);
                }
                Collections.sort(mAllMemberList, MembersAdapter.PinyinComparator.getInstance());
                mAdapter.setData(mAllMemberList);
                mAdapter.notifyDataSetChanged();
            }
        });

        mUserInfoViewModel.getFollowerRequestListResult().observe(this, result -> {
            if (result.RsCode == NetConstant.REQUEST_SUCCESS_CODE) {
                mRsData = (ArrayList<FollowRequestInfo>) result.getRsData();
                for (FollowRequestInfo requestInfo : mRsData) {
                    if (!requestInfo.isIsFriend()) {
                        requestCount++;
                    }
                }
            }
            refreshDot();
        });

        mUserInfoViewModel.getRemoveFollowingsResult().observe(this,result->{
            if (result.RsCode == NetConstant.REQUEST_SUCCESS_CODE) {
                mAllMemberList.remove(deletePos);
               mAdapter.notifyDataSetChanged();
            }
        });

        mUserInfoViewModel.getFollowerRequestList(NetConstant.SKIP, NetConstant.TAKE);

        mUserInfoViewModel.getFollowerList(NetConstant.SKIP, NetConstant.TAKE);
    }

    private void refreshDot() {
        if (requestCount > 0) {
            mTvCount.setVisibility(View.VISIBLE);
            mTvCount.setText(String.valueOf(requestCount));
        }else {
            mTvCount.setVisibility(View.GONE);
        }
    }

    public void onEventMainThread(AddFollowCompleteEvent event) {
        mUserInfoViewModel.getFollowerList(NetConstant.SKIP, NetConstant.TAKE);
        requestCount--;
        refreshDot();
    }

    private ListView mListView;
    private List<MembersAdapter.MemberInfo> mAllMemberList;
    private MembersAdapter mAdapter;

    private Conversation.ConversationType mConversationType;
    private String mTargetId;


    @OnClick(R.id.ll_focus)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("beans",mRsData);
        readyGo(FriendsRequestListActivity.class,bundle);
    }


    @Override
    public void onDelete(int position) {
        this.deletePos = position;
        mUserInfoViewModel.removeFollowings(mRsData.get(position).getUID());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
