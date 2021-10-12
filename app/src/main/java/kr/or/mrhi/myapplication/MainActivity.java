package kr.or.mrhi.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import kr.or.mrhi.myapplication.viewModel.CoinViewModel;

public class MainActivity extends AppCompatActivity {
    static final String COINNAME = "DOGE", INTERVALS = "5m";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CoinViewModel model = new ViewModelProvider(this).get(CoinViewModel.class);
        //model.getNewCoinData();
        model.getLastCoinData(COINNAME, INTERVALS).getValue();
    }
}


