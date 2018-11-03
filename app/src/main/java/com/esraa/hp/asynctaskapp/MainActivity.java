package com.esraa.hp.asynctaskapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    String link="https://jsonplaceholder.typicode.com/users";
URL url;
HttpURLConnection urlConnection;
InputStream inputStream;
String result;
Button button;
TextView textView;
StringBuffer buffer1;
String finalResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.btn);
        textView=findViewById(R.id.txt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            new JSONTask().execute(link);
            }
        });
    }

    public class JSONTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                url=new URL(link);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                urlConnection= (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                inputStream=urlConnection.getInputStream();
                int c=0;
                StringBuffer buffer=new StringBuffer();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    while ((c = inputStream.read()) != -1) {
                        buffer.append((char) c);
                    }
                }
                result=buffer.toString();
                buffer1=new StringBuffer();
                JSONArray array=new JSONArray(result);
                for(int i=0;i<array.length();i++) {
                    JSONObject object = array.getJSONObject(i);
                    int id = object.getInt("id");
                    String name = object.getString("name");
                    buffer1.append(name + " " + id + "\n");
                }
                finalResult=buffer1.toString();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return finalResult;
        }

        @Override
        protected void onPostExecute(String finalResult) {
            super.onPostExecute(finalResult);
            textView.setText(finalResult);
        }
    }
}
