package com.example.freelanceproject.BusinessModel;

import java.util.ArrayList;

public class Project {

    public static final int SHORT_PROJECT = -1;
    public static final int LONG_PROJECT = -2;
    public static final int MEDIUM_PROJECT = -3;


    public static final int ONGOING = -100;
    public static final int POSTPONED = -200;
    public static final int FINISHED = -300;


    private int mProjectId;
    private String mName;
    private String mDescription;
    private int mDuration;
    private int mStatus;

    // TODO: 9/5/2019 implement a way to store a project additional files

    private int mBudget;

    // TODO: 9/5/2019 implement a way to store a project specific tasks

    private int mRate;

    private ArrayList<Integer> mEmployees;

    public Project(String name, String description, int duration, int budget) {
        mName = name;
        mDescription = description;
        mDuration = duration;
        mBudget = budget;
        mEmployees = new ArrayList<>();
    }

    public Project(String name){
        mName = name;
        mEmployees = new ArrayList<>();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public int getBudget() {
        return mBudget;
    }

    public void setBudget(int budget) {
        mBudget = budget;
    }

    public int getRate() {
        return mRate;
    }

    public void setRate(int rate) {
        mRate = rate;
    }

    public ArrayList<Integer> getEmployees() {
        return mEmployees;
    }

    public void addEmployee(int employeeId) {
        if (mEmployees != null) {
            if (!mEmployees.contains(employeeId)) {
                mEmployees.add(employeeId);
            }
        }
    }
}
