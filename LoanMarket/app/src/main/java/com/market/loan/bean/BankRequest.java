package com.market.loan.bean;

public class BankRequest {
    String ifsc_code;
    String bank_name;
    String bank_account_no;

    public BankRequest() {
    }

    public BankRequest(UserResult userResult) {
        this.ifsc_code = userResult.ifscCode;
        this.bank_name = userResult.bankName;
        this.bank_account_no = userResult.bankAccountNo;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_account_no() {
        return bank_account_no;
    }

    public void setBank_account_no(String bank_account_no) {
        this.bank_account_no = bank_account_no;
    }

    public String checked() {
        if (ifsc_code == null || ifsc_code.equals("")) {
            return "Ifsc code type not be empty";
        }
        if (bank_name == null || bank_name.equals("")) {
            return "Bank name salary not be empty";
        }
        if (bank_account_no == null || bank_account_no.equals("")) {
            return "Bank account no not be empty";
        }
        return null;
    }
}
