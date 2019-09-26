package com.example.freelanceproject;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.freelanceproject.Adapters.PostsAdapter;
import com.example.freelanceproject.BusinessModel.User;
import com.example.freelanceproject.Util.RegistrationState;
import com.example.freelanceproject.Util.SaveSharedPreference;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.security.Provider;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;

    private MaterialButton signupButton;
    private MaterialButton loginButton;
    private TextInputLayout usernameInput;
    private TextInputLayout emailInput;
    private TextInputLayout passwordInput;
    private RadioButton clientRadio;
    private RadioButton freelancerRadio;

    private String username;
    private String email;
    private String password;

    private int accType = -1;

    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        signupViewModel = ViewModelProviders
                .of(requireActivity())
                .get(SignupViewModel.class);
        loginViewModel = ViewModelProviders
                .of(requireActivity())
                .get(LoginViewModel.class);

        loginButton = view.findViewById(R.id.have_account_button);
        signupButton = view.findViewById(R.id.signup_button);
        usernameInput = view.findViewById(R.id.username_text_input);
        emailInput = view.findViewById(R.id.email_text_input);
        passwordInput = view.findViewById(R.id.password_text_input);
        clientRadio = view.findViewById(R.id.client_radio);
        freelancerRadio = view.findViewById(R.id.freelancer_radio);


//        loginButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.have_account_action, null));
//        signupButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.new_account_action, null));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.have_account_action, null));

        final NavController navController = Navigation.findNavController(view);

        clientRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clientRadio.isChecked())
                accType = User.CLIENT_ACCOUNT;
            }
        });
        freelancerRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (freelancerRadio.isChecked())
                accType = User.FREELANCER_ACCOUNT;
            }
        });

        // TODO: 9/9/2019 fix the signup issue (DONE)
        // TODO: 9/10/2019 Handle the cases of no text or wrong text (DONE)
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameInput.getEditText().getText().toString();
                email = emailInput.getEditText().getText().toString();
                password = passwordInput.getEditText().getText().toString();

                if (accType == -1){
                    Toast.makeText(getContext(), "SelectAccountType", Toast.LENGTH_LONG).show();
                }else if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailInput.setError("Enter a valid Email");
                }else if (password.isEmpty() || password.length() + 1 < 8){
                    passwordInput.setError("Password must be at least 8 characters");
                }else if (username.isEmpty()){
                    usernameInput.setError("Username can't be empty");
                }else {
                    signupViewModel.signUpNewAccount(username, email, password, accType);
                    SaveSharedPreference.setUsername(getContext(), username);
                }
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                signupViewModel.userCanclledRegistration();
                navController.popBackStack(R.id.login_dest,false);
            }
        });

        signupViewModel.registrationState.observe(getViewLifecycleOwner(), new Observer<RegistrationState>() {
            @Override
            public void onChanged(RegistrationState registrationState) {
                if (registrationState == RegistrationState.REGISTRATION_COMPLETED){
                    loginViewModel.authenticate(username, password);
                    navController.navigate(R.id.login_to_home_action);
                }
            }
        });
    }
}
