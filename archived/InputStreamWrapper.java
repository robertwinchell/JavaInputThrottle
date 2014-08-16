import java.io.IOException;
import java.io.InputStream;

public class InputStreamWrapper extends InputStream {

	/** usable bandwidth in bytes/second **/
	private long bandwidth = 0;

	/** bandwidth limit will be calculated form the start time **/
	private boolean isReading = false;

	/** number of bytes read **/
	private int count = 0;

	/** check bandwidth every n bytes **/
	private static int CHECK_INTERVAL = 100;

	/** start time **/
	long starttime = 0;

	/** used time **/
	long usedtime = 0;

	public InputStreamWrapper(InputStream in, long bandwidth)
			throws IOException {
		if (bandwidth > 0) {
			this.bandwidth = bandwidth;
		} else {
			this.bandwidth = 0;
		}

		count = 0;
	}

	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	public int read(byte[] b, int off, int len) throws IOException {
		int mycount = 0;
		int current = 0;
		// limit bandwidth ?
		if (bandwidth > 0) {
			for (int i = off; i < off + len; i++) {
				current = read();
				if (current == -1) {
					return mycount;
				} else {
					b[i] = (byte) current;
					count++;
					mycount++;
				}
			}
			return mycount;
		} else {
			return read(b, off, len);
		}
	}

	public int read() throws IOException {
		long currentBandwidth;

		if (!isReading) {
			starttime = System.currentTimeMillis();
			isReading = true;
		}

		// do bandwidth check only if bandwidth
		if ((bandwidth > 0) && ((count % CHECK_INTERVAL) == 0)) {
			do {
				usedtime = System.currentTimeMillis() - starttime;
				if (usedtime > 0) {
					currentBandwidth = (count * 1000) / usedtime;
				} else {
					currentBandwidth = 0;
				}
				if (currentBandwidth > bandwidth) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
			} while (currentBandwidth > bandwidth);
		}

		count++;
		return read();
	}

}
