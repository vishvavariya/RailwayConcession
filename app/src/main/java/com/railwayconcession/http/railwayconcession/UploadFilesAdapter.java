package com.railwayconcession.http.railwayconcession;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class UploadFilesAdapter extends RecyclerView.Adapter<UploadFilesAdapter.ViewHolder> {

    public List<String> fileNameList;
    public List<String> fileDoneList;

    public UploadFilesAdapter(List<String> fileNameList, List<String> fileDoneList){
        this.fileNameList = fileNameList;
        this.fileDoneList = fileDoneList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.upload_notices_list,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String fileName = fileNameList.get(position);
        viewHolder.tvUploadFileName.setText(fileName);

        String fileDone = fileDoneList.get(position);

        if (fileDone.equals("uploading")){
            viewHolder.ivUploadFileDone.setImageResource(R.drawable.progress);
        }else {
            viewHolder.ivUploadFileDone.setImageResource(R.drawable.checked);
        }

    }

    @Override
    public int getItemCount() {
        return fileNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public TextView tvUploadFileName;
        public ImageView ivUploadFileDone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            tvUploadFileName = (TextView) mView.findViewById(R.id.tvUploadFileName);
            ivUploadFileDone = (ImageView) mView.findViewById(R.id.ivUploadFileDone);


        }
    }
}

