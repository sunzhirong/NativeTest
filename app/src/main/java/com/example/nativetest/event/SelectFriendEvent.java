package com.example.nativetest.event;

import com.example.nativetest.db.model.ProfileHeadInfo;
import com.example.nativetest.model.FriendInfo;

public class SelectFriendEvent {
    public FriendInfo bean ;
    public SelectFriendEvent(FriendInfo bean) {
        this.bean = bean;
    }
}
