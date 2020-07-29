package com.example.nativetest.task;

import android.content.Context;

import com.example.nativetest.ProfileUtils;
import com.example.nativetest.model.Result;
import com.example.nativetest.model.VIPCheckBean;
import com.example.nativetest.model.VIPConfigBean;
import com.example.nativetest.net.HttpClientManager;
import com.example.nativetest.net.RetrofitUtil;
import com.example.nativetest.net.service.ScUserService;

import java.util.List;

import androidx.lifecycle.LiveData;

public class VipTask {
    private ScUserService scUserService;
    private Context context;

    public VipTask(Context context) {
        this.context = context.getApplicationContext();
        scUserService = HttpClientManager.getInstance(context).getClient().createService(ScUserService.class);
    }

    public LiveData<Result<Boolean>> updateProfile(int type,String key,Object value){
        return scUserService.updateProfileInfo(RetrofitUtil.createJsonRequest(ProfileUtils.getUpdateInfo(type,key,value)));
    }

    public LiveData<Result<VIPCheckBean>> checkVip(){
        return scUserService.vipCheck();
    }

    public LiveData<Result<List<VIPConfigBean>>> vipInfo(){
        return scUserService.vipInfo();
    }

}
