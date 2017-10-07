package com.project.rushabh.jarvis;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Rushabh on 06-Oct-17.
 */

public class ChangeAccountInfo extends AsyncTask<String,Void,Boolean> {
    @Override
    protected Boolean doInBackground(String... params) {
        try {
            String oldpassword = params[0];
            String password = params[1];

            String link = LinkSet.link + "changeaccountinfo.php";
            String data;
            data = URLEncoder.encode("oldpassword", "UTF-8") + "=" +
                    URLEncoder.encode(oldpassword, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            return reader != null;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}