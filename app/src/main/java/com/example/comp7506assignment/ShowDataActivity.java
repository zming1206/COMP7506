package com.example.comp7506assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowDataActivity extends AppCompatActivity {
    TextView name, institution, category_title, sex_title, date_title, number;

    ImageView back, chain, phone, donate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        name = (TextView) findViewById(R.id.name);
        institution = (TextView) findViewById(R.id.institution);
        category_title = (TextView) findViewById(R.id.sex_title);
        sex_title = (TextView) findViewById(R.id.year_title);
        date_title = (TextView) findViewById(R.id.date_title);
        number = (TextView) findViewById(R.id.number);

        back = (ImageView) findViewById(R.id.my_back);
        chain = (ImageView) findViewById(R.id.chain);
        phone = (ImageView) findViewById(R.id.phone);
        donate = (ImageView) findViewById(R.id.donate);


        Bundle dataBundle = getIntent().getExtras();
        ArrayList<PetAdoption> data = (ArrayList) dataBundle.getSerializable("sheetData");
        String petName = dataBundle.getString("petName");

        PetAdoption pet;
        int targetIndex = 0;
        for(int i = 0; i < data.size(); i++) {
            pet = data.get(i);
            if(pet.name.equals(petName)) {
                targetIndex = i;
            }
        }

        pet = data.get(targetIndex);

        name.setText(pet.name);
        institution.setText(pet.organization);
        category_title.setText(pet.type);
        sex_title.setText(pet.gender);
        date_title.setText(pet.announceDate);
        number.setText(pet.adoptionNumber);

        String sourceURL = pet.source;
        String contactNumber = pet.contactNumber;

        donate.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sourceURL));
            startActivity(browserIntent);
        });
        phone.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(contactNumber));
            startActivity(intent);
        });
        chain.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sourceURL));
            startActivity(browserIntent);
        });
        back.setOnClickListener(view -> onBackPressed());
    }
}