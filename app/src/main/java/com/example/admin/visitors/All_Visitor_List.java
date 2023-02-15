package com.example.admin.visitors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.controller.Controller;
import com.example.admin.visitors.adapter.AllVisitorList_Adapter;
import com.example.admin.visitors.model.AllVisitorListModel;

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

                if(data!=null){
    //                recyclerView.setVisibility(View.VISIBLE);
//                    imageView.setVisibility(View.GONE);
                    AllVisitorList_Adapter myAdaptar = new AllVisitorList_Adapter(data);
                    recyclerView.setAdapter(myAdaptar);
                }
                else {
      //              recyclerView.setVisibility(View.GONE);
  //                  imageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<AllVisitorListModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}