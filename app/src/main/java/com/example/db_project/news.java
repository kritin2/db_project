package com.example.db_project;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class news extends AppCompatActivity {
    //
    public class helpernews extends AsyncTask<Void, Void, Void> {
//        String category;
        JSONArray res;
        String[] usernames = new String[0];

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {

                url = new URL("http://192.168.43.142:8000/users/getstorenews");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                JSONObject data = new JSONObject();
//                data.put("symptom", name);
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
                res= (JSONArray) (new JSONObject(response.toString())).get("info");
                usernames = new String[res.length()];
                for (int i=0;i<res.length();i++){
                    JSONObject row = res.getJSONObject(i);
                    usernames[i] = (String) row.get("news");
                }
                for (int i=0;i<res.length();i++){
                    System.out.println(usernames[i]);
                }

//                System.out.println(usernames[0]);
                //String x = usernames.get(2);
                //Integer y = res.length();
                //Integer xx =1;
            } catch (IOException | JSONException e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String he = "Completed Search";
            Toast.makeText(news.this,he,Toast.LENGTH_SHORT).show() ;
//
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.mylistviewlayout, usernames);
            final ListView listView = (ListView) findViewById(R.id.news_list);
            listView.setAdapter(adapter);

//            listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    String item = (String) listView.getItemAtPosition(position);
//                    //selected username is item
//                    Intent myIntent = new Intent(displayshops.this, storeinfo.class);
//                    myIntent.putExtra("username", item); //Optional parameters
//                    startActivity(myIntent);
//                }
//            });
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_layout);
        final Button button_news = findViewById(R.id.news);
        button_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new helpernews().execute();
            }
        });
    }
}
