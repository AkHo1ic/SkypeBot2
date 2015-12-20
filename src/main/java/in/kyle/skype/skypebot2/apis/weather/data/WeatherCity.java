package in.kyle.skype.skypebot2.apis.weather.data;

import lombok.Data;

/**
 * Created by Kyle on 12/17/2015.
 */
@Data
public class WeatherCity {
    
    private final int id;
    private final String name;
    private final WeatherCords coord;
    private final String country;
    private final int population;
}
