package com.example.rest_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView newsTextView;

    RequestQueue requestQueue;
    String BASE_URL = "https://newsapi.org";
    String TOP_HEADLINE_ENDPOINT = "/v2/top-headlines";
    String COUNTRY_ENDPOINT = "?country=";
    String CATEGORY_ENDPOINT = "?category=";
    String API_KEY = "&apiKey=4da597679d98440a8837fa3e07013588";
    String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsTextView = findViewById(R.id.newsTextView);
        URL = BASE_URL + TOP_HEADLINE_ENDPOINT + CATEGORY_ENDPOINT + "technology" + API_KEY;
        URL = BASE_URL + TOP_HEADLINE_ENDPOINT + COUNTRY_ENDPOINT + "us" + API_KEY;
        URL = "https://api.nytimes.com/svc/topstories/v2/technology.json?api-key=Xdfow53YSgfP4KXgVxl3o5anOblbArRu";
        System.out.println("URL: " + URL);

        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Response: " + response);
                parseJSONObject(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error: " + error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public  void parseJSONObject(JSONObject response){
        try {
            JSONArray articlesArray = response.getJSONArray("results");
            for(int i = 0; i < articlesArray.length(); i++){
                JSONObject element = articlesArray.getJSONObject(i);
                String content = "";
                content += "Author: " + element.getString("author") + "\n";
                content += "Title: " + element.getString("title") + "\n";
                content += "Desctiption: " + element.getString("description") + "\n\n";

                newsTextView.append(content);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
