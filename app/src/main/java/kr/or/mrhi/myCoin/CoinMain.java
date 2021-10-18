package kr.or.mrhi.myCoin;

import static kr.or.mrhi.myCoin.MainActivity.stringSymbol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import kr.or.mrhi.myCoin.POJO.TickerPOJOData;
import kr.or.mrhi.myCoin.model.Transaction;
import kr.or.mrhi.myCoin.viewModel.CoinViewModel;

public class CoinMain extends AppCompatActivity implements View.OnClickListener{
    private TextView coinName,coinSymbol,currentCoinPrice;
    private Button btnSell,btnBuying;
    private EditText edtCoinNumber;

    private String mainCoinName,mainCoinPrice;
    private int position;

    private CoinViewModel model;
    private Transaction transaction;
    private DBController dbController;
    private static final int DEFAULTVALUE=1000;
    private String prevClosingPrice = "0.00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_detail);
        dbController = new DBController(this);

        model = new ViewModelProvider(this).get(CoinViewModel.class);


        Intent intent = getIntent();
        mainCoinName = intent.getStringExtra("CoinID");
        mainCoinPrice = intent.getStringExtra("CoinData");
        position = intent.getIntExtra("Position", DEFAULTVALUE);

        coinName = findViewById(R.id.coinName);
        coinSymbol = findViewById(R.id.coinSymbol);
        currentCoinPrice = findViewById(R.id.currentCoinPrice);
        btnSell = findViewById(R.id.btnSell);
        btnBuying = findViewById(R.id.btnBuying);
        edtCoinNumber = findViewById(R.id.edtCoinNumber);

        btnSell.setOnClickListener(this);
        btnBuying.setOnClickListener(this);




        coinSymbol.setText(mainCoinName);
        currentCoinPrice.setText(mainCoinPrice);

        model.getTransactionCoinData(mainCoinName).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> stringList) {
                currentCoinPrice.setText(stringList.get(position));
                Log.i("값이 오나",stringList.get(position).toString());
                Log.i("전일대비",String.valueOf((Double.parseDouble(stringList.get(position))-Double.parseDouble(prevClosingPrice))/Double.parseDouble(prevClosingPrice)*100));//전일대비 변동률 작동 확인 완료!
            }
        });

        model.refrashTransactionDataThread(new String[]{mainCoinName});

        model.getTickerDTO(mainCoinName).observe(this, new Observer<TickerPOJOData>() {
            @Override
            public void onChanged(TickerPOJOData tickerPOJOData) {
                prevClosingPrice = tickerPOJOData.getPrevClosingPrice();
                Log.i("변동률과 거래대금",tickerPOJOData.getPrevClosingPrice().toString());
            }
        });
    }
//
//
//
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBuying:
//                dbController.insertTransaction(dbController.getWritableDatabase(),String.valueOf(Integer.parseInt(currentCoinPrice)*Integer.parseInt(coinNumber.getText().toString())));
                   String buyCoinNumber = edtCoinNumber.getText().toString();
                   String buyCoinPrice = currentCoinPrice.getText().toString();
//                   String buyingPrice = String.valueOf(Double.parseDouble(buyCoinNumber)*Double.parseDouble(buyCoinPrice));
                dbController.insertTransaction(new Transaction(stringSymbol[position],"buy", "", buyCoinNumber, buyCoinPrice, 100000, null));
                transaction = dbController.getCoinTransaction(mainCoinName);
                Log.i("DB값", transaction.toString());
                break;

            case R.id.btnSell:
//                dbController.insertTransaction(dbController.getWritableDatabase(),String.valueOf(Integer.parseInt(currentCoinPrice)*Integer.parseInt(coinNumber.getText().toString())));
                String sellCoinNumber = edtCoinNumber.getText().toString();
                String sellCoinPrice = currentCoinPrice.getText().toString();
//                String sellPrice = String.valueOf(Double.parseDouble(sellCoinNumber)*Double.parseDouble(sellCoinPrice));
                dbController.insertTransaction(new Transaction(stringSymbol[position],"sell", "", sellCoinNumber, sellCoinPrice, 1020000, null));
                 transaction = dbController.getCoinTransaction(mainCoinName);
                Log.i("DB값", transaction.toString());
                break;
        }
    }
}