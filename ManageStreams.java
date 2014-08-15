import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ManageStreams {

	public static void main(String[] args) {

		ManageStreams manage = new ManageStreams();
		long bandWidth = manage.checkTimeGetBandwidthLimit(1);
		String line = null;
		try {
			Path file = Paths.get("D:\\EventLog.txt");
			InputStream in = Files.newInputStream(file);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		
			InputStreamWrapper inputStreamWrapper = new InputStreamWrapper(in,
					bandWidth);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * TODO calculate the period from joda time and pass accordingly Here a
	 * thread may come and to read the hour of the day periodically and pass the
	 * bandwidth accordingly
	 * 
	 * @param timeSlab
	 * @return
	 */
	public long checkTimeGetBandwidthLimit(int timeSlab) {
		long bandWidth = 0;
		switch (timeSlab) {
		case 1:// between 8-10 am
			bandWidth = 64;
			break;
		case 2: // between 10am to 5 pm
			bandWidth = 65536;
			break;
		case 3:// after 5 pm 8am
			bandWidth = 8388608;
			break;
		default:
			break;
		}

		return bandWidth;
	}

}
