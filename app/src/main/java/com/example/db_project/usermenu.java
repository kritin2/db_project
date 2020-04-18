package com.example.db_project;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;

public class usermenu extends AppCompatActivity {
    //
    String username = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_menu);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        final Button button1 = findViewById(R.id.button);
        final Button button2 = findViewById(R.id.button2);
        final Button button3 = findViewById(R.id.button3);
        final Button button4 = findViewById(R.id.button4);
        button1.setOnClickListener(new android.widget.Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(usermenu.this, profileuser.class);
                myIntent.putExtra("username", username);
                startActivity(myIntent);
            }
        });
        button2.setOnClickListener(new android.widget.Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(usermenu.this, displayshops.class);
                myIntent.putExtra("username", username);
                startActivity(myIntent);
            }
        });
        button3.setOnClickListener(new android.widget.Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(usermenu.this, news.class);
                myIntent.putExtra("username", username);
                startActivity(myIntent);
            }
        });
        button4.setOnClickListener(new android.widget.Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(usermenu.this, Signup.class);
                myIntent.putExtra("username", username);
                startActivity(myIntent);
            }
        });
    }
}
