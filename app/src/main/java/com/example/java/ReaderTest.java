package com.example.java;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by mapara on 5/6/15.
 */
public class ReaderTest {

    /**
     * The incremental size increase when reading a line of bytes from the inputStream
     * used when dynamically increasing the buffer size
     */
    private static final int LINE_READ_BUFFER_INCREMENTS_BYTES = 256;

    /**
     * The size of the temporary buffer when writing multipart content into an output stream
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024;

    // UTF-8 char constants for carriage return "\r" and line feed "\n"
    public static final int CarriageReturn = 0x0D; //'\015';
    public static final int LineFeed = 0x0A; //'\012';

    // constants used in Multipart parts
    private static class Constants {
        private final static String BOUNDARY = "boundary";
        private final static String STATUS_CODE = "Status-Code";
        private final static String CONTENT_TYPE = "Content-Type";
        private final static String CONTENT_REQUESTID = "Content-RequestId";
        private static final String MULTIPART_BOUNDARY_PREFIX = "--";
        private static final String MULTIPART_BOUNDARY_SUFFIX = "--";
        private static final String MULTIPART = "multipart/";
        private static final int MULTIPART_MAX_LINE_LENGTH_BYTES = 512;
    }

    private InputStream mInputStream;

    private boolean mStreamFinished;

    private String mBoundary = "";
    private String mBoundaryEnd = "";

    private String mEncoding;

    // TODO
    // read the header lines up to empty line of CR/LF.
    // next, read until you get to the "--" of the part boundary and discard those two bytes.
    // The buffer you read can then be converted into the type you see in the header's Content-Type
    // e.g. application/json = JSON object
    //      text/html        = String
    //      text/plain       = String
    //      octet-stream     = byte[]
    //      pdf              = write bytes to a File and return the File object
    //      other?           = write bytes to a File and return the File object?

    /**
     * reads a set of bytes up to CarriageReturn\LineFeed from the input stream, one byte at a time
     * (a.k.a. a "line")
     *
     * @param inStream
     * @return byte array of bytes that were read, or null if nothing was read
     * @throws IOException
     */
    private byte[] readLineOfBytes(InputStream inStream) throws IOException {
        // FIXME reuse this... make it a member var, since we never read two parts at the same time from the same MultipartStreamReader
        byte[] buf = new byte[Constants.MULTIPART_MAX_LINE_LENGTH_BYTES];

        int i = 0;
        int byteReadFromStream = -1;

        while (true) {
            byteReadFromStream = inStream.read();
            if (byteReadFromStream < 0) {
                break;
            }

            // skip a leading LF's
            if (byteReadFromStream == LineFeed) {
                continue;
            }

            // skip writing the CR/LF bytes
            if (byteReadFromStream == CarriageReturn) {
                break;
            }

            // expand the temp buffer if we need more space for this line
            if (i >= buf.length) {
                byte[] old_buf = buf;
                buf = new byte[old_buf.length + LINE_READ_BUFFER_INCREMENTS_BYTES];
                System.arraycopy(old_buf, 0, buf, 0, old_buf.length);
            }
            buf[i++] = (byte) byteReadFromStream;
        }

//        if (byteReadFromStream == -1) {
//            // nothing found
//            mStreamFinished = true;
//            return new byte[0];
//        }
//
//        // skip a trailing LF if it exists
//        if (byteReadFromStream == CarriageReturn && inStream.available() >= 1 && inStream.markSupported()) {
//            inStream.mark(1);
//            byteReadFromStream = inStream.read();
//            if (byteReadFromStream != LineFeed) {
//                inStream.reset();
//            }
//        }

        // trim the buffer so it's length is correct
        byte[] old_buf = buf;
        buf = new byte[i];
        System.arraycopy(old_buf, 0, buf, 0, i);

        return buf;
    }

    public String readMyLine(InputStream iStream) throws IOException {
        InputStreamReader isr = new InputStreamReader(iStream);

        BufferedReader br = new BufferedReader(isr);

        String s = br.readLine();
//        String s;
//        while ((s = br.readLine()) != null) {
//            System.out.println(s);
//        }

        return s;
    }

    public static void main(String[] args) {
        ReaderTest readerTest = new ReaderTest();
        try {
            String filePath = "/Users/mapara/Desktop/test1.txt";
            String result;
            long now = System.currentTimeMillis();
            byte[] barray = readerTest.readLineOfBytes(new FileInputStream(filePath));
            result = new String(barray);
//
//            result = readerTest.readMyLine(new FileInputStream(filePath));
            long time = System.currentTimeMillis() - now;
            System.out.print("result = " + result);
            System.out.print("time = " + time);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
