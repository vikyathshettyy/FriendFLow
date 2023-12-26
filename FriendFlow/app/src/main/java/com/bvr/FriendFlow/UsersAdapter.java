package com.bvr.FriendFlow;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<ProfileData> usersArrayList;


    public UsersAdapter(Context context, ArrayList<ProfileData> usersArrayList) {
        this.context = context;
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, int position) {

        ProfileData model = usersArrayList.get(position);
        holder.nameTV.setText(model.getFName()+" "+model.getLName());
        holder.ageTV.setText(" Age :  "+model.getAge());
        holder.genderTV.setText(" Gender :  "+model.getGender());
        holder.dpIV.setImageResource(R.drawable.dp);
        holder.view.setOnClickListener(v->{
            Intent i = new Intent(context,ExploreProfileActivity.class);
            i.putExtra("Email",model.getEmail());
            i.putExtra("FName",model.getFName());
            i.putExtra("LName",model.getLName());
            i.putExtra("Bio",model.getBio());
            i.putExtra("Phone",model.getPhNumber());
            i.putExtra("Gender",model.getGender());
            i.putExtra("Age",model.getAge());
            i.putExtra("Address",model.getAddress());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {

        return usersArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements com.bvr.FriendFlow.ViewHolder {
        private final ImageView dpIV;
        private final TextView nameTV;
        private final TextView genderTV;
        private final TextView ageTV;
        public View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dpIV = itemView.findViewById(R.id.dp);
            nameTV = itemView.findViewById(R.id.name);
            genderTV = itemView.findViewById(R.id.gender);
            ageTV = itemView.findViewById(R.id.age);
            this.view = itemView;

        }
    }




}
