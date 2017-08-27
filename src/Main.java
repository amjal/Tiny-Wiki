/**Amirmohammad Jalili**/
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Iterator;
import java.util.Set;

public class Main {
    protected static Proxy proxy = null;
    public static void main(String[] args) {
        //TODO searches with spaces
        try {
            String address = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&explaintext&titles="+args[0];
            if(args.length == 3){
                proxy = new Proxy(Proxy.Type.HTTP , new InetSocketAddress(args[1] , Integer.parseInt(args[2])));
            }
            ProxySelector.setDefault(new LocalProxySelector());
            ResponseCache.setDefault(new LocalResponseCache());
            URL url = new URL(address);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            String result="";
            int in;
            while (true) {
                in = inputStream.read();
                if (in != -1) {
                    result = result.concat(Character.toString((char) in));
                } else break;
            }
            JSONParser parser = new JSONParser();
            JSONObject main = (JSONObject)parser.parse(result);
            JSONObject query = (JSONObject)main.get("query");
            JSONObject pages = (JSONObject)query.get("pages");
            Set<Object> set = pages.keySet();
            Iterator<Object> iterator = set.iterator();
            int c=0;
            while(iterator.hasNext()){
                Object key = iterator.next();
                JSONObject page = (JSONObject)pages.get(key);
                Object description = page.get("extract");
                System.out.println("\n\nArticle #"+(++c));
                System.out.println(description);
                iterator.remove();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
