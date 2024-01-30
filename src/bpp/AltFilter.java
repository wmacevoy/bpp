class ByteOut {
    // write data
    long write(byte[] data, long offset, long length);
    // stop writing data - future writes will return 0
    void close();
}

class ByteIn {
    long peek(byte [] data, long offset, long maxLength);
    long read(byte[] data, long offset, long maxLength);
    // stop reading data - future writes will return -1
    void close();
}

class ByteQueue {
    ByteIn getIn();
    ByteOut getOut();
    void block();
}

class Stage {
    ByteQueue 

}

class 

class 

class Queue {
    boolean closed;
    long baseOffset;
    long readOffset;
    long writeOffset;
    LinkedList<byte[]> blocks = new LinkedList<byte[]> ();
    long write(byte[] buffer, int offset, int length) {
	synchonized(this) {
	    if (!closed && length > 0) {
		byte[] copy = Arrays.copyOfRange(buffer,offset,offset+length);
		blocks.add(copy);
		writeOffset += length;
		notifyAll();
		return length;
	    } else {
		return closed ? -1 : 0;
	    }
	}
    }
    int write(byte[] buffer) { return write(buffer,0,buffer.length); }
    
    int read(byte [] to, int offset, int maxLength) {
	synchronized(this) {
	    if (closed && readLength == writeLength) {
		return -1;
	    } else {
		for (;;) {
		    while (writeOffset < readOffset && maxLength > 0) {
			int n = Math.min(writeOffset-baseOffset,
			int n = Math.min(maxLength,data.get(0).size();
			int n = Math.min(maxLength,data.get(0).

		    }
		    while (data.size() == 0
		    int n = Math.min(maxLength,data.

				 }
	    }

	    }
	}
    }
}



Queue
    write(byte[] data)
    read(byte [] data)

