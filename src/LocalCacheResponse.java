import java.io.*;
import java.net.CacheResponse;
import java.util.*;

public class LocalCacheResponse extends CacheResponse{
    File file;
    FileInputStream fin;
    public LocalCacheResponse(File file){
        this.file = file;
        try {
            fin = new FileInputStream(this.file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Map<String, List<String>> getHeaders() throws IOException {
        HashMap<String , List<String>> map = new HashMap<>();
        LinkedList<String> list = new LinkedList<>();
        int in;
        String result = "";
        while(true){
            in = fin.read();
            if(in == '\n') break;
            result = result.concat(Character.toString((char)in));
        }
        list.add(result);
        map.put("Date",list);
        return map;
    }

    @Override
    public InputStream getBody() throws IOException {
        return fin;
    }
}
