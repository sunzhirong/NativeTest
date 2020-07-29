package com.example.nativetest.ui.adapter;

import android.content.Context;
import android.view.View;

import com.example.nativetest.db.model.ProfileHeadInfo;
import com.example.nativetest.model.FriendInfo;
import com.example.nativetest.ui.item.ItemFollow;
import com.example.nativetest.ui.item.ItemFriend;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class FriendRvAdapter extends BaseRvAdapter<FriendInfo> {

    public FriendRvAdapter(Context context, List<FriendInfo> datas) {
        super(context, datas);
    }

    @Override
    protected View getItemView(int viewType) {
        return new ItemFriend(mContext);
    }

    @Override
    protected void bindData(RecyclerView.ViewHolder holder, int position) {
        ((ItemFriend) holder.itemView).bindData(mDatas.get(position));
    }
}
