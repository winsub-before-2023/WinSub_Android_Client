package kr.winsub.cloud;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Profile> ProfileArrayList;

    ProfileAdapter(ArrayList<Profile> ProfileArrayList) {
        this.ProfileArrayList = ProfileArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile, viewGroup, false);

        return new ProfileAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final ProfileAdapter.MyViewHolder myViewHolder = (ProfileAdapter.MyViewHolder) viewHolder;

        final Profile data = ProfileArrayList.get(i);

        myViewHolder.name.setText(ProfileArrayList.get(i).name);
        myViewHolder.desc.setText(ProfileArrayList.get(i).desc);

        myViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(data.getHomepage())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return ProfileArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView name;
        TextView desc;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.item_dev_name);
            desc = view.findViewById(R.id.item_dev_desc);
            mCardView = view.findViewById(R.id.card_view);
        }
    }
}
