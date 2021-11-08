package kr.or.mrhi.myCoin.viewModel;

import static kr.or.mrhi.myCoin.activity.MainActivity.namePositionMap;
import static kr.or.mrhi.myCoin.activity.MainActivity.stringSymbol;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import kr.or.mrhi.myCoin.POJO.TransactionData;
import kr.or.mrhi.myCoin.model.TickerDTO;
import kr.or.mrhi.myCoin.network.CoinRetrofit;
import kr.or.mrhi.myCoin.POJO.TickerDataClass;
import kr.or.mrhi.myCoin.POJO.CandleCoinData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CoinViewModel extends ViewModel {
    private MutableLiveData<List<CandleCoinData>> candleCoinData;
    private MutableLiveData<List<TickerDTO>> tickerCoinData;
    private MutableLiveData<List<String>> transactionCoinData;
    private MutableLiveData<String> searchName;

    private NewCandleData newCandleData;
    private NewTickerData newTickerData;
    private NewTransactionData newTransactionData;
    private TickerData_single tickerDatasingle;
    private static final int LATELYDATA = 19;
    private List<String> priceList;
    private boolean stopFlag;
    private CoinRetrofit retrofit;

    public CoinViewModel() {
        retrofit = CoinRetrofit.create();
        this.searchName = new MutableLiveData<String>();
        this.newCandleData = new NewCandleData();
        this.newTickerData = new NewTickerData();
        this.newTransactionData = new NewTransactionData();
        this.tickerDatasingle = new TickerData_single();
        this.priceList= new ArrayList<>(20);
        for (int i=0; i<stringSymbol.length; i++){
            priceList.add("0.00");
        }
        stopFlag=false;
    }


    public MutableLiveData<String> getSearchName() {
        searchName.setValue("");
        Log.i("검색:getSearchName", searchName.getValue());
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName.setValue(searchName);
        Log.i("검색:setSearchName", this.searchName.getValue());
    }

    public MutableLiveData<List<CandleCoinData>> getCandleCoinData(String coinName, String intervals) {
        if (candleCoinData == null) {
            candleCoinData = new MutableLiveData<List<CandleCoinData>>();
        }
        newCandleData.refreshCoinData(coinName, intervals);
        return candleCoinData;
    }

    public MutableLiveData<List<TickerDTO>> getTickerCoinData() {
        if (tickerCoinData == null) {
            tickerCoinData = new MutableLiveData<List<TickerDTO>>();
        }
        newTickerData.refreshCoinData();

        return tickerCoinData;
    }

    public MutableLiveData<List<TickerDTO>> getTickerDTO(String coinName) {
        if (tickerCoinData == null) {
            tickerCoinData = new MutableLiveData<List<TickerDTO>>();
        }
        tickerDatasingle.refreshTickerDTO(coinName);

        return tickerCoinData;
    }

    public MutableLiveData<List<String>> getTransactionCoinData(String coinName) {
        if (transactionCoinData == null) {
            transactionCoinData = new MutableLiveData<List<String>>();
        }
        newTransactionData.refreshTransactionCoinData(coinName);

        return transactionCoinData;
    }

    public void refrashTransactionDataThread(String[] coinNames) {
        stopFlag=true;
        Thread thread = new Thread(() -> {
            while (stopFlag) {
                for (int i = 0; i < coinNames.length; i++) {
                    synchronized (this) {
                        newTransactionData.refreshTransactionCoinData(coinNames[i]);
                    }
                }
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

    public void stopThread() {
        this.stopFlag = false;
    }

    public class NewCandleData {

        @SerializedName("status")
        @Expose
        private String status;

        @SerializedName("data")
        @Expose
        private List<List<String>> data = null;

        public void refreshCoinData(@NonNull String coinName, String intervals) {

            retrofit.getCoinData(coinName.toUpperCase(), "KRW", intervals)
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
        private TickerDataClass tickerDataClass;

        private void refreshCoinData() {
            retrofit.getTickerCoinData("ALL", "KRW")
                    .enqueue(new Callback<NewTickerData>() {
                        @Override
                        public void onResponse(Call<NewTickerData> call, Response<NewTickerData> response) {
                            tickerCoinData.setValue(response.body().getNewData());
                        }

                        @Override
                        public void onFailure(Call<NewTickerData> call, Throwable t) {
                            Log.i("현재코인", "실패 : " + t.fillInStackTrace());
                        }
                    });
        }




        private List<TickerDTO> getNewData() {
           List<TickerDTO> list =  new ArrayList<>();
           list.add(tickerDataClass.getBtc());
           list.add(tickerDataClass.getEth());
           list.add(tickerDataClass.getBch());
           list.add(tickerDataClass.getLtc());
           list.add(tickerDataClass.getBsv());
           list.add(tickerDataClass.getAxs());
           list.add(tickerDataClass.getBtg());
           list.add(tickerDataClass.getEtc());
           list.add(tickerDataClass.getDot());
           list.add(tickerDataClass.getAtom());
           list.add(tickerDataClass.getWaves());
           list.add(tickerDataClass.getLink());
           list.add(tickerDataClass.getRep());
           list.add(tickerDataClass.getOmg());
           list.add(tickerDataClass.getQtum());
            return list;
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

                retrofit.getTransactionCoinData(coinName)
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

        private List<String> makeMapData(String coinName, Response<NewTransactionData> response) {

            if (response.body() != null) {
                priceList.set(namePositionMap.get(coinName), response.body().getNewData().get(LATELYDATA).getPrice());
            }


            return priceList;
        }

        private List<TransactionData> getNewData() {
            return data;
        }
    }

    public class TickerData_single {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("data")
        @Expose
        private TickerDTO data;

        private void refreshTickerDTO(String coinName) {
            retrofit.getTickerDTO(coinName)
                    .enqueue(new Callback<TickerData_single>() {
                        @Override
                        public void onResponse(Call<TickerData_single> call, Response<TickerData_single> response) {
                            tickerCoinData.setValue(response.body().getData());
                            Log.i("데이터", tickerCoinData.getValue().get(0).getMaxPrice());
                        }
                        @Override
                        public void onFailure(Call<TickerData_single> call, Throwable t) {
                            Log.i("현재코인", "실패 : " + t.fillInStackTrace());
                        }
                    });
        }

        public List<TickerDTO> getData() {
            List<TickerDTO> list =  new ArrayList<>();
            list.add(data);
            return list;
        }

    }

}