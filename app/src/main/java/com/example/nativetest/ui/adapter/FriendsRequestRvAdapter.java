package com.example.nativetest.ui.adapter;

import android.content.Context;
import android.view.View;

import com.example.nativetest.model.FollowRequestInfo;
import com.example.nativetest.ui.item.ItemFriendsRequest;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class FriendsRequestRvAdapter extends BaseRvAdapter<FollowRequestInfo> {
    public FriendsRequestRvAdapter(Context context, List<FollowRequestInfo> datas) {
        super(context, datas);
    }

    @Override
    protected View getItemView(int viewType) {
        return new ItemFriendsRequest(mContext);
    }

    @Override
    protected void bindData(RecyclerView.ViewHolder holder, int position) {
        ((ItemFriendsRequest) holder.itemView).bindData(mDatas.get(position),position);
    }
}
