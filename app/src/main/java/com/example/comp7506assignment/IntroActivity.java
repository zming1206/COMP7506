package com.example.comp7506assignment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_start;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        btn_start = (Button) findViewById(R.id.btn_start);
        textview = findViewById(R.id.textView);
        String text = "<font color=#000000>Adopt Your</font> <font color=#CCE4FC>Dream</font> <font color=#000000>Pet Here</font>";
        textview.setText(Html.fromHtml(text));

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });
    }
    public void openNewActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
    }
}
