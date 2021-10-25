package kr.or.mrhi.myCoin.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kr.or.mrhi.myCoin.DBController;
import kr.or.mrhi.myCoin.R;
import kr.or.mrhi.myCoin.adapter.TransactionHistoryAdapter;
import kr.or.mrhi.myCoin.model.Transaction;


public class Fragment_transactionHistory extends Fragment {
    RecyclerView recyclerView;
    DBController controller;
    List<Transaction> transactionList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_history, container, false);
        controller = new DBController(requireActivity());
        transactionList = controller.getTransactionList();
        for (int i=0; i<transactionList.size(); i++){
            Log.i("거래",  transactionList.get(i).toString());
        }
        TransactionHistoryAdapter adapter = new TransactionHistoryAdapter(transactionList);
        recyclerView = view.findViewById(R.id.transactionRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }
}