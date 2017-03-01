package com.example.java;


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
import java.util.HashSet;
import java.util.TimeZone;

/**
 * Helper class to parse the .ics (calendar invite) file
 * Created by mapara on 10/29/14.
 */
public class CalendarICSParser {
    public  static final String TAG = CalendarICSParser.class.getSimpleName();
    private static final String BEGIN_VCAL ="BEGIN:VCALENDAR";
    private static final String BEGIN_VEVENT = "BEGIN:VEVENT";
    private static final String BEGIN = "BEGIN";
    private static final String ORGANIZER = "ORGANIZER";
    private static final String ATTENDEE = "ATTENDEE";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String SUMMARY = "SUMMARY";
    private static final String DTSTART = "DTSTART";
    private static final String DTEND = "DTEND";
    private static final String TZID = "TZID";
    private static final String LOCATION ="LOCATION";

    static HashSet<String> sKeywordSet = new HashSet<String>();
    static {
        sKeywordSet.add("BEGIN:");sKeywordSet.add("METHOD:");
        sKeywordSet.add("PRODID:");sKeywordSet.add("VERSION:");
        sKeywordSet.add("TZID:");sKeywordSet.add("DTSTART:");
        sKeywordSet.add("TZOFFSETFROM:");sKeywordSet.add("TZOFFSETTO:");
        sKeywordSet.add("RRULE:");sKeywordSet.add("END:");
        sKeywordSet.add("ORGANIZER;");sKeywordSet.add("ATTENDEE;");
        sKeywordSet.add("DESCRIPTION;");sKeywordSet.add("SUMMARY;");
        sKeywordSet.add("DTEND;");sKeywordSet.add("UID:");
        sKeywordSet.add("CLASS:");sKeywordSet.add("PRIORITY:");
        sKeywordSet.add("DTSTAMP:");sKeywordSet.add("TRANSP:");
        sKeywordSet.add("STATUS:");sKeywordSet.add("SEQUENCE:");
        sKeywordSet.add("X-MICROSOFT-CDO-APPT-SEQUENCE:");sKeywordSet.add("X-MICROSOFT-CDO-OWNERAPPTID:");
        sKeywordSet.add("X-MICROSOFT-CDO-BUSYSTATUS:");sKeywordSet.add("X-MICROSOFT-CDO-INTENDEDSTATUS:");
        sKeywordSet.add("X-MICROSOFT-CDO-ALLDAYEVENT:");sKeywordSet.add("X-MICROSOFT-CDO-IMPORTANCE:");
        sKeywordSet.add("X-MICROSOFT-CDO-INSTTYPE:");sKeywordSet.add("X-MICROSOFT-DISALLOW-COUNTER:");
        sKeywordSet.add("ACTION:");sKeywordSet.add("TRIGGER;");
    }



    // Regex to split the event time to get the timezone and time
    // e.g DTSTART;TZID=Pacific Standard Time:20141014T130000
    private static final String REGEX_SPLIT_EVENT_DATE = "[=:]";

    //Regex to match the non-standard timezone string e.g Pacific Standard Time
    private static final String REGEX_REGION_LONG_FORM = "^\\w+( +\\w+)+$";

    //Regex to split and read the first character (e.g i/p "Pacific Standard Time", o/p = "PST"
    private static final String REGEX_READ_FIRST_CHARS = "(?<=[\\S])[\\S]*\\s*";


    public static void main(String[] args) {
//        String icspath = "/Users/mapara/work/jf_sent.ics";
        String path1 = "/Users/mapara/Desktop/2524047608.ics";
        String path2 = "/Users/mapara/work/Lab/MyLab/app/src/main/res/raw/jf_sent.ics";
        String path3 = "/Users/mapara/work/Lab/MyLab/app/src/main/res/raw/invite.ics";
        String icspath = path3;
        try {
            VEvent event = parseICS(icspath);
            System.out.println(event);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    /**
     * ics files contain event invite information.
     * @param icsFilePath
     * @return {@link VEvent} object filled with event information or null if icsFilepath is empty or null
     * or the elements are not in the desired format.
     * @throws IOException
     */
    public static VEvent parseICS(String icsFilePath) throws Exception {
        return icsFilePath!=null ? parseICS(new FileReader(new File(icsFilePath))) : null;
    }

    /**
     * ics files contain event invite information.
     * @param icsInputStream
     * @return {@link VEvent} object filled with event information or null if icsInputStream is null
     * or the elements are not in the desired format.
     * @throws IOException
     */
    public static VEvent parseICS(InputStream icsInputStream) throws Exception {
        return icsInputStream !=null ? parseICS(new InputStreamReader(icsInputStream, "utf-8")) : null;
    }

    private static String lastLine = null;
    static BufferedReader reader = null;
    private static String lReadLine() throws IOException {
        if (lastLine != null) {
            String temp = lastLine;
            lastLine = null;
            return temp;
        } else {
            return reader.readLine();
        }
    }

    static void pushBack(String s) {
        lastLine = s;
    }

    static boolean startsWithKeyWords(String line) {
        for(String key : sKeywordSet) {
            if(line.startsWith(key)) return true;
        }
        return false;
    }

    /**
     * ics files contain event invite information.
     * @param icsStreamReader
     * @return {@link VEvent} object filled with event information or null if the icsStreamReader is null
     * or the elements are not in the desired format.
     * @throws IOException
     */
    public static VEvent parseICS(InputStreamReader icsStreamReader) throws Exception {

        if(icsStreamReader == null) {
            return null;
        }
//        BufferedReader reader = null;
        VEvent event = null;

        // Please add additional block to extract more fields if required
        try {
            reader = new BufferedReader(icsStreamReader);

            String line;
            event = new VEvent();
            String currentBlock = BEGIN_VCAL;
            while ((line = lReadLine()) != null) {
                if (line.startsWith(BEGIN)) {
                    currentBlock = line;
                } else if (line.startsWith(ORGANIZER)) {
                    String unparsedName = line.substring(ORGANIZER.length() + 1);
                    String parsedName = parseName(unparsedName);
                    event.organizer = parsedName!=null ? parsedName : unparsedName;
                } else if (line.startsWith(ATTENDEE)) {
                    //event.attendees.add(line.substring(ATTENDEE.length() + 1));
                    String buff = "";
                    while(true) {
                        String sen = lReadLine();
                        if (sen != null) {
                            if(startsWithKeyWords(sen)) {
                                pushBack(sen);
                                break;
                            } else {
                                buff = buff + sen.trim();
                            }
                        } else {
                            break;
                        }
                    }
                    event.attendees.add(extractNameOrEmail(line + buff)); //TODO detect /n and act accordingly to get name/email
                } else if (line.startsWith(DESCRIPTION)) {
                    if (currentBlock.equals(BEGIN_VEVENT)) {
                        String buff = "";
                        while(true) {
                            String sen = lReadLine();
                            if (sen != null) {
                                if(startsWithKeyWords(sen)) {
                                    pushBack(sen);
                                    break;
                                } else {
                                    buff = buff + sen.trim();
                                }
                            } else {
                                break;
                            }
                        }
                        event.description = (line+buff).substring(DESCRIPTION.length() + 1);
                    }
                } else if (line.startsWith(LOCATION)) {
                    String location = line.substring(LOCATION.length() + 1);
                    event.location  = location.startsWith("LANGUAGE=") ? location.split(":")[1] : location;
                } else if (line.startsWith(TZID)) {
                    event.timeZone = getTimeZoneId(line.substring(TZID.length() + 1));
                }   else if (line.startsWith(SUMMARY)) {
                    if (currentBlock.equals(BEGIN_VEVENT)) {
                        String title = line.substring(SUMMARY.length() + 1);
                        event.summary = title.startsWith("LANGUAGE=") ? title.split(":")[1] : title;
                    }
                } else if (line.startsWith(DTSTART)) {
                    if (currentBlock.equals(BEGIN_VEVENT)) {
                        //Splitng Event time to extract timezone and time
                        //e.g input = DTSTART;TZID=Pacific Standard Time:20141014T130000 and we want to
                        //extract "Pacific Standard Time" and "20141014T130000"
                        String[] parts = line.split(REGEX_SPLIT_EVENT_DATE);
                        //TODO find and handle different ICS Event start/end time format
                        if(parts.length != 3) {
                            System.out.println(TAG + "DTSTART in event didn't match the desired format, stay tuned");
                            return null;
                        }
                        event.timeZone = getTimeZoneId(parts[1]);
                        event.startCalendar = convertToLocalTimeZone(parts[2], event.timeZone);
                    }
                } else if (line.startsWith(DTEND)) {
                    if (currentBlock.equals(BEGIN_VEVENT)) {
                        String[] parts = line.split(REGEX_SPLIT_EVENT_DATE);
                        //TODO find and handle different ICS Event start/end time format
                        if(parts.length != 3) {
                            System.out.println(TAG + "DTSTART in event didn't match the desired format, stay tuned");
                            return null;
                        }
                        event.timeZone = getTimeZoneId(parts[1]);
                        event.endCalendar = convertToLocalTimeZone(parts[2], event.timeZone);
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

    private static String extractNameOrEmail(String line) {
        int lengthOf_CN = "CN=".length();
        int lengthOf_mailto = "MAILTO:".length();
        int indexOf_CN = line.indexOf("CN=");
        int indexOf_cn = line.indexOf("cn=");
        int indexOf_MAILTO = line.indexOf("MAILTO:");
        int indexOf_mailto = line.indexOf("mailto:");

        String nameOrEmail = "";
        if(indexOf_CN!=-1) {
            int indexOf_colon = line.indexOf(";",indexOf_CN + lengthOf_CN);
            nameOrEmail = line.substring(indexOf_CN + lengthOf_CN,indexOf_colon == -1? line.length() : indexOf_colon);
        } else if(indexOf_cn!=-1) {
            int indexOf_colon = line.indexOf(";",indexOf_CN + lengthOf_CN);
            nameOrEmail = line.substring(indexOf_CN + lengthOf_CN,indexOf_colon == -1? line.length() : indexOf_colon);
        } else if(indexOf_MAILTO!=-1) {
            int indexOf_colon = line.indexOf(";",indexOf_MAILTO + lengthOf_mailto);
            nameOrEmail = line.substring(indexOf_MAILTO + lengthOf_mailto,indexOf_colon == -1? line.length() : indexOf_colon);
        } else if(indexOf_mailto!=-1) {
            int indexOf_colon = line.indexOf(";", indexOf_mailto + lengthOf_mailto);
            nameOrEmail = line.substring(indexOf_mailto + lengthOf_mailto, indexOf_colon == -1 ? line.length() : indexOf_colon);
        }
        System.out.println(TAG + "Attendee = " + nameOrEmail);
        return nameOrEmail;
    }

    /**
     * A pojo class to hold the event information
     */
    public static class VEvent{
        public String organizer;
        public String description;
        public String summary;
        public Calendar startCalendar;
        public Calendar endCalendar;
        public String timeZone;
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
            sb.append("Event TimeZone   : "); sb.append(timeZone); sb.append("\n");
            sb.append("Event Start Time : "); sb.append(startCalendar.getTime()); sb.append("\n");
            sb.append("Event End Time   : "); sb.append(endCalendar.getTime()); sb.append("\n");
            sb.append("Event Description: "); sb.append(description);
            return sb.toString();
        }
    }

    /**
     * @param icsTimezone
     * @return String containing standard Timezone ID Or null if the input string is empty
     */
    public static String getTimeZoneId(String icsTimezone) {
        if(icsTimezone == null) {
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
     *                   throw {@link ParseException}
     * @param sourceTimeZoneId Strictly a standard timezone id. "Pacific Standard Time" is not standard.
     *                         Please use {@link CalendarUtil.getTimeZoneId(String)} method to convert to
     *                         standard timezone ID
     * @return {@link Calendar} object containing default(local ?) timeZone date information.
     * From Calendar, we can easily get the year/month/date/hour/min information for the given time
     * @throws ParseException
     */
    public static Calendar convertToLocalTimeZone(String sourceDate, String sourceTimeZoneId) throws ParseException {
        if(sourceTimeZoneId == null) return null;
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
    public static String parseName(String unParsedName) {
        String tokens[] = unParsedName.split(":",2);
        if(tokens.length >= 1) {
            if(tokens[0].toUpperCase().startsWith("CN=")) {
                return tokens[0].substring(3); // "CN=".length()
            }
        }
        return null;
    }

}

