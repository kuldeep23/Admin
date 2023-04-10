package com.securegates.admin.visitors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.securegates.admin.R;
import com.securegates.admin.controller.Controller;
import com.securegates.admin.visitors.adapter.AllVisitorList_Adapter;
import com.securegates.admin.visitors.model.AllVisitorListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class All_Visitor_List extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_visitor_list);

        recyclerView = findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        processdata();
    }

    private void processdata() {

        Call<List<AllVisitorListModel>> call = Controller
                .getInstance()
                .getapi()
                .getVisitorList();
        call.enqueue(new Callback<List<AllVisitorListModel>>() {
            @Override
            public void onResponse(Call<List<AllVisitorListModel>> call, Response<List<AllVisitorListModel>> response) {
                    List<AllVisitorListModel> data = response.body();
                    AllVisitorList_Adapter myAdaptar = new AllVisitorList_Adapter(data);
                    recyclerView.setAdapter(myAdaptar);
            }
            @Override
            public void onFailure(Call<List<AllVisitorListModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}