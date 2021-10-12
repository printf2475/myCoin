package kr.or.mrhi.myCoin.coins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nu {

    @SerializedName("opening_price")
    @Expose
    private String openingPrice;
    @SerializedName("closing_price")
    @Expose
    private String closingPrice;
    @SerializedName("min_price")
    @Expose
    private String minPrice;
    @SerializedName("max_price")
    @Expose
    private String maxPrice;
    @SerializedName("units_traded")
    @Expose
    private String unitsTraded;
    @SerializedName("acc_trade_value")
    @Expose
    private String accTradeValue;
    @SerializedName("prev_closing_price")
    @Expose
    private String prevClosingPrice;
    @SerializedName("units_traded_24H")
    @Expose
    private String unitsTraded24H;
    @SerializedName("acc_trade_value_24H")
    @Expose
    private String accTradeValue24H;
    @SerializedName("fluctate_24H")
    @Expose
    private String fluctate24H;
    @SerializedName("fluctate_rate_24H")
    @Expose
    private String fluctateRate24H;

    public String getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(String openingPrice) {
        this.openingPrice = openingPrice;
    }

    public String getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(String closingPrice) {
        this.closingPrice = closingPrice;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getUnitsTraded() {
        return unitsTraded;
    }

    public void setUnitsTraded(String unitsTraded) {
        this.unitsTraded = unitsTraded;
    }

    public String getAccTradeValue() {
        return accTradeValue;
    }

    public void setAccTradeValue(String accTradeValue) {
        this.accTradeValue = accTradeValue;
    }

    public String getPrevClosingPrice() {
        return prevClosingPrice;
    }

    public void setPrevClosingPrice(String prevClosingPrice) {
        this.prevClosingPrice = prevClosingPrice;
    }

    public String getUnitsTraded24H() {
        return unitsTraded24H;
    }

    public void setUnitsTraded24H(String unitsTraded24H) {
        this.unitsTraded24H = unitsTraded24H;
    }

    public String getAccTradeValue24H() {
        return accTradeValue24H;
    }

    public void setAccTradeValue24H(String accTradeValue24H) {
        this.accTradeValue24H = accTradeValue24H;
    }

    public String getFluctate24H() {
        return fluctate24H;
    }

    public void setFluctate24H(String fluctate24H) {
        this.fluctate24H = fluctate24H;
    }

    public String getFluctateRate24H() {
        return fluctateRate24H;
    }

    public void setFluctateRate24H(String fluctateRate24H) {
        this.fluctateRate24H = fluctateRate24H;
    }

}
