package kr.or.mrhi.myCoin.viewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import kr.or.mrhi.myCoin.POJO.OrderBookData;
import kr.or.mrhi.myCoin.POJO.TransactionData;
import kr.or.mrhi.myCoin.retrofit.CoinRetrofit;
import kr.or.mrhi.myCoin.POJO.TickerData;
import kr.or.mrhi.myCoin.POJO.FormerCoinData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinViewModel extends ViewModel {

    private MutableLiveData<List<FormerCoinData>> formerCoinData;
    private MutableLiveData<List<TickerData>> tickerCoinData;
    private MutableLiveData<List<OrderBookData>> orderbookCoinData;
    private MutableLiveData<List<TransactionData>> transactionCoinData;

    private FormerData formerData;
    private NewTickerData newTickerData;
    private NewOrderBookData orderBookData;
    private NewTransactionData newTransactionData;

    public CoinViewModel() {
        this.formerData = new FormerData();
        this.newTickerData = new NewTickerData();
        this.orderBookData = new NewOrderBookData();
        this.newTransactionData = new NewTransactionData();
    }

    public LiveData<List<FormerCoinData>> getLastCoinData(String coinName, String intervals) {
        if (formerCoinData == null) {
            formerCoinData = new MutableLiveData<List<FormerCoinData>>();
        }
        formerData.refreshCoinData(coinName, intervals);

        return formerCoinData;
    }

    public MutableLiveData<List<TickerData>> getNewCoinData() {
        if (tickerCoinData == null) {
            tickerCoinData = new MutableLiveData<List<TickerData>>();
        }
        newTickerData.refreshCoinData();

        return tickerCoinData;
    }

    public MutableLiveData<List<OrderBookData>> getOrderBookCoinData() {
        if (orderbookCoinData == null) {
            orderbookCoinData = new MutableLiveData<List<OrderBookData>>();
        }
        orderBookData.refreshOrderBookCoinData();

        return orderbookCoinData;
    }

    public MutableLiveData<List<TransactionData>> getTransactionCoinData(String coinName) {
        if (transactionCoinData == null) {
            transactionCoinData = new MutableLiveData<List<TransactionData>>();
        }
        newTransactionData.refreshTransactionCoinData(coinName);

        return transactionCoinData;
    }

    public void refrashNewCoinDataThread(){
      Thread thread = new Thread(()->{
            while (true){
                newTickerData.refreshCoinData();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

      thread.setDaemon(true);
      thread.start();
    }


    public class FormerData {

        @SerializedName("status")
        @Expose
        private String status;

        @SerializedName("data")
        @Expose
        private List<List<String>> data = null;

        private void refreshCoinData(@NonNull String coinName, String intervals) {

            CoinRetrofit.create()
                    .getCandlestickCoinData(coinName.toUpperCase(), "KRW", intervals)
                    .enqueue(new Callback<FormerData>() {
                        @Override
                        public void onResponse(Call<FormerData> call, Response<FormerData> response) {
                            formerCoinData.setValue(makeCoinList(response));
                        }

                        @Override
                        public void onFailure(Call<FormerData> call, Throwable t) {
                            Log.i("이전코인", "실패 : " + t.fillInStackTrace());
                        }
                    });
        }

        @NonNull
        private List<FormerCoinData> makeCoinList(@NonNull Response<FormerData> response) {
            List<FormerCoinData> formerCoinList = new ArrayList<>();
            for (List<String> list : response.body().getData()) {
                FormerCoinData coin = new FormerCoinData(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5));
                formerCoinList.add(coin);
                Log.i("이전코인", coin.toString());
            }
            Log.i("이전코인", formerCoinList.size()+"");
            return formerCoinList;
        }

        private List<List<String>> getData() {
            return data;
        }

    }

    public class NewTickerData {
        @SerializedName("status")
        @Expose
        private String status;

        @SerializedName("data")
        @Expose
        private TickerData TickerData;

        private void refreshCoinData() {
            CoinRetrofit.create()
                    .getTickerCoinData("ALL", "KRW")
                    .enqueue(new Callback<NewTickerData>() {
                        @Override
                        public void onResponse(Call<NewTickerData> call, Response<NewTickerData> response) {
                            tickerCoinData.setValue(makeNewcoinList(response));
                            Log.i("현재코인", tickerCoinData.getValue().get(0).getBtc().getMaxPrice());
                        }

                        @Override
                        public void onFailure(Call<NewTickerData> call, Throwable t) {
                            Log.i("현재코인", "실패 : " + t.fillInStackTrace());
                        }
                    });
        }

        @NonNull
        private List<TickerData> makeNewcoinList(@NonNull Response<NewTickerData> response) {
            List<TickerData> list = new ArrayList<>();
            list.add(response.body().getNewData());
            return list;
        }

        private TickerData getNewData() {
            return TickerData;
        }
    }

    public class NewOrderBookData {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("data")
        @Expose
        private OrderBookData orderBookData;

        private void refreshOrderBookCoinData() {
            CoinRetrofit.create()
                    .getOrderBookCoinData("ALL", "KRW")
                    .enqueue(new Callback<NewOrderBookData>() {
                        @Override
                        public void onResponse(Call<NewOrderBookData> call, Response<NewOrderBookData> response) {
                            orderbookCoinData.setValue(makeNewcoinList(response));
                            Log.i("현재코인", orderbookCoinData.getValue().get(0).getBtc().getAsks().get(0).getPrice().toString());
                        }

                        @Override
                        public void onFailure(Call<NewOrderBookData> call, Throwable t) {
                            Log.i("현재코인", "실패 : " + t.fillInStackTrace());
                        }
                    });
        }

        @NonNull
        private List<OrderBookData> makeNewcoinList(@NonNull Response<NewOrderBookData> response) {
            List<OrderBookData> list = new ArrayList<>();
            list.add(response.body().getNewData());
            return list;
        }

        private OrderBookData getNewData() {
            return orderBookData;
        }

    }

    public class NewTransactionData {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("data")
        @Expose
        private List<TransactionData> data = null;


        private void refreshTransactionCoinData(String coinName) {
            CoinRetrofit.create()
                    .getTransactionCoinData(coinName)
                    .enqueue(new Callback<NewTransactionData>() {
                        @Override
                        public void onResponse(Call<NewTransactionData> call, Response<NewTransactionData> response) {
                            transactionCoinData.setValue(makeNewcoinList(response));
                            Log.i("현재코인", transactionCoinData.getValue().get(0).getPrice());
                        }

                        @Override
                        public void onFailure(Call<NewTransactionData> call, Throwable t) {
                            Log.i("현재코인", "실패 : " + t.fillInStackTrace());
                        }
                    });
        }

        @NonNull
        private List<TransactionData> makeNewcoinList(@NonNull Response<NewTransactionData> response) {
            List<TransactionData> list = new ArrayList<>();
            list = response.body().getNewData();
            return list;
        }

        private List<TransactionData> getNewData() {
            return data;
        }

    }

}