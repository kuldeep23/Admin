package com.example.admin.visitors.adapter;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.admin.Dashboard;
import com.example.admin.controller.Controller;
import com.example.admin.visitors.All_Visitor_List;
import com.example.admin.visitors.MainActivity;
import com.example.admin.visitors.model.AllVisitorListModel;
import com.example.admin.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllVisitorList_Adapter extends RecyclerView.Adapter<AllVisitorList_Adapter.myViewHolder> {
    List<AllVisitorListModel> data;

    TextToSpeech textToSpeech;
    public AllVisitorList_Adapter(List<AllVisitorListModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_visitor_singlerow_design, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        final AllVisitorListModel temp = data.get(position);
        holder.name.setText(data.get(position).getVisitor_name());
        holder.type.setText(data.get(position).getVisitor_type());
        holder.status.setText(data.get(position).getVisitor_status());
        holder.approve_by.setText(data.get(position).getVisitor_approve_by());
        holder.enter_time.setText(data.get(position).getVisitor_enter_time());
        holder.enter_date.setText(data.get(position).getVisitor_enter_date());
        Glide.with(holder.name.getContext()).load(data.get(position).getVisitor_image()).into(holder.img);

        //Animation
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_four));

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone_number = temp.getVisitor_mobile();
                Intent phone_intent = new Intent(Intent.ACTION_DIAL);
                phone_intent.setData(Uri.parse("tel:" + phone_number));
                if (ActivityCompat.checkSelfPermission(view.getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        view.getContext().startActivity(phone_intent);
                    return;
                }
            }
        });

        holder.visitorout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech = new TextToSpeech(view.getContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if(i != TextToSpeech.ERROR)
                            textToSpeech.setLanguage(Locale.US);
                        textToSpeech.speak("Do you want to exit this visitor? Then press enter yes otherwise press cancel", TextToSpeech.QUEUE_FLUSH, null);
                    }
                });

                new MaterialAlertDialogBuilder(view.getContext(), R.style.AlertDialogTheme)
                        .setTitle("Visitor Out")
                        .setMessage("Do you want exit this visitor??")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Call<AllVisitorListModel> call = Controller
                                        .getInstance()
                                        .getapi()
                                        .visitorOut(temp.getVisitor_id());

                                call.enqueue(new Callback<AllVisitorListModel>() {
                                    @Override
                                    public void onResponse(Call<AllVisitorListModel> call, Response<AllVisitorListModel> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body() != null) {
                                                textToSpeech = new TextToSpeech(view.getContext(), new TextToSpeech.OnInitListener() {
                                                    @Override
                                                    public void onInit(int i) {
                                                        if(i != TextToSpeech.ERROR)
                                                            textToSpeech.setLanguage(Locale.US);
                                                        textToSpeech.speak("Visitor Exit Successfully. Do you want to exit more visitor? Then press enter exit more otherwise press no", TextToSpeech.QUEUE_FLUSH, null);
                                                    }
                                                });
                                                new MaterialAlertDialogBuilder(view.getContext(), R.style.AlertDialogTheme)
                                                        .setTitle("Visitor Out")
                                                        .setMessage("Visitor Out Successfully from the society!!!")
                                                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                Intent goToNextActivity = new Intent(view.getContext(), Dashboard.class);
                                                                view.getContext().startActivity(goToNextActivity);

                                                            }
                                                        })
                                                        .setNeutralButton("Exit More", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                Intent goToNextActivity = new Intent(view.getContext(), All_Visitor_List.class);
                                                                view.getContext().startActivity(goToNextActivity);
                                                            }
                                                        })
                                                        .show();
                                            } else {
                                                Toast.makeText(view.getContext(), "Visitor Out Un-Successfull", Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            Toast.makeText(view.getContext(), "Response not successful "+response.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<AllVisitorListModel> call, Throwable t) {
                                        Toast.makeText(view.getContext(), "Error occurred!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent goToNextActivity = new Intent(view.getContext(), All_Visitor_List.class);
                                view.getContext().startActivity(goToNextActivity);
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView name,type,status,approve_by,enter_time,enter_date;
        private CardView cardView;
        private Button call,visitorout;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.vistor_image);
            name = itemView.findViewById(R.id.vistor_name);
            type = itemView.findViewById(R.id.visitor_type);
            status = itemView.findViewById(R.id.vistor_status);
            approve_by = itemView.findViewById(R.id.vistor_approved_by);
            enter_time = itemView.findViewById(R.id.vistor_enter_time);
            enter_date = itemView.findViewById(R.id.vistor_enter_date);
            call = itemView.findViewById(R.id.visitor_call);
            visitorout = itemView.findViewById(R.id.visitor_out);
            cardView = itemView.findViewById(R.id.eachCardView);
        }
    }
}
