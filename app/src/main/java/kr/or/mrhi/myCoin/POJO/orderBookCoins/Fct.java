package kr.or.mrhi.myCoin.POJO.orderBookCoins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Fct {

    @SerializedName("order_currency")
    @Expose
    private String orderCurrency;
    @SerializedName("bids")
    @Expose
    private List<Bid> bids = null;
    @SerializedName("asks")
    @Expose
    private List<Ask> asks = null;

    public String getOrderCurrency() {
        return orderCurrency;
    }

    public void setOrderCurrency(String orderCurrency) {
        this.orderCurrency = orderCurrency;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public List<Ask> getAsks() {
        return asks;
    }

    public void setAsks(List<Ask> asks) {
        this.asks = asks;
    }

}