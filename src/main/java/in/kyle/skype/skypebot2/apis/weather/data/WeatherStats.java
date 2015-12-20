package in.kyle.skype.skypebot2.apis.weather.data;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by Kyle on 12/17/2015.
 */
@Data
public class WeatherStats {
    
    @SerializedName("temp")
    private final double temperature;
    
    @SerializedName("temp_min")
    private final double minTemperature;
    
    @SerializedName("temp_max")
    private final double maxTemperature;
    
    private final double pressure;
    
    @SerializedName("sea_level")
    private final double seaLevel;
    
    @SerializedName("grnd_level")
    private final double groundLevel;
    
    private final int humidity;
    
}
