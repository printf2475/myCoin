package kr.or.mrhi.myCoin.adapter;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import kr.or.mrhi.myCoin.fragment.Fragment_AllCoins;
import kr.or.mrhi.myCoin.fragment.Fragment_LikeCoin;

public class CoinListAdapter extends FragmentStateAdapter {
    private final static int NUM_PAGES = 2;
    String searchName;
    Fragment fragment1;
    Fragment fragment2;
    public CoinListAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        fragment1 = new Fragment_AllCoins();
        if (position == 0) {
            if (searchName!=null){
                Bundle bundle = new Bundle();
                bundle.putString("searchName", searchName);
                fragment1.setArguments(bundle);
            }
            return fragment1;

        } else if (position == 1) {
            fragment2 = new Fragment_LikeCoin();
            if (searchName!=null){
                Bundle bundle = new Bundle();
                bundle.putString("searchName", searchName);
                fragment2.setArguments(bundle);
            }
            return fragment2;
        } else {
            Log.d("프레그먼트", "createFragment() 프레그먼트 생성 오류");
            return null;
        }
    }

    //뷰페이지 개수
    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }

    public void setSearchList(String searchName) {
        this.searchName=searchName;

    }


}//end of adapter

