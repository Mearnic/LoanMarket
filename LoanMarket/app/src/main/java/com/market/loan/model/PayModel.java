package com.market.loan.model;

import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.market.loan.bean.CashfreeResult;
import com.market.loan.bean.EventRequest;
import com.market.loan.bean.Result;
import com.market.loan.bean.UserResult;
import com.market.loan.constant.Apis;
import com.market.loan.http.HttpRequest;
import com.market.loan.http.adapter.CallbackAdapter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PayModel extends ViewModel {

    public void request(String id) {
        Call call = new HttpRequest().get(Apis.CASHFREE_URL + "?id=" + id, RequestBody.create(HttpRequest.JSON, ""));
        call.enqueue(new CallbackAdapter() {
            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                if (response.isSuccessful()) {
                    String resultBody = response.body().string();
                    Result<CashfreeResult> result = JSON.parseObject(resultBody, new TypeReference<Result<CashfreeResult>>() {});
                }
            }
        });
    }

}
