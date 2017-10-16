package com.example.java;



import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mapara on 10/7/14.
 */
public class MyMain {
    private static Pattern p = Pattern
            .compile("P(\\d*)d?\\s*(\\d*)h?\\s*(\\d*)m?\\s*(\\d*)S?");
    private static Pattern PS = Pattern
            .compile("\\w?T?(\\d*)S");
    private static Pattern PD = Pattern
            .compile("\\w?(\\d*)D");
    private static Pattern PW = Pattern
            .compile("\\w?(\\d*)W");
    private static Pattern PM = Pattern
            .compile("\\w?T?(\\d*)M");
    private static Pattern PH = Pattern
            .compile("\\w?T?(\\d*)H");

    public static List<Long> myList = new CustomList<Long>();

    public static void main(String[] args) {
        try {
            versionCheck();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String ics_path = "/Users/mapara/work/jf_sent.ics";
//        printSpyral();
//        try{
//            TreeMap<String,String> map = getProperties(ics_path);
//            System.out.println(map);
//        }catch(Exception e) {
//            System.out.println(e);
//        }

//        try {
//            FileInputStream fis = new FileInputStream(ics_path);
//            CalendarBuilder builder = new CalendarBuilder();
//            Calendar calendar = builder.build(fis);
//
//            for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
//                Component component = (Component) i.next();
//                System.out.println("Component [" + component.getName() + "]");
//
//                for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
//                    Property property = (Property) j.next();
//                    System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
//                }
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (ParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static void m10() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Long entry : myList) {
                    System.out.println("Key =" + entry);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
//                long t1 = System.currentTimeMillis();
                for (int i = 0; i < 675050; i++)
                    myList.add(i * 2L);
//                System.out.println("Time = " + (System.currentTimeMillis() - t1));
            }
        }).start();

    }

    private static void m9() {
        try {
            String s1 = "P15DT5H0M20S";
            String s2 = "P3600S";
            String s3 = "P7W";
            parseDurationFull(s3);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static long parseDuration(String duration) throws ParseException {
        Matcher m = PS.matcher(duration);

        long milliseconds = 0;

        if (m.find()) {
//            int days = Integer.parseInt(m.group(1));
//            milliseconds += TimeUnit.MILLISECONDS.convert(days, TimeUnit.DAYS);
//            int hours = Integer.parseInt(m.group(2));
//            milliseconds += TimeUnit.MILLISECONDS
//                    .convert(hours, TimeUnit.HOURS);
//            int minutes = Integer.parseInt(m.group(3));
//            milliseconds += TimeUnit.MILLISECONDS.convert(minutes,
//                    TimeUnit.MINUTES);
//            int seconds = Integer.parseInt(m.group(1));
//            milliseconds += TimeUnit.MILLISECONDS.convert(seconds,
//                    TimeUnit.SECONDS);


        } else {
            throw new ParseException("Cannot parse duration " + duration, 0);
        }

        return milliseconds;
    }

    public static void parseDurationFull(String duration) throws ParseException {
        Matcher m = PS.matcher(duration);
        if (m.find()) System.out.println("Seconds = " + m.group(1));

        m = PH.matcher(duration);
        if (m.find()) System.out.println("Hours = " + m.group(1));

        m = PD.matcher(duration);
        if (m.find()) System.out.println("Days = " + m.group(1));

        m = PW.matcher(duration);
        if (m.find()) System.out.println("Weeks = " + m.group(1));

        m = PM.matcher(duration);
        if (m.find()) System.out.println("Months = " + m.group(1));
    }

    private static void m8() {
        String summary1 = "LANGUAGE=en-US:testTitle :: here" + "LOCATION;LANGUAGE=en-US:";
        summary1 = summary1.startsWith("LANGUAGE=") ? summary1.split(":", 2)[1] : summary1;

        String line = "LOCATION;LANGUAGE=en-US:";
        String location = line.substring("LOCATION".length() + 1);
        String result = location.startsWith("LANGUAGE=") ? location.split(":")[1] : location;

        System.out.println(result);
    }

    private static void m7() {
        String REGEX_TIME_Z_FORM1 = "DT((START)|(END)):\\d{8}T\\d{6}Z";
        String REGEX_TIME_Z_FORM = "^\\d{8}T\\d{6}Z";
        String str = "DTEND:20141125T204500Z";
        System.out.println("matched ? " + str.matches(REGEX_TIME_Z_FORM1));
    }

    private static void m6() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.MILLISECOND, -28800000);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        String time = year + "/" + month + "/" + day + " " + hour + ":" + min;
        System.out.println("UTC Time = " + time);
    }

    private static void m5() {
        //zzzz = Pacific Standard Time
        //zzz = PST
        //Z = +0530
        SimpleDateFormat sdf = new SimpleDateFormat("Z");//sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        //Calendar locCal = new GregorianCalendar(2014,9,10); //locCal.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

        try {
            System.out.println("toLocal: " + sdf.parse("+0530"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("---------------------------------------");

/*
        Long gmtTime =1317951113613L; // 2.32pm NZDT
        long gmtTime = java.util.Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis();
        Long timezoneAlteredTime = 0L;
        long offset = -28800000;

        if (offset != 0L) {
            long multiplier = (offset*60)*(60*1000);
            timezoneAlteredTime = gmtTime + multiplier;
        } else {
            timezoneAlteredTime = gmtTime;
        }

        java.util.Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(timezoneAlteredTime);

        DateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");

        formatter.setCalendar(calendar);
        formatter.setTimeZone(TimeZone.getTimeZone(timeZone));

        String newZealandTime = formatter.format(calendar.getTime());
        System.out.println(tz.getID())
*/
    }

    private static void m4() {
        String ln1 = "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=TRUE;CN=Harshit Mapara:MAILTO:mapara@yahoo-inc.com";
        String ln2 = "ATTENDEE;PARTSTAT=NEEDS-ACTION;ROLE=REQ_PARTICIPANT;RSVP=TRUE;SCHEDULE-STAT US=1.1:mailto:harshit.mapara@yahoo.com";
        String ln3 = "ATTENDEE;CUTYPE=INDIVIDUAL;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=" +
                "TRUE;CN=harshit.mapara@yahoo.com;X-NUM-GUESTS=0:mailto:harshit.mapara@yahoo.com";

        String line1 = ln3;
        int i1 = line1.indexOf("CN=");
        int i11 = line1.indexOf("cn=");
        int i2 = line1.indexOf("MAILTO:");
        int i21 = line1.indexOf("mailto:");

        String name = "";
        if (i1 != -1) {
            int iColon = line1.indexOf(";", i1 + 3);
            name = line1.substring(i1 + 3, iColon == -1 ? line1.length() : iColon);
        } else if (i11 != -1) {
            int iColon = line1.indexOf(";", i1 + 3);
            name = line1.substring(i1 + 3, iColon == -1 ? line1.length() : iColon);
        } else if (i2 != -1) {
            int iColon = line1.indexOf(";", i2 + 7);
            name = line1.substring(i2 + 7, iColon == -1 ? line1.length() : iColon);
        } else if (i21 != -1) {
            int iColon = line1.indexOf(";", i21 + 7);
            name = line1.substring(i21 + 7, iColon == -1 ? line1.length() : iColon);
        }
        System.out.println(name);
    }

    private static void m3() {
//      String REGEX_REGION_LONG_FORM = "^\\w+( +\\w+)*$";
//        String REGEX_REGION_LONG_FORM = "([a-zA-Z0-9]+ )+?";
        String REGEX_REGION_LONG_FORM = "^\\w+( +\\w+)+$";
        String str = "Pacific Standard Time";
        System.out.println("matched ? " + str.matches(REGEX_REGION_LONG_FORM));
    }

    private static void m1() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(1418349600000L);

        DateFormat utcFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println("Formatted = " + utcFormat.format(calendar.getTime()));
//        System.out.println("Hour = " + calendar.get(Calendar.HOUR_OF_DAY));
//        System.out.println("Day = " + calendar.get(Calendar.DAY_OF_MONTH));
    }

    private static void m2() {
        try {
            java.util.Calendar calendar = convertToLocalTimeZone("20141103T143000", "PST");
            //String str = ConvertDateToSpecialFormat(calendar);
            System.out.println("Time  = " + ConvertDatesToTimes(calendar));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static java.util.Calendar convertToLocalTimeZone(String sourceDate, String sourceTimeZoneId) throws ParseException {
        java.util.Calendar localCalendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone(sourceTimeZoneId));
        localCalendar.setTime(formatter.parse(sourceDate));
        return localCalendar;
    }

    public static String ConvertDateToSpecialFormat(java.util.Calendar calendarForGivenDate) throws ParseException {
        DateFormat targetFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
        Date date = null;
        try {
            if (calendarForGivenDate != null) date = calendarForGivenDate.getTime();
        } catch (IllegalArgumentException exception) {
        }
        return date != null ? targetFormat.format(date) : null;
    }

    public static String ConvertDatesToTimes(java.util.Calendar calendarForGivenDate) throws ParseException {
        DateFormat targetFormat = new SimpleDateFormat("hh:mm a");
        Date date = null;
        try {
            if (calendarForGivenDate != null) date = calendarForGivenDate.getTime();
        } catch (IllegalArgumentException exception) {
        }
        return date != null ? targetFormat.format(date) : null;
    }

    public static class Utility {
        public static final TimeZone utcTZ = TimeZone.getTimeZone("UTC");

        public static long toLocalTime(long time, TimeZone to) {
            return convertTime(time, utcTZ, to);
        }

        public static long toUTC(long time, TimeZone from) {
            return convertTime(time, from, utcTZ);
        }

        public static long convertTime(long time, TimeZone from, TimeZone to) {
            return time + getTimeZoneOffset(time, from, to);
        }

        public static int dp(int m, int n) {
            int M_MAX = 100;
            int N_MAX = 100;
            int mat[][] = new int[M_MAX + 2][N_MAX + 2];
            mat[m][n + 1] = 1;

            for (int r = m; r >= 1; r--)
                for (int c = n; c >= 1; c--)
                    mat[r][c] = mat[r + 1][c] + mat[r][c + 1];

            return mat[1][1];
        }


        private static long getTimeZoneOffset(long time, TimeZone from, TimeZone to) {
            int fromOffset = from.getOffset(time);
            int toOffset = to.getOffset(time);
            int diff = 0;

            if (fromOffset >= 0) {
                if (toOffset > 0) {
                    toOffset = -1 * toOffset;
                } else {
                    toOffset = Math.abs(toOffset);
                }
                diff = (fromOffset + toOffset) * -1;
            } else {
                if (toOffset <= 0) {
                    toOffset = -1 * Math.abs(toOffset);
                }
                diff = (Math.abs(fromOffset) + toOffset);
            }
            return diff;
        }
    }

    static class CustomList<L> extends ArrayList<L> {

        @Override
        public boolean add(L object) {
            synchronized (this) {
                return super.add(object);
            }
        }
    }


    static void printSpyral() {
        int[][] input = {{1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        int input1[][] = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        print(input1);
    }

    static void printNum(int[][] input, int x, int y, int dir) {
        String d = "";
        if (dir == 1) d = "UP";
        else if (dir == -1) d = "DOWN";
        else if (dir == 2) d = "RIGHT";
        else d = "LEFT";

        System.out.print(input[x][y] + "(" + d + ") ");
    }

    static void print(int[][] input) {
        int xorg = -1;
        int yorg = -1;

        int xlen = input.length; //{{1,2,3},{4,5,6},{7,8,9}};
        int ylen = input[0].length;
        int UP = 1;
        int DOWN = -1;
        int RIGHT = 2;
        int LEFT = -2;
        int dir = RIGHT;
        int x = 0;
        int y = 0;
        int total = xlen * ylen;
            /*
            while(total>=0) {
                if (dir == RIGHT) {
                    y = y +1;
                    if (y == ylen) {
                        dir = DOWN;
                        y--;
                        xorg++;
                    } else {
                        total--;
                        printNum(input,x,y,dir);

                    }
                } else if (dir == DOWN) {
                    x = x + 1;
                    if (x == xlen) {
                        dir = LEFT;
                        x--;
                        ylen--;
                    } else {
                        total--;
                        printNum(input,x,y,dir);

                    }

                } else if (dir == LEFT) {
                    y = y -1;
                    if (y == yorg) {
                        dir = UP;
                        y++;
                        xlen--;
                    } else {
                        total--;
                        printNum(input,x,y,dir);

                    }

                } else if (dir == UP) {
                    x = x -1;
                    if (x == xorg) {
                        dir = RIGHT;
                        x++;
                        yorg++;
                    } else {
                        total--;
                        printNum(input,x,y,dir);

                    }

                }
            }
            */
        System.out.print(input[x][y] + ",");
        total--;
        while (total > 0) {
            if (dir == RIGHT) {
                y = y + 1;
                if (y == ylen) {
                    y = y - 1;
                    dir = DOWN;
                    xorg++; //Will make sure this row is covered and next up direction should not reach till here
                } else {
                    printNum(input, x, y, dir);
                    total--;
                }
            } else if (dir == DOWN) {
                x = x + 1;
                if (x == xlen) {
                    x = x - 1;
                    dir = LEFT;
                    ylen--; //Will make sure this column is covered and next right direction should not reach till here
                } else {
                    printNum(input, x, y, dir);
                    total--;
                }
            } else if (dir == LEFT) {
                y = y - 1;
                if (y == yorg) {
                    y = y + 1;
                    dir = UP;
                    xlen--; //Will make sure this row is covered and next down direction should not reach till here
                } else {
                    printNum(input, x, y, dir);
                    total--;
                }
            } else if (dir == UP) {
                x = x - 1;
                if (x == xorg) {
                    x = x + 1;
                    dir = RIGHT;
                    yorg++; //Will make sure this column is covered and next left direction should not reach till here
                } else {
                    printNum(input, x, y, dir);
                    total--;
                }
            }
        }
    }

    public static void versionCheck() {
        List<String> keyList = Arrays.asList(">= 5.16.0", ">= 5.15.11", ">= 5.15.0", ">= 5.14.9");

        Map<String, String> myMap = new HashMap<>();
        myMap.put(">= 5.16.0", "5.16.0 vadu");
        myMap.put(">= 5.15.11", "5.15.11 vadu");
        myMap.put(">= 5.15.0", "5.15.0 vadu");
        myMap.put(">= 5.14.9", "5.14.9 vadu");

        String version = getMatchingVersion(keyList, myMap);

        System.out.print(version);
    }

    public static int versionCompare(String appVersion, String str2) {
        String[] vals1 = appVersion.split("\\.");
        String[] vals2 = str2.split("\\.");
        int i = 0;
        // set index to first non-equal ordinal or length of shortest version string
        while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
            i++;
        }
        // compare first non-equal ordinal number
        if (i < vals1.length && i < vals2.length) {
            int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
            return Integer.signum(diff);
        }
        // the strings are equal or one string is a substring of the other
        // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
        return Integer.signum(vals1.length - vals2.length);
    }

    @Nullable
    public static String getMatchingVersion(List<String> keysList, Map<String, String> myMap) {


        String matchedKey = null;
        for (String key : keysList) {
            String[] tokens = key.split(" ");
            int result = versionCompare("5.15.1", tokens[1]);
            if (result == 0) {
                matchedKey = key;
                break;
            } else if (result < 0) {
                continue;
            } else {
                matchedKey = key;
                break;
            }
        }

        return matchedKey == null ? null : myMap.get(matchedKey);
    }

}