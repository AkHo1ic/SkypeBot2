package in.kyle.skype.skypebot2.apis.weather;

import com.google.gson.JsonObject;
import in.kyle.skype.skypebot2.http.HttpRequest;
import in.kyle.skype.skypebot2.http.HttpRequestBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyle on May 17, 2015
 */
public class WeatherQuery {
    
    public String getWeather(String location, String api) throws IOException {
        String query;
        String tname;
        String tcountry = "";
        if (location.contains(",")) {
            tname = location.substring(0, location.indexOf(","));
            tcountry = location.substring(location.indexOf(","), location.length());
        } else {
            tname = location;
        }
        
        HttpRequestBuilder requestBuilder = HttpRequest.builder().url("http://api.openweathermap.org/data/2.5/weather");
        try {
            requestBuilder.urlParameter("zip", Integer.parseInt(tname) + tcountry);
        } catch (NumberFormatException e) {
            requestBuilder.urlParameter("q", location);
        }
        
        requestBuilder.urlParameter("APIID", api).urlParameter("mode", "json").urlParameter("units", "imperial");
        JsonObject jsonObject = requestBuilder.build().makeRequestJson();
        JsonObject cord = jsonObject.getAsJsonObject("coord");
        JsonObject sys = jsonObject.getAsJsonObject("sys");
        JsonObject weather = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject();
        JsonObject main = jsonObject.getAsJsonObject("main");
        
        // extract data
        double lat = cord.get("lat").getAsDouble();
        double lon = cord.get("lon").getAsDouble();
        
        String country = sys.get("country").getAsString();
        
        Date sunrise = new Date(1000 * sys.get("sunrise").getAsLong());
        Date sunset = new Date(1000 * sys.get("sunset").getAsLong());
        SimpleDateFormat time = new SimpleDateFormat("h:mm a");
        
        String weatherDescription = weather.get("description").getAsString();
        
        double temp = main.get("temp").getAsDouble();
        int humidity = main.get("humidity").getAsInt();
        
        //String mapsUrl = "http://maps.google.com/maps?z=12&t=m&q=loc:" + lat + "+" + lon;
        
        String name = jsonObject.get("name").getAsString();
        
        // build report
        StringBuilder report = new StringBuilder();
        report.append("Weather for " + name + ", " + country).append("\n");
        report.append("Temp: ").append(temp).append("°F\n");
        report.append("Humidity: ").append(humidity).append("%\n");
        report.append("Sunrise: ").append(time.format(sunrise)).append("\n");
        report.append("Sunset: ").append(time.format(sunset)).append("\n");
        report.append("Description: ").append(weatherDescription).append("\n");
        report.append("Geo data:\n");
        //report.append("  Maps url: " + Chat.link(mapsUrl) + "\n");
        report.append("  Cords: lat=").append(lat).append(" lon=").append(lon).append("\n");
        report.append("  Country: ").append(country).append("\n");
        return report.toString();
    }
    
    public static void main(String[] args) throws IOException {
        String a1e9fa802937227454c4fe2278876f6e = new WeatherQuery().getWeather("Castle", "a1e9fa802937227454c4fe2278876f6e");
        System.out.println(a1e9fa802937227454c4fe2278876f6e);
    }
}