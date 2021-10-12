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

import kr.or.mrhi.myCoin.retrofit.CoinRetrofit;
import kr.or.mrhi.myCoin.POJO.Data;
import kr.or.mrhi.myCoin.POJO.FormerCoinData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinViewModel extends ViewModel {

    private MutableLiveData<List<FormerCoinData>> formerCoinData;
    private MutableLiveData<List<Data>> newCoinData;
    private FormerData formerData = new FormerData();
    private NewData newData = new NewData();

    public LiveData<List<FormerCoinData>> getLastCoinData(String coinName, String intervals) {
        if (formerCoinData == null) {
            formerCoinData = new MutableLiveData<List<FormerCoinData>>();
        }
        formerData.refreshCoinData(coinName, intervals);

        return formerCoinData;
    }

    public MutableLiveData<List<Data>> getNewCoinData() {
        if (newCoinData == null) {
            newCoinData = new MutableLiveData<List<Data>>();
        }
        newData.refreshCoinData();

        return newCoinData;
    }


    public void refrashNewCoinDataThread(){
      Thread thread = new Thread(()->{
            while (true){
                newData.refreshCoinData();
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
                    .getCoinData(coinName.toUpperCase(), "KRW", intervals)
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

    public class NewData {
        @SerializedName("status")
        @Expose
        private String status;

        @SerializedName("data")
        @Expose
        private Data newData;

        private void refreshCoinData() {
            CoinRetrofit.create()
                    .getNewCoinData("ALL", "KRW")
                    .enqueue(new Callback<NewData>() {
                        @Override
                        public void onResponse(Call<NewData> call, Response<NewData> response) {
                            newCoinData.setValue(makeNewcoinList(response));
                            Log.i("현재코인", newCoinData.getValue().get(0).getBtc().getMaxPrice());
                        }

                        @Override
                        public void onFailure(Call<NewData> call, Throwable t) {
                            Log.i("현재코인", "실패 : " + t.fillInStackTrace());
                        }
                    });
        }

        @NonNull
        private List<Data> makeNewcoinList(@NonNull Response<NewData> response) {
            List<Data> list = new ArrayList<>();
            list.add(response.body().getNewData());
            return list;
        }

        private Data getNewData() {
            return newData;
        }
    }
}