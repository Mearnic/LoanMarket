package com.market.loan.core;

import android.os.Build;

import com.market.loan.constant.Constants;
import com.market.loan.http.BasicHeader;

public class SimpleCache {

    private static BasicHeader basicHeader = null;

    public synchronized static void initHeaders() {
        basicHeader = new BasicHeader();
        basicHeader.setOsType("android");
        basicHeader.setOsVersion(Build.VERSION.RELEASE.trim());
        basicHeader.setBrand(Build.BRAND.trim());
        basicHeader.setModel(Build.MODEL.trim());
        basicHeader.setAdid(Constants.ADID);
        basicHeader.setGpsAdid(Constants.GPS_ADID);

        basicHeader.setDeviceId("");
        basicHeader.setAppVersion("");
    }

    public static BasicHeader getBasicHeader() {
        if (basicHeader == null) {
            initHeaders();
        }
        return basicHeader;
    }
}
