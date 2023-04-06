package my.securegates.admin;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.securegates.admin.R;

import my.securegates.admin.visitors.All_Visitor_List;
import my.securegates.admin.visitors.MainActivity;

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