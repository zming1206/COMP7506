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

    private Button btn1,btn2,btn3,btn4,btn5;

    private Boolean[] checked = {false,false,false,false,false};

    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        recyclerView = findViewById(R.id.search_list);
        btn1= findViewById(R.id.btn_cat);
        btn2= findViewById(R.id.btn_dog);
        btn3= findViewById(R.id.btn_rabbit);
        btn4= findViewById(R.id.btn_turtle);
        btn5= findViewById(R.id.btn_snake);

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

        btn1.setOnClickListener(view -> filterListByBtn("貓",0));
        btn2.setOnClickListener(view -> filterListByBtn("狗",1));
        btn3.setOnClickListener(view -> filterListByBtn("兔",2));
        btn4.setOnClickListener(view -> filterListByBtn("龜",3));
        btn5.setOnClickListener(view -> filterListByBtn("蛇",4));
    }

    private void filterList(String s) {
        Log.i("filterList", s);
        ArrayList<PetAdoption> items = new ArrayList<>();

        if (s.isEmpty()) {
            items = new ArrayList<>(myData);
        }
        else {
            for(int i = 0; i < myData.size(); i++) {
                if (myData.get(i).name.toLowerCase().contains(s.toLowerCase()) || myData.get(i).organization.toLowerCase().contains(s.toLowerCase()))
                    items.add(myData.get(i));
            }
        }

        loadList(items);
    }

    private void filterListByBtn(String s, int index){
        Log.i("filterListByBtn", s);
        ArrayList<PetAdoption> items = new ArrayList<>();

        if(checked[index]) {
            items = new ArrayList<>(myData);
            checked[index] = !checked[index];
        }else {
            for(int i = 0; i < myData.size(); i++) {
                if (myData.get(i).type.toLowerCase().contains(s.toLowerCase()))
                    items.add(myData.get(i));
            }
            checked[index] = !checked[index];
        }
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
                    String contactNumber = "";

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
                    // 8 = Contact Number

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

                    // 7 Contact Number
                    fieldObect = valueArray.getJSONObject(8);
                    valueObject = fieldObect.getJSONObject("userEnteredValue");
                    contactNumber = valueObject.getString("stringValue");

                    // Prepare PetAdoption Record
                    PetAdoption rec = new PetAdoption(organization, type, gender, name, adpotionNumber, source, date, contactNumber);

                    System.out.println(rec.toString());

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

            ArrayList<PetAdoption> items = new ArrayList<>(data);

            loadList(items);

            myData = data;
        }
    }

    private void loadList(ArrayList<PetAdoption> items) {

        customAdapter = new CustomAdapter(items);

        customAdapter.setOnClickListener((position, item) -> {
            Log.i("123", item.name);

            Intent intent = new Intent(MainActivity.this, ShowDataActivity.class);
            intent.putExtra("sheetData", myData);
            intent.putExtra("petName", item.name);
            intent.putExtra("Institution", item.organization);
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customAdapter);
    }
}