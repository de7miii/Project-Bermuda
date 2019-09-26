package com.example.freelanceproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freelanceproject.BusinessModel.Freelancer;
import com.example.freelanceproject.BusinessModel.Project;
import com.example.freelanceproject.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Project> mProjectsList;

    private Context mContext;

    public ProjectAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_view_profile, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Project currentProject = mProjectsList.get(position);
        if (holder instanceof  ProjectViewHolder){
            ((ProjectViewHolder) holder).bind(currentProject);
        }
    }

    @Override
    public int getItemCount() {
        return mProjectsList != null? mProjectsList.size() : 0;
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder{
        private MaterialTextView nameTxtView, descriptionView, statusView, durationView;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxtView = itemView.findViewById(R.id.project_name_text);
            descriptionView = itemView.findViewById(R.id.description_text_view);
            statusView = itemView.findViewById(R.id.status_text);
            durationView = itemView.findViewById(R.id.duration_text);
        }

        public void bind(Project project){
            nameTxtView.setText(project.getName());
            descriptionView.setText(project.getDescription());
            switch (project.getStatus()){
                case Project.FINISHED:
                    statusView.setText("Finished Project");
                    break;
                case Project.ONGOING:
                    statusView.setText("Ongoing Project");
                    break;
                case Project.POSTPONED:
                    statusView.setText("Postponed Project");
                    break;
            }
            switch (project.getDuration()){
                case Project.LONG_PROJECT:
                    durationView.setText("Long Project");
                    break;
                case Project.MEDIUM_PROJECT:
                    durationView.setText("Medium Length Project");
                    break;
                case Project.SHORT_PROJECT:
                    durationView.setText("Short Project");
            }
        }
    }

    public void setProjectsList(ArrayList<Project> newProjectList){
        mProjectsList = newProjectList;
        notifyDataSetChanged();
    }
}
