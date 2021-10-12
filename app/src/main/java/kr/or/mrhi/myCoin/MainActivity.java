package kr.or.mrhi.myCoin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import kr.or.mrhi.myCoin.viewModel.CoinViewModel;

public class MainActivity extends AppCompatActivity {
    static final String COINNAME = "DOGE", INTERVALS = "5m";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CoinViewModel model = new ViewModelProvider(this).get(CoinViewModel.class);
//
//        Observer<List<Data>> observer = new Observer<List<Data>>() {
//
//            @Override
//            public void onChanged(List<Data> data) {
//                Log.i("옵져버", ""+data.get(data.size()-1).getBtc().getAccTradeValue());
//            }
//        };


//        model.getNewCoinData().observe(this, observer);
//        model.refrashNewCoinDataThread();
        //model.getLastCoinData(COINNAME, INTERVALS).getValue();

//        DBController controller = new DBController(this);
//        controller.insertTransaction(new Transaction(
//                "BTC", "true", null, 10L, 1000L, 3000L));
//
//        Log.i("디비", controller.getTransactionList().get(0).toString()) ;

        model.getOrderBookCoinData();

    }
}


