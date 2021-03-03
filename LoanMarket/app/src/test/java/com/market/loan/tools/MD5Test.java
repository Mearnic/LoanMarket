package com.market.loan.tools;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MD5Test {

    @Test
    public void encode() {
        final String chars = "123456";
        final String md5Code = "e10adc3949ba59abbe56e057f20f883e";
        String encode = MD5.encode(chars);
        Assert.assertEquals(md5Code, encode);
    }
}