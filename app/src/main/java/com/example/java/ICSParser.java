package com.example.java;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mapara on 10/20/14.
 */
public class ICSParser {
    private static final String BEGIN_VCAL ="BEGIN:VCALENDAR";
    private static final String BEGIN_TZ = "BEGIN:VTIMEZONE";
    private static final String BEGIN_VEVENT = "BEGIN:VEVENT";
    private static final String BEGIN_VALARM = "BEGIN:VALARM";
    private static final String END = "END:VTIMEZONE";
    private static final String END_VEVENT = "END:VEVENT";
    private static final String REGEX_REGION_SLASH_REGION = "[a-wA-W]+/[^_][a-wA-W_]+"; //For matching America/Los_Angeles
    private static final String REGEX_REGION_SHORT_FORM = "[a-zA-Z]{3}"; //For matching PST
    private static final String REGEX_REGION_LONG_FORM = "^\\w+( +\\w+)+$"; //For matching Pacific Standard Time
    private static final String REGEX_READ_FIRST_CHAR = "(?<=[\\S])[\\S]*\\s*";
    public static void main(String[] args) {

        try {
            parseICS();
//            lab3();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void lab3() {
//        int n1 = 1,n2=0,n3=1,n4=1,n5=5;
//        int masked = n1|n3|n5;
//
//        System.out.println((masked & n1) != 0? true : false);
//        System.out.println((masked & n3) != 0? true : false);
//        System.out.println((masked & n4) != 0? true : false);
        int n1 = 1,n2=2,n3=3,n4=4,n5=5;
        int masked = n1|n3|n5;

        System.out.println((masked & n1) != 0? true : false);
        System.out.println((masked & n3) != 0? true : false);
        System.out.println((masked & n4) != 0? true : false);
    }

    public static void lab2() throws Exception {
//        String regex = "(?<=[\\S])[\\S]*\\s*\";
        String[] tokens = "Pacific Standard Time".split("(?<=[\\S])[\\S]*\\s*");
        for(String s : "Pacific Standard Time".split("(?<=[\\S])[\\S]*\\s*"))
            System.out.print(s);


    }
    public static void lab1() throws ParseException {
        String st = "DTSTART;TZID=America/Los_Angeles:20141018T113000";
        String[] atoms = st.split("[=:]");
        for(String s: atoms)
            System.out.println(s);
//        DateFormat df = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
//        df.setTimeZone(TimeZone.getTimeZone("Pacific Standard Time"));
//        Date d = df.parse(atoms[2]);
//        System.out.printf("Meeting in LA time is %s\n",d);
//
//        DateFormat df2 = DateFormat.getInstance();
//        df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
//        System.out.printf("The meeting time in India is %s\n", df2.format(d));

//        Calendar currentdate = Calendar.getInstance();
//        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        TimeZone obj = TimeZone.getTimeZone("Asia/Kolkata");
//        formatter.setTimeZone(obj);
//        System.out.println("Local:: " +currentdate.getTime());
//        System.out.println("CST:: "+ formatter.format(currentdate.getTime()));



//        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        //Pacific Standard Time
        //America/Los_Angeles
        //Asia/Kolkata
//        DateFormat formatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
//        TimeZone fromZone = TimeZone.getTimeZone("EST");
//        formatter.setTimeZone(fromZone);
//        Date date = formatter.parse("20141018T143000");
//
//        TimeZone toZone = TimeZone.getTimeZone("PST");
//        formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        formatter.setTimeZone(toZone);
//        System.out.println("EST:: " +date.toString());
//        System.out.println("PST:: "+ formatter.format(date));

//        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("PDT"));
//        cal.set(2014,Calendar.OCTOBER,18,14,30,00);
//        Date date = cal.getTime();
//
//        //displaying this date on IST timezone
//        DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:SS z");
//        df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
//        String IST = df.format(date);
//        System.out.println("Date in Indian Timezone (IST) : " + IST);
//
//        //dispalying date on PST timezone
//        df.setTimeZone(TimeZone.getTimeZone("America/New_York"));
//        String PST = df.format(date);
//        System.out.println("Date in EST Timezone : " + PST);

        Calendar localTime = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        Date date = formatter.parse("20141020T060000");
//        localTime.setTime(formatter.parse("20141020T180000"));
//        localTime.set(Calendar.YEAR,2014);
//        localTime.set(Calendar.MONTH,Calendar.OCTOBER);
//        localTime.set(Calendar.DAY_OF_MONTH, 20);
//        localTime.set(Calendar.HOUR_OF_DAY, 18);
//        localTime.set(Calendar.MINUTE, 00);
//        localTime.set(Calendar.SECOND, 00);

        int month = localTime.get(Calendar.MONTH);
        int day = localTime.get(Calendar.DAY_OF_MONTH);
        int hour = localTime.get(Calendar.HOUR_OF_DAY);
        int minute = localTime.get(Calendar.MINUTE);
        int second = localTime.get(Calendar.SECOND);
//
//
//        // Print the local time
//        System.out.printf("Local time : %02d/%02d  %02d:%02d:%02d\n", month+1,day, hour, minute, second);


        // Create a calendar object for representing a Germany time zone. Then we
        // wet the time of the calendar with the value of the local time


        Calendar germanyTime = new GregorianCalendar();
        germanyTime.setTime(date);
        month = germanyTime.get(Calendar.MONTH);
        day = germanyTime.get(Calendar.DAY_OF_MONTH);
        hour = germanyTime.get(Calendar.HOUR_OF_DAY);
        minute = germanyTime.get(Calendar.MINUTE);
        second = germanyTime.get(Calendar.SECOND);


        // Print the local time in Germany time zone
        System.out.printf("India time : %02d/%02d  %02d:%02d:%02d\n",month+1,day, hour, minute, second);

    }

    public static void parseICS() throws Exception {
        String ics_path = "/Users/mapara/work/invite.ics";
        BufferedReader bfr = new BufferedReader(new FileReader(new File(ics_path)));
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        Calendar localCal = new GregorianCalendar();

        String line;
        VEvent event = new VEvent();
        String currentBlock = BEGIN_VCAL;
        while ((line = bfr.readLine()) != null) {
            if(line.startsWith("BEGIN")) { currentBlock = line;}
            else if(line.startsWith("ORGANIZER")) event.organizer = line.substring("ORGANIZER".length() +1);
            else if(line.startsWith("ATTENDEE")) event.attendees.add(line.substring("ATTENDEE".length()+1));
            else if(line.startsWith("DESCRIPTION")) event.description = line.substring("DESCRIPTION".length()+1);
            else if(line.startsWith("SUMMARY")) {if(currentBlock.equals(BEGIN_VEVENT)) event.summary = line.substring("SUMMARY".length()+1);}
            else if(line.startsWith("DTSTART")) {
                if(currentBlock.equals(BEGIN_VEVENT)) {
                    String[] tokens = line.split("[=:]");
                    formatter.setTimeZone(TimeZone.getTimeZone(getTimeZoneId(tokens[1])));
                    Date date = formatter.parse(tokens[2]);
                    localCal.setTime(date);
                    event.dtStart = "Original in " + tokens[1] +" converted to local " + localCal.getTimeZone().getDisplayName(false,TimeZone.LONG) +
                            String.format(" %02d/%02d  %02d:%02d:%02d",localCal.get(Calendar.MONTH)+1,localCal.get(Calendar.DAY_OF_MONTH),
                                    localCal.get(Calendar.HOUR_OF_DAY), localCal.get(Calendar.MINUTE), localCal.get(Calendar.SECOND));
                }
            }
            else if(line.startsWith("DTEND")) {
                if(currentBlock.equals(BEGIN_VEVENT)) {
                    String[] tokens = line.split("[=:]");
                    formatter.setTimeZone(TimeZone.getTimeZone(getTimeZoneId(tokens[1])));
                    Date date = formatter.parse(tokens[2]);
                    localCal.setTime(date);
                    event.dtEnd = "Original in " + tokens[1] +" converted to local " + localCal.getTimeZone().getDisplayName(false,TimeZone.LONG) +
                            String.format(" %02d/%02d  %02d:%02d:%02d",localCal.get(Calendar.MONTH)+1,localCal.get(Calendar.DAY_OF_MONTH),
                                    localCal.get(Calendar.HOUR_OF_DAY), localCal.get(Calendar.MINUTE), localCal.get(Calendar.SECOND));
                }
            }

        }
        System.out.println(event.toString());
    }
    static class VTimeZone{ boolean started =false,finished = false;}
    static class VEvent{
        boolean started =false,finished = false;
        String organizer,description,summary,dtStart,dtEnd;
        ArrayList<String> attendees;
        public VEvent() {attendees = new ArrayList<String>();}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Summary = " + summary + "\n");
            sb.append("Organizer = " + organizer + "\n");
            sb.append("Total Attendees :" + attendees.size() + "\n");
            sb.append("Event Start TIme = " + dtStart + "\n");
            sb.append("Event End TIme = " + dtEnd + "\n");
            return sb.toString();
        }
    }

    public static String getTimeZoneId(String fromICS) {
        if(fromICS.matches(REGEX_REGION_LONG_FORM)) {
            StringBuilder sb = new StringBuilder();
            String[] tokens = fromICS.split(REGEX_READ_FIRST_CHAR);
            for(String c : tokens)
                sb.append(c);
            return sb.toString();
        }
        return fromICS;
    }
}