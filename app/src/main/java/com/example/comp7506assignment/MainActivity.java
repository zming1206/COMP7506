package com.example.comp7506assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.PixelCopy;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private SearchView searchView;

    private ArrayList<PetAdoption> myData;

    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        recyclerView = findViewById(R.id.search_list);

        getAPIData request = new getAPIData(MainActivity.this);
        request.execute();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });

        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE},1);
        }

    }

    private void filterList(String s) {

        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("b"));
        items.add(new Item("a"));
        items.add(new Item("a"));
        items.add(new Item("b"));
        items.add(new Item("a"));
        items.add(new Item("a"));
        items.add(new Item("c"));
        items.add(new Item("a"));

        loadList(items);
    }

    private class getAPIData extends AsyncTask<String, String, String> {
        private Context context;
        ArrayList<PetAdoption> data;

        public getAPIData(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();

            String url = "https://sheets.googleapis.com/v4/spreadsheets/1zJDWR96nD6h20x8e3Mr8NHsrT4KpGzalaWXwv_pLKag?includeGridData=true&key=AIzaSyClTM6kyb_y6Ww3xcWWwK_Kv_c1OIWvyeE";

            data = new ArrayList<PetAdoption>();

            Response response = null;
            try {
                Request request = new Request.Builder().url(url).build();
                response = client.newCall(request).execute();

                String result = response.body().string();

                int lineNum = 0;

                JSONObject mainObject = new JSONObject(result);
                JSONArray sheetsArray = mainObject.getJSONArray("sheets");

                // First Sheet
                JSONObject sheetObject = sheetsArray.getJSONObject(0);
                JSONArray dataArray = sheetObject.getJSONArray("data");
                JSONObject dataObject = dataArray.getJSONObject(0);

                // First Sheet data object
                JSONArray rowDataArray = dataObject.getJSONArray("rowData");
                lineNum = rowDataArray.length();

                // Header Line (Not Use)
                JSONObject headerObject = rowDataArray.getJSONObject(0);

                // Row Data
                for(int i = 1; i < lineNum; i++) {
                    String organization = "";
                    String type = "";
                    String gender = "";
                    String name = "";
                    String adpotionNumber = "";
                    String source = "";
                    String date = "";

                    JSONObject eachRowObject = rowDataArray.getJSONObject(i);
                    JSONArray valueArray = eachRowObject.getJSONArray("values");

                    // Field Data
                    // 0 = Index Number
                    // 1 = Organization
                    // 2 = Type
                    // 3 = Gender
                    // 4 = Name
                    // 5 = Adoption Number
                    // 6 = Source
                    // 7 = Date

                    // 0 Index Number (Not Used)
                    JSONObject fieldObect = valueArray.getJSONObject(0);
                    JSONObject valueObject = fieldObect.getJSONObject("userEnteredValue");

                    // 1 Organization
                    fieldObect = valueArray.getJSONObject(1);
                    valueObject = fieldObect.getJSONObject("userEnteredValue");
                    organization = valueObject.getString("stringValue");

                    // 2 Type
                    fieldObect = valueArray.getJSONObject(2);
                    valueObject = fieldObect.getJSONObject("userEnteredValue");
                    type = valueObject.getString("stringValue");

                    // 3 Gender
                    fieldObect = valueArray.getJSONObject(3);
                    valueObject = fieldObect.getJSONObject("userEnteredValue");
                    gender = valueObject.getString("stringValue");

                    // 4 Name
                    fieldObect = valueArray.getJSONObject(4);
                    valueObject = fieldObect.getJSONObject("userEnteredValue");
                    name = valueObject.getString("stringValue");

                    // 5 Adoption Number
                    fieldObect = valueArray.getJSONObject(5);
                    valueObject = fieldObect.getJSONObject("userEnteredValue");
                    adpotionNumber = valueObject.getString("stringValue");

                    // 6 Source
                    fieldObect = valueArray.getJSONObject(6);
                    valueObject = fieldObect.getJSONObject("userEnteredValue");
                    source = valueObject.getString("stringValue");

                    // 7 Date
                    fieldObect = valueArray.getJSONObject(7);
                    valueObject = fieldObect.getJSONObject("userEnteredValue");
                    date = valueObject.getString("stringValue");

                    // Prepare PetAdoption Record
                    PetAdoption rec = new PetAdoption(organization, type, gender, name, adpotionNumber, source, date);

                    data.add(rec);
                }
            } catch (Exception e) {
                System.out.print("Unknown Exception: ");
                System.out.println(e);
            }
            return response.toString();
        }

        protected  void onPostExecute(String x) {
            Log.i("sheetData", data.toString());

            ArrayList<Item> items = new ArrayList<>();
            items.add(new Item("a"));
            items.add(new Item("a"));
            items.add(new Item("a"));
            items.add(new Item("b"));
            items.add(new Item("a"));
            items.add(new Item("a"));
            items.add(new Item("c"));
            items.add(new Item("a"));
            items.add(new Item("a"));
            items.add(new Item("a"));
            items.add(new Item("a"));
            items.add(new Item("a"));

            loadList(items);

            myData = data;
        }
    }

    private void loadList(ArrayList<Item> items) {
        customAdapter = new CustomAdapter(items);

        customAdapter.setOnClickListener((position, item) -> {
            Log.i("123", item.getName());

            Intent intent = new Intent(MainActivity.this, ShowDataActivity.class);
            intent.putExtra("sheetData", myData);
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customAdapter);
    }
}