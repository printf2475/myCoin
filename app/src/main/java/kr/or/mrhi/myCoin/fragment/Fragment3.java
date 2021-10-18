package kr.or.mrhi.myCoin.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kr.or.mrhi.myCoin.DBController;
import kr.or.mrhi.myCoin.R;
import kr.or.mrhi.myCoin.Transaction;

public class Fragment3 extends Fragment {

    private DBController dbController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        // Inflate the layout for this fragment

        dbController = new DBController(getActivity().getApplicationContext());
        Transaction transaction = dbController.getCoinTransaction("BTC");
        Log.i("DB값", transaction.toString());



        return view;
    }

}