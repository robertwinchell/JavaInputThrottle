import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ManageStreams {

	public static void main(String[] args) {

		ManageStreams manage = new ManageStreams();
		// Check the interval
		int interVal = manage.checkTimeInterval();
		// after getting the interval get the band width
		long bandWidth = manage.checkTimeGetBandwidthLimit(interVal);
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
	 * This method checks the time interval and returns to which time interval 
	 * the hour belongs to the hard coded times can be changed in the constants or property
	 * @return
	 */
	public int checkTimeInterval() 
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Constants.EIGHT, 0);
		DateTime start1 = new DateTime(calendar.getTime());
		calendar.clear();
		calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Constants.TEN, 0);
		DateTime end1 = new DateTime(calendar.getTime());
		calendar.clear();
		
		calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Constants.TEN, 0);
		DateTime start2 = new DateTime(calendar.getTime());
		calendar.clear();
		calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Constants.FIVE_HOUR, 0);
		DateTime end2 = new DateTime(calendar.getTime());
		calendar.clear();
		
		calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Constants.FIVE_HOUR, 0);
		DateTime start3 = new DateTime(calendar.getTime());
		calendar.clear();
		calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Constants.EIGHT, 0);
		DateTime end3 = new DateTime(calendar.getTime());
		calendar.clear();
		
		int interVal = 0;
		Interval interval1 = new Interval(start1, end1);
		Interval interval2 = new Interval(start2, end2);
		Interval interval3 = new Interval(start3, end3);

		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		DateTime dateTime = new DateTime(cal.getTime());
		DateTime toBeCompared = new DateTime(year, month, day,
				dateTime.getHourOfDay(), 0, 0, 0);
		if (interval1.contains(toBeCompared.getHourOfDay())) {
			interVal = Constants.ONE;
		} else if (interval2.contains(toBeCompared.getHourOfDay())) {
			interVal = Constants.TWO;
		} else if (interval3.contains(toBeCompared.getHourOfDay())) {
			interVal = Constants.THREE;
		}

		return interVal;
	}

	/**
	 * 
	 * returns the 
	 * bandwidth accordingly
	 * 
	 * @param timeSlab
	 * @return
	 */
	public long checkTimeGetBandwidthLimit(int timeSlab) {
		long bandWidth = 0;
		switch (timeSlab) {
		case 1:// between 8-10 am
			bandWidth = Constants.BANDWIDTH1;
			break;
		case 2: // between 10am to 5 pm
			bandWidth = Constants.BANDWIDTH2;
			break;
		case 3:// after 5 pm 8am
			bandWidth = Constants.BANDWIDTH3;
			break;
		default:
			break;
		}

		return bandWidth;
	}

}
