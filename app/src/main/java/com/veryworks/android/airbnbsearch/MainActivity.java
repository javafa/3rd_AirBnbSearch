package com.veryworks.android.airbnbsearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private Button btnCheckin, btnCheckout;
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
        setCalendarButtonText();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        btnCheckin = (Button) findViewById(R.id.btnCheckin);
        btnCheckout = (Button) findViewById(R.id.btnCheckout);

        fab = (FloatingActionButton) findViewById(R.id.fab);

    }

    private void setListener(){
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Log.i("Calendar","year:"+year+", month:"+month+", dayOfMonth:"+dayOfMonth);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setCalendarButtonText(){
        // 버튼에 다양한 색깔의 폰트 적용하기
        // 위젯의 android:textAllCaps="false" 적용 필요
        String inText = "<font color='#888888'>" + getString(R.string.hint_start_date)
                + "</font> <br> <font color=\"#fd5a5f\">" + getString(R.string.hint_select_date) + "</font>";
        StringUtil.setHtmlText(btnCheckin, inText);


        String outText = "<font color='#888888'>" + getString(R.string.hint_start_date)
                + "</font> <br> <font color=\"#fd5a5f\"> - </font>";
        StringUtil.setHtmlText(btnCheckout, outText);
    }
}
