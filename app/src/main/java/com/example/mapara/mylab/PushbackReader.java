package com.example.mapara.mylab;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by mapara on 10/19/14.
 */

public class PushbackReader extends FilterReader
{
    /**
     * This is the default buffer size
     */
    private static final int DEFAULT_BUFFER_SIZE = 1;

    /**
     * This is the buffer that is used to store the pushed back data
     */
    private char[] buf;

    /**
     * This is the position in the buffer from which the next char will be
     * read.  Bytes are stored in reverse order in the buffer, starting from
     * <code>buf[buf.length - 1]</code> to <code>buf[0]</code>.  Thus when
     * <code>pos</code> is 0 the buffer is full and <code>buf.length</code> when
     * it is empty
     */
    public int pos;

    /**
     * This method initializes a <code>PushbackReader</code> to read from the
     * specified subordinate <code>Reader</code> with a default pushback buffer
     * size of 1.
     *
     * @param in The subordinate stream to read from
     */
    public PushbackReader(Reader in)
    {
        this(in, DEFAULT_BUFFER_SIZE);
    }

    /**
     * This method initializes a <code>PushbackReader</code> to read from the
     * specified subordinate <code>Reader</code> with the specified buffer
     * size
     *
     * @param in The subordinate <code>Reader</code> to read from
     * @param bufsize The pushback buffer size to use
     */
    public PushbackReader(Reader in, int bufsize)
    {
        super(in);

        if (bufsize < 0)
            throw new IllegalArgumentException("buffer size must be positive");

        buf = new char[bufsize];
        pos = bufsize;
    }

    /**
     * This method closes the stream and frees any associated resources.
     *
     * @exception IOException If an error occurs.
     */
    public void close() throws IOException
    {
        synchronized (lock)
        {
            buf = null;
            super.close();
        }
    }

    /**
     * This method throws an exception when called since this class does
     * not support mark/reset.
     *
     * @param read_limit Not used.
     *
     * @exception IOException Always thrown to indicate mark/reset not supported.
     */
    public void mark(int read_limit) throws IOException
    {
        throw new IOException("mark not supported in this class");
    }

    /**
     * This method returns <code>false</code> to indicate that it does not support
     * mark/reset functionality.
     *
     * @return This method returns <code>false</code> to indicate that this
     * class does not support mark/reset functionality
     *
     */
    public boolean markSupported()
    {
        return(false);
    }

    /**
     * This method always throws an IOException in this class because
     * mark/reset functionality is not supported.
     *
     * @exception IOException Always thrown for this class
     */
    public void reset() throws IOException
    {
        throw new IOException("reset not supported in this class");
    }

    /**
     * This method determines whether or not this stream is ready to be read.
     * If it returns <code>false</code> to indicate that the stream is not
     * ready, any attempt to read from the stream could (but is not
     * guaranteed to) block.
     * <p>
     * This stream is ready to read if there are either chars waiting to be
     * read in the pushback buffer or if the underlying stream is ready to
     * be read.
     *
     * @return <code>true</code> if this stream is ready to be read,
     * <code>false</code> otherwise
     *
     * @exception IOException If an error occurs
     */
    public boolean ready() throws IOException
    {
        synchronized (lock)
        {
            if (buf == null)
                throw new IOException ("stream closed");

            if (((buf.length - pos) > 0) || super.ready())
                return(true);
            else
                return(false);
        }
    }

    // Don't delete this method just because the spec says it shouldn't be there!
    // See the CVS log for details.
    /**
     * This method skips the specified number of chars in the stream.  It
     * returns the actual number of chars skipped, which may be less than the
     * requested amount.
     * <p>
     * This method first discards chars from the buffer, then calls the
     * <code>skip</code> method on the underlying <code>Reader</code> to
     * skip additional chars if necessary.
     *
     * @param num_chars The requested number of chars to skip
     *
     * @return The actual number of chars skipped.
     *
     * @exception IOException If an error occurs
     */
    public long skip(long num_chars) throws IOException
    {
        synchronized (lock)
        {
            if (num_chars <= 0)
                return(0);

            if ((buf.length - pos) >= num_chars)
            {
                pos += num_chars;
                return(num_chars);
            }

            int chars_discarded = buf.length - pos;
            pos = buf.length;

            long chars_skipped = in.skip(num_chars - chars_discarded);

            return(chars_discarded + chars_skipped);
        }
    }

    /**
     * This method reads an unsigned char from the input stream and returns it
     * as an int in the range of 0-65535.  This method also will return -1 if
     * the end of the stream has been reached.  The char returned will be read
     * from the pushback buffer, unless the buffer is empty, in which case
     * the char will be read from the underlying stream.
     * <p>
     * This method will block until the char can be read.
     *
     * @return The char read or -1 if end of stream
     *
     * @exception IOException If an error occurs
     */
    public int read() throws IOException
    {
        synchronized (lock)
        {
            if (buf == null)
                throw new IOException("stream closed");

            if (pos == buf.length)
                return(super.read());

            ++pos;
            return((buf[pos - 1] & 0xFFFF));
        }
    }

    /**
     * This method read chars from a stream and stores them into a caller
     * supplied buffer.  It starts storing the data at index <code>offset</code>
     * into
     * the buffer and attempts to read <code>len</code> chars.  This method can
     * return before reading the number of chars requested.  The actual number
     * of chars read is returned as an int.  A -1 is returned to indicate the
     * end of the stream.
     *  <p>
     * This method will block until some data can be read.
     * <p>
     * This method first reads chars from the pushback buffer in order to
     * satisfy the read request.  If the pushback buffer cannot provide all
     * of the chars requested, the remaining chars are read from the
     * underlying stream.
     *
     * @param buffer The array into which the chars read should be stored
     * @param offset The offset into the array to start storing chars
     * @param length The requested number of chars to read
     *
     * @return The actual number of chars read, or -1 if end of stream.
     *
     * @exception IOException If an error occurs.
     */
    public synchronized int read(char[] buffer, int offset, int length)
            throws IOException
    {
        synchronized (lock)
        {
            if (buf == null)
                throw new IOException("stream closed");

            if (offset < 0 || length < 0 || offset + length > buffer.length)
                throw new ArrayIndexOutOfBoundsException();

            int numBytes = Math.min(buf.length - pos, length);
            if (numBytes > 0)
            {
                System.arraycopy (buf, pos, buffer, offset, numBytes);
                pos += numBytes;
                return numBytes;
            }

            return super.read(buffer, offset, length);
        }
    }

    /**
     * This method pushes a single char of data into the pushback buffer.
     * The char pushed back is the one that will be returned as the first char
     * of the next read.
     * <p>
     * If the pushback buffer is full, this method throws an exception.
     * <p>
     * The argument to this method is an <code>int</code>.  Only the low eight
     * bits of this value are pushed back.
     *
     * @param b The char to be pushed back, passed as an int
     *
     * @exception IOException If the pushback buffer is full.
     */
    public void unread(int b) throws IOException
    {
        synchronized (lock)
        {
            if (buf == null)
                throw new IOException("stream closed");
            if (pos == 0)
                throw new IOException("Pushback buffer is full");

            --pos;
            buf[pos] = (char)(b & 0xFFFF);
        }
    }

    /**
     * This method pushes all of the chars in the passed char array into
     * the pushback buffer.  These chars are pushed in reverse order so that
     * the next char read from the stream after this operation will be
     * <code>buf[0]</code> followed by <code>buf[1]</code>, etc.
     * <p>
     * If the pushback buffer cannot hold all of the requested chars, an
     * exception is thrown.
     *
     * @param buf The char array to be pushed back
     *
     * @exception IOException If the pushback buffer is full
     */
    public synchronized void unread(char[] buf) throws IOException
    {
        unread(buf, 0, buf.length);
    }

    /**
     * This method pushed back chars from the passed in array into the pushback
     * buffer.  The chars from <code>buf[offset]</code> to
     * <code>buf[offset + len]</code>
     * are pushed in reverse order so that the next char read from the stream
     * after this operation will be <code>buf[offset]</code> followed by
     * <code>buf[offset + 1]</code>, etc.
     * <p>
     * If the pushback buffer cannot hold all of the requested chars, an
     * exception is thrown.
     *
     * @param buffer The char array to be pushed back
     * @param offset The index into the array where the chars to be push start
     * @param length The number of chars to be pushed.
     *
     * @exception IOException If the pushback buffer is full
     */
    public synchronized void unread(char[] buffer, int offset, int length)
            throws IOException
    {
        synchronized (lock)
        {
            if (buf == null)
                throw new IOException("stream closed");
            if (pos < length)
                throw new IOException("Pushback buffer is full");

            // Note the order that these chars are being added is the opposite
            // of what would be done if they were added to the buffer one at a time.
            // See the Java Class Libraries book p. 1397.
            System.arraycopy(buffer, offset, buf, pos - length, length);

            // Don't put this into the arraycopy above, an exception might be thrown
            // and in that case we don't want to modify pos.
            pos -= length;
        }
    }
}
