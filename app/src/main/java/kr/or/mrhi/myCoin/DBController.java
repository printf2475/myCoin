package kr.or.mrhi.myCoin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import kr.or.mrhi.myCoin.model.Transaction;

public class DBController extends SQLiteOpenHelper {

    public DBController(@Nullable Context context) {
        super(context, "MyCoinDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE TransactionTBL (" +
                "name text, time time, transactions text, quantity text, price text, balance int );");
        sqLiteDatabase.execSQL("CREATE TABLE favoritesTBL ( coinName var(5) PRIMARY KEY);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onCreate(sqLiteDatabase);
    }

    public void insertBalanceTransactionTBL(int balance) {
        insertTransaction(new Transaction("포인트", "add", null, "0.0", "0.0", balance, null));
    }

    public void insertFavorite(String name) {
        SQLiteDatabase sqlDB = getWritableDatabase();
        try {
            sqlDB.execSQL("INSERT INTO favoritesTBL VALUES ('"
                    + name + "')");
        } catch (Exception e) {
            Log.e("데이터베이스", "faviriteList Insert에러" + e.toString());
        } finally {
            sqlDB.close();
        }
    }

    public void insertTransaction(Transaction transaction) {
        SQLiteDatabase sqlDB = getWritableDatabase();
        try {
            sqlDB.execSQL("INSERT INTO TransactionTBL VALUES ('"
                    + transaction.getCoinName() + "'," +
                    "(SELECT DATETIME('NOW','localtime')),'"
                    + transaction.getTransaction() + "', '"
                    + transaction.getQuantity() + "', '"
                    + transaction.getPrice() + "', '"
                    + transaction.getBalance() + "')");


        } catch (Exception e) {
            Log.e("데이터베이스", "insert에러" + e.toString());
        } finally {
            sqlDB.close();
        }
    }

    public Transaction getCoinTransaction(String coinName) {
        SQLiteDatabase sqlDB = getReadableDatabase();
        Cursor cursor = null;
        Transaction transaction = null;
        String myCoinName = "null";
        String transactionTime = "null";
        String transactionForm = "null";
        String currentPrice = "null";
        //현재 산 코인의 값
        double quantity = 0.00;
        double price = 0.00;
        double tradeQualtity = 0.00;
        int balance = 0;
        int count = 0;
        try {
            cursor = sqlDB.rawQuery("SELECT * FROM TransactionTBL where name = '" + coinName + "';", null);
            while (cursor.moveToNext()) {
                myCoinName = cursor.getString(0);
                transactionTime = cursor.getString(1);
                transactionForm = cursor.getString(2);//사고 팔고
                tradeQualtity = Double.parseDouble(cursor.getString(3));//갯수
                currentPrice = cursor.getString(4);//가격
                balance += cursor.getInt(5);//잔액
                if (transactionForm.equals("buy")) {
                    count++;
                    price = price + Double.parseDouble(currentPrice);
                    quantity = quantity + tradeQualtity;
                } else if (transactionForm.equals("sell")) {
                    quantity = quantity - tradeQualtity;
                }
                //보유수량 ,구매횟수, 구매당시가격, 구매당시가격*갯수
            }

            double avgPrice = price / count;
//                quantity 보유수량 price
            transaction = new Transaction(myCoinName, transactionTime, transactionForm, String.valueOf(quantity), String.valueOf(price), balance, String.valueOf(avgPrice));
        } catch (Exception e) {
            Log.e("데이터베이스", "select에러3" + e.toString());
        } finally {
            sqlDB.close();
            if (cursor != null) {
                cursor.close();
            }
        }
        return transaction;
    }


    public List<Transaction> getTransactionList() {
        List<Transaction> transactionList = new ArrayList<>();
        SQLiteDatabase sqlDB = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqlDB.rawQuery("SELECT * FROM TransactionTBL;", null);
            while (cursor.moveToNext()) {
                String coinName = cursor.getString(0);
                String transactionTime = cursor.getString(1);
                String transaction = cursor.getString(2);
                String quantity = String.valueOf(cursor.getLong(3));
                String price = String.valueOf(cursor.getLong(4));
                int balance = cursor.getInt(5);
                transactionList.add(
                        new Transaction(coinName, transaction, transactionTime, quantity, price, balance, null));
            }
        } catch (Exception e) {
            Log.e("데이터베이스", "select에러 : getTransactionList" + e.toString());
        } finally {
            sqlDB.close();
            if (cursor != null) {
                cursor.close();
            }
        }
        return transactionList;
    }

    public List<String> getFavoritesList() {
        List<String> favoritesList = new ArrayList<>();
        SQLiteDatabase sqlDB = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqlDB.rawQuery("SELECT * FROM favoritesTBL;", null);
            while (cursor.moveToNext()) {
                favoritesList.add(cursor.getString(0));
            }
        } catch (Exception e) {
            Log.e("데이터베이스", "select에러 : getFavoritesList" + e.toString());
        } finally {
            sqlDB.close();
            if (cursor != null) {
                cursor.close();
            }
        }
        return favoritesList;
    }

    public void deleteFavoritesList(String coinName) {
        SQLiteDatabase sqlDB = getWritableDatabase();
        try {
            sqlDB.execSQL("delete from favoritesTBL where coinName = '" + coinName + "';");
        } catch (Exception e) {
            Log.e("데이터베이스", "delete에러 : deleteFavoritesList" + e.toString());
        } finally {
            sqlDB.close();
        }
    }


    public List<Transaction> getMyWallet() {
        List<String> list = new ArrayList<>();
        List<Transaction> transactionList = new ArrayList<>();

        SQLiteDatabase sqlDB = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqlDB.rawQuery("SELECT name From TransactionTBL GROUP BY name ;", null);
            while (cursor.moveToNext()) {
                list.add(cursor.getString(0));
            }

        } catch (Exception e) {
            Log.e("데이터베이스", "SELECT 에러 : getMyWallet" + e.toString());
        } finally {
            sqlDB.close();
            if (cursor != null) {
                cursor.close();
            }
        }

        for (String str : list) {
            transactionList.add(getCoinTransaction(str));
        }
        return transactionList;
    }


}
