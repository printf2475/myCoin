package kr.or.mrhi.myCoin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import java.util.Map;
import java.util.Set;

import kr.or.mrhi.myCoin.POJO.TickerData;
import kr.or.mrhi.myCoin.POJO.TransactionData;
import kr.or.mrhi.myCoin.POJO.tickerCoins.True;
import kr.or.mrhi.myCoin.viewModel.CoinViewModel;

public class MainActivity extends AppCompatActivity {
    static final String COINNAME = "DOGE", INTERVALS = "5m";
    public static final String[] strings = new String[]{"BTC", "ETH", "BCH", "LTC", "BSV", "AXS", "BTG", "STRK",
            "ETC", "NEO", "DOT", "ATOM", "WAVES", "LINK", "REP", "FLOW", "OMG", "QTUM", "TON", "GAS", "SRM",
            "SBD", "XTZ", "THETA", "KAVA", "EOS", "AQT", "LSK", "DAWN", "MTL", "SXP", "STX", "STRAX", "ADA",
            "ARK", "ICX", "KNC", "PUNDIX", "ENJ", "IOTA", "STORJ", "MLK", "GRS", "ONT", "XLM", "CHZ", "DOGE",
            "XEC", "BTT", "AHT", "QKC"};
    public static Boolean TRANSACTIONFLAG=false;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TRANSACTIONFLAG=true;

        CoinViewModel model = new ViewModelProvider(this).get(CoinViewModel.class);


        model.getTransactionCoinData("BTC").observe(this, new Observer<Map<String, TransactionData>>() {
            @Override
            public void onChanged(Map<String, TransactionData> transactionDataMap) {
                for (int i = 0; i < strings.length; i++) {
                    if (transactionDataMap.containsKey(strings[i])) {
                        Log.i(strings[i], transactionDataMap.get(strings[i]).toString());
                    }
                }

            }
        });

        model.refrashTransactionDataThread(strings);

        model.getNewCoinData().observe(this, new Observer<TickerData>() {
            @Override
            public void onChanged(TickerData tickerData) {
                name=tickerData.getBtc().getName();
            }
        });
        Log.i("메인", name);

        model.getNewCoinData();

//        model.getLastCoinData(COINNAME, INTERVALS);
    }


}


