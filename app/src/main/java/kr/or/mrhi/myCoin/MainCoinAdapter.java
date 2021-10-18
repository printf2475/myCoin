package kr.or.mrhi.myCoin;

import static kr.or.mrhi.myCoin.MainActivity.strings;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.or.mrhi.myCoin.POJO.TickerData;
import kr.or.mrhi.myCoin.POJO.TickerPOJOData;
import kr.or.mrhi.myCoin.viewModel.CoinViewModel;

public class MainCoinAdapter extends RecyclerView.Adapter<MainCoinAdapter.ViewHolders> {
    private List<String> transactionCoin;
    private TickerData tickerCoin;
    private Context context;



    public MainCoinAdapter(List<String> transactionCoin ,TickerData tickerData) {
        this.transactionCoin = transactionCoin;
        this.tickerCoin = tickerData;


    }

    @NonNull
    @Override
    public MainCoinAdapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coinlist_item,parent,false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainCoinAdapter.ViewHolders holder, int position) {
        holder.onBind(position);
    }



    @Override
    public int getItemCount() {
        return transactionCoin.size();
    }



    public class ViewHolders extends RecyclerView.ViewHolder {
            TextView coinId,coinCurrentPrice,coinCompareYesterday;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            coinId = itemView.findViewById(R.id.coinId);
            coinCurrentPrice = itemView.findViewById(R.id.coinCurrentPrice);
            coinCompareYesterday = itemView.findViewById(R.id.coinCompareYesterday);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    for(int position = 0;position<15;position++) {
                        int position = getAdapterPosition();
                        Intent intent = new Intent(view.getContext(), CoinMain.class);
                        intent.putExtra("CoinID", strings[position]);
                        intent.putExtra("CoinData", transactionCoin.get(position));
                        intent.putExtra("Position", position);
//                    intent.putExtra("CoinCurrentPrice", String.valueOf(coinCurrentPrice));
                        view.getContext().startActivity(intent);
                        Log.i("값이가나", intent.toUri(0));
//                    }
                }
            });
        }

        public void onBind(int position) {

            coinId.setText(strings[position]);
            coinCurrentPrice.setText(transactionCoin.get(position));
//            coinCompareYesterday.setText(String.valueOf(currentPrice/closingPrice*100-100));
        }
    }
//    private void startActivity(Intent intent) {
//        startActivity(intent);
//    }
}
