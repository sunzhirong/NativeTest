package com.example.nativetest.viewmodel;

import android.app.Application;
import android.net.Uri;

import com.example.nativetest.db.model.ProfileHeadInfo;
import com.example.nativetest.db.model.ProfileInfo;
import com.example.nativetest.model.CommentAtReq;
import com.example.nativetest.model.CommentBean;
import com.example.nativetest.model.FollowBean;
import com.example.nativetest.model.FollowRequestInfo;
import com.example.nativetest.model.FriendInfo;
import com.example.nativetest.model.GroupDataReq;
import com.example.nativetest.model.Resource;
import com.example.nativetest.model.Result;
import com.example.nativetest.sp.ProfileCache;
import com.example.nativetest.task.UserTask;
import com.example.nativetest.utils.SingleSourceLiveData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class UserInfoViewModel extends AndroidViewModel {

    private UserTask userTask;
    private ProfileCache profileCache;

    private SingleSourceLiveData<Resource<ProfileInfo>> profileResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<Boolean>> updateProfileResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<Boolean>> hasSetPasswordResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Resource<Boolean>> logoutResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Resource<String>> uploadResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<List<CommentBean>>> commentResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<List<FollowBean>>> followResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<List<FriendInfo>>> followerListResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<List<FollowRequestInfo>>> getFollowerRequestListResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<Boolean>> addFollowingsResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<Boolean>> removeFollowingsResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<Integer>> cmtAddResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<Integer>> createGroupResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<List<FriendInfo>>> getFriendListResult =  new SingleSourceLiveData<>();




    public UserInfoViewModel(@NonNull Application application) {
        super(application);
        userTask = new UserTask(application);
        profileCache = new ProfileCache(application);
    }

    public ProfileCache getProfileCache(){
        return profileCache;
    }

    public SingleSourceLiveData<Resource<ProfileInfo>> getProfileResult() {
        return profileResult;
    }

    public void getProfile() {
        profileResult.setSource(userTask.getProfile());
    }


    public SingleSourceLiveData<Result<Boolean>> getUpdateProfile() {
        return updateProfileResult;
    }

    public void updateProfile(int type, String key, Object value) {
        updateProfileResult.setSource(userTask.updateProfile(type, key, value));
    }

    public SingleSourceLiveData<Result<Boolean>> getHasSetPasswordResult() {
        return hasSetPasswordResult;
    }

    public void hasSetPassword() {
        hasSetPasswordResult.setSource(userTask.hasSetPassword());
    }

    public SingleSourceLiveData<Resource<Boolean>> getLogoutResult() {
        return logoutResult;
    }

    public void logout() {
        logoutResult.setSource(userTask.logout());
    }



    public void uploadAvatar(Uri uri ){
        uploadResult.setSource(userTask.upload(uri));
    }

    public SingleSourceLiveData<Resource<String>> getUploadResult(){
        return uploadResult;
    }

    public void getCommentList(int skip,int take){
        commentResult.setSource(userTask.getCommentList(skip,take));
    }

    public SingleSourceLiveData<Result<List<CommentBean>>> getCommentListResult() {
        return commentResult;
    }

    public void getFollowList(int skip,int take){
        followResult.setSource(userTask.getFollowList(skip,take));
    }

    public SingleSourceLiveData<Result<List<FollowBean>>> getFollowListResult() {
        return followResult;
    }


    public void getFollowerList(int skip,int take){
        followerListResult.setSource(userTask.getFollowerList(skip,take));
    }

    public SingleSourceLiveData<Result<List<FriendInfo>>> getFollowerListResult() {
        return followerListResult;
    }

    public void getFollowerRequestList(int skip,int take){
        getFollowerRequestListResult.setSource(userTask.getFollowerRequestList(skip,take));
    }

    public SingleSourceLiveData<Result<List<FollowRequestInfo>>> getFollowerRequestListResult() {
        return getFollowerRequestListResult;
    }

    public void addFollowings(int uid){
        addFollowingsResult.setSource(userTask.addFollowings(uid));
    }

    public SingleSourceLiveData<Result<Boolean>> getAddFollowingsResult() {
        return addFollowingsResult;
    }

    public void removeFollowings(int uid){
        removeFollowingsResult.setSource(userTask.removeFollowings(uid));
    }

    public SingleSourceLiveData<Result<Boolean>> getRemoveFollowingsResult() {
        return removeFollowingsResult;
    }

    public void cmtAdd(CommentAtReq data){
        cmtAddResult.setSource(userTask.cmtAdd(data));
    }

    public SingleSourceLiveData<Result<Integer>> getCmtAddResult() {
        return cmtAddResult;
    }


    public void createGroup(GroupDataReq data){
        createGroupResult.setSource(userTask.createGroup(data));
    }

    public SingleSourceLiveData<Result<Integer>> getCreateGroupResult() {
        return createGroupResult;
    }



    public void getFriendList(int skip,int take){
        getFriendListResult.setSource(userTask.getFriendList(skip,take));
    }

    public SingleSourceLiveData<Result<List<FriendInfo>>> getFriendListResult() {
        return getFriendListResult;
    }








}
