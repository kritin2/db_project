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
import org.json.JSONObject;

import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class profileuser extends AppCompatActivity {
    //
    String username = null;
    String name_of_person = null;
    public class helperclass extends AsyncTask<Void, Void, Void> {

        public helperclass(String a){
            name_of_person = a;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            System.out.println("GfG2");


            try {
                URL url = new URL("http://192.168.43.142:8000/users/update");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                JSONObject data = new JSONObject();
                data.put("username", username);
                data.put("name", name_of_person);
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
                JSONObject type_person = (new JSONObject(response.toString()));
                String val = type_person.getString("status");
                System.out.println(val);
//                type_user = type_person.getString(0);

                if (val.equals("success")){
                    System.out.println(val);
                }
                System.out.println(val);

            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            final EditText name;
            name = findViewById(R.id.username_login);
            name.setText(name_of_person);
            String he = "completed saving";
            Toast.makeText(profileuser.this,he,Toast.LENGTH_SHORT).show() ;
//            if (type_user.equals("user")){
//                System.out.println("hgjhbkj");
//            Intent myIntent = new Intent(Signup.this, usermenu.class);
//            myIntent.putExtra("username", user_name);
//            startActivity(myIntent);
//            }
//            else {
//                Intent myIntent = new Intent(Signup.this, employerActivity.class);
//            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        final EditText name;
        name = findViewById(R.id.username_login);
        final Button save_button = findViewById(R.id.button5);
        final Button back_button = findViewById(R.id.button6);
        save_button.setOnClickListener(new android.widget.Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name_person = name.getText().toString();
                new helperclass(name_person).execute();
            }
        });
        back_button.setOnClickListener(new android.widget.Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(profileuser.this, usermenu.class);
                myIntent.putExtra("username", username);
                startActivity(myIntent);
//                final String all_symptoms = symptom_name.getText().toString();
//                //email.setText("abc");
//                new Searcher(all_symptoms).execute();

            }
        });
    }
}
