package ru.itis.kurguskina;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import org.json.JSONException;
import org.json.JSONObject;
public class J {
    public void get() throws Exception {
        File f = new File("src/bus.json");
        if (f.exists()){
            InputStream is = new FileInputStream("src/bus.json");
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            System.out.println(jsonTxt);
            JSONObject json = new JSONObject(jsonTxt);
        }
    }

    private  String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public  String getJson() throws IOException, JSONException {
        try {
            JSONObject json = readJsonFromUrl("http://data.kzn.ru:8082/api/v0/dynamic_datasets/bus/2175.json");
            return json.toString();
        }
        catch (Exception e) {
            return "САЙТ http://data.kzn.ru:8082/api/v0/dynamic_datasets/bus/2175.json НЕ ОТВЕЧАЕТ";
        }
    }
}
