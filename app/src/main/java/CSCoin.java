public class CSCoin {
    private long createdAt;

    private float open;
    private float close;
    private float shadowHigh;
    private float shadowLow;

    public CSCoin(long createdAt, float open, float close, float shadowHigh, float shadowLow) {
        this.createdAt = createdAt;
        this.open = open;
        this.close = close;
        this.shadowHigh = shadowHigh;
        this.shadowLow = shadowLow;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getShadowHigh() {
        return shadowHigh;
    }

    public void setShadowHigh(float shadowHigh) {
        this.shadowHigh = shadowHigh;
    }

    public float getShadowLow() {
        return shadowLow;
    }

    public void setShadowLow(float shadowLow) {
        this.shadowLow = shadowLow;
    }
}
