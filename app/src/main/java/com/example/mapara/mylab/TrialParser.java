package com.example.mapara.mylab;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by mapara on 11/3/14.
 */

/**
 * Helper class to parse the .ics (calendar invite) file
 * Created by mapara on 10/29/14.
 */
public class TrialParser {

    public  static final String TAG = TrialParser.class.getSimpleName();
    private static final String BEGIN_VCAL ="BEGIN:VCALENDAR";
    private static final String BEGIN_VEVENT = "BEGIN:VEVENT";
    private static final String BEGIN = "BEGIN";
    private static final String ORGANIZER = "ORGANIZER";
    private static final String ATTENDEE = "ATTENDEE";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String SUMMARY = "SUMMARY";
    private static final String DTSTART = "DTSTART";
    private static final String DTEND = "DTEND";
    private static final String LOCATION ="LOCATION";


    // Regex to split the event time to get the timezone and time
    // e.g DTSTART;TZID=Pacific Standard Time:20141014T130000
    private static final String REGEX_SPLIT_EVENT_DATE = "[=:]";

    //Regex to match the non-standard timezone string e.g Pacific Standard Time
    private static final String REGEX_REGION_LONG_FORM = "^\\w+( +\\w+)+$";

    //Regex to split and read the first character (e.g i/p "Pacific Standard Time", o/p = "PST"
    private static final String REGEX_READ_FIRST_CHARS = "(?<=[\\S])[\\S]*\\s*";

    public static void main(String[] args) {
//        String icspath = "/Users/mapara/work/jf_sent.ics";
        String icspath = "/Users/mapara/Desktop/2524047608.ics";
        try {
            VEvent event = parseICS(icspath);
            System.out.println(event);
            String tzId = event.startTz;
            Calendar startCalendar = convertToLocalTimeZone(event.dtStart, tzId);
            Calendar endCalendar = convertToLocalTimeZone(event.dtEnd, tzId);
            String boundary = ConvertDatesToEventTimeBoundary(startCalendar,endCalendar);
            System.out.println("Boundary = " + boundary);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String tzId = getTimeZoneId("Pacific Standard Time");
        try {
            Calendar startCalendar = convertToLocalTimeZone("20141028T140000", tzId);
            Calendar endCalendar = convertToLocalTimeZone("20141028T143000", tzId);

            String boundary = ConvertDatesToEventTimeBoundary(startCalendar,endCalendar);
            System.out.println("working Boundary = " + boundary);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ics files contain event invite information.
     * @param icsFilePath
     * @return {@link VEvent} object filled with event information or null if icsFilepath is empty or null
     * or the elements are not in the desired format.
     * @throws java.io.IOException
     */
    public static VEvent parseICS(String icsFilePath) throws IOException {
        return icsFilePath!=null? parseICS(new FileReader(new File(icsFilePath))) : null;
    }

    /**
     * ics files contain event invite information.
     * @param icsInputStream
     * @return {@link VEvent} object filled with event information or null if icsInputStream is null
     * or the elements are not in the desired format.
     * @throws IOException
     */
    public static VEvent parseICS(InputStream icsInputStream) throws IOException {
        return icsInputStream !=null ? parseICS(new InputStreamReader(icsInputStream, "UTF-8")) : null;
    }

    /**
     * ics files contain event invite information.
     * @param icsStreamReader
     * @return {@link VEvent} object filled with event information or null if the icsStreamReader is null
     * or the elements are not in the desired format.
     * @throws IOException
     */
    public static VEvent parseICS(InputStreamReader icsStreamReader) throws IOException {

        if(icsStreamReader == null) {
            return null;
        }
        BufferedReader reader = null;
        VEvent event = null;

        // Please add additional block to extract more fields if required
        try {
            reader = new BufferedReader(icsStreamReader);

            String line;
            event = new VEvent();
            String currentBlock = BEGIN_VCAL;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(BEGIN)) {
                    currentBlock = line;
                } else if (line.startsWith(ORGANIZER))
                    event.organizer = line.substring(ORGANIZER.length() + 1);
                else if (line.startsWith(ATTENDEE))
                    event.attendees.add(line.substring(ATTENDEE.length() + 1));
                else if (line.startsWith(DESCRIPTION))
                    event.description = line.substring(DESCRIPTION.length() + 1);
                else if (line.startsWith(LOCATION))
                    event.location = line.substring(LOCATION.length() + 1);
                else if (line.startsWith(SUMMARY)) {
                    if (currentBlock.equals(BEGIN_VEVENT))
                        event.summary = line.substring(SUMMARY.length() + 1);
                } else if (line.startsWith(DTSTART)) {
                    if (currentBlock.equals(BEGIN_VEVENT)) {
                        //Splitng Event time to extract timezone and time
                        //e.g input = DTSTART;TZID=Pacific Standard Time:20141014T130000 and we want to
                        //extract "Pacific Standard Time" and "20141014T130000"
                        String[] parts = line.split(REGEX_SPLIT_EVENT_DATE);
                        //TODO find and handle different ICS Event start/end time format
                        if(parts.length != 3) {
                            System.out.println("DTSTART in event didn't match the desired format, stay tuned");
                            return null;
                        }
                        event.startTz = getTimeZoneId(parts[1]);
                        event.dtStart = parts[2];
                    }
                } else if (line.startsWith(DTEND)) {
                    if (currentBlock.equals(BEGIN_VEVENT)) {
                        String[] parts = line.split(REGEX_SPLIT_EVENT_DATE);
                        //TODO find and handle different ICS Event start/end time format
                        if(parts.length != 3) {
                            System.out.println("DTSTART in event didn't match the desired format, stay tuned");
                            return null;
                        }
                        event.endTz = getTimeZoneId(parts[1]);
                        event.dtEnd = parts[2];
                    }
                }
            }
        } finally {
            if(reader!=null) {
                reader.close();
            }
        }

        return event;
    }

    /**
     * A pojo class to hold the event information
     */
    public static class VEvent{
        public String organizer;
        public String description;
        public String summary;
        public String dtStart;
        public String dtEnd;
        public String startTz;
        public String endTz;
        public String location;
        public ArrayList<String> attendees;

        public VEvent() {attendees = new ArrayList<String>();}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Summary          : "); sb.append(summary); sb.append("\n");
            sb.append("Organizer        : "); sb.append(organizer); sb.append("\n");
            sb.append("Total Attendees  : "); sb.append(attendees.size()); sb.append("\n");
            sb.append("Event Location   : "); sb.append(location); sb.append("\n");
            sb.append("Event Start Time : "); sb.append(startTz); sb.append(" "); sb.append(dtStart); sb.append("\n");
            sb.append("Event End Time   : "); sb.append(endTz); sb.append(" "); sb.append(dtEnd); sb.append("\n");
            return sb.toString();
        }
    }

    /**
     * @param icsTimezone
     * @return String containing standard Timezone ID Or null if the input string is empty
     */
    public static String getTimeZoneId(String icsTimezone) {
        if(icsTimezone==null) {
            return null;
        }
        if(icsTimezone.matches(REGEX_REGION_LONG_FORM)) {
            StringBuilder sb = new StringBuilder();
            String[] tokens = icsTimezone.split(REGEX_READ_FIRST_CHARS);
            for(String c : tokens) {
                sb.append(c);
            }
            return sb.toString();
        }
        return icsTimezone;
    }

    /**
     *
     * @param sourceDate format must be yyyyMMdd'T'HHmmss (e.g 20141018T143000), otherwise this will
     *                   throw {@link java.text.ParseException}
     * @param sourceTimeZoneId Strictly a standard timezone id. "Pacific Standard Time" is not standard.
     *                         Please use {@link CalendarUtil.getTimeZoneId(String)} method to convert to
     *                         standard timezone ID
     * @return {@link java.util.Calendar} object containing default(local ?) timeZone date information.
     * From Calendar, we can easily get the year/month/date/hour/min information for the given time
     * @throws java.text.ParseException
     */
    public static Calendar convertToLocalTimeZone(String sourceDate, String sourceTimeZoneId) throws ParseException {
        Calendar localCalendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone(sourceTimeZoneId));
        localCalendar.setTime(formatter.parse(sourceDate));
        return localCalendar;
    }

    public static String convertDateToSpecialFormat(Calendar calendarForGivenDate) throws ParseException {
        DateFormat targetFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
        Date date = null;
        try {
            if(calendarForGivenDate != null) date = calendarForGivenDate.getTime();
        } catch (IllegalArgumentException exception) {}
        return date!=null  ? targetFormat.format(date) : null;
    }

    public static String ConvertDatesToEventTimeBoundary(Calendar startCalendar, Calendar endCalendar) throws ParseException {
        if(startCalendar ==null || endCalendar == null) return null;
        DateFormat targetFormat = new SimpleDateFormat("hh:mm a");
        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = startCalendar.getTime();
            endDate = endCalendar.getTime();
        } catch (IllegalArgumentException exception) {
            return null;
        }
        String timeBoundary = targetFormat.format(beginDate) + " - " + targetFormat.format(endDate);
        return timeBoundary;
    }

}
