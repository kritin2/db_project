package com.example.db_project;

import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HttpHandler {

    JSONObject data;
    Handler handler;

    public HttpHandler(JSONObject data, Handler handler) throws JSONException {
        this.data = data;
        this.handler = handler;
    }

    public void doPost() {
        final Message msg = Message.obtain();
        final Bundle bundle = new Bundle();
        new Thread(new Runnable() {
            @Override
            public void run() {
            try {
                System.out.println("GfG2");

                URL url = new URL("http://192.168.0.5:8000");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                String a = data.toString();
                System.out.println(a);
                conn.setRequestProperty("Content-Type", "application/json");

                Log.i("JSON", data.toString());
                conn.getOutputStream().write(data.toString().getBytes());
                conn.connect();

                int resCode = conn.getResponseCode();
                String resMeg = conn.getResponseMessage();
                Log.i("STATUS", String.valueOf(resCode));
                Log.i("MSG", resMeg);

                DataInputStream is = new DataInputStream(conn.getInputStream());
                StringBuilder response = new StringBuilder();
                int c;
                while ((c = is.read()) != -1)
                    response.append((char) c);
                Log.i("DATA", response.toString());
                is.close();

                conn.disconnect();

                bundle.putString("response", response.toString());
                msg.setData(bundle);

            } catch (Exception e) {
                e.printStackTrace();
            }
            handler.sendMessage(msg);
            }
        }).start();
    }

}
