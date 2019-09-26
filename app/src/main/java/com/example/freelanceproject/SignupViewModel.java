package com.example.freelanceproject;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.freelanceproject.BusinessModel.User;
import com.example.freelanceproject.Repository.Repository;
import com.example.freelanceproject.Util.RegistrationState;

public class SignupViewModel extends AndroidViewModel {

    private Repository repo;
    final MutableLiveData<RegistrationState> registrationState =
            new MutableLiveData<>();

    public SignupViewModel(@NonNull Application application) {
        super(application);
        repo = Repository.getInstance();
        registrationState.setValue(RegistrationState.COLLECT_USER_DATA);
    }

    public void signUpNewAccount(String username, String email, String password, int accType){
        if (accType == User.CLIENT_ACCOUNT){
            repo.createNewClient(username, email, password);
            registrationState.setValue(RegistrationState.REGISTRATION_COMPLETED);
        }else if (accType == User.FREELANCER_ACCOUNT){
            repo.createNewFreelancer(username, email, password);
            registrationState.setValue(RegistrationState.REGISTRATION_COMPLETED);
        }
        Log.i("SignupViewModel", "signUpNewAccount: " + repo.getClientsList().size() + "\t" + repo.getFreelancersList().size());
    }

    public boolean userCanclledRegistration(){
        registrationState.setValue(RegistrationState.COLLECT_USER_DATA);
        return true;
    }
}
