package in.kyle.skype.skypebot2.apis;

import org.apache.commons.net.whois.WhoisClient;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainWhoisLookup {
    
    private static final Pattern pattern = Pattern.compile("Whois Server:\\s(.*)");
    private final String domainName;
    
    public DomainWhoisLookup(String domainName) {
        this.domainName = domainName;
    }
    
    public String getWhois() throws IOException {
        StringBuilder result = new StringBuilder("");
        
        WhoisClient whois = new WhoisClient();
        whois.connect(WhoisClient.DEFAULT_HOST);
        
        String whoisData1 = whois.query("=" + domainName);
        
        whois.disconnect();
        
        String whoisServerUrl = getWhoisServer(whoisData1);
        if (!whoisServerUrl.equals("")) {
            
            String whoisData2 =
                    queryWithWhoisServer(domainName, whoisServerUrl);
            
            result.append(whoisData2);
        }
        
        return result.toString();
        
    }
    
    private String queryWithWhoisServer(String domainName, String whoisServer) throws IOException {
        
        WhoisClient whois = new WhoisClient();
        whois.connect(whoisServer);
        String result = whois.query(domainName);
        whois.disconnect();
        
        return result;
    }
    
    private String getWhoisServer(String whois) {
        
        String result = "";
        
        Matcher matcher = pattern.matcher(whois);
        
        // get last whois server
        while (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }
    
}