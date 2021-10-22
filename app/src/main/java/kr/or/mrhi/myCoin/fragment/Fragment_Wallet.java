package kr.or.mrhi.myCoin.fragment;

import static kr.or.mrhi.myCoin.MainActivity.namePositionMap;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import kr.or.mrhi.myCoin.DBController;
import kr.or.mrhi.myCoin.R;
import kr.or.mrhi.myCoin.adapter.OwnBankAdapter;
import kr.or.mrhi.myCoin.model.Transaction;
import kr.or.mrhi.myCoin.viewModel.CoinViewModel;

public class Fragment_Wallet extends Fragment {

    private TabLayout tabLayout2;
    private ViewPager2 pager2;


    private final static String[] tabElement = {"보유코인", "거래내역"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        // Inflate the layout for this fragment
        pager2 = view.findViewById(R.id.pager2);
        tabLayout2 = view.findViewById(R.id.tabLayout2);



        OwnBankAdapter ownBankAdapter = new OwnBankAdapter(getActivity());
        pager2.setAdapter(ownBankAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout2, pager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView = new TextView(requireActivity());
                textView.setGravity(Gravity.CENTER);
                textView.setText(tabElement[position]);
                tab.setCustomView(textView);
            }
        });
        tabLayoutMediator.attach();

        return view;
    }

}