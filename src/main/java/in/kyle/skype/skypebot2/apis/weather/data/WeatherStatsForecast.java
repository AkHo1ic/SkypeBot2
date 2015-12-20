package in.kyle.skype.skypebot2.apis.weather.data;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by Kyle on 12/17/2015.
 */
@Data
public class WeatherStatsForecast {
    
    @SerializedName("min")
    private final double minTemp;
    
    @SerializedName("max")
    private final double maxTemp;
}
