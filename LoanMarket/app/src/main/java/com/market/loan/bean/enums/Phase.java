package com.market.loan.bean.enums;

public enum Phase {
    UNAUTHORIZED("0"),
    IN_REVIEW("1"),
    PASS_REVIEW("2"),
    PAYMENT("3");

    private String code;

    Phase(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
