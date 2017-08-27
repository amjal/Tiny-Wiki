import java.io.*;
import java.net.CacheRequest;

public class LocalCacheRequest extends CacheRequest{
    FileOutputStream fout;
    File file;
    public LocalCacheRequest(File file, FileOutputStream fout) {
        this.fout = fout;
        this.file = file;
    }

    @Override
    public OutputStream getBody() throws IOException {
        return fout;
    }

    @Override
    public void abort() {
        file.delete();
    }
}
