package kr.or.mrhi.myCoin.viewModel;

import static kr.or.mrhi.myCoin.MainActivity.TRANSACTIONFLAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.mrhi.myCoin.POJO.OrderBookData;
import kr.or.mrhi.myCoin.POJO.TransactionData;
import kr.or.mrhi.myCoin.retrofit.CoinRetrofit;
import kr.or.mrhi.myCoin.POJO.TickerData;
import kr.or.mrhi.myCoin.POJO.CandleCoinData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinViewModel extends ViewModel {
    private MutableLiveData<List<CandleCoinData>> candleCoinData;
    private MutableLiveData<TickerData> tickerCoinData;
    private MutableLiveData<List<OrderBookData>> orderbookCoinData;
    private MutableLiveData<Map<String, TransactionData>> transactionCoinData;

    private NewCandleData newCandleData;
    private NewTickerData newTickerData;
    private NewOrderBookData orderBookData;
    private NewTransactionData newTransactionData;
    private static final int LATELYDATA = 19;


    public CoinViewModel() {
        this.newCandleData = new NewCandleData();
        this.newTickerData = new NewTickerData();
        this.orderBookData = new NewOrderBookData();
        this.newTransactionData = new NewTransactionData();

    }


    public LiveData<List<CandleCoinData>> getLastCoinData(String coinName, String intervals) {
        if (candleCoinData == null) {
            candleCoinData = new MutableLiveData<List<CandleCoinData>>();
        }
        newCandleData.refreshCoinData(coinName, intervals);
        return candleCoinData;
    }

    public MutableLiveData<TickerData> getNewCoinData() {
        if (tickerCoinData == null) {
            tickerCoinData = new MutableLiveData<TickerData>();
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

    public MutableLiveData<Map<String, TransactionData>> getTransactionCoinData(String coinName) {
        if (transactionCoinData == null) {
            transactionCoinData = new MutableLiveData<Map<String, TransactionData>>();
        }
        newTransactionData.refreshTransactionCoinData(coinName);

        return transactionCoinData;
    }

    public void refrashTransactionDataThread(String[] coinNames) {
        Thread thread=new Thread(() -> {
            while (TRANSACTIONFLAG) {
                for (int i = 0; i < 50; i++) {
                    synchronized (this) {
                       newTransactionData.refreshTransactionCoinData(coinNames[i]);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public class NewCandleData {

        @SerializedName("status")
        @Expose
        private String status;

        @SerializedName("data")
        @Expose
        private List<List<String>> data = null;

        public void refreshCoinData(@NonNull String coinName, String intervals) {

            CoinRetrofit.create()
                    .getCoinData(coinName.toUpperCase(), "KRW", intervals)
                    .enqueue(new Callback<NewCandleData>() {
                        @Override
                        public void onResponse(Call<NewCandleData> call, Response<NewCandleData> response) {
                            candleCoinData.setValue(makeCoinList(response));
                        }

                        @Override
                        public void onFailure(Call<NewCandleData> call, Throwable t) {
                            Log.i("이전코인", "실패 : " + t.fillInStackTrace());
                        }
                    });
        }

        @NonNull
        private List<CandleCoinData> makeCoinList(@NonNull Response<NewCandleData> response) {
            List<CandleCoinData> formerCoinList = new ArrayList<>();
            for (List<String> list : response.body().getData()) {
                CandleCoinData coin = new CandleCoinData(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5));
                formerCoinList.add(coin);
                Log.i("이전코인", coin.toString());
            }
            Log.i("이전코인", formerCoinList.size() + "");
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
                            tickerCoinData.setValue(response.body().getNewData());
                            Log.i("현재코인", tickerCoinData.getValue().getBtc().getName());
                        }

                        @Override
                        public void onFailure(Call<NewTickerData> call, Throwable t) {
                            Log.i("현재코인", "실패 : " + t.fillInStackTrace());
                        }
                    });
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
                            transactionCoinData.setValue(makeMapData(coinName, response));
                        }

                        @Override
                        public void onFailure(Call<NewTransactionData> call, Throwable t) {
                            Log.i("현재코인", "실패 : " + t.fillInStackTrace());
                        }
                    });


        }

        private Map<String, TransactionData> makeMapData(String coinName, Response<NewTransactionData> response) {
            Map<String, TransactionData> dataMap = new HashMap<>();
            if (response.body()!=null){
                dataMap.put(coinName, response.body().getNewData().get(LATELYDATA));
            }
            return dataMap;
        }


        private List<TransactionData> getNewData() {
            return data;
        }

    }

}