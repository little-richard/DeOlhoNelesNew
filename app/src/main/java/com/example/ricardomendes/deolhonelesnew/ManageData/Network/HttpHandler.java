package com.example.ricardomendes.deolhonelesnew.ManageData.Network;

import android.app.DownloadManager;
import android.util.Log;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class HttpHandler{
    private static final String TAG = HttpHandler.class.getSimpleName();
    private Map<String, String> map;
    private URL url;
    private HttpURLConnection conn;
    public HttpHandler(){
        map = new HashMap<String, String>();
        conn = null;
        url = null;
    }
    public Map<String, String> getHeader(){
        return this.map;

    }
    public String makeServiceCall(String reqUrl){
        InputStream in = openConnection(reqUrl, "GET", new String[] {"Accept", "application/json"});
        String response = convertStreamToString(in);
        String total = conn.getHeaderField("x-total-count");
        String link = conn.getHeaderField("link");
        map.put("x-total-count", total);
        map.put("link", link);
        return response;
    }

    private InputStream openConnection(String url, String method, String[] property){
        InputStream in = null;
        try{
            this.url = new URL(url);
            this.conn = (HttpURLConnection) this.url.openConnection();
            this.conn.setRequestMethod(method);
            this.conn.setRequestProperty(property[0], property[1]);
            in = new BufferedInputStream(this.conn.getInputStream());
            return in;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String convertStreamToString(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();

        String line;
        try{
            while((line = reader.readLine()) != null){
                sb.append(line).append('\n');
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                in.close();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        return  sb.toString();
    }
}
