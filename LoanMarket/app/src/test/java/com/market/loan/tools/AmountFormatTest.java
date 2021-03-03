package com.market.loan.tools;

import org.junit.Assert;
import org.junit.Test;

public class AmountFormatTest {

    @Test
    public void format() {
        String format = AmountFormat.format("500000");
        Assert.assertEquals("500,000",format);
    }
}