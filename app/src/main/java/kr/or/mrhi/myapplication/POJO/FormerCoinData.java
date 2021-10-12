package kr.or.mrhi.myapplication.POJO;

public class FormerCoinData {
    private String date;
    private String openingPrice;
    private String closingPrice;
    private String maxPrice;
    private String minPrice;
    private String unitsTraded;

    public FormerCoinData(String date, String openingPrice, String closingPrice, String maxPrice, String minPrice, String unitsTraded) {
        this.date = date;
        this.openingPrice = openingPrice;
        this.closingPrice = closingPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.unitsTraded = unitsTraded;
    }

    @Override
    public String toString() {
        return "CoinResult{" +
                "date='" + date + '\'' +
                ", openingPrice='" + openingPrice + '\'' +
                ", closingPrice='" + closingPrice + '\'' +
                ", maxPrice='" + maxPrice + '\'' +
                ", minPrice='" + minPrice + '\'' +
                ", unitsTraded='" + unitsTraded + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getUnitsTraded() {
        return unitsTraded;
    }

    public void setUnitsTraded(String unitsTraded) {
        this.unitsTraded = unitsTraded;
    }
}


