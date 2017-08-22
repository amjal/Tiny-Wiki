

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&explaintext&titles="+args[0]);
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            String result="";
            int in;
            while(true){
                in = inputStream.read();
                if(in != -1){
                    result = result.concat(Character.toString((char)in));
                }
                else break;
            }
            JSONParser parser = new JSONParser();
            JSONObject main = (JSONObject)parser.parse(result);
            JSONObject query = (JSONObject)main.get("query");
            JSONObject pages = (JSONObject)query.get("pages");
            Set<Object> set = pages.keySet();
            Iterator<Object> iterator = set.iterator();
            while(iterator.hasNext()){
                Object key = iterator.next();
                JSONObject page = (JSONObject)pages.get(key);
                Object description = page.get("extract");
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
