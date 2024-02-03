package bpp;

import java.io.*;

public class AppendableOutputStream extends OutputStream {
	protected boolean autoFlush = false;
	protected Appendable appendable;

	protected int state = 0;
	protected int codepoint = 0;

	@Override
	public synchronized void write(int c) throws IOException {
		if (state == 0) {
			if ((c & 0x80) == 0) {
				appendable.append((char) c);
				if (autoFlush && c == '\n') { flush(); }
			} else if ((c & 0xE0) == 0xC0) {
				codepoint = (c & 0x1F);
				state = 1;
			} else if ((c & 0xF0) == 0xE0) {
				codepoint = (c & 0x0F);
				state = 2;
			} else if ((c & 0xF8) == 0xF0) {
				codepoint = (c & 0x07);
				state = 3;
			} else {
				throw new IOException("invalid utf8 byte: " + Integer.toBinaryString(c));
			}
		} else {
			codepoint = (codepoint << 6) | (c & 0x3F);
			state = state - 1;
			if (state == 0) {
				appendCodePoint(codepoint);
			}
		}
	}

	AppendableOutputStream(Appendable appendable, boolean autoFlush) {
		this.appendable = appendable;
		this.autoFlush = autoFlush;
	}

	public synchronized void appendCodePoint(int codepoint) throws IOException {
		if (state != 0) {
			throw new IOException("illegal state");
		}
		if (codepoint >= 0x10000) {
			// Calculate surrogate pairs
			int temp = codepoint - 0x10000;
			char highSurrogate = (char) ((temp >>> 10) + 0xD800);
			char lowSurrogate = (char) ((temp & 0x3FF) + 0xDC00);

			appendable.append(highSurrogate);
			appendable.append(lowSurrogate);
		} else {
			appendable.append((char) codepoint);
		}
		if (autoFlush && codepoint == '\n') {
			flush();
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
