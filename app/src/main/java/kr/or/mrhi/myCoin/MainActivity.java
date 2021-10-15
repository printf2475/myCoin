package kr.or.mrhi.myCoin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Map;
import java.util.Set;

import kr.or.mrhi.myCoin.POJO.TransactionData;
import kr.or.mrhi.myCoin.POJO.tickerCoins.True;
import kr.or.mrhi.myCoin.viewModel.CoinViewModel;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerViewMain;

    //    static final String COINNAME = "DOGE", INTERVALS = "5m";
    public static final String[] strings = new String[]{"BTC", "ETH", "BCH", "LTC", "BSV", "AXS", "BTG",
            "ETC", "DOT", "ATOM", "WAVES", "LINK", "REP", "OMG", "QTUM",};
    public static Boolean TRANSACTIONFLAG = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TRANSACTIONFLAG = true;

        bottomNavigationView = findViewById(R.id.bottomNavi);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //item을 클릭시 id값을 가져와 FrameLayout에 fragment.xml띄우기
                    case R.id.fragment1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Fragment_Coins()).commit();
                        break;
                    case R.id.fragment2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Fragment2()).commit();
                        break;
                    case R.id.fragment3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Fragment3()).commit();
                        break;
                    default:
                        finish();
                }
                return true;
            }
        });

    }


}


