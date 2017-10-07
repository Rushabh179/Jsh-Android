package com.project.rushabh.jarvis;

import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Rushabh on 05-Oct-17.
 */

public class ChangeStatus extends AsyncTask<Integer,Void,String> {

    @Override
    protected String doInBackground(Integer... params) {
        try {

            Integer val = params[0];

            String link = "http://192.168.43.214/serr.php";
            String data;
            data = URLEncoder.encode("val", "UTF-8") + "=" +
                    URLEncoder.encode(String.valueOf(val), "UTF-8");

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

            line = reader.readLine();
            while (line != null && !line.equalsIgnoreCase("")) {
                sb.append(line);
                line = reader.readLine();
            }
            return sb.toString();

        } catch (Exception e) {
            return e.toString();
        }
    }
}