package com.example.nativetest.viewmodel;

import android.app.Application;

import com.example.nativetest.model.Resource;
import com.example.nativetest.model.Result;
import com.example.nativetest.model.VIPCheckBean;
import com.example.nativetest.model.VIPConfigBean;
import com.example.nativetest.model.sc.TokenBean;
import com.example.nativetest.task.VipTask;
import com.example.nativetest.utils.SingleSourceLiveData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class VipViewModel  extends AndroidViewModel {
    private VipTask vipTask;

    private SingleSourceLiveData<Result<Boolean>> updateProfileResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<VIPCheckBean>> vipCheckResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<List<VIPConfigBean>>> vipConfigResult =  new SingleSourceLiveData<>();



    public VipViewModel(@NonNull Application application) {
        super(application);
        vipTask = new VipTask(application);
    }

    public SingleSourceLiveData<Result<Boolean>> getUpdateProfile() {
        return updateProfileResult;
    }

    public void updateProfile(int type, String key, Object value) {
        updateProfileResult.setSource(vipTask.updateProfile(type, key, value));
    }

    public SingleSourceLiveData<Result<VIPCheckBean>> getVipCheckResult() {
        return vipCheckResult;
    }

    public void vipCheck(){
        vipCheckResult.setSource(vipTask.checkVip());
    }



    public SingleSourceLiveData<Result<List<VIPConfigBean>>> getVipConfigResult() {
        return vipConfigResult;
    }

    public void vipConfigInfo(){
        vipConfigResult.setSource(vipTask.vipInfo());
    }
}