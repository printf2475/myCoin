package kr.or.mrhi.myCoin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    public static final String[] stringSymbol = new String[]{"BTC", "ETH", "BCH", "LTC", "BSV", "AXS", "BTG",
            "ETC", "DOT", "ATOM", "WAVES", "LINK", "REP", "OMG", "QTUM",};

    public static final String[] stringName = new String[]{"비트코인", "이더리움", "비트코인캐시", "라이트코인",
            "비트코인SV", "엑시인피니티", "비트코인골드","이더리움클래식", "폴카닷", "코스모스", "웨이브",
            "체인링크", "어거", "오미세고", "퀀텀"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //item을 클릭시 id값을 가져와 FrameLayout에 fragment.xml띄우기
                    case R.id.main:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Fragment_Main()).commit();
                        break;
                    case R.id.news:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new FragmentRss()).commit();
                        break;
                    case R.id.wallet:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new FragmentWallet()).commit();
                        break;
                    default:
                        finish();
                }
                return true;
            }
        });
    }
}


