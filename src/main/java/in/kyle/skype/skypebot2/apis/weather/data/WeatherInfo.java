package in.kyle.skype.skypebot2.apis.weather.data;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;

/**
 * Created by Kyle on 12/17/2015.
 */
@AllArgsConstructor
public class WeatherInfo {
    
    @SerializedName("main")
    private final String name;
    
    private final String description;
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
}
