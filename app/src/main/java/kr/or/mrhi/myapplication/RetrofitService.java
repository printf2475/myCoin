package kr.or.mrhi.myapplication;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.Call;


public interface RetrofitService {
    //{order_currency} = 주문 통화(코인), ALL(전체), 기본값 : BTC
    //{payment_currency} = 결제 통화(마켓), 입력값 : KRW 혹은 BTC12
    @GET("candlestick/{order_currency}_{payment_currency}/1m")
    Call<CoinViewModel.FormerData> getCoinData(@Path("order_currency") String order_currency, @Path("payment_currency") String payment_currency);

    @GET("ticker/{order_currency}_{payment_currency}")
    Call<CoinViewModel.NewData> getNewCoinData(@Path("order_currency") String order_currency, @Path("payment_currency") String payment_currency);


    static RetrofitService create() {
        String hostURL = "https://api.bithumb.com/public/";

        OkHttpClient client = new OkHttpClient.Builder().build();

        return new Retrofit.Builder()
                .baseUrl(hostURL) // 서버 호스트
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService.class);
    }
}
