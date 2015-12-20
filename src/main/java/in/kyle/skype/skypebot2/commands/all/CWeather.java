package in.kyle.skype.skypebot2.commands.all;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import in.kyle.commands.Bind;
import in.kyle.commands.Command;
import in.kyle.ezskypeezlife.Chat;
import in.kyle.skype.skypebot2.SkypeBot;
import in.kyle.skype.skypebot2.apis.weather.WeatherResponseCurrent;
import in.kyle.skype.skypebot2.apis.weather.WeatherResponseForecast;
import in.kyle.skype.skypebot2.http.HttpRequest;
import in.kyle.skype.skypebot2.http.HttpRequestBuilder;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Kyle on 12/15/2015.
 */
public class CWeather {
    
    private Gson gson;
    private String apiKey;
    
    public CWeather() {
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, context) -> 
                LocalDateTime.ofInstant(new Date(json.getAsJsonPrimitive().getAsLong() * 1000).toInstant(), ZoneId.systemDefault()))
                .create();
        
    }
    
    private void initialise(SkypeBot skypeBot) {
        if (apiKey == null) {
            apiKey = skypeBot.getSkypeConfiguration().getApiKeys().get("weather");
        }
    }
    
    @Command(description = "Weather commands", subCommands = {"current", "forecast"})
    public void weather() {
    }
    
    @Command(description = "Get the current weather for an area")
    public String current(@Bind(name = "location", infinite = true) String location, SkypeBot bot) throws IOException {
        WeatherResponseCurrent weatherResponse = getResponse(location, bot, "weather", WeatherResponseCurrent.class);
        if (weatherResponse.getResponseCode() == 200) {
            StringBuilder report = new StringBuilder();
            report.append("Current weather for ").append(Chat.bold(weatherResponse.getCityName())).append("\n");
            
            report.append(weatherResponse.getStats().getTemperature()).append("\u00b0F").append(", ");
            report.append(weatherResponse.getStats().getHumidity()).append("% humidity, ");
            report.append(weatherResponse.getInfo().get(0).getDescription());
            return report.toString();
        } else {
            return "Error getting weather, " + weatherResponse.getMessage();
        }
    }
    
    private <T> T getResponse(String location, SkypeBot bot, String endpoint, Class<T> tClass) throws IOException {
        initialise(bot);
        HttpRequestBuilder builder = HttpRequest.builder().url("http://api.openweathermap.org/data/2.5/" + endpoint).urlParameter
                ("units", "imperial").urlParameter("mode", "json").urlParameter("appid", apiKey);
        if (StringUtils.isNumeric(location)) {
            builder.urlParameter("zip", location);
        } else {
            builder.urlParameter("q", location);
        }
        builder.urlParameter("cnt", "5");
        String json = builder.build().makeRequestText();
        return gson.fromJson(json, tClass);
    }
    
    @Command(description = "Get the 5 day weather forecast for an area")
    public String forecast(@Bind(name = "location", infinite = true) String location, SkypeBot bot) throws IOException {
        WeatherResponseForecast weatherResponse = getResponse(location, bot, "forecast/daily", WeatherResponseForecast.class);
        if (weatherResponse.getResponseCode() == 200) {
            StringBuilder report = new StringBuilder();
            String header = "Forecast for " + Chat.bold(weatherResponse.getCity().getName());
            
            weatherResponse.getData().forEach(weatherData -> {
                report.append("\n");
                StringBuilder reportLine = new StringBuilder();
                reportLine.append(weatherData.getDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
                matchLength(reportLine, 8);
                reportLine.append(" - ");
                reportLine.append("Min: ").append(weatherData.getStats().getMinTemp()).append("\u00b0F").append(", ");
                reportLine.append("Max: ").append(weatherData.getStats().getMaxTemp()).append("°F, ");
                reportLine.append(weatherData.getWeather().get(0).getDescription());
                report.append(reportLine.toString());
            });
            return header + Chat.code(report.toString());
        } else {
            return "Error getting weather, " + weatherResponse.getResponseMessage();
        }
    }
    
    private void matchLength(StringBuilder stringBuilder, int length) {
        while (stringBuilder.length() < length) {
            stringBuilder.append(" ");
        }
    }
}
