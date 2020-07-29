package com.example.nativetest.event;

import com.example.nativetest.db.model.ProfileHeadInfo;
import com.example.nativetest.model.FollowBean;
import com.example.nativetest.model.FriendInfo;

public class SelectAtEvent {
    public ProfileHeadInfo bean ;
    public SelectAtEvent(ProfileHeadInfo bean) {
        this.bean = bean;
    }
}
