package kr.or.mrhi.myCoin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.RecoverySystem;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kr.or.mrhi.myCoin.POJO.TickerData;
import kr.or.mrhi.myCoin.POJO.TransactionData;


public class Fragment_LikeCoin extends Fragment {
    private RecyclerView recyclerView1;
    private List<TransactionData> transactionCoin;
    private List<TickerData> tickerCoin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fragment1_1, container, false);



        return view;
    }
}