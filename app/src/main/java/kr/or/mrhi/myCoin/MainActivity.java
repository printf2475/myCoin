package kr.or.mrhi.myCoin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import kr.or.mrhi.myCoin.POJO.TransactionData;
import kr.or.mrhi.myCoin.viewModel.CoinViewModel;

public class MainActivity extends AppCompatActivity{
    static final String COINNAME = "DOGE", INTERVALS = "5m";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CoinViewModel model = new ViewModelProvider(this).get(CoinViewModel.class);

        for (int i=0; i<2; i++){
            model.getTransactionCoinData("BTC").observe(this, new Observer<TransactionData>() {
                @Override
                public void onChanged(TransactionData transactionData) {
                    Log.i("트랜", transactionData.getPrice());
                }
            });
        }

        model.refrashTransactionDataThread("BTC");

        model.getLastCoinData(COINNAME, INTERVALS);
    }


}


