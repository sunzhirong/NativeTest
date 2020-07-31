package com.example.nativetest.event;

import com.example.nativetest.db.model.ProfileHeadInfo;
import com.example.nativetest.model.FollowBean;
import com.example.nativetest.model.FriendInfo;

import java.util.List;

public class SelectCompleteEvent {
    public List<FollowBean> list ;
    public SelectCompleteEvent(List<FollowBean> list) {
        this.list = list;
    }
}
