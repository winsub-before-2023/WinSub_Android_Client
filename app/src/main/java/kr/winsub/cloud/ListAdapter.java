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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Lists> ListArrayList;

    private Context mContext;

    ListAdapter(ArrayList<Lists> ListArrayList, Context context) {
        this.ListArrayList = ListArrayList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

        final Lists data = ListArrayList.get(i);

        myViewHolder.name.setText(ListArrayList.get(i).name);
        myViewHolder.size.setText(ListArrayList.get(i).size);

        if (data.getUrl().isEmpty())
            myViewHolder.icon.setImageResource(android.R.color.transparent);
        else if (!data.getIsFIle())
            myViewHolder.icon.setImageResource(R.drawable.ic_folder);
        else if (data.getIsFIle())
            myViewHolder.icon.setImageResource(R.drawable.ic_file);


        myViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.getUrl().isEmpty())
                    Toast.makeText(view.getContext(), "Empty", Toast.LENGTH_SHORT).show();
                else if (!data.getIsFIle())
                    ((MainActivity) mContext).reload(data.getUrl(), true);
                else if (data.getIsFIle())
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(data.getUrl())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView name;
        TextView size;
        ImageView icon;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.item_name);
            size = view.findViewById(R.id.item_size);
            icon = view.findViewById(R.id.item_icon);
            mCardView = view.findViewById(R.id.card_view);
        }
    }
}
