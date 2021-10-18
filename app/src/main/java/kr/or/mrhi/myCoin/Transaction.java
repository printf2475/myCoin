package kr.or.mrhi.myCoin;

public class Transaction {

    private String coinName;
    private String transaction;
    private String transactionTime;
    private String quantity;
    private String price;
    private int balance;
    private String avgPrice;

    public Transaction(String coinName, String transaction, String transactionTime, String quantity, String price, int balance, String avgPrice) {
        this.coinName = coinName;
        this.transaction = transaction;
        this.transactionTime = transactionTime;
        this.quantity = quantity;
        this.price = price;
        this.balance = balance;
        this.avgPrice = avgPrice;
    }

    public String getCoinName() {
        return coinName;
    }

    public String getTransaction() {
        return transaction;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "coinName='" + coinName + '\'' +
                ", transaction='" + transaction + '\'' +
                ", transactionTime='" + transactionTime + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                ", balance=" + balance +
                ", avgPrice='" + avgPrice + '\'' +
                '}';
    }
}
