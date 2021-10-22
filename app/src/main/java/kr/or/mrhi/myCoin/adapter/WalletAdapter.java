package kr.or.mrhi.myCoin.adapter;

import static kr.or.mrhi.myCoin.MainActivity.namePositionMap;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.or.mrhi.myCoin.POJO.TickerData;
import kr.or.mrhi.myCoin.POJO.TickerPOJOData;
import kr.or.mrhi.myCoin.POJO.TransactionData;
import kr.or.mrhi.myCoin.R;
import kr.or.mrhi.myCoin.model.Transaction;

public class WalletAdapter extends BaseAdapter {
    private String currentPrice = null;
    private List<TransactionData> transactionData;
    List<String> searchList;
    private List<Transaction> transactionList;
    private TickerData tickerCoin;
    Context context;
    TextView myCoinName, myCoinMarginCount, myCoinPercentCount, myCoinAmountCount, myCoinAveragePriceCount, myCoinValueCount, myCoinTicker, myCoinBuyPriceCount;

    public WalletAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public int getCount() {
        return transactionList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        double curruntPrice = 0.0;

        context = viewGroup.getContext();
        view = LayoutInflater.from(context).inflate(R.layout.my_coin_item, viewGroup, false);

        myCoinName = view.findViewById(R.id.myCoinName);
        myCoinMarginCount = view.findViewById(R.id.myCoinMarginCount);
        myCoinPercentCount = view.findViewById(R.id.myCoinPercentCount);
        myCoinAmountCount = view.findViewById(R.id.myCoinAmountCount);
        myCoinAveragePriceCount = view.findViewById(R.id.myCoinAveragePriceCount);
        myCoinValueCount = view.findViewById(R.id.myCoinValueCount);
        myCoinBuyPriceCount = view.findViewById(R.id.myCoinBuyPriceCount);
        //
        myCoinTicker = view.findViewById(R.id.myCoinTicker);


        if (i < transactionData.size() && tickerCoin != null && !searchList.isEmpty()) {
            currentPrice = String.valueOf(transactionData.get(i));
            myCoinName.setText(searchList.get(i));
            if (myCoinName.getText().equals("BTC")) {
                myCoinTicker.setText("(" + myCoinName + ")");
                myCoinMarginCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinPercentCount.setText(String.valueOf((Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
//          매수평균가 myCoinAveragePriceCount.setText(/transactionList.get(i).getQuantity());
                myCoinValueCount.setText(String.valueOf(Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinBuyPriceCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice())));
            } else if (myCoinName.getText().equals("ETH")) {
                myCoinTicker.setText("(" + myCoinName + ")");
                myCoinMarginCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinPercentCount.setText(String.valueOf((Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
//          매수평균가 myCoinAveragePriceCount.setText(/transactionList.get(i).getQuantity());
                myCoinValueCount.setText(String.valueOf(Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinBuyPriceCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice())));
            } else if (myCoinName.getText().equals("BCH")) {
                myCoinTicker.setText("(" + myCoinName + ")");
                myCoinMarginCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinPercentCount.setText(String.valueOf((Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
//          매수평균가 myCoinAveragePriceCount.setText(/transactionList.get(i).getQuantity());
                myCoinValueCount.setText(String.valueOf(Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinBuyPriceCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice())));
            } else if (myCoinName.getText().equals("LTC")) {
                myCoinTicker.setText("(" + myCoinName + ")");
                myCoinMarginCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinPercentCount.setText(String.valueOf((Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
//          매수평균가 myCoinAveragePriceCount.setText(/transactionList.get(i).getQuantity());
                myCoinValueCount.setText(String.valueOf(Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinBuyPriceCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice())));
            } else if (myCoinName.getText().equals("BSV")) {
                myCoinTicker.setText("(" + myCoinName + ")");
                myCoinMarginCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinPercentCount.setText(String.valueOf((Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
//          매수평균가 myCoinAveragePriceCount.setText(/transactionList.get(i).getQuantity());
                myCoinValueCount.setText(String.valueOf(Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinBuyPriceCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice())));
            } else if (myCoinName.getText().equals("AXS")) {
                myCoinTicker.setText("(" + myCoinName + ")");
                myCoinMarginCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinPercentCount.setText(String.valueOf((Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
//          매수평균가 myCoinAveragePriceCount.setText(/transactionList.get(i).getQuantity());
                myCoinValueCount.setText(String.valueOf(Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinBuyPriceCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice())));
            } else if (myCoinName.getText().equals("BTG")) {
                myCoinTicker.setText("(" + myCoinName + ")");
                myCoinMarginCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinPercentCount.setText(String.valueOf((Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
//          매수평균가 myCoinAveragePriceCount.setText(/transactionList.get(i).getQuantity());
                myCoinValueCount.setText(String.valueOf(Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinBuyPriceCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice())));
            } else if (myCoinName.getText().equals("ETC")) {
                myCoinTicker.setText("(" + myCoinName + ")");
                myCoinMarginCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinPercentCount.setText(String.valueOf((Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
//          매수평균가 myCoinAveragePriceCount.setText(/transactionList.get(i).getQuantity());
                myCoinValueCount.setText(String.valueOf(Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinBuyPriceCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice())));
            } else if (myCoinName.getText().equals("DOT")) {
                myCoinTicker.setText("(" + myCoinName + ")");
                myCoinMarginCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinPercentCount.setText(String.valueOf((Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
//          매수평균가 myCoinAveragePriceCount.setText(/transactionList.get(i).getQuantity());
                myCoinValueCount.setText(String.valueOf(Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinBuyPriceCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice())));
            } else if (myCoinName.getText().equals("ATOM")) {
                myCoinTicker.setText("(" + myCoinName + ")");
                myCoinMarginCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinPercentCount.setText(String.valueOf((Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
//          매수평균가 myCoinAveragePriceCount.setText(/transactionList.get(i).getQuantity());
                myCoinValueCount.setText(String.valueOf(Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinBuyPriceCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice())));
            } else if (myCoinName.getText().equals("WAVES")) {
                myCoinTicker.setText("(" + myCoinName + ")");
                myCoinMarginCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinPercentCount.setText(String.valueOf((Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
//          매수평균가 myCoinAveragePriceCount.setText(/transactionList.get(i).getQuantity());
                myCoinValueCount.setText(String.valueOf(Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinBuyPriceCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice())));
            } else if (myCoinName.getText().equals("LINK")) {
                myCoinTicker.setText("(" + myCoinName + ")");
                myCoinMarginCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinPercentCount.setText(String.valueOf((Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
//          매수평균가 myCoinAveragePriceCount.setText(/transactionList.get(i).getQuantity());
                myCoinValueCount.setText(String.valueOf(Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinBuyPriceCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice())));
            } else if (myCoinName.getText().equals("REP")) {
                myCoinTicker.setText("(" + myCoinName + ")");
                myCoinMarginCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinPercentCount.setText(String.valueOf((Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
//          매수평균가 myCoinAveragePriceCount.setText(/transactionList.get(i).getQuantity());
                myCoinValueCount.setText(String.valueOf(Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinBuyPriceCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice())));
            } else if (myCoinName.getText().equals("OMG")) {
                myCoinTicker.setText("(" + myCoinName + ")");
                myCoinMarginCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinPercentCount.setText(String.valueOf((Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
//          매수평균가 myCoinAveragePriceCount.setText(/transactionList.get(i).getQuantity());
                myCoinValueCount.setText(String.valueOf(Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinBuyPriceCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice())));
            } else if (myCoinName.getText().equals("QTUM")) {
                myCoinTicker.setText("(" + myCoinName + ")");
                myCoinMarginCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinPercentCount.setText(String.valueOf((Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
//          매수평균가 myCoinAveragePriceCount.setText(/transactionList.get(i).getQuantity());
                myCoinValueCount.setText(String.valueOf(Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinBuyPriceCount.setText(String.valueOf(Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice())));
            }

            // 평가손익 textEvaluationProfitCount.setText(String.format("%.0f", evaluationProfitCount - totalBuyCount));
            //

        }
            return view;
    }
}
