package com.project.rushabh.jarvis;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * Created by Rushabh on 13-Sep-17.
 */

public class LoginAuthentication extends AsyncTask<Object,Object,String> {

    //String value = "2";
    @Override
    protected String doInBackground(Object[] params) {
        try {
            String id = (String) params[0];
            String password = (String) params[1];

            String link = "http://192.168.0.104:8080/Jarvis/loginauthentication.php";
            String data = URLEncoder.encode("id", "UTF-8") + "=" +
                    URLEncoder.encode(id, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");
            /*String link = "http://192.168.1.35:8080/Jarvis/serr.php";
            String data = URLEncoder.encode("val", "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");*/

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;

            // Read Server Response
            line = reader.readLine();
            while (line != null && !line.equalsIgnoreCase("")) {
                sb.append(line);
                line = reader.readLine();
            }
            return sb.toString();
        } catch (Exception e) {
            return "Exception:" + Arrays.toString(e.getStackTrace());
        }
    }
}