package com.market.loan.bean;

import java.util.List;

public class ProductResult {


    private String phase;
    private String certification;
    private List<Limit> limits;
    private List<Vip> viplist;
    public void setPhase(String phase) {
        this.phase = phase;
    }
    public String getPhase() {
        return phase;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }
    public String getCertification() {
        return certification;
    }

    public void setLimits(List<Limit> limits) {
        this.limits = limits;
    }
    public List<Limit> getLimits() {
        return limits;
    }

    public void setViplist(List<Vip> vip) {
        this.viplist = vip;
    }
    public List<Vip> getViplist() {
        return viplist;
    }

}
