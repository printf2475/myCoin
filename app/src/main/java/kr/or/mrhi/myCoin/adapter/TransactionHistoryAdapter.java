package kr.or.mrhi.myCoin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.or.mrhi.myCoin.DBController;
import kr.or.mrhi.myCoin.R;
import kr.or.mrhi.myCoin.model.Transaction;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolders> {

    Context context;
    List<Transaction> transactionList;

    public TransactionHistoryAdapter(List<Transaction> transactionList){
        this.transactionList = transactionList;
    }
    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.fragment_transacitem, parent, false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView time, coinName, coinAmount, coinPrice, coinTotalPrice;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            time= itemView.findViewById(R.id.transactionItemTime);
            coinName= itemView.findViewById(R.id.transactionItemCoin);
            coinAmount= itemView.findViewById(R.id.transactionItemAmount);
            coinPrice= itemView.findViewById(R.id.transactionItemPrice);
            coinTotalPrice= itemView.findViewById(R.id.transactionItemTotalPrice);
        }

        public void onBind(int position) {
            Double amount = Double.parseDouble(transactionList.get(position).getQuantity());
            Double price = Double.parseDouble(transactionList.get(position).getPrice());
            String totalPrice = String.format("%.2f",amount*price);

            coinName.setText(transactionList.get(position).getCoinName());
            time.setText(transactionList.get(position).getTransactionTime());
            coinAmount.setText(transactionList.get(position).getQuantity());
            coinPrice.setText(transactionList.get(position).getPrice());
            coinTotalPrice.setText(totalPrice);

        }
    }
}
