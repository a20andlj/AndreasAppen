package com.example.andreasappen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BokActivity extends AppCompatActivity {

    private ArrayList<Books> arrayList;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bok);

        new JsonTask().execute("https://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=a20andlj");

        arrayList = new ArrayList();
        adapter = new ArrayAdapter(this, R.layout.list_item_textview, arrayList);
        ListView listView = findViewById(R.id.bok_listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Books temp_b = arrayList.get(position);
            Log.d("BokActivity ==>", "Toast");
            Toast.makeText(BokActivity.this, "Boken: " + temp_b.getName() + "." + " Vart: "
                    + temp_b.getLocation() + "." + " Utgivningsår: " + temp_b.getSize() + "."
                    + " Pris: " + temp_b.getCost() + " kr.", Toast.LENGTH_SHORT).show();

        });



        Button close = findViewById(R.id.close_bok_activity);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public class JsonTask extends AsyncTask<String, String, String> {

        private HttpURLConnection connection = null;
        private BufferedReader reader = null;
        private Object Mountain;

        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null && !isCancelled()) {
                    builder.append(line).append("\n");
                }
                return builder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json) {
            Log.d("BokActivity ==>", json);
            Gson gson = new Gson();
            Books[] newBooks = gson.fromJson(json, Books[].class);
            arrayList.clear();
            for (int i = 0; i < newBooks.length; i++) {
                Books b = newBooks[i];
                Log.d("BokActivity ==>", "En bok: " + newBooks[i]);
                arrayList.add(b);
            }
            adapter.notifyDataSetChanged();
        }
    }
}