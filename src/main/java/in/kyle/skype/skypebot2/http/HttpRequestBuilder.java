package in.kyle.skype.skypebot2.http;

import lombok.ToString;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ToString
public class HttpRequestBuilder {
    
    private String url;
    private HttpRequestType requestType;
    private Map<String, String> headers;
    private ContentType contentType;
    private int timeout;
    private Optional<Proxy> proxy;
    private Optional<HttpRequest.RequestDataWriter> requestDataWriter;
    private StringBuilder postData;
    private Optional<byte[]> postBytes;
    
    HttpRequestBuilder() {
        this.headers = new HashMap<>();
        this.timeout = 0;
        this.proxy = Optional.empty();
        this.requestDataWriter = Optional.empty();
        this.postData = new StringBuilder();
        this.postBytes = Optional.empty();
        this.contentType = ContentType.WWW_FORM;
        this.requestType = HttpRequestType.GET;
    }
    
    public HttpRequestBuilder addPostData(String key, String value) {
        if (postData.length() != 0) {
            postData.append("&");
        }
        postData.append(key).append("=").append(value);
        return this;
    }
    
    public HttpRequestBuilder addEncodedPostData(String key, String value) {
        try {
            addPostData(key, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException ignored) {
        }
        return this;
    }
    
    public HttpRequestBuilder header(String key, String value) {
        headers.put(key, value);
        return this;
    }
    
    public HttpRequestBuilder postData(String data) {
        this.postData = new StringBuilder(data);
        return this;
    }
    
    public HttpRequestBuilder postBytes(byte[] bytes) {
        this.postBytes = Optional.ofNullable(bytes);
        return this;
    }
    
    public HttpRequestBuilder url(String url) {
        this.url = url;
        return this;
    }
    
    public HttpRequestBuilder urlParameter(String key, String value) {
        if (!url.contains("?")) {
            url += "?";
        } else {
            url += "&";
        }
        try {
            url += key + "=" + URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ignored) {
        }
        return this;
    }
    
    public HttpRequestBuilder requestType(HttpRequestType requestType) {
        this.requestType = requestType;
        return this;
    }
    
    public HttpRequestBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }
    
    public HttpRequestBuilder contentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }
    
    public HttpRequestBuilder timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }
    
    public HttpRequestBuilder proxy(Proxy proxy) {
        this.proxy = Optional.ofNullable(proxy);
        return this;
    }
    
    public HttpRequestBuilder requestDataWriter(HttpRequest.RequestDataWriter requestDataWriter) {
        this.requestDataWriter = Optional.ofNullable(requestDataWriter);
        return this;
    }
    
    public HttpRequest build() {
        
        if (postData.length() != 0 || postBytes.isPresent()) {
            byte[] data = new byte[0];
            if (postData.length() != 0) {
                try {
                    data = postData.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                data = postBytes.get();
            }
            final byte[] finalData = data;
            requestDataWriter = Optional.of((stream) -> {
                stream.write(finalData);
            });
        }
    
        try {
            return new HttpRequest(new URL(url), requestType, headers, contentType, timeout, proxy, requestDataWriter);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}