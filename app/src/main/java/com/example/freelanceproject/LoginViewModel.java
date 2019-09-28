package com.example.freelanceproject;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.freelanceproject.BusinessModel.User;
import com.example.freelanceproject.Repository.Repository;
import com.example.freelanceproject.Util.AuthenticationState;
import com.example.freelanceproject.Util.SaveSharedPreference;

public class LoginViewModel extends AndroidViewModel {

    final MutableLiveData<AuthenticationState> authenticateState = new MediatorLiveData<>();

    private Repository repo;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        authenticateState.setValue(AuthenticationState.UNAUTHENTICATED);
        repo = Repository.getInstance();
    }


    public void authenticate(String username, String password){
        if (passwordIsValidForUsername(username, password)){
            authenticateState.setValue(AuthenticationState.AUTHENTICATED);
        }else {
            authenticateState.setValue(AuthenticationState.INVALID_AUTHENTICATION);
        }
    }

    private boolean passwordIsValidForUsername(String username, String password){
        if (repo.validateAccount(username,password) == User.CLIENT_ACCOUNT){
            SaveSharedPreference.setAccType(getApplication().getApplicationContext(), User.CLIENT_ACCOUNT);
            return true;
        }else if (repo.validateAccount(username, password) == User.FREELANCER_ACCOUNT){
            SaveSharedPreference.setAccType(getApplication().getApplicationContext(), User.FREELANCER_ACCOUNT);
            return true;
        }else{
            return false;
        }
    }

    public void refuseAuthentication(){
        authenticateState.setValue(AuthenticationState.UNAUTHENTICATED);
    }

    public boolean logOut(Context context){
        if (SaveSharedPreference.getLoginStatus(context)){
            SaveSharedPreference.setLoginStatus(context, false);
            SaveSharedPreference.removeUsername(context);
            authenticateState.setValue(AuthenticationState.UNAUTHENTICATED);
            return true;
        }else{
            return false;
        }
    }

}
