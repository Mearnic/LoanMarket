package com.market.loan.model;

import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.market.loan.bean.EventRequest;
import com.market.loan.constant.Apis;
import com.market.loan.http.HttpRequest;
import com.market.loan.http.adapter.CallbackAdapter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EventModel extends ViewModel {

    public void request(EventRequest request) {
        Call call = new HttpRequest().post(Apis.EVENT_URL, RequestBody.create(HttpRequest.JSON, JSON.toJSONString(request)));
        call.enqueue(new CallbackAdapter() {
            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                if (response.isSuccessful()) {
                }
            }
        });
    }

}
