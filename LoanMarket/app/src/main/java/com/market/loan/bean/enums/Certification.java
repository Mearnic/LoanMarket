package com.market.loan.bean.enums;

public enum Certification {
    SUCCESS("0"),
    BASE_INFO("1"),
    WORK_INFO("2"),
    BANK_INFO("3");

    private String code;

    Certification(String code) {
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
