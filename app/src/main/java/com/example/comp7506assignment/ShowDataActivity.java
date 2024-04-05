package com.example.comp7506assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowDataActivity extends AppCompatActivity {
    TextView display1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        display1 = (TextView)findViewById(R.id.displayTextView);

        Bundle dataBundle = getIntent().getExtras();
        ArrayList data = (ArrayList) dataBundle.getSerializable("sheetData");

        display1.setText((String) data.get(2).toString());
    }
}