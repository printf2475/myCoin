package kr.or.mrhi.myCoin;

import static kr.or.mrhi.myCoin.MainActivity.stringName;
import static kr.or.mrhi.myCoin.MainActivity.stringSymbol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import kr.or.mrhi.myCoin.POJO.TickerPOJOData;
import kr.or.mrhi.myCoin.model.Transaction;
import kr.or.mrhi.myCoin.viewModel.CoinViewModel;

public class CoinMain extends AppCompatActivity implements View.OnClickListener {
    private TextView ACD_CoinName, ACD_PriceChange, ACD_tvCompare, ACD_Percent, ACD_CoinPrice, totalAmountCount, orderAvailableCount;
    private LineChart lineChart;
    private CandleStickChart candleChart;
    private ImageView btnFavorite;
    private Button btnSell, btnBuy;
    private EditText orderAmount_edttext;
    private TabLayout tabLayout;

    private String mainCoinName, mainCoinPrice;
    private double mainPercent, mainChangePrice, totalPriceTemp=0.00;
    private int position;

    private CoinViewModel model;
    private Transaction transaction;
    private DBController dbController;
    private static final int DEFAULTVALUE = 1000;
    private String prevClosingPrice = "0.00";
    private int count = 0, tabLayoutPosition = 0;

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

        ACD_CoinName = findViewById(R.id.ACD_CoinName);
        ACD_PriceChange = findViewById(R.id.ACD_PriceChange);
        ACD_tvCompare = findViewById(R.id.ACD_tvCompare);
        ACD_Percent = findViewById(R.id.ACD_Percent);
        ACD_CoinPrice = findViewById(R.id.ACD_CoinPrice);
        totalAmountCount = findViewById(R.id.totalAmountCount);
        orderAvailableCount = findViewById(R.id.orderAvailableCount);
        lineChart = findViewById(R.id.lineChart);
        candleChart = findViewById(R.id.candleChart);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnBuy = findViewById(R.id.btnBuy);
        orderAmount_edttext = findViewById(R.id.orderAmount_edttext);
        tabLayout = findViewById(R.id.tabLayout2);

//        btnSell.setOnClickListener(this);
//        btnBuy.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
        btnFavorite.setOnClickListener(this);

        tabLayoutPosition = tabLayout.getSelectedTabPosition();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayoutPosition = tab.getPosition();
                if (tabLayoutPosition==0){
                    btnBuy.setText("매수");
                }else if(tabLayoutPosition==1){
                    btnBuy.setText("매도");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        ACD_CoinName.setText(stringName[position] + "(" + mainCoinName + "/KRW)");
        ACD_CoinPrice.setText(mainCoinPrice);

        orderAmount_edttext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (orderAmount_edttext.getText().length() != 0) {
                    totalPriceTemp = Double.parseDouble(orderAmount_edttext.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        model.getTransactionCoinData(mainCoinName).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> stringList) {
                ACD_CoinPrice.setText(stringList.get(position));
                mainChangePrice = Double.parseDouble(stringList.get(position)) - Double.parseDouble(prevClosingPrice);
                mainPercent = Double.parseDouble(String.format("%.2f", (mainChangePrice) / Double.parseDouble(prevClosingPrice) * 100));
                if (mainPercent == 0.00) {
                    ACD_CoinPrice.setTextColor(Color.BLACK);
                    ACD_Percent.setTextColor(Color.BLACK);
                    ACD_PriceChange.setTextColor(Color.BLACK);
                } else if (mainPercent < 0.00) {
                    ACD_CoinPrice.setTextColor(Color.BLUE);
                    ACD_Percent.setTextColor(Color.BLUE);
                    ACD_PriceChange.setTextColor(Color.BLUE);
                } else if (mainPercent > 0.00) {
                    ACD_CoinPrice.setTextColor(Color.RED);
                    ACD_Percent.setTextColor(Color.RED);
                    ACD_PriceChange.setTextColor(Color.RED);
                }
                ACD_Percent.setText(String.valueOf(mainPercent) + "%");
                ACD_PriceChange.setText(String.valueOf(mainChangePrice));

                totalAmountCount.setText(String.valueOf((int)totalPriceTemp * Double.parseDouble(stringList.get(position))));

//                Log.i("값이 오나", stringList.get(position).toString());
//                Log.i("전일대비", String.valueOf((Double.parseDouble(stringList.get(position)) - Double.parseDouble(prevClosingPrice)) / Double.parseDouble(prevClosingPrice) * 100));//전일대비 변동률 작동 확인 완료!
            }
        });

        model.refrashTransactionDataThread(new String[]{mainCoinName});

        model.getTickerDTO(mainCoinName).observe(this, new Observer<TickerPOJOData>() {
            @Override
            public void onChanged(TickerPOJOData tickerPOJOData) {
                prevClosingPrice = tickerPOJOData.getPrevClosingPrice();
                Log.i("변동률과 거래대금", tickerPOJOData.getPrevClosingPrice().toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnFavorite:
                if (count % 2 == 0) {
                    dbController.insertFavorite(stringSymbol[position]);
                    btnFavorite.setImageResource(R.drawable.likebutton);
                    count++;
                } else {
                    dbController.deleteFavoritesList(stringSymbol[position]);
                    btnFavorite.setImageResource(R.drawable.unlikebutton);
                    count++;
                }
                break;
            case R.id.btnBuy:
                Log.i("DB값", "눌림");
                if (tabLayoutPosition == 0) {
                    String buyCoinNumber = orderAmount_edttext.getText().toString();
                    String buyCoinPrice = ACD_CoinPrice.getText().toString();
                    dbController.insertTransaction(new Transaction(stringSymbol[position], "buy", "", buyCoinNumber, buyCoinPrice, 100000, null));
                    transaction = dbController.getCoinTransaction(mainCoinName);
                    Log.i("DB값 매수", transaction.toString());
                    break;
                } else if (tabLayoutPosition == 1) {
                    String buyCoinNumber = orderAmount_edttext.getText().toString();
                    String buyCoinPrice = ACD_CoinPrice.getText().toString();
                    dbController.insertTransaction(new Transaction(stringSymbol[position], "sell", "", buyCoinNumber, buyCoinPrice, 100000, null));
                    transaction = dbController.getCoinTransaction(mainCoinName);
                    Log.i("DB값 매도", transaction.toString());
                    break;
                }
        }

    }
}