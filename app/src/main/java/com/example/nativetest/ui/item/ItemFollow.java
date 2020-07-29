package com.example.nativetest.ui.item;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.nativetest.ProfileUtils;
import com.example.nativetest.R;
import com.example.nativetest.db.model.ProfileHeadInfo;
import com.example.nativetest.event.SelectAtEvent;
import com.example.nativetest.model.FollowBean;
import com.example.nativetest.model.FriendInfo;
import com.example.nativetest.ui.adapter.BaseItemView;
import com.example.nativetest.utils.glideutils.GlideImageLoaderUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.eventbus.EventBus;
import io.rong.imkit.widget.AsyncImageView;

public class ItemFollow extends BaseItemView {
    @BindView(R.id.rc_left)
    AsyncImageView mRcLeft;
    @BindView(R.id.tv_name)
    TextView mTvName;
    private ProfileHeadInfo bean;

    public ItemFollow(Context context) {
        super(context);
    }

    public ItemFollow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_follow;
    }


    public void bindData(ProfileHeadInfo bean) {
        this.bean = bean;
        mTvName.setText(bean.getName());
        mTvName.setTextColor(ProfileUtils.getNameColor(bean.getNameColor()));
        GlideImageLoaderUtil.loadCircleImage(mContext,mRcLeft,bean.getUserIcon());
    }

    @OnClick(R.id.ll_container)
    public void onViewClicked() {
        EventBus.getDefault().post(new SelectAtEvent(bean));
    }
}
