package com.market.loan.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.market.loan.bean.Result;
import com.market.loan.bean.UserResult;
import com.market.loan.constant.Apis;
import com.market.loan.constant.Status;
import com.market.loan.http.HttpRequest;
import com.market.loan.http.adapter.CallbackAdapter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyProfileViewModel extends ViewModel {

    public MyProfileViewModel() {

    }

    private MutableLiveData<Result<UserResult>> userResult;

    public LiveData<Result<UserResult>> getUserResult() {
        if (userResult == null) {
            userResult = new MutableLiveData<>();
        }
        return userResult;
    }

    public void request() {
        Call call = new HttpRequest().get(Apis.USER_INFO_URL, RequestBody.create(HttpRequest.JSON, ""));
        call.enqueue(new CallbackAdapter() {
            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                Result<UserResult> result = new Result<>(Status.FAIL_CODE,"Network request failed.");
                MyProfileViewModel.this.userResult.postValue(result);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                Result<UserResult> result;
                if (response.isSuccessful()) {
                    String resultBody = response.body().string();
                    result = JSON.parseObject(resultBody, new TypeReference<Result<UserResult>>() {});
                } else {
                    result = new Result<>(response.code() + "", response.message());
                }
                MyProfileViewModel.this.userResult.postValue(result);
            }
        });
    }
}
