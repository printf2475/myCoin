package kr.or.mrhi.myCoin.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.or.mrhi.myCoin.network.DBController;
import kr.or.mrhi.myCoin.R;
import kr.or.mrhi.myCoin.adapter.TransactionHistoryAdapter;
import kr.or.mrhi.myCoin.model.Transaction;

public class Fragment_transactionHistory extends Fragment {

    RecyclerView recyclerView;
    DBController controller;
    List<Transaction> transactionList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_history, container, false);
        recyclerView = view.findViewById(R.id.transactionRecycler);
        controller = new DBController(requireActivity());
        transactionList = controller.getTransactionList();
        for (Transaction transaction : transactionList){
            Log.i("거래내역" ,transaction.toString());
        }

        TransactionHistoryAdapter adapter = new TransactionHistoryAdapter(transactionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}
