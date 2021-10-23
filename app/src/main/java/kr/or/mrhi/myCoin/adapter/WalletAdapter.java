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
    List<String> myCoinNameList;
    private List<Transaction> transactionList;
    private TickerData tickerCoin;
    Context context;
    TextView myCoinName, myCoinMarginCount, myCoinPercentCount, myCoinAmountCount, myCoinAveragePriceCount, myCoinValueCount, myCoinTicker, myCoinBuyPriceCount;

    public WalletAdapter(List<Transaction> transactionList, List<String> myCoinNameList) {
        this.transactionList = transactionList;
        this.myCoinNameList = myCoinNameList;

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

    public void setTickerData(TickerData tickerData) {
        this.tickerCoin = tickerData;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        double currentPrice = 0.0;

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


        if (i < transactionList.size() && tickerCoin != null && !myCoinNameList.isEmpty()) {
            currentPrice = Double.parseDouble(transactionList.get(i).getPrice());
            myCoinTicker.setText("(" + myCoinNameList.get(i) + ")");
            if (myCoinNameList.get(i).equals("BTC")) {
                myCoinName.setText("비트코인");

            } else if (myCoinNameList.get(i).equals("ETH")) {
                myCoinName.setText("이더리움");

            } else if (myCoinNameList.get(i).equals("BCH")) {
                myCoinName.setText("비트코인캐시");

            } else if (myCoinNameList.get(i).equals("LTC")) {
                myCoinName.setText("라이트코인");

            } else if (myCoinNameList.get(i).equals("BSV")) {
                myCoinName.setText("비트코인에스브이");

            } else if (myCoinNameList.get(i).equals("AXS")) {
                myCoinName.setText("엑시인피니티");

            } else if (myCoinNameList.get(i).equals("BTG")) {
                myCoinName.setText("비트코인골드");

            } else if (myCoinNameList.get(i).equals("ETC")) {
                myCoinName.setText("이더리움클래식");

            } else if (myCoinNameList.get(i).equals("DOT")) {
                myCoinName.setText("폴카닷");

            } else if (myCoinNameList.get(i).equals("ATOM")) {
                myCoinName.setText("코스모스");

            } else if (myCoinNameList.get(i).equals("WAVES")) {
                myCoinName.setText("웨이브");

            } else if (myCoinNameList.get(i).equals("LINK")) {
                myCoinName.setText("체인링크");

            } else if (myCoinNameList.get(i).equals("REP")) {
                myCoinName.setText("어거");

            } else if (myCoinNameList.get(i).equals("OMG")) {
                myCoinName.setText("오미세고");

            } else if (myCoinNameList.get(i).equals("QTUM")) {
                myCoinName.setText("퀀텀");

            }

            double among = Double.parseDouble(transactionList.get(i).getQuantity());
            double price = Double.parseDouble(transactionList.get(i).getPrice());

            double valueCount = currentPrice * among;
            double priceCount = among * price;



            myCoinMarginCount.setText(String.format("%.0f", priceCount - valueCount));
            myCoinAmountCount.setText(String.format("%.0f", among));
            myCoinAveragePriceCount.setText(String.format("%.0f", price / among));
            myCoinValueCount.setText(String.format("%.0f", valueCount));
            myCoinBuyPriceCount.setText(String.format("%.0f", priceCount));
            myCoinPercentCount.setText(String.format("%.0f", (valueCount -  priceCount) / valueCount*100));
        }
        return view;
    }
}
