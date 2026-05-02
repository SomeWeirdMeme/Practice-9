
/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version 7.0
 */
public class LogAnalyzer
{
    public static final int HOURS_PER_DAY = 24;
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String filename)
    { 
        // Create the array object to hold the hourly
        // access counts.
        reader = new LogfileReader(filename);
        // Create the reader to obtain the data.
        hourCounts = new int[24];
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    public int quietestHour() {
        int min = hourCounts[0];
        int quietest = 0;

        for(int hour = 1; hour < hourCounts.length; hour++) {
            if(hourCounts[hour] < min) {
                min = hourCounts[hour];
                quietest = hour;
            }
        }

        return quietest;
    }

    public int busiestTwoHourPeriod() {
        int max = hourCounts[0] + hourCounts[1];
        int busiestHour = 0;

        for(int hour = 1; hour < hourCounts.length - 1; hour++) {
            int current = hourCounts[hour] + hourCounts[hour + 1];

            if(current > max) {
                max = current;
                busiestHour = hour;
            }
        }

        return busiestHour;
    }

    public int busiestHour() {
        int max = hourCounts[0];
        int busiest = 0;

        for(int hour = 1; hour < hourCounts.length; hour++) {
            if(hourCounts[hour] > max) {
                max = hourCounts[hour];
                busiest = hour;
            }
        }

        return busiest;
    }

    public int numberOfAccesses() {
        int total = 0;

        for(int count : hourCounts) {
            total += count;
        }

        return total;
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        int hour = 0;
        while(hour < hourCounts.length) {
            System.out.println(hour + ": " + hourCounts[hour]);
            hour++;
        }
    }

    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
