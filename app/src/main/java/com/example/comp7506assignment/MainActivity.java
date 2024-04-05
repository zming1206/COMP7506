package com.example.comp7506assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.PixelCopy;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button button1;
    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button_getAPIData);
        text1 = (TextView) findViewById(R.id.textView1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setText("Please wait until activity swap");
                getAPIData request = new getAPIData(MainActivity.this);
                request.execute();
            }
        });
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
            Intent intent = new Intent(context, ShowDataActivity.class);
            intent.putExtra("sheetData", data);
            context.startActivity(intent);
        }

    }
}