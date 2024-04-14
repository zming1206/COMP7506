package com.example.comp7506assignment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowDataActivity extends AppCompatActivity {
    TextView name, institution, category_title, sex_title, date_title, number;

    ImageView back, chain, phone, donate,showAnimal;

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
        showAnimal = (ImageView) findViewById(R.id.show_animal);

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

//        if (pet.type.toLowerCase().contains("貓")) {
//            showAnimal.setImageResource(R.drawable.catt);
//        }
//        else if (pet.type.toLowerCase().contains("狗")) {
//            showAnimal.setImageResource(R.drawable.cute_dog);
//        }
//        else if (pet.type.toLowerCase().contains("兔")) {
//            showAnimal.setImageResource(R.drawable.cute_rab);
//        }
//        else if (pet.type.toLowerCase().contains("龜")) {
//            showAnimal.setImageResource(R.drawable.cute_tort);
//        }
//        else if (pet.type.toLowerCase().contains("蛇")) {
//            showAnimal.setImageResource(R.drawable.cartoon_snake);
//        }
//        else if (pet.name.toLowerCase().contains("靚靚")) {
//            showAnimal.setImageResource(R.drawable.p103);
//        }
        if (pet.name.contains("靚靚")) {
            showAnimal.setImageResource(R.drawable.p103);
        }
        else if (pet.name.contains("金金")) {
            showAnimal.setImageResource(R.drawable.p102);
        }
        else if (pet.name.contains("Ziti")) {
            showAnimal.setImageResource(R.drawable.p101);
        }
        else if (pet.name.contains("Kayla")) {
            showAnimal.setImageResource(R.drawable.p100);
        }
        else if (pet.name.contains("蓮子")) {
            showAnimal.setImageResource(R.drawable.p99);
        }
        else if (pet.name.contains("Kei")) {
            showAnimal.setImageResource(R.drawable.p98);
        }
        else if (pet.name.contains("阿中")) {
            showAnimal.setImageResource(R.drawable.p97);
        }
        else if (pet.name.contains("妮妮")) {
            showAnimal.setImageResource(R.drawable.p96);
        }
        else if (pet.name.contains("莉莉")) {
            showAnimal.setImageResource(R.drawable.p95);
        }
        else if (pet.name.contains("Y6")) {
            showAnimal.setImageResource(R.drawable.p94);
        }

        String sourceURL = pet.source;
        String contactNumber = pet.contactNumber;

        donate.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sourceURL));
            startActivity(browserIntent);
        });
        phone.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactNumber));
            startActivity(intent);
        });
        chain.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sourceURL));
            startActivity(browserIntent);
        });
        back.setOnClickListener(view -> onBackPressed());


    }
}