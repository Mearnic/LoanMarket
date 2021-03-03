package com.market.loan.bean;

import java.util.List;

public class ConfigResult {

    String sysServiceEmail;
    String sysServiceEmailBak;
    String sysServiceTime;
    String tipsPay;
    String tipsCongratulations;
    String tipsProcessing;
    String payChannel;

    List<ConfigData> dicts;

    public String getSysServiceEmailBak() {
        return sysServiceEmailBak;
    }

    public void setSysServiceEmailBak(String sysServiceEmailBak) {
        this.sysServiceEmailBak = sysServiceEmailBak;
    }

    public String getSysServiceEmail() {
        return sysServiceEmail;
    }

    public void setSysServiceEmail(String sysServiceEmail) {
        this.sysServiceEmail = sysServiceEmail;
    }

    public String getSysServiceTime() {
        return sysServiceTime;
    }

    public void setSysServiceTime(String sysServiceTime) {
        this.sysServiceTime = sysServiceTime;
    }

    public String getTipsPay() {
        return tipsPay;
    }

    public void setTipsPay(String tipsPay) {
        this.tipsPay = tipsPay;
    }

    public String getTipsCongratulations() {
        return tipsCongratulations;
    }

    public void setTipsCongratulations(String tipsCongratulations) {
        this.tipsCongratulations = tipsCongratulations;
    }

    public String getTipsProcessing() {
        return tipsProcessing;
    }

    public void setTipsProcessing(String tipsProcessing) {
        this.tipsProcessing = tipsProcessing;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public List<ConfigData> getDicts() {
        return dicts;
    }

    public void setDicts(List<ConfigData> dicts) {
        this.dicts = dicts;
    }
}
