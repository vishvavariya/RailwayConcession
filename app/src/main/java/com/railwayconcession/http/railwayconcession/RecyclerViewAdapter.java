package com.railwayconcession.http.railwayconcession;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

FirebaseDatabase mDatabase;
    Context context;
    List<Students> MainImageUploadInfoList;

    public RecyclerViewAdapter(Context context, List<Students> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Students studentDetails = MainImageUploadInfoList.get(position);
        holder.StudentNameTextView.setText(studentDetails.getName());
        holder.StudentNumberTextView.setText(studentDetails.getMobile());
        holder.StudentEmailTextView.setText(studentDetails.getEmail());
        holder.StudentSAPIDTextView.setText(studentDetails.getSAPID());
        holder.StudentDOBTextView.setText(studentDetails.getDOB());
        holder.StudentGenderTextView.setText(studentDetails.getGender());
        holder.StudentFromTextView.setText(studentDetails.getFrom());
        holder.StudentToTextView.setText(studentDetails.getTo());
        holder.StudentPeriodTextView.setText(studentDetails.getPeriod());
        holder.StudentCoachTextView.setText(studentDetails.getCoach());
        holder.StudentSuburbTextView.setText(studentDetails.getSuburb());
        holder.StudentUIDTextView.setText(studentDetails.getUID());

    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView StudentNameTextView;
        public TextView StudentNumberTextView;
        public TextView StudentEmailTextView;
        public TextView StudentSAPIDTextView;
        public TextView StudentDOBTextView;
        public TextView StudentGenderTextView;
        public TextView StudentFromTextView;
        public TextView StudentToTextView;
        public TextView StudentSuburbTextView;
        public TextView StudentCoachTextView;
        public TextView StudentPeriodTextView;
        public TextView StudentUIDTextView;

        public ViewHolder(View itemView) {

            super(itemView);

            StudentNameTextView = (TextView) itemView.findViewById(R.id.ShowStudentNameTextView);
            StudentNumberTextView = (TextView) itemView.findViewById(R.id.ShowStudentNumberTextView);
            StudentEmailTextView = (TextView) itemView.findViewById(R.id.ShowStudentEmailTextView);
            StudentSAPIDTextView = (TextView) itemView.findViewById(R.id.ShowStudentSAPIDTextView);
            StudentDOBTextView = (TextView) itemView.findViewById(R.id.ShowStudentDOBTextView);
            StudentGenderTextView = (TextView) itemView.findViewById(R.id.ShowStudentGenderTextView);
            StudentFromTextView = (TextView) itemView.findViewById(R.id.ShowStudentFromTextView);
            StudentToTextView = (TextView) itemView.findViewById(R.id.ShowStudentToTextView);
            StudentSuburbTextView = (TextView) itemView.findViewById(R.id.ShowStudentSuburbTextView);
            StudentCoachTextView = (TextView) itemView.findViewById(R.id.ShowStudentCoachTextView);
            StudentPeriodTextView = (TextView) itemView.findViewById(R.id.ShowStudentPeriodTextView);
          StudentUIDTextView = (TextView) itemView.findViewById(R.id.ShowStudentUIDTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String StudentNameText = StudentNameTextView.getText().toString();
                String StudentEmailText = StudentEmailTextView.getText().toString();
                String StudentNumberText= StudentNumberTextView.getText().toString();
                String StudentSAPIDText= StudentSAPIDTextView.getText().toString();
                String StudentGenderText= StudentGenderTextView.getText().toString();
                String StudentDOBText= StudentDOBTextView.getText().toString();
                String StudentFromText= StudentFromTextView.getText().toString();
                String StudentToText= StudentToTextView.getText().toString();
                String StudentSuburbText= StudentSuburbTextView.getText().toString();
                String StudentCoachText= StudentCoachTextView.getText().toString();
                String StudentPeriodText= StudentPeriodTextView.getText().toString();
                String StudentUIDText= StudentUIDTextView.getText().toString();

                Intent intent = new Intent(v.getContext(), StudentDetails.class);
                intent.putExtra("email",StudentEmailText);
                intent.putExtra("name", StudentNameText);
                intent.putExtra("mobile",StudentNumberText);
                intent.putExtra("sapid",StudentSAPIDText);
                intent.putExtra("dob",StudentDOBText);
                intent.putExtra("gender",StudentGenderText);
                intent.putExtra("from",StudentFromText);
                intent.putExtra("to",StudentToText);
                intent.putExtra("period",StudentPeriodText);
                intent.putExtra("Coach",StudentCoachText);
                intent.putExtra("suburb",StudentSuburbText);
                intent.putExtra("uid",StudentUIDText);
                v.getContext().startActivity(intent);

            }


}
      );}}}