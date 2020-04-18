package com.example.db_project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;

public class storeinfo extends AppCompatActivity {

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.store_info);
        Intent intent = getIntent();
        name = intent.getStringExtra("username");
        //name contains the username that is passed
        newSearcher helper_again = new newSearcher();
        helper_again.execute();
    }

    public class newSearcher extends AsyncTask<Void, Void, Void> {
        String location = null;
        String count = null;
        String status = null;

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL("http://192.168.43.142:8000/users/getstoreinfo");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                System.out.println(name);
                conn.setRequestProperty("Content-Type", "application/json");
                JSONObject data = new JSONObject();
                data.put("username", name);
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
                System.out.println(response.toString());
                JSONArray res= (JSONArray) (new JSONObject(response.toString())).get("info");
                JSONObject row = res.getJSONObject(0);
                location = row.getString("location");
                count = row.getString("count");
                Integer a =  Integer.parseInt(count);
                if (a >= 15){
                    status = "crowded";
                }
                else {
                    status = "non-crowded";
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TextView name_text = (TextView) findViewById(R.id.name);
            TextView location_text = (TextView) findViewById(R.id.location);
            TextView count_text = (TextView) findViewById(R.id.count);
            TextView status_text = (TextView) findViewById(R.id.status);
            name_text.setText(name);
            location_text.setText(location);
            count_text.setText(count);
            status_text.setText(status);

            Integer x = 1;
        }
    }
}



