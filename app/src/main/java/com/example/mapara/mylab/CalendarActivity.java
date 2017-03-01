package com.example.mapara.mylab;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.SyncRequest;
import android.content.SyncStatusObserver;
import android.graphics.Color;
import android.net.MailTo;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class CalendarActivity extends Activity {
    private static final String TAG = "CalendarActivity";
    static DateFormat formatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventDayView view = new EventDayView(this);
//       setContentView(view);R.layout.event_body
        setContentView(R.layout.event_body);
        RelativeLayout eventBand = (RelativeLayout) findViewById(R.id.event_band);
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                150, 30);
        Button button = new Button(this);
        button.setLayoutParams(params);
        button.setBackgroundColor(Color.parseColor("#9ACC61"));
        button.setAlpha(0.5f);

        button.setTextSize(9);

        params.setMargins(50, 0, 0, 0);

        eventBand.addView(button);



//        ColorBarDrawable drawable = new ColorBarDrawable(25, false);
//        txv2.setBackground(drawable);
        m4();

    }

    private void m4() {
        Account account = new Account("mapara@yahoo-inc.com", "com.google");
//        Account[] accounts = AccountManager.get(this).getAccounts();
//        for(Account acc : accounts) {
//            Log.d(TAG, "account === " + acc.name + " type=" + acc.type);
//            if(acc.name.equalsIgnoreCase("harshit.mapara@gmail.com")) {
//                account = acc;
//            }
//        }
        if(ContentResolver.getSyncAutomatically(account, CalendarContract.AUTHORITY)){
            Log.d(TAG, "Sync is enabled");
        } else {
            Log.d(TAG, "Sync disabled/not known");
        }
//        ContentResolver.setSyncAutomatically(account, CalendarContract.AUTHORITY, true);

//        Bundle bundle = new Bundle();
//        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
//        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
//        ContentResolver.requestSync(account, CalendarContract.AUTHORITY, bundle);

        ContentResolver.addStatusChangeListener(ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE | ContentResolver.SYNC_OBSERVER_TYPE_PENDING | ContentResolver.SYNC_OBSERVER_TYPE_SETTINGS, new SyncStatusObserver() {
            @Override
            public void onStatusChanged(int i) {
                Log.d(TAG, "Callback ==== " + i);
            }
        });

    }

    //Joda testing
    private void m3() {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd MMMM YYYY HH:mm").withLocale(Locale.ENGLISH);
        dtf.withZone(DateTimeZone.forID("Asia/Kolkata"));
        DateTime dt = dtf.parseDateTime("10 November 2014 15:30");
        LocalDateTime localdt =null;
        localdt = dt.toLocalDateTime();
        Log.d(TAG, "Day of week =" + localdt.getDayOfWeek());
        Log.d(TAG, "Day of month =" +localdt.getDayOfMonth());
        Log.d(TAG, "Min of hour =" +localdt.getMinuteOfHour());
        Log.d(TAG, "Hour of day =" +localdt.getHourOfDay());
    }

    private void m2() {
        try {
            MailTo mailto = MailTo.parse("CN=Harshit Mapara:mailto:mapara@yahoo-inc.com");
            Log.d(TAG, "mailto Body =" + mailto.getBody());
            Log.d(TAG, "mailto To =" + mailto.getTo());
            Log.d(TAG, "mailto Header =" + mailto.getHeaders());
            Log.d(TAG, "mailto toString =" + mailto.toString());
        } catch (Exception ex) {
            Log.e(TAG, "got exception " + ex.getMessage());
        }
    }

    private void m1() {
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("PST"));
        Log.d("CalendarActivity", formatter.getTimeZone().toString());

        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        sourceFormat.setTimeZone(TimeZone.getTimeZone("PDT"));
        Date parsed = null; // => Date is in UTC now
        try {
            parsed = sourceFormat.parse("20141103T153000");

            TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
            SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            destFormat.setTimeZone(tz);

            String result = destFormat.format(parsed);

            Log.d(TAG, "Check this " + result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
