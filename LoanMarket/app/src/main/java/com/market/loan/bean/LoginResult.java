package com.market.loan.bean;

public class LoginResult {

    String id;
    String mobile;
    String token;
    String authorized;
    String action;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAuthorized() {
        return authorized;
    }

    public void setAuthorized(String authorized) {
        this.authorized = authorized;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "id='" + id + '\'' +
                ", mobile='" + mobile + '\'' +
                ", token='" + token + '\'' +
                ", authorized='" + authorized + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
