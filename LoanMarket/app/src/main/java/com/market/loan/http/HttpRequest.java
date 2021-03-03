package com.market.loan.http;

import com.market.loan.constant.Constants;
import com.market.loan.core.ConfigCache;
import com.market.loan.core.SimpleCache;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpRequest {

    public final static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public final static MediaType FORM = MediaType.parse("application/x-www-form-urlencoded");

    public HttpRequest() {
    }

    public HttpRequest(String token) {
    }

    public String getToken() {
        return ConfigCache.token;
    }


    public Headers getHeaders() {
        BasicHeader basicHeader = SimpleCache.getBasicHeader();
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("os-type", basicHeader.getOsType());
        headerMap.put("os-version", basicHeader.getOsVersion());
        headerMap.put("brand", basicHeader.getBrand());
        headerMap.put("model", basicHeader.getModel());
        headerMap.put("app-version", basicHeader.getAppVersion());
        headerMap.put("device-id", basicHeader.getDeviceId());
        headerMap.put("adid", basicHeader.getAdid());
        headerMap.put("gps_adid", basicHeader.getGpsAdid());
        if (getToken() != null) {
            headerMap.put("token", getToken());
        }
        return Headers.of(headerMap);
    }

    public Call get(String uri, RequestBody requestBody) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Constants.DOMAIN + uri)
                .headers(getHeaders())
                .post(requestBody)
                .build();
        return client.newCall(request);
    }

    public Call post(String uri, RequestBody requestBody) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Constants.DOMAIN + uri)
                .headers(getHeaders())
                .post(requestBody)
                .build();
        return client.newCall(request);
    }
}
