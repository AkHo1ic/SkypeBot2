package in.kyle.skype.skypebot2.http;

import com.google.gson.JsonObject;
import in.kyle.ezskypeezlife.EzSkype;
import lombok.Cleanup;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Kyle on 11/16/2015.
 */
@Data
public class HttpRequest {
    
    private final URL url;
    private final HttpRequestType requestType;
    private final Map<String, String> headers;
    private final ContentType contentType;
    private final int timeout;
    private final Optional<Proxy> proxy;
    private final Optional<RequestDataWriter> requestDataWriter;
    private HttpURLConnection urlConnection;
    
    public static HttpRequestBuilder builder() {
        return new HttpRequestBuilder();
    }
    
    public String makeRequestText() throws IOException {
        if (proxy.isPresent()) {
            urlConnection = (HttpURLConnection) url.openConnection(proxy.get());
        } else {
            urlConnection = (HttpURLConnection) url.openConnection();
        }
        
        urlConnection.setRequestMethod(requestType.name());
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
        urlConnection.setRequestProperty("Content-Type", contentType.getValue());
        urlConnection.setConnectTimeout(timeout);
        
        headers.forEach(urlConnection::addRequestProperty);
        
        if (requestDataWriter.isPresent()) {
            @Cleanup ByteArrayOutputStream byos = new ByteArrayOutputStream();
            requestDataWriter.get().writeData(byos);
            urlConnection.addRequestProperty("Content-Length", Integer.toString(byos.size()));
            urlConnection.setDoOutput(true);
            @Cleanup DataOutputStream bos = new DataOutputStream(urlConnection.getOutputStream());
            bos.write(byos.toByteArray());
            urlConnection.getOutputStream().close();
        }
        
        @Cleanup InputStream inputStream = urlConnection.getInputStream();
        StringWriter stringWriter = new StringWriter();
        IOUtils.copy(inputStream, stringWriter);
        return stringWriter.toString();
    }
    
    public JsonObject makeRequestJson() throws IOException {
        return EzSkype.GSON.fromJson(makeRequestText(), JsonObject.class);
    }
    
    public <T> T makeRequestGson(Class<T> tClass) throws IOException {
        return EzSkype.GSON.fromJson(makeRequestText(), tClass);
    }
    
    public Document makeRequestSoup() throws IOException {
        return Jsoup.parse(makeRequestText());
    }
    
    public interface RequestDataWriter {
        
        void writeData(OutputStream outputStream) throws IOException;
    }
}
