package kr.or.mrhi.myCoin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBController {

    Context context;
    OpenHelper openHelper;

    public DBController(Context context) {
        this.context = context;
        openHelper = new OpenHelper(context);
    }

    class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(@Nullable Context context) {

            super(context, "MyCoinDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE TransactionTBL (" +
                    "name var(5), time time, transactions var(4), quantity long, price long, balance long);");
            sqLiteDatabase.execSQL("CREATE TABLE favoritesTBL ( coinName var(5) PRIMARY KEY);");

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            onCreate(sqLiteDatabase);
        }
    }

    public void insertTransaction(Transaction transaction) {
        SQLiteDatabase sqlDB = openHelper.getWritableDatabase();
        try {
            sqlDB.execSQL("INSERT INTO TransactionTBL VALUES ('"
                    + transaction.getCoinName() + "'," +
                    "(SELECT DATETIME('NOW','localtime')),'"
                    + transaction.getTransaction() + "', '"
                    + transaction.getQuantity() + "', '"
                    + transaction.getPrice()+ "', '"
                    + transaction.getBalance() + "')");


        } catch (Exception e) {
            Log.e("데이터베이스", "insert에러" + e.toString());
        } finally {
            sqlDB.close();
        }
    }

    public List<Transaction> getTransactionList() {
        List<Transaction> transactionList = new ArrayList<>();
        SQLiteDatabase sqlDB = openHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqlDB.rawQuery("SELECT * FROM TransactionTBL;", null);
            while (cursor.moveToNext()) {
                String coinName = cursor.getString(0);
                String transactionTime = cursor.getString(1);
                String transaction = cursor.getString(2);
                Long quantity = cursor.getLong(3);
                Long price = cursor.getLong(4);
                Long balance = cursor.getLong(5);
                transactionList.add(
                        new Transaction(coinName, transaction, transactionTime, quantity, price, balance));
            }
        } catch (Exception e) {
            Log.e("데이터베이스", "select에러" + e.toString());
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
        SQLiteDatabase sqlDB = openHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqlDB.rawQuery("SELECT * FROM favoritesTBL;", null);
            while (cursor.moveToNext()) {
                favoritesList.add(cursor.getString(0));
            }
        } catch (Exception e) {
            Log.e("데이터베이스", "select에러" + e.toString());
        } finally {
            sqlDB.close();
            if (cursor != null) {
                cursor.close();
            }
        }
        return favoritesList;
    }

    public void deleteFavoritesList(String coinName) {
        SQLiteDatabase sqlDB = openHelper.getWritableDatabase();
        try {
            sqlDB.execSQL("delete from favoritesTBL where coinName = " + coinName + ";");
            Log.e("데이터베이스", "delete성공");
        } catch (Exception e) {
            Log.e("데이터베이스", "delete에러" + e.toString());
        } finally {
            sqlDB.close();
        }
    }
}
