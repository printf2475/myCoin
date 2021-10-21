package kr.or.mrhi.myCoin;

import static kr.or.mrhi.myCoin.MainActivity.stringName;
import static kr.or.mrhi.myCoin.MainActivity.stringSymbol;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import kr.or.mrhi.myCoin.POJO.CandleCoinData;
import kr.or.mrhi.myCoin.POJO.TickerPOJOData;
import kr.or.mrhi.myCoin.model.Transaction;
import kr.or.mrhi.myCoin.viewModel.CoinViewModel;

public class CoinMain extends AppCompatActivity implements View.OnClickListener {
    private TextView ACD_CoinName, ACD_PriceChange, ACD_tvCompare, ACD_Percent, ACD_CoinPrice, totalAmountCount, orderAvailableCount;
    private LineChart lineChart;
    private CandleStickChart candleChart;
    private ImageView btnFavorite,ivUpDown;
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
    private List<CandleCoinData> candleCoinData;

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
        ivUpDown = findViewById(R.id.ivUpDown);



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
                    ivUpDown.setVisibility(View.GONE);
                } else if (mainPercent < 0.00) {
                    ACD_CoinPrice.setTextColor(Color.BLUE);
                    ACD_Percent.setTextColor(Color.BLUE);
                    ACD_PriceChange.setTextColor(Color.BLUE);
                    ivUpDown.setImageResource(R.drawable.decrease);
                } else if (mainPercent > 0.00) {
                    ACD_CoinPrice.setTextColor(Color.RED);
                    ACD_Percent.setTextColor(Color.RED);
                    ACD_PriceChange.setTextColor(Color.RED);
                    ivUpDown.setImageResource(R.drawable.increase);
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

        model.getCandleCoinData(mainCoinName, "1m").observe(this, new Observer<List<CandleCoinData>>() {
            @Override
            public void onChanged(List<CandleCoinData> candleCoinData) {
                Log.d("캔들", candleCoinData.size()+"");
                candleDataSet(candleCoinData);
            }
        });
    }

    public void candleDataSet(List<CandleCoinData> candleCoinData) {
        ArrayList<CandleEntry> values = new ArrayList<>();

        for (int i = 0; i < candleCoinData.size(); i++) {
            float val = candleCoinData.size();
            float high = Float.parseFloat(candleCoinData.get(i).getMaxPrice());
            float low = Float.parseFloat(candleCoinData.get(i).getMinPrice());
            float open = Float.parseFloat(candleCoinData.get(i).getOpeningPrice());
            float close = Float.parseFloat(candleCoinData.get(i).getClosingPrice());

            boolean odd = i % 2 != 0;
            values.add(new CandleEntry(i + 1, high, low, open, close));

        }

        candleChart.setBackgroundColor(Color.WHITE);

        candleChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        candleChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        candleChart.setPinchZoom(false);

        candleChart.setDrawGridBackground(true);

        XAxis xAxis = candleChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = candleChart.getAxisLeft();
//        leftAxis.setEnabled(false);
        leftAxis.setLabelCount(7, false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = candleChart.getAxisRight();
        rightAxis.setEnabled(false);
//        rightAxis.setStartAtZero(false);

        candleChart.getLegend().setEnabled(false);

        CandleDataSet set1 = new CandleDataSet(values, "Data Set");

        set1.setDrawIcons(false);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
//        set1.setColor(Color.rgb(80, 80, 80));
        set1.setShadowColor(Color.DKGRAY);
        set1.setShadowWidth(0.7f);
        set1.setDecreasingColor(Color.RED);
        set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setIncreasingColor(Color.rgb(122, 242, 84));
        set1.setIncreasingPaintStyle(Paint.Style.STROKE);
        set1.setNeutralColor(Color.BLUE);
        //set1.setHighlightLineWidth(1f);

        CandleData data = new CandleData(set1);

        candleChart.setData(data);
        candleChart.invalidate();
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