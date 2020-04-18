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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    String symptom1;
public class Searcher extends AsyncTask<Void, Void, Void> {
    String[] diseases = new String[0];
    String name;
    JSONArray res;
    public Searcher(String n){name = n;}
    @Override
    protected Void doInBackground(Void... voids) {

        System.out.println("GfG2");


        try {
            URL url = new URL("http://192.168.43.142:8000");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            System.out.println(name);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject data = new JSONObject();
            data.put("symptom", name);
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
            res= (JSONArray) (new JSONObject(response.toString())).get("disease");
            diseases = new String[res.length()];
            for (int i=0;i<res.length();i++){
                String row = res.getString(i);
                diseases[i] = row;
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        String he = "Completed Search";
        Toast.makeText(MainActivity.this,he,Toast.LENGTH_SHORT).show() ;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.mylistviewlayout, diseases);
        final ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) listView.getItemAtPosition(position);
                Intent myIntent = new Intent(MainActivity.this, UserActivity.class);
                myIntent.putExtra("disease", item); //Optional parameters
                startActivity(myIntent);
                //selected username is item

            }
        });
    }
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        final EditText symptom_name;
//        disease_name = findViewById(R.id.disease);
//        symptom_name = findViewById(R.id.alias);
//        treatment_name = findViewById(R.id.treatment);

        sharedPref = getPreferences(Context.MODE_PRIVATE);
//        disease_name.setText(sharedPref.getString("disease", disease1));
//        symptom_name.setText(sharedPref.getString("symptom", symptom1));
//        treatment_name.setText(sharedPref.getString("treatment", treatment1));
//        final Button button = findViewById(R.id.graph_query);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String all_symptoms = symptom_name.getText().toString();
                //email.setText("abc");
//                new Searcher(all_symptoms).execute();

//            }
//        });
    }
}
