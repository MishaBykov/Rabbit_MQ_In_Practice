import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Map;

public class MessageFTP implements Serializable{
    private String path;
    private Map<String, ByteArrayOutputStream> file;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, ByteArrayOutputStream> getFile() {
        return file;
    }

    public void setFile(Map<String, ByteArrayOutputStream> file) {
        this.file = file;
    }
}
