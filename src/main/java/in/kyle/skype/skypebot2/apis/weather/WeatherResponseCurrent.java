package in.kyle.skype.skypebot2.apis.weather;

import com.google.gson.annotations.SerializedName;
import in.kyle.skype.skypebot2.apis.weather.data.WeatherClouds;
import in.kyle.skype.skypebot2.apis.weather.data.WeatherCords;
import in.kyle.skype.skypebot2.apis.weather.data.WeatherInfo;
import in.kyle.skype.skypebot2.apis.weather.data.WeatherStats;
import in.kyle.skype.skypebot2.apis.weather.data.WeatherWind;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Kyle on 12/17/2015.
 */
@Data
public class WeatherResponseCurrent {
    
    @SerializedName("dt")
    private final LocalDateTime date;
    
    private final WeatherCords coord;
    
    @SerializedName("weather")
    private final List<WeatherInfo> info;
    
    @SerializedName("main")
    private final WeatherStats stats;
    
    private final WeatherWind wind;
    
    private final WeatherClouds clouds;
    
    @SerializedName("name")
    private final String cityName;
    
    @SerializedName("cod")
    private final int responseCode;
    
    private final String message;
}
