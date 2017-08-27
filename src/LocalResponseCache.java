
import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Map;

public class LocalResponseCache extends ResponseCache{
    @Override
    public CacheResponse get(URI uri, String s, Map<String, List<String>> map) throws IOException {
        if(s.equalsIgnoreCase("head")) return null;
        File file = new File("../../../caches");
        URL url = uri.toURL();
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        connection.setRequestMethod("HEAD");
        File[] files= file.listFiles();
        for(int i =0; i < files.length ; i ++){
            if(files[i].getName().equals(uri.getQuery())){
                LocalCacheResponse localCacheResponse = new LocalCacheResponse(files[i]);
                if(connection.getLastModified()>
                        Long.parseLong(localCacheResponse.getHeaders().get("Date").get(0))){
                    files[i].delete();
                    break;
                }
                else return localCacheResponse;
            }
        }
        return null;
    }

    @Override
    public CacheRequest put(URI uri, URLConnection urlConnection) throws IOException {
        if(((HttpsURLConnection) urlConnection).getRequestMethod().equalsIgnoreCase("head"))
            return null;
        if(uri.getAuthority().contains("wikipedia.org")) {
            File file = new File("../../../caches", uri.getQuery());
            FileOutputStream fout = new FileOutputStream(file);
            fout.write(Long.toString(urlConnection.getDate()).getBytes());
            fout.write('\n');
            return new LocalCacheRequest(file, fout);
        }
        return null;
    }
}
