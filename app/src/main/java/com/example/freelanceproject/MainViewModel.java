package com.example.freelanceproject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.freelanceproject.BusinessModel.Client;
import com.example.freelanceproject.BusinessModel.Freelancer;
import com.example.freelanceproject.Repository.Repository;

import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel {

    // TODO: 9/5/2019 implement the viewmodel that'll provide the ui with the dummy data

    private Repository repo;
    private ArrayList<Client> clientsList;
    private ArrayList<Freelancer> freelancersList;

    private LiveData<ArrayList<Client>> clientLiveData;
    private LiveData<ArrayList<Freelancer>> freelancerLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repo = Repository.getInstance();
        clientsList = repo.getClientsList();
        freelancersList = repo.getFreelancersList();
        clientLiveData = repo.getClientLiveData();
        freelancerLiveData = repo.getFreelancerLiveData();
    }

    public ArrayList<Client> getClientsList() {
        return clientsList;
    }

    public ArrayList<Freelancer> getFreelancersList() {
        return freelancersList;
    }

    public Object getUser(String username){
        Object obj = null;
        for (Client c:
             clientsList) {
            if (c.getUserName().equals(username)){
                obj = c;
            }
        }

        for (Freelancer f :
                freelancersList) {
            if (f.getUserName().equals(username)){
                obj = f;
            }
        }
        return obj;
    }

    public LiveData<ArrayList<Client>> getClientLiveData(){return clientLiveData;}
    public LiveData<ArrayList<Freelancer>> getFreelancerLiveData(){return freelancerLiveData;}
}
