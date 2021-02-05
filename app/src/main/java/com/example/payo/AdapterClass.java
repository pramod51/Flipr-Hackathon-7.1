package com.example.payo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.AdopterViewHolder> {
    public ArrayList<AdapterItem> dataArrayList;
    private Context activity;
public AdapterClass(Context activity,ArrayList<AdapterItem> dataArrayList){
    this.activity=activity;
    this.dataArrayList=dataArrayList;
}

    @NonNull
    @Override
    public AdapterClass.AdopterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(activity)
            .inflate(R.layout.adopter_item,parent,false);
    return new AdopterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterClass.AdopterViewHolder holder, int position) {
        AdapterItem item =dataArrayList.get(position);
        holder.fName.setText(item.getfName());
        holder.lName.setText(item.getlName());
        holder.email.setText(item.getEmail());
        Picasso.get().load(item.getProfileUrl()).into(holder.imageView);
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.v.setBackgroundColor(color);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openDilog();
                return false;
            }

            private void openDilog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure to delete?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dataArrayList.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public static class AdopterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView fName,lName,email;
        View v;
        public AdopterViewHolder(@NonNull View itemView) {
            super(itemView);
            fName=itemView.findViewById(R.id.first_name);
            lName=itemView.findViewById(R.id.last_name);
            imageView=itemView.findViewById(R.id.profile_image);
            email=itemView.findViewById(R.id.email);
            v=itemView.findViewById(R.id.view_line);

        }
    }

}
