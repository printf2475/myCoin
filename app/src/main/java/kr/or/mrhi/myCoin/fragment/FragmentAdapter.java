package kr.or.mrhi.myCoin.fragment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {
    private final static int NUM_PAGES = 2;
    public FragmentAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            Fragment fragment1 = new Fragment_LikeCoin();
            return fragment1;

        } else if (position == 1) {
            Fragment fragment2 = new Fragment_AllCoins();

            return fragment2;
        } else {
            Log.d("음악플레이어", "createFragment() 프레그먼트 생성 오류");
            return null;
        }
    }

    //뷰페이지 개수
    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}//end of adapter

