package in.kyle.skype.skypebot2.apis.weather;

import com.google.gson.annotations.SerializedName;
import in.kyle.skype.skypebot2.apis.weather.data.WeatherCity;
import in.kyle.skype.skypebot2.apis.weather.data.WeatherDataForecast;
import lombok.Data;

import java.util.List;

/**
 * Created by Kyle on 12/17/2015.
 */
@Data
public class WeatherResponseForecast {
    
    private final WeatherCity city;
    
    @SerializedName("message")
    private final String responseMessage;
    
    @SerializedName("cod")
    private final int responseCode;
    
    @SerializedName("cnt")
    private final int count;
    
    @SerializedName("list")
    private final List<WeatherDataForecast> data;
}
