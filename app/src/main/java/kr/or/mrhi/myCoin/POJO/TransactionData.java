package kr.or.mrhi.myCoin.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionData {
    @SerializedName("transaction_date")
    @Expose
    private String transactionDate;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("units_traded")
    @Expose
    private String unitsTraded;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("total")
    @Expose
    private String total;

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnitsTraded() {
        return unitsTraded;
    }

    public void setUnitsTraded(String unitsTraded) {
        this.unitsTraded = unitsTraded;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
