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

public class Signup extends AppCompatActivity {
//
    public class Signingup extends AsyncTask<Void, Void, Void> {

        public Signingup(String a, String b, String c, boolean d, boolean e){
            user_name = a;
            passwd = b;
            category = c;
            if (d == true){
                type = "doctor";
            }
            else if (e == true){
                type = "patient";
            }
        }
        String user_name = null;
        String passwd = null;
        String type = null;
        String category = null;
        @Override
        protected Void doInBackground(Void... voids) {

            System.out.println("GfG2");


            try {
                URL url = new URL("http://192.168.0.5:8000/signup");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                JSONObject data = new JSONObject();
                data.put("username", user_name);
                data.put("passwd", passwd);
                data.put("type_person", type);
                data.put("category", category);
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
                JSONArray res= (JSONArray) (new JSONObject(response.toString())).get("status");
                String val = res.getString(0);
                if (val == "success"){
                    System.out.println(val);
                }


            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText uname;
        final EditText passwd;
        final EditText category;
        final Switch doc ;
        final Switch pat;
        uname = findViewById(R.id.username);
        passwd = findViewById(R.id.password);
        category = findViewById(R.id.category);
        doc = findViewById(R.id.select_doctor);
        pat = findViewById(R.id.select_patient);
        final Button button_signup = findViewById(R.id.sign_up);
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String un = uname.getText().toString();
                final String pw = passwd.getText().toString();
                final String ct = category.getText().toString();
                final boolean doc_check = doc.isChecked();
                final  boolean pat_check = pat.isChecked();
                new Signingup(un, pw, ct, doc_check, pat_check).execute();
//                System.out.println(doc_check);
//                final String all_symptoms = symptom_name.getText().toString();
//                //email.setText("abc");
//                new Searcher(all_symptoms).execute();

            }
        });
        final Button button_login = findViewById(R.id.login_1);
        button_login.setOnClickListener(new android.widget.Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Signup.this, login.class);
                startActivity(myIntent);
//                final String all_symptoms = symptom_name.getText().toString();
//                //email.setText("abc");
//                new Searcher(all_symptoms).execute();

            }
        });
    }
}
