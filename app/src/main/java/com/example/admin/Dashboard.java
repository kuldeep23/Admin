package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.admin.visitors.MainActivity;

public class Dashboard extends AppCompatActivity {

    CardView visitorin, carddomestic, cardbills, cardcommunication, cardcomplaints, cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        visitorin = findViewById(R.id.visitorin);


        visitorin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}