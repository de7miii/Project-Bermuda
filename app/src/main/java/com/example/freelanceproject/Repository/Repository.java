package com.example.freelanceproject.Repository;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.freelanceproject.BusinessModel.Client;
import com.example.freelanceproject.BusinessModel.Freelancer;
import com.example.freelanceproject.BusinessModel.Project;
import com.example.freelanceproject.BusinessModel.User;
import com.example.freelanceproject.Util.SaveSharedPreference;

import java.util.ArrayList;

public class Repository {

    private static volatile Repository mRepository;

    private Client client1, client2, client3, client4, client5;
    private Freelancer freelancer1, freelancer2, freelancer3, freelancer4, freelancer5;
    ArrayList<Client> clients = new ArrayList<>();
    ArrayList<Freelancer> freelancers = new ArrayList<>();

    private final MutableLiveData<ArrayList<Client>> clientLiveData = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Freelancer>> freelancerLiveData = new MutableLiveData<>();

    // TODO: 9/10/2019 Implement the repository using livedata to update the users list

    private Repository(){
        if (mRepository != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        initilizeDataSet();
    }

    public static Repository getInstance(){
        if (mRepository == null){
            mRepository = new Repository();
        }
        return mRepository;
    }

    private void initilizeDataSet(){
        // TODO: 9/5/2019 create multiple profiles to be used for the app showcase (DONE)

        client1 = new Client("client1", "client1@example.com","12345678");
        client2 = new Client("client2", "client2@example.com", "12345678");
        client3 = new Client("client3", "client3@example.com", "12345678");
        client4 = new Client("client4", "client4@example.com", "12345678");
        client5 = new Client("client5", "client5@example.com", "12345678");

        client1.createProject("First Project", "Testing Project For Ui Purposes", Project.MEDIUM_PROJECT, Project.ONGOING);
        client1.getProject("First Project").setBudget(1500);

        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);
        clients.add(client5);
        clientLiveData.setValue(clients);

        freelancer1 = new Freelancer("freelancer1", "freelancer1@example.com", "12345678");
        freelancer2 = new Freelancer("freelancer2", "freelancer2@example.com", "12345678");
        freelancer3 = new Freelancer("freelancer3", "freelancer3@example.com", "12345678");
        freelancer4 = new Freelancer("freelancer4", "freelancer4@example.com", "12345678");
        freelancer5 = new Freelancer("freelancer5", "freelancer5@example.com", "12345678");

        freelancer1.createProject("First Project", "Testing Project For Ui Purposes", Project.MEDIUM_PROJECT, Project.ONGOING);
        freelancer1.getProject("First Project").setBudget(1500);
        freelancer1.createProject("First Project", "Testing Project For Ui Purposes", Project.MEDIUM_PROJECT, Project.ONGOING);
        freelancer1.getProject("First Project").setBudget(1500);
        freelancer1.createProject("First Project", "Testing Project For Ui Purposes", Project.MEDIUM_PROJECT, Project.ONGOING);
        freelancer1.getProject("First Project").setBudget(1500);
        freelancer1.createProject("First Project", "Testing Project For Ui Purposes", Project.MEDIUM_PROJECT, Project.ONGOING);
        freelancer1.getProject("First Project").setBudget(1500);
        freelancer1.createProject("First Project", "Testing Project For Ui Purposes", Project.MEDIUM_PROJECT, Project.ONGOING);
        freelancer1.getProject("First Project").setBudget(1500);
        freelancer1.createProject("First Project", "Testing Project For Ui Purposes", Project.MEDIUM_PROJECT, Project.ONGOING);
        freelancer1.getProject("First Project").setBudget(1500);
        freelancer1.createProject("First Project", "Testing Project For Ui Purposes", Project.MEDIUM_PROJECT, Project.ONGOING);
        freelancer1.getProject("First Project").setBudget(1500);
        freelancer1.createProject("First Project", "Testing Project For Ui Purposes", Project.MEDIUM_PROJECT, Project.ONGOING);
        freelancer1.getProject("First Project").setBudget(1500);

        freelancer1.setMinRate(25);
        freelancer1.setMaxRate(50);
        freelancer1.setUserAge(31);
        freelancer1.setUserDescription("Freelancer Number One description for Ui purpose");
        freelancer1.addSkill("good with computers");
        freelancer1.addCertificate("MS Excel");

        freelancers.add(freelancer1);
        freelancers.add(freelancer2);
        freelancers.add(freelancer3);
        freelancers.add(freelancer4);
        freelancers.add(freelancer5);
        freelancerLiveData.setValue(freelancers);
    }

    public ArrayList<Client> getClientsList(){
        return clients;
    }

    public ArrayList<Freelancer> getFreelancersList(){
        return freelancers;
    }

    public LiveData<ArrayList<Client>> getClientLiveData(){return clientLiveData;}
    public LiveData<ArrayList<Freelancer>> getFreelancerLiveData(){return freelancerLiveData;}

    // TODO: 9/9/2019 the return value should be true if the account is added to the database successfully
    public boolean createNewClient(String username, String email, String password){
        clientLiveData.setValue(clients);
        return clients.add(new Client(username, email, password));
    }

    // TODO: 9/9/2019 the return value should be true if the account is added to the database successfully
    public boolean createNewFreelancer(String username, String email, String password){
        freelancerLiveData.setValue(freelancers);
        return freelancers.add(new Freelancer(username, email, password));
    }

    public int validateAccount(String username, String password){
        for (User user:
             getFreelancersList()) {
            if (user.getUserName().equals(username)){
                if (user.getPassword().equals(password)){
                    return User.FREELANCER_ACCOUNT;
                }
            }
        }
        for (User user:
                getClientsList()) {
            if (user.getUserName().equals(username)){
                if (user.getPassword().equals(password)){
                    return User.CLIENT_ACCOUNT;
                }
            }
        }
        return -1;
    }
}
