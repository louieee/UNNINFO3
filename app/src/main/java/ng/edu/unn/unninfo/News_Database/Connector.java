package ng.edu.unn.unninfo.News_Database;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Connector {
    public static HttpURLConnection connect (String urlAddress){
        try {
            URL url = new URL(urlAddress);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //conn props
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(20000);
            conn.setDoInput(true);

            return conn;

        }catch(MalformedURLException m){
            m.printStackTrace();
        }catch (IOException i){
            i.printStackTrace();
        }
        return null;
    }
}
