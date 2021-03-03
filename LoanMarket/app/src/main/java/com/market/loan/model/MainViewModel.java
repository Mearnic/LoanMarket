package com.market.loan.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.market.loan.bean.MarqueeResult;
import com.market.loan.bean.ProductResult;
import com.market.loan.bean.Result;
import com.market.loan.constant.Apis;
import com.market.loan.constant.Status;
import com.market.loan.http.HttpRequest;
import com.market.loan.http.adapter.CallbackAdapter;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainViewModel extends ViewModel {

    public MainViewModel() {

    }

    private MutableLiveData<Result<ProductResult>> productResult;

    public LiveData<Result<ProductResult>> getProductResult() {
        if (productResult == null) {
            productResult = new MutableLiveData<>();
        }
        return productResult;
    }


    private MutableLiveData<Result<List<MarqueeResult>>> marqueeResult;

    public LiveData<Result<List<MarqueeResult>>> getMarqueeResult() {
        if (marqueeResult == null) {
            marqueeResult = new MutableLiveData<>();
        }
        return marqueeResult;
    }


    public void getProduct() {
        Call call = new HttpRequest().get(Apis.PRODUCT_URL, RequestBody.create(HttpRequest.JSON, ""));
        call.enqueue(new CallbackAdapter() {
            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                Result<ProductResult> result = new Result<>(Status.FAIL_CODE,"Network request failed.");
                MainViewModel.this.productResult.postValue(result);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                Result<ProductResult> result;
                if (response.isSuccessful()) {
                    String resultBody = response.body().string();
                    result = JSON.parseObject(resultBody, new TypeReference<Result<ProductResult>>() {});
                } else {
                    result = new Result<>(response.code() + "", response.message());
                }
                MainViewModel.this.productResult.postValue(result);
            }
        });
    }


    public void getMarquee() {
        Call call = new HttpRequest().get(Apis.MARQUEE_URL, RequestBody.create(HttpRequest.JSON, ""));
        call.enqueue(new CallbackAdapter() {
            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                Result<List<MarqueeResult>> result = new Result<>(Status.FAIL_CODE,"Network request failed.");
                MainViewModel.this.marqueeResult.postValue(result);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                Result<List<MarqueeResult>> result;
                if (response.isSuccessful()) {
                    String resultBody = response.body().string();
                    result = JSON.parseObject(resultBody, new TypeReference<Result<List<MarqueeResult>>>() {});
                } else {
                    result = new Result<>(response.code() + "", response.message());
                }
                MainViewModel.this.marqueeResult.postValue(result);
            }
        });
    }
}
