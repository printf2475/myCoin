package kr.or.mrhi.myCoin.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import kr.or.mrhi.myCoin.R;
import kr.or.mrhi.myCoin.adapter.CoinListAdapter;


public class Fragment_Coins extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 pager;

    private final static String[] tabElement = {"LIKE COINS", "ALL COINS"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_main, container, false);

        tabLayout = view.findViewById(R.id.tabLayout2);
        pager = view.findViewById(R.id.pager);

        CoinListAdapter screenSlidePagerAdapter = new CoinListAdapter(getActivity());
        pager.setAdapter(screenSlidePagerAdapter);


        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
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