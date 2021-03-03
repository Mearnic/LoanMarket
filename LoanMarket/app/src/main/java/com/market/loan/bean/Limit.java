
package com.market.loan.bean;
import java.util.List;


public class Limit {

    private String amount;
    private String isDefault;
    private List<Duration> durations;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public List<Duration> getDurations() {
        return durations;
    }

    public void setDurations(List<Duration> durations) {
        this.durations = durations;
    }
}