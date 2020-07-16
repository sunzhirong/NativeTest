package com.example.nativetest.task;

import android.content.Context;

import com.example.nativetest.ProfileUtils;
import com.example.nativetest.db.model.ProfileInfo;
import com.example.nativetest.model.Resource;
import com.example.nativetest.model.Result;
import com.example.nativetest.model.sc.NetResponse;
import com.example.nativetest.model.sc.TokenBean;
import com.example.nativetest.model.sc.UserInfo;
import com.example.nativetest.net.HttpClientManager;
import com.example.nativetest.net.NetworkOnlyResource;
import com.example.nativetest.net.RetrofitUtil;
import com.example.nativetest.net.ScInterceptor;
import com.example.nativetest.net.service.ScUserService;
import com.example.nativetest.net.service.TokenService;
import com.example.nativetest.net.token.TokenHttpClientManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import okhttp3.RequestBody;

public class UserTask {
//    private FileManager fileManager;
    private ScUserService scUserService;
    private TokenService tokenService;
    private Context context;

    public UserTask(Context context) {
        this.context = context.getApplicationContext();
        scUserService = HttpClientManager.getInstance(context).getClient().createService(ScUserService.class);
        tokenService = TokenHttpClientManager.getInstance(context).getClient().createService(TokenService.class);
    }


    public LiveData<Resource<UserInfo>> getUserInfo(){
        return new NetworkOnlyResource<UserInfo, NetResponse<UserInfo>>() {
            @NonNull
            @Override
            protected LiveData<NetResponse<UserInfo>> createCall() {
                HashMap<String, Object> paramsMap = new HashMap<>();
//                paramsMap.put("phone", "13305938755");
//                paramsMap.put("password", "niko9999");
                RequestBody body = RetrofitUtil.createJsonRequest(paramsMap);
                return scUserService.getUserInfo(body);
            }
        }.asLiveData();
    }


    /**
     * 用户登录
     *
     * @param region   国家区号
     * @param phone    手机号码
     * @param password 密码
     */
    public LiveData<Resource<String>> login(String region, String phone, String password) {
//        MediatorLiveData<Resource<String>> result = new MediatorLiveData<>();
//        result.setValue(Resource.loading(null));

        LiveData<Resource<String>> login = new NetworkOnlyResource<String, Result<String>>() {
            @NonNull
            @Override
            protected LiveData<Result<String>> createCall() {
                HashMap<String, Object> paramsMap = new HashMap<>();
//                paramsMap.put("phone", "13305938755");
//                paramsMap.put("password", "niko9999");
                RequestBody body = RetrofitUtil.createJsonRequest(paramsMap);
                return scUserService.login(body);
            }
        }.asLiveData();
        return login;

//        result.addSource(login, loginResultResource -> {
//            if (loginResultResource.status == Status.SUCCESS) {
//                result.removeSource(login);
//
//                LoginResult loginResult = loginResultResource.data;
//                if (loginResult != null) {
//                    imManager.connectIM(loginResult.token, true, new ResultCallback<String>() {
//                        @Override
//                        public void onSuccess(String s) {
//                            result.postValue(Resource.success(s));
//                            // 存储当前登录成功的用户信息
//                            UserCacheInfo info = new UserCacheInfo(s, loginResult.token, phone, password, region, countryCache.getCountryInfoByRegion(region));
//                            userCache.saveUserCache(info);
//                        }
//
//                        @Override
//                        public void onFail(int errorCode) {
//                            result.postValue(Resource.error(errorCode, null));
//                        }
//                    });
//                } else {
//                    result.setValue(Resource.error(ErrorCode.API_ERR_OTHER.getCode(), null));
//                }
//            } else if (loginResultResource.status == Status.ERROR) {
//                result.setValue(Resource.error(loginResultResource.code, null));
//            } else {
//                // do nothing
//            }
//        });
//        return result;
    }


    public LiveData<TokenBean> getAccessToken(){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("grant_type", "client_credentials");
        paramsMap.put("scope", "jjApiScope");
        Map<String, RequestBody> stringRequestBodyMap = RetrofitUtil.generateRequestBody(paramsMap);
        return tokenService.connectToken(stringRequestBodyMap);
    }

    public LiveData<List<Result>> getSms(){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("PhoneNumber", "13305938755");
        paramsMap.put("PhoneCountry", "86");
        RequestBody body = RetrofitUtil.getRequestBody(paramsMap);
        return scUserService.getSms(body);
    }

    public LiveData<List<Result>> smsVerify(){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("PhoneNumber", "13305938755");
        paramsMap.put("PhoneCountry", "86");
        paramsMap.put("VCode", "9999");
        RequestBody body = RetrofitUtil.getRequestBody(paramsMap);
        return scUserService.verifyCode(body);
    }

    public LiveData<TokenBean> getUserToken(){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("grant_type", "password");
        paramsMap.put("scope", "jjApiScope");

        paramsMap.put("UserName", "13305938755");
        paramsMap.put("Password", ScInterceptor.getDV()+"9999");
        paramsMap.put("VCode", "9999");

        Map<String, RequestBody> stringRequestBodyMap = RetrofitUtil.generateRequestBody(paramsMap);
        return tokenService.connectToken(stringRequestBodyMap);
    }


   /* public LiveData<Resource<List<Result<ProfileInfo>>>> getProfile(){
        MediatorLiveData<Resource<List<Result<ProfileInfo>>>> result = new MediatorLiveData<>();
        result.setValue(Resource.loading(null));
        LiveData<Resource<List<Result<ProfileInfo>>>> profile = new NetworkOnlyResource<List<Result<ProfileInfo>>, List<Result<ProfileInfo>>>() {
            @NonNull
            @Override
            protected LiveData<List<Result<ProfileInfo>>> createCall() {
                return scUserService.getProfileInfo();
            }
        }.asLiveData();
        try{
            ProfileInfo rsData = profile.getValue().data.get(0).RsData;
            ProfileUtils.sProfileInfo = rsData;
            SLog.d("niko SingleSourceMapLiveData", "暂存profile");

        }catch (Exception e){

        }

        SLog.d("niko SingleSourceMapLiveData", "5");

        result.addSource(profile,listResource -> {
            SLog.d("niko SingleSourceMapLiveData", "6");

            if (listResource.status == Status.SUCCESS) {
                result.removeSource(profile);
                List<Result<ProfileInfo>> list = listResource.data;
                result.postValue(Resource.success(listResource.data));
            }
        });

        return result;
    }*/

    public LiveData<List<Result<ProfileInfo>>> getProfile(){
        return scUserService.getProfileInfo();
    }


    public LiveData<List<Result<Boolean>>> updateProfile(int type,String key,String value){
//        ProfileInfo profileInfo = ProfileUtils.sProfileInfo;
//        profileInfo.getHead().setName("niko");
        return scUserService.updateProfileInfo(RetrofitUtil.createJsonRequest(ProfileUtils.getUpdateInfo(type,key,value)));
    }
}
