package kr.or.mrhi.myCoin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import kr.or.mrhi.myCoin.DBController;
import kr.or.mrhi.myCoin.R;
import kr.or.mrhi.myCoin.adapter.TransactionHistoryAdapter;

public class Fragment_transactionHistory extends Fragment {

    RecyclerView recyclerView;
    DBController controller;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_history, container, false);
        recyclerView = view.findViewById(R.id.transactionRecycler);
        controller = new DBController(requireActivity());


        TransactionHistoryAdapter adapter = new TransactionHistoryAdapter(controller.getTransactionList());
        recyclerView.setAdapter(adapter);

        return view;
    }
}
