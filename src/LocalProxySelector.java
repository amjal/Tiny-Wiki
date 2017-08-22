import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LocalProxySelector extends ProxySelector{
    ArrayList<URI> failed = new ArrayList<>();
    @Override
    public List<Proxy> select(URI uri) {
        ArrayList<Proxy> result = new ArrayList<>();
        if(Main.proxy == null || failed.contains(uri) || (!"http".equalsIgnoreCase(uri.getScheme()) &&
                !"https".equalsIgnoreCase(uri.getScheme()))) {
            result.add(Proxy.NO_PROXY);
        }
        else{
            result.add(Main.proxy);
        }
        return result;
    }

    @Override
    public void connectFailed(URI uri, SocketAddress socketAddress, IOException e) {
        failed.add(uri);
    }
}
