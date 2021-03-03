package com.market.loan.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.market.loan.bean.ConfigResult;
import com.market.loan.bean.Result;
import com.market.loan.constant.Apis;
import com.market.loan.constant.Status;
import com.market.loan.http.HttpRequest;
import com.market.loan.http.adapter.CallbackAdapter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StartViewModel extends ViewModel {

    public StartViewModel() {

    }

    private MutableLiveData<Result<ConfigResult>> configResult;

    public LiveData<Result<ConfigResult>> getConfigResult() {
        if (configResult == null) {
            configResult = new MutableLiveData<>();
        }
        return configResult;
    }


    public void request() {
        Call call = new HttpRequest().get(Apis.CONFIG_URI, RequestBody.create(HttpRequest.JSON, ""));
        call.enqueue(new CallbackAdapter() {
            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                Result<ConfigResult> result = new Result<>(Status.FAIL_CODE,"Network request failed.");
                StartViewModel.this.configResult.postValue(result);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                String resultBody = response.body().string();
                Result<ConfigResult> result = JSON.parseObject(resultBody, new TypeReference<Result<ConfigResult>>() {});
                StartViewModel.this.configResult.postValue(result);
            }
        });
    }

}
