package in.kyle.skype.skypebot2.util;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Kyle on 11/28/2015.
 */
public class TimeBuilder {
    
    private final String string;
    private final String[] split;
    
    private TimeBuilder(String string) {
        this.string = string.toLowerCase();
        split = new String[getSplits()];
        split();
    }
    
    public long buildTime() {
        long time = 0;
        
        for (String s : split) {
            char timeValue = s.charAt(s.length() - 1);
            
            int value;
            
            try {
                value = Integer.parseInt(s.replace(timeValue + "", " ").trim());
            } catch (Exception e) {
                return -1;
            }
            
            switch (timeValue) {
                case 's':
                    time += SECONDS.toMillis(value);
                    break;
                
                case 'm':
                    time += MINUTES.toMillis(value);
                    break;
                
                case 'h':
                    time += HOURS.toMillis(value);
                    break;
                
                case 'd':
                    time += DAYS.toMillis(value);
                    break;
            }
        }
        return time;
    }
    
    private int getSplits() {
        int count = 0;
        for (char c : string.toCharArray()) {
            if (Character.isLetter(c)) {
                count++;
            }
        }
        return count;
    }
    
    public static long msFromString(String string) {
        return new TimeBuilder(string).buildTime();
    }
    
    public static String stringFromMsLong(long time) {
        long days = time / DAYS.toMillis(1);
        if (days != 0) {
            time %= DAYS.toMillis(1);
        }
        
        long hours = time / HOURS.toMillis(1);
        if (hours != 0) {
            time %= HOURS.toMillis(1);
        }
        
        long minutes = time / MINUTES.toMillis(1);
        if (minutes != 0) {
            time %= MINUTES.toMillis(1);
        }
        
        long seconds = time / SECONDS.toMillis(1);
        
        StringBuilder r = new StringBuilder();
        if (days != 0) {
            r.append(days).append(" day").append(days != 1 ? "s" : "").append(" ");
        }
        
        if (hours != 0) {
            r.append(hours).append(" hour").append(hours != 1 ? "s" : "").append(" ");
        }
        
        if (minutes != 0) {
            r.append(minutes).append(" minute").append(minutes != 1 ? "s" : "").append(" ");
        }
        
        if (seconds != 0) {
            r.append(seconds).append(" second").append(seconds != 1 ? "s" : "").append(" ");
        }
        
        return r.toString();
    }
    
    private void split() {
        String s = "";
        int run = 0;
        for (char c : string.replace(" ", "").toCharArray()) {
            if (Character.isDigit(c)) {
                s += Character.toString(c);
            } else {
                split[run] = s + c;
                s = "";
                run++;
            }
        }
    }
    
    public static String stringFromMsShort(long l) {
        long time = l;
        int days = (int) (time / DAYS.toMillis(1));
        time %= DAYS.toMillis(1);
        int hours = (int) (time / HOURS.toMillis(1));
        time %= HOURS.toMillis(1);
        int mins = (int) (time / MINUTES.toMillis(1));
        time %= MINUTES.toMillis(1);
        int secs = (int) (time / SECONDS.toMillis(1));
        time %= SECONDS.toMillis(1);
        long ms = time;
        
        StringBuilder b = new StringBuilder();
        if (days != 0) {
            b.append(days).append("d ");
        }
        if (hours != 0) {
            b.append(hours).append("h ");
        }
        if (mins != 0) {
            b.append(mins).append("m ");
        }
        if (secs != 0) {
            b.append(secs).append("s ");
        }
        if (ms != 0) {
            b.append(ms).append("ms ");
        }
        return b.toString().trim();
    }
}
