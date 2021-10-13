package kr.or.mrhi.myCoin;

public class Transaction {

    private String coinName;
    private String transaction;
    private String transactionTime;
    private Long quantity;
    private Long price;
    private Long balance;

    public Transaction(String coinName, String transaction, String transactionTime, Long quantity, Long price, Long balance) {
        this.coinName = coinName;
        this.transaction = transaction;
        this.transactionTime = transactionTime;
        this.quantity = quantity;
        this.price = price;
        this.balance = balance;
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

    public Long getQuantity() {
        return quantity;
    }

    public Long getPrice() {
        return price;
    }

    public Long getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "coinName='" + coinName + '\'' +
                ", transaction='" + transaction + '\'' +
                ", transactionTime='" + transactionTime + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", balance=" + balance +
                '}';
    }
}
