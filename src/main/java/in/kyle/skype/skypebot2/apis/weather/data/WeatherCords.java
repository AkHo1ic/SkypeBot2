package in.kyle.skype.skypebot2.apis.weather.data;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by Kyle on 12/17/2015.
 */
@Data
public class WeatherCords {
    @SerializedName("lat")
    private final float latitude;
    
    @SerializedName("lon")
    private final float longitude;
}
