package in.kyle.skype.skypebot2.apis.weather.data;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by Kyle on 12/17/2015.
 */
@Data
public class WeatherWind {
    
    private final float speed;
    
    @SerializedName("deg")
    private final float direction;
}
