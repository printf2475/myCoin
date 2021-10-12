package kr.or.mrhi.myCoin.orderBookCoins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ask {

    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("quantity")
    @Expose
    private String quantity;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
