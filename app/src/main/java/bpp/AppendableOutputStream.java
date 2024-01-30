package bpp;

import java.io.*;

// Use UTF-16 encoding to append codepoints from
// a UTF-8 outputstream. This allows StringBuilder,
// StringBuffer, and Writer to all be used as
// UTF-8 Encoded OutputStream.
// autoFlush will flush at the end of stream and
// after every newline.

public class AppendableOutputStream extends OutputStream {
	private boolean autoFlush = false;
	private Appendable appendable;
	private byte[] utf8 = new byte[4];
	private int n = 0;

	AppendableOutputStream(Appendable appendable, boolean autoFlush) {
		this.appendable = appendable;
		this.autoFlush = autoFlush;
	}

	private int utf8Length() {
		if (n <= 0)
			return -1;
		byte c = utf8[0];
		if ((c & 0x80) == 0)
			return 1;
		if ((c & 0x40) != 0) {
			for (int len = 2; len <= 4; ++len) {
				if (n < len || (utf8[len - 1] & 0xC0) != 0x80) {
					return -1;
				}
				if ((c & (0x80 >> len)) == 0)
					return len;
			}
		}
		return -1;
	}

	private int utf8CodePoint(int len) {
		if (len == 1) {
			return utf8[0];
		} else if (len > 0) {
			int val = utf8[0] & ~(0xFF << (7 - len));
			for (int i = 1; i < len; ++i) {
				val = (val << 6) | (utf8[i] & 0x3F);
			}
			return val;
		}
		return -1;
	}

	public void appendCodePoint(int codePoint) throws IOException {
		if (codePoint >= 0x10000) {
			// Calculate surrogate pairs
			int temp = codePoint - 0x10000;
			char highSurrogate = (char) ((temp >>> 10) + 0xD800);
			char lowSurrogate = (char) ((temp & 0x3FF) + 0xDC00);

			appendable.append(highSurrogate);
			appendable.append(lowSurrogate);
		} else {
			appendable.append((char) codePoint);
		}
		if (autoFlush && codePoint == '\n') {
			flush();
		}
	}

	@Override
	public void write(int c) throws IOException {
		if (c == -1) {
			close();
			return;
		}
		utf8[n] = (byte) c;
		++n;
		int len = utf8Length();
		if (len > 0) {
			int codePoint = utf8CodePoint(len);
			appendCodePoint(codePoint);
			n = 0;
		} else if (len < 0) {
			n = 0;
		}
	}

	@Override
	public void close() throws IOException {
		if (autoFlush) {
			flush();
		}
		if (appendable instanceof Closeable) {
			((Closeable) appendable).close();
		}
	}

	@Override
	public void flush() throws IOException {
		if (appendable instanceof Flushable) {
			((Flushable) appendable).flush();
		}
	}
}
