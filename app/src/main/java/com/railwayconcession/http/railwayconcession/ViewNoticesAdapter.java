package com.railwayconcession.http.railwayconcession;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

public class ViewNoticesAdapter extends RecyclerView.Adapter<ViewNoticesAdapter.ViewHolder> {
    RecyclerView rvViewNotes;
    Context context;
    ArrayList<String> items = new ArrayList<>();
    ArrayList<String> urls = new ArrayList<>();

    public void update(String name, String url){
        items.add(name);
        urls.add(url);
        notifyDataSetChanged();

    }

    public ViewNoticesAdapter(RecyclerView rvViewNotes, Context context, ArrayList<String> items,  ArrayList<String> urls) {
        this.rvViewNotes = rvViewNotes;
        this.context = context;
        this.items = items;
        this.urls = urls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_notices_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.tvViewFileName.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvViewFileName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvViewFileName = itemView.findViewById(R.id.tvViewFileName);
            itemView.setOnClickListener(new View.OnClickListener() {  //what will happen when you click a single item
                @Override
                public void onClick(View view) {
                    int position = rvViewNotes.getChildLayoutPosition(view);
                    Intent intent = new Intent();
                    intent.setType(Intent.ACTION_VIEW); // says we are going to view something
                    intent.setData(Uri.parse(urls.get(position)));
                    context.startActivity(Intent.createChooser(intent,"Complete action using"));
                }
            });
        }
    }
}