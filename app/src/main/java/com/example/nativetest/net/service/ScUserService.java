package com.example.nativetest.net.service;

import com.example.nativetest.common.NetConstant;
import com.example.nativetest.db.model.ProfileHeadInfo;
import com.example.nativetest.db.model.ProfileInfo;
import com.example.nativetest.model.CommentBean;
import com.example.nativetest.model.FollowBean;
import com.example.nativetest.model.FollowRequestInfo;
import com.example.nativetest.model.FriendInfo;
import com.example.nativetest.model.VIPCheckBean;
import com.example.nativetest.model.VIPConfigBean;
import com.example.nativetest.model.sc.NetResponse;
import com.example.nativetest.model.Result;
import com.example.nativetest.model.sc.UserInfo;
import com.example.nativetest.net.ScUrl;

import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ScUserService {
    @POST("user/login")
    LiveData<Result<String>> login(@Body RequestBody body);




    @POST(ScUrl.USER_GET_SMS)
    @Headers(NetConstant.JSON)
    LiveData<Result> getSms(@Body RequestBody body);

    @POST(ScUrl.USER_VERIFY_CODE)
    @Headers(NetConstant.JSON)
    LiveData<Result> verifyCode(@Body RequestBody body);


    @POST(ScUrl.PROFILE_GET)
    @Headers(NetConstant.JSON)
    LiveData<Result<ProfileInfo>> getProfileInfo();

    @POST(ScUrl.PROFILE_UPDATE)
    @Headers(NetConstant.JSON)
    LiveData<Result<Boolean>> updateProfileInfo(@Body RequestBody body);


    @POST(ScUrl.HAS_SET_PASSWORD)
    @Headers(NetConstant.JSON)
    LiveData<Result<Boolean>> hasSetPassword();


    @POST(ScUrl.LOG_OUT)
    @Headers(NetConstant.JSON)
    LiveData<Result<Boolean>> logout();


    @Multipart
    @POST(ScUrl.UPLOAD_AVATAR)
//    LiveData<Result<String>> uploadAvatar(@PartMap() Map<String, RequestBody> maps);
    LiveData<Result<String>> uploadAvatar(@Part MultipartBody.Part partList);



    @POST(ScUrl.COMMENTS_LIST)
    @Headers(NetConstant.JSON)
    LiveData<Result<List<CommentBean>>> getCommentList(@Body RequestBody body);

    @POST(ScUrl.FOLLOWING_LIST)
    @Headers(NetConstant.JSON)
    LiveData<Result<List<FollowBean>>> getFollowList(@Body RequestBody body);

    @POST(ScUrl.FOLLOWERS_LIST)
    @Headers(NetConstant.JSON)
    LiveData<Result<List<FriendInfo>>> getFollowerList(@Body RequestBody body);


    @POST(ScUrl.FOLLOWERS_REQUEST_LIST)
    @Headers(NetConstant.JSON)
    LiveData<Result<List<FollowRequestInfo>>> getFollowerRequestList(@Body RequestBody body);


    @POST(ScUrl.FOLLOWINGS_ADD)
    @Headers(NetConstant.JSON)
    LiveData<Result<Boolean>> addFollowings(@Body RequestBody body);

    @POST(ScUrl.FOLLOWINGS_REMOVE)
    @Headers(NetConstant.JSON)
    LiveData<Result<Boolean>> removeFollowings(@Body RequestBody body);


    @POST(ScUrl.CMT_ADD)
    @Headers(NetConstant.JSON)
    LiveData<Result<Integer>> cmtAdd(@Body RequestBody body);


    @POST(ScUrl.VIP_CHECK)
    @Headers(NetConstant.JSON)
    LiveData<Result<VIPCheckBean>> vipCheck();


    @POST(ScUrl.VIP_INFO)
    @Headers(NetConstant.JSON)
    LiveData<Result<List<VIPConfigBean>>> vipInfo();


    @POST(ScUrl.GET_FRIEND_LIST)
    @Headers(NetConstant.JSON)
    LiveData<Result<List<FriendInfo>>> getFriendList(@Body RequestBody body);


}
