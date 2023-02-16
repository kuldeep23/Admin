package com.example.admin.visitors.adapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import com.example.admin.visitors.model.AllVisitorListModel;
import com.example.admin.R;

import java.util.List;

public class AllVisitorList_Adapter extends RecyclerView.Adapter<AllVisitorList_Adapter.myViewHolder> {
    List<AllVisitorListModel> data;


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
                Toast.makeText(view.getContext(), temp.getVisitor_mobile(), Toast.LENGTH_SHORT).show();
                String phone_number = temp.getVisitor_mobile();

                // Getting instance of Intent with action as ACTION_CALL
                Intent phone_intent = new Intent(Intent.ACTION_CALL);

                // Set data of Intent through Uri by parsing phone number
                phone_intent.setData(Uri.parse("tel:" + phone_number));
                if (ActivityCompat.checkSelfPermission(view.getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        view.getContext().startActivity(phone_intent);
                    return;
                }

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
        private Button call;

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

            cardView = itemView.findViewById(R.id.eachCardView);
        }
    }
}
