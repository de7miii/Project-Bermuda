package com.example.freelanceproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.freelanceproject.Util.AuthenticationState;
import com.example.freelanceproject.Util.SaveSharedPreference;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment {
  private MaterialButton loginButton;
  
  LoginViewModel loginViewModel;

  private TextInputLayout passwordInput;
  private MaterialButton signupButton;
  private TextInputLayout usernameInput;
  private String username;
  private String password;

  public void onActivityCreated(Bundle paramBundle) { super.onActivityCreated(paramBundle); }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    View view = paramLayoutInflater.inflate(R.layout.fragment_login, paramViewGroup, false);
    this.loginViewModel = ViewModelProviders.of(requireActivity()).get(LoginViewModel.class);
    this.loginButton = view.findViewById(R.id.login_button);
    this.signupButton = view.findViewById(R.id.login_signup_button);
    this.usernameInput = view.findViewById(R.id.username_text_input);
    this.passwordInput = view.findViewById(R.id.password_text_input);
    return view;
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {
    super.onViewCreated(paramView, paramBundle);
    final NavController navController = Navigation.findNavController(paramView);
    this.signupButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.new_account_action, null));
    this.loginButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            username = usernameInput.getEditText().getText().toString();
            password = passwordInput.getEditText().getText().toString();
            if (username.isEmpty()) {
              usernameInput.setError("Username can't be empty");
            } 
            if (password.isEmpty() || password.length() + 1 < 8) {
              passwordInput.setError("Password must be at least 8 characters");
            } 
            loginViewModel.authenticate(username, password);
            SaveSharedPreference.setUsername(getContext(), username);
          }
        });
    requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
          public void handleOnBackPressed() {
            loginViewModel.refuseAuthentication();
            navController.popBackStack();
            requireActivity().finish();
          }
        });
    loginViewModel.authenticateState.observe(getViewLifecycleOwner(), new Observer<AuthenticationState>() {
          public void onChanged(AuthenticationState state) {
              switch (state) {
                  case AUTHENTICATED:
                    navController.navigate(R.id.login_to_home_action);
                    break;
                  case INVALID_AUTHENTICATION:
                      Toast.makeText(getContext(), "Wrong Username/Password", Toast.LENGTH_SHORT).show();
                      break;
              }
          }
        });
  }
}
