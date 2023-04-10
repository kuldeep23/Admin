package com.securegates.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.securegates.admin.visitors.All_Visitor_List;
import com.securegates.admin.visitors.MainActivity;

public class Dashboard extends AppCompatActivity {

    CardView visitorin, vistiorout, cardbills, cardcommunication, cardcomplaints, cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        visitorin = findViewById(R.id.visitorin);
        vistiorout = findViewById(R.id.visitorout);

        visitorin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        vistiorout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), All_Visitor_List.class));
            }
        });
    }
}