package com.example.freelanceproject.BusinessModel;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

    private String mUserId;

    private ArrayList<Project> mProjects;

    public Portfolio(String userId) {
        mUserId = userId;
        mProjects = new ArrayList<>();
    }

    public void addProject(Project project){
        mProjects.add(project);
    }

    // TODO: 9/5/2019 change implementation to check for a specific project in the list and delete it
    public Boolean removerProject(String projectName){
        boolean check = false;
        for (Project p :
                mProjects) {
            if (p.getName().equals(projectName)){
                check = mProjects.remove(p);
            }
        }
        return check;
    }

    public ArrayList<Project> getProjects() {
        return mProjects;
    }

    public Project getProject(String projectName){
        for (Project project :
                mProjects) {
            if (project.getName().equals(projectName)){
                return project;
            }
        }
        return new Project(projectName);
    }

    public String getPortfolioOwner() {
        return mUserId;
    }
}
