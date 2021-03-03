package com.market.loan.tools;

import java.text.DecimalFormat;

public class AmountFormat {

    public static String format(String amount) {
        DecimalFormat format = new DecimalFormat("###,###");
        return format.format(Double.valueOf(amount));
    }
}
