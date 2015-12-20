package in.kyle.skype.skypebot2.apis.weather.data;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Kyle on 12/17/2015.
 */
@Data
public class WeatherDataForecast {
    
    @SerializedName("dt")
    private final LocalDateTime date;
    
    @SerializedName("temp")
    private final WeatherStatsForecast stats;
    
    @SerializedName("weather")
    private final List<WeatherInfo> weather;
    
    private final Object clouds;
    
    private final WeatherWind wind;
    
    private final Object snow;
    
    private final Object rain;
    
    @SerializedName("name")
    private final String cityName;
}
