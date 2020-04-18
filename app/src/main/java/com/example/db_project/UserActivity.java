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

public class UserActivity extends AppCompatActivity {

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.disease_info);
        Intent intent = getIntent();
        name = intent.getStringExtra("disease");
        //name contains the username that is passed
        newSearcher helper_again = new newSearcher();
        helper_again.execute();
    }

    public class newSearcher extends AsyncTask<Void, Void, Void> {
        String disease_name = null;
        String alias_name = null;
        String symptom_name = null;
        String treatment_name = null;

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL("http://192.168.43.142:8000/disease");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                System.out.println(name);
                conn.setRequestProperty("Content-Type", "application/json");
                JSONObject data = new JSONObject();
                data.put("disease", name);
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
                disease_name = "Disease : ";
                disease_name = disease_name + name;
                symptom_name = "Symptoms : ";
                treatment_name = "Treatments : ";
                alias_name = "Aliases : ";
                JSONArray sy= (JSONArray) (new JSONObject(response.toString())).get("symptom");
                JSONArray al= (JSONArray) (new JSONObject(response.toString())).get("alias");
                JSONArray tr= (JSONArray) (new JSONObject(response.toString())).get("treatment");
                for (int i=0;i<sy.length();i++){
                    String h = sy.getString(i);
                    symptom_name = symptom_name + h + ", ";

                }
                for (int i=0;i<al.length();i++){
                    String h = al.getString(i);
                    alias_name = alias_name + h + ", ";
                }
                for (int i=0;i<tr.length();i++){
                    String h = tr.getString(i);
                    treatment_name = treatment_name + h + ", ";
                }
                System.out.println(symptom_name);


            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TextView mydisease = (TextView) findViewById(R.id.disease);
            TextView myalias = (TextView) findViewById(R.id.symptom);
            TextView mysymptom = (TextView) findViewById(R.id.alias);
            TextView mytreatment = (TextView) findViewById(R.id.treatment);
            mydisease.setText(disease_name);
            myalias.setText(alias_name);
            mysymptom.setText(symptom_name);
            mytreatment.setText(treatment_name);

            Integer x = 1;
        }
    }
}



