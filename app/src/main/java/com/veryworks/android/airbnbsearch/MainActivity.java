package com.veryworks.android.airbnbsearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;
    private Button btnCheckin, btnCheckout;
    private CalendarView calendarView;

    private Search search;
    private TextView guest;
    private Button btnGuestMinus;
    private Button btnGuestPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initView();
        setListener();
        setCalendarButtonText();
    }

    private void init() {
        search = new Search();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        btnCheckin = (Button) findViewById(R.id.btnCheckin);
        btnCheckout = (Button) findViewById(R.id.btnCheckout);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        guest = (TextView) findViewById(R.id.guest);
        btnGuestMinus = (Button) findViewById(R.id.btnGuestMinus);
        btnGuestPlus = (Button) findViewById(R.id.btnGuestPlus);
    }

    private static final int CHECK_IN = 10;
    private static final int CHECK_OUT = 20;
    private int checkStatus = CHECK_IN;


    private void setListener() {
        calendarView.setOnDateChangeListener(dateChangeListener);
        btnCheckin.setOnClickListener(this);
        btnCheckout.setOnClickListener(this);
        fab.setOnClickListener(this);
        btnGuestMinus.setOnClickListener(this);
        btnGuestPlus.setOnClickListener(this);
    }

    private void setCalendarButtonText() {
        setButtonText(btnCheckin, getString(R.string.hint_start_date), getString(R.string.hint_select_date));
        setButtonText(btnCheckout, getString(R.string.hint_end_date), "-");
    }

    private void setButtonText(Button btn, String upText, String downText) {
        String inText = "<font color='#888888'>" + upText
                + "</font> <br> <font color=\"#fd5a5f\">" + downText + "</font>";
        StringUtil.setHtmlText(btn, inText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCheckin:
                checkStatus = CHECK_IN;
                setButtonText(btnCheckin, getString(R.string.hint_start_date), getString(R.string.hint_select_date));
                setButtonText(btnCheckout, getString(R.string.hint_end_date), search.checkoutDate);
                break;
            case R.id.btnCheckout:
                checkStatus = CHECK_OUT;
                setButtonText(btnCheckout, getString(R.string.hint_end_date), getString(R.string.hint_select_date));
                setButtonText(btnCheckin, getString(R.string.hint_start_date), search.checkinDate);
                break;
            case R.id.fab: // 검색 전송
                search();
                break;
            case R.id.btnGuestMinus:
                search.setGuestsMinus();
                guest.setText(search.guests + "");
                break;
            case R.id.btnGuestPlus:
                search.setGuestsPlus();
                guest.setText(search.guests + "");
                break;
        }
    }

    private void search() {
        // 1. 레트로핏 생성
        Retrofit client = new Retrofit.Builder()
                .baseUrl(ISearch.SERVER)
                //.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 2. 서비스 연결
        ISearch server = client.create(ISearch.class);

        // 3. 서비스의 특정 함수 호출 -> Observable 생성
        Observable<ResponseBody> observable = server.get(
                search.checkinDate,
                search.checkoutDate,
                search.guests,
                -1,
                -1,
                -1,
                -1
        );

        // 4. subscribe 등록
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseBody -> {
                            // 1. 데이터를 꺼내고
                            String jsonString = responseBody.string();
                            Log.e("RESULT",""+jsonString);

                            // 2. 데이터를 아답터에 세팅하고

                            // 3. 아답터 갱신
                            // 호출된 곳에 따라 처리가 달라진다.
                        }
                );
    }

    CalendarView.OnDateChangeListener dateChangeListener = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            month = month + 1;
            Log.i("Calendar", "year:" + year + ", month:" + month + ", dayOfMonth:" + dayOfMonth);
            String theDay = String.format("%d-%02d-%02d", year, month, dayOfMonth);
            //String theDay = year+"-"+month+"-"+dayOfMonth;
            switch (checkStatus) {
                case CHECK_IN:
                    search.checkinDate = theDay;
                    setButtonText(btnCheckin, getString(R.string.hint_start_date), search.checkinDate);
                    break;
                case CHECK_OUT:
                    search.checkoutDate = theDay;
                    setButtonText(btnCheckout, getString(R.string.hint_end_date), search.checkoutDate);
                    break;
            }
        }
    };
}
