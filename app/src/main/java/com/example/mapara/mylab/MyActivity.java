package com.example.mapara.mylab;

import android.app.Activity;
import android.app.Fragment;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.util.Calendars;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;


public class MyActivity extends Activity {
    public final static String TAG = "CalActivity";
    final static DocumentBuilderFactory BUILDER_FACTORY = DocumentBuilderFactory.newInstance();

    static {
        BUILDER_FACTORY.setNamespaceAware(true);
        BUILDER_FACTORY.setIgnoringComments(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        AsyncQueryHandler asyncHandler;
        TextView calText;
        public String startTime,endTime;
        private StringBuilder sb;

        public static final String[] CALENDAR_PROJECTION = new String[] {
                CalendarContract.Calendars._ID,                           // 0
                CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
                CalendarContract.Calendars.OWNER_ACCOUNT,                  // 3
                CalendarContract.Calendars.CALENDAR_COLOR                  // 4
        };

        // The indices for the projection array above.
        private static final int PROJECTION_ID_INDEX = 0;
        private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
        private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
        private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
        private static final int PROJECTION_OWNER_CALENDAR_COLOR = 4;
        private static final String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        private static final String[] selectionArgs = new String[] {"hnmapara@gmail.com", "com.google",
                "hnmapara@gmail.com"};

        public static final String[] EVENT_PROJECTION = new String[] {
                CalendarContract.Events._ID,               //0
                CalendarContract.Events.CALENDAR_ID,       //1
                CalendarContract.Events.TITLE,             //2
                CalendarContract.Events.EVENT_COLOR,       //3
                CalendarContract.Events.DTSTART,             //4
                CalendarContract.Events.DTEND,               //5
        };
        private static final String EVENT_SELECTION =  "(("+ CalendarContract.Events.DTSTART + ">= ?) AND ("+ CalendarContract.Events.DTEND +"<= ?))";
        static XPath XPATH;
        public PlaceholderFragment() {
            //starttime :1413493200000 endtime =1413583200000
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(2014,Calendar.OCTOBER,16,14,0);
//            startTime = ""+calendar.getTimeInMillis();
//            Log.d(TAG, "Startime :" + startTime);
//            calendar.set(2014,Calendar.OCTOBER,17,15,0);
//            endTime = ""+calendar.getTimeInMillis();
//            Log.d(TAG, "Endtime :" + endTime);

            Calendar calendar = new GregorianCalendar(2014,Calendar.OCTOBER,20,14,0,0);

            startTime = ""+calendar.getTimeInMillis();
            Log.d(TAG, "Startime :" + startTime);
            calendar = new GregorianCalendar(2014,Calendar.OCTOBER,23,15,0,0);
            endTime = ""+calendar.getTimeInMillis();
            Log.d(TAG, "Endtime :" + endTime);

            sb = new StringBuilder();

            XPATH = XPathFactory.newInstance().newXPath();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            asyncHandler = new AsyncQueryHandler(getActivity().getContentResolver()) {
                @Override
                public void startQuery(int token, Object cookie, Uri uri, String[] projection, String selection, String[] selectionArgs, String orderBy) {
                    super.startQuery(token, cookie, uri, projection, selection, selectionArgs, orderBy);
                }

                @Override
                protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                    Log.d(TAG, "AsyncQueryHandler - onQueryComplete()");
                    switch (token) {
                        case 0:
                            try {
                                Log.d(TAG, "onQueryComplete() - cal count = " + cursor.getCount());
                                while (cursor.moveToNext()) {
                                    long calID = -1;
                                    String displayName = null;
                                    String accountName = null;
                                    String ownerName = null;
                                    int color = -1;

                                    // Get the field values
                                    calID = cursor.getLong(PROJECTION_ID_INDEX);
                                    displayName = cursor.getString(PROJECTION_DISPLAY_NAME_INDEX);
                                    accountName = cursor.getString(PROJECTION_ACCOUNT_NAME_INDEX);
                                    ownerName = cursor.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
                                    color = cursor.getInt(PROJECTION_OWNER_CALENDAR_COLOR);

                                    Log.d(TAG, "CAL_ID :" + calID + " DisplayName :" + displayName + " AccName :" + accountName);
                                    Log.d(TAG, " OwnerName :" + ownerName + " Color :" + color + " \n");

//                                    startQuery(1, null, CalendarContract.Events.CONTENT_URI, EVENT_PROJECTION, EVENT_SELECTION,
//                                            new String[]{startTime, endTime}, null);
//                                    startQuery(1, null, CalendarContract.Events.CONTENT_URI, EVENT_PROJECTION, null,
//                                            null, null);
                                }
                            } finally {
                                if (cursor != null) cursor.close();
                            }
                            break;

                        case 1:
                            try {
                                Log.d(TAG, "onQueryComplete() - event count = " + cursor.getCount());
                                while (cursor.moveToNext()) {
                                    long calID = -1;
                                    long eventid = -1;
                                    String etitle = null;
                                    int eColor = -1;
                                    long estarttime = -1;
                                    long eendtime = -1;


                                    // Get the field values
                                    eventid = cursor.getLong(0);
                                    calID = cursor.getLong(1);
                                    etitle = cursor.getString(2);
                                    eColor = cursor.getInt(3);
                                    estarttime = cursor.getLong(4);
                                    eendtime = cursor.getLong(5);

                                    sb.append("EventId :" + eventid + " CalId :"+ calID + " eTitle :" + etitle + " eColor :"+ eColor + " starttime :" + estarttime + " endtime ="+ eendtime+" \n");
                                    Log.d(TAG, "EventId :" + eventid + " CalId :"+ calID + " eTitle :" + etitle + " eColor :"+ eColor + " starttime :" + estarttime + " endtime ="+ eendtime);

                                }
                                calText.setText(sb.toString());
                            } finally {
                                if (cursor != null) cursor.close();
                            }
                            break;
                        default:
                            break;
                    }


                }

                @Override
                protected void onInsertComplete(int token, Object cookie, Uri uri) {
                    Log.d(TAG, "onInsertCompleted() Uir :" + uri.toString() );
                    startQuery(1, null, CalendarContract.Events.CONTENT_URI, EVENT_PROJECTION, EVENT_SELECTION,
                            new String[]{startTime, endTime}, null);

                }
            };
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);
            calText = (TextView) rootView.findViewById(R.id.caltext);

            try {
                asyncCalDBOp();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return rootView;
        }

        private void asyncCalDBOp() throws ParseException {
            Log.d(TAG, "Querying using AsyncQueryHandler");

//            Calendar beginTime = Calendar.getInstance();
//            beginTime.set(2014, Calendar.OCTOBER, 16, 12, 30);
//            long startMillis = beginTime.getTimeInMillis();
//            Calendar endTime = Calendar.getInstance();
//            endTime.set(2014,Calendar.OCTOBER, 16, 13, 30);
//            long endMillis = endTime.getTimeInMillis();
            DateFormat formatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
            formatter.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
            Date stdate  = formatter.parse("20141122T133000");
            Date enddate = formatter.parse("20141122T143000");


            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, stdate.getTime());
            values.put(CalendarContract.Events.DTEND, enddate.getTime());
            values.put(CalendarContract.Events.TITLE, "Priya's HTC-one m8");
            values.put(CalendarContract.Events.DESCRIPTION, "Group workout");
            values.put(CalendarContract.Events.CALENDAR_ID, 1); // Harshit.mapara@gmail (2 =in Motog, 4 = galaxy nexus)  //mapara@dev.yahoo-inc.com (motog=n/a , galaxy nexus=1)
            values.put(CalendarContract.Events.SELF_ATTENDEE_STATUS, 1);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");

            asyncHandler.startInsert(1,null,CalendarContract.Events.CONTENT_URI,values);


//            asyncHandler.startQuery(1, null, CalendarContract.Events.CONTENT_URI, EVENT_PROJECTION, EVENT_SELECTION,
//                    new String[]{startTime, endTime}, null);
            asyncHandler.startQuery(0, null, CalendarContract.Calendars.CONTENT_URI, CALENDAR_PROJECTION, null,
                    null, null);

        }

        private void ical4jParsing() {
            try {
                CalendarBuilder builder = new CalendarBuilder();
                net.fortuna.ical4j.model.Calendar calendar = builder.build(getResources().openRawResource(R.raw.tushar_sent));
//                net.fortuna.ical4j.model.Calendar calendar = Calendars.load(new URL("http://ical4j.cvs.sourceforge.net/viewvc/ical4j/iCal4j/etc/samples/valid/Australian32Holidays.ics"));

                final StringBuilder b = new StringBuilder();
                //b.append(calendar.getProperty("X-WR-CALNAME").getValue());
                for (Object event : calendar.getComponents(Component.VEVENT)) {
                    VEvent event1 = (VEvent) event;
                    if (event1.getSummary() != null) {
                        b.append("\n\n");
                        b.append(event1.getSummary().getValue());
                        b.append(": ");
                        b.append(event1.getStartDate().getDate());
                        b.append("\n");
                        b.append(event1.getDescription().getValue());
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        calText.setText("Hello, " + b.toString());
                    }
                });

            } catch (Exception e) {
                Log.e(TAG, "unexpected error", e);
            }
        }

        static String convertStreamToString(java.io.InputStream is) {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }
    }
}
