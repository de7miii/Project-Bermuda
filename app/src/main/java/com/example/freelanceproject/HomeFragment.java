package com.example.freelanceproject;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.freelanceproject.Adapters.PostsAdapter;
import com.example.freelanceproject.BusinessModel.Client;
import com.example.freelanceproject.BusinessModel.Freelancer;
import com.example.freelanceproject.BusinessModel.User;
import com.example.freelanceproject.Util.AuthenticationState;
import com.example.freelanceproject.Util.OnPostListner;
import com.example.freelanceproject.Util.RegistrationState;
import com.example.freelanceproject.Util.SaveSharedPreference;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnPostListner {
    private final static  String TAG = HomeFragment.class.getSimpleName();

    private MainViewModel viewModel;
    private RecyclerView mRecyclerView;
    private PostsAdapter mPostsAdapter;
    private Toolbar mToolbar;

    private LoginViewModel loginViewModel;
    private SignupViewModel signupViewModel;

    private NavController navController;

    private ArrayList<Object> items = new ArrayList<>();

    private String username;
    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: 9/11/2019 use sharedpreference to remember user logging status (DONE)


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
        loginViewModel = ViewModelProviders.of(requireActivity()).get(LoginViewModel.class);
        signupViewModel = ViewModelProviders.of(requireActivity()).get(SignupViewModel.class);
        mPostsAdapter = new PostsAdapter(getContext(), this);

        mPostsAdapter.setClientsList(viewModel.getClientsList());
        mPostsAdapter.setFreelancersList(viewModel.getFreelancersList());

        items.addAll(viewModel.getClientsList());
        items.addAll(viewModel.getFreelancersList());

        username = SaveSharedPreference.getUsername(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mPostsAdapter);

        viewModel.getFreelancerLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Freelancer>>() {
            @Override
            public void onChanged(ArrayList<Freelancer> freelancers) {
                mPostsAdapter.updateFreelancersList(freelancers);
            }
        });

        viewModel.getClientLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Client>>() {
            @Override
            public void onChanged(ArrayList<Client> clients) {
                mPostsAdapter.updateClientList(clients);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        if (username.equals("freelancer1")
                        || username.equals("freelancer2")
                        || username.equals("freelancer3")
                        || username.equals("freelancer4")
                        || username.equals("freelancer5")
                        || username.equals("client1")
                        || username.equals("client2")
                        || username.equals("client3")
                        || username.equals("client4")
                        || username.equals("client5")){
                            navController.popBackStack();
                            requireActivity().finish();
                        }else{
                            loginViewModel.logOut(getContext());
                            navController.popBackStack();
                            requireActivity().finish();
                        }
                    }
                });

        if (!SaveSharedPreference.getLoginStatus(getContext())) {
            loginViewModel.authenticateState.observe(getViewLifecycleOwner(), new Observer<AuthenticationState>() {
                @Override
                public void onChanged(AuthenticationState state) {
                    Log.i(TAG, "onChanged: " + state);
                    switch (state) {
                        case AUTHENTICATED:
                            signupViewModel.registrationState.setValue(RegistrationState.REGISTRED);
                            SaveSharedPreference.setLoginStatus(getContext(), true);
                            showWlecomeMessage();
                            break;
                        case UNAUTHENTICATED:
                            navController.navigate(R.id.action_home_dest_to_login_navigation);
                            SaveSharedPreference.setLoginStatus(getContext(), false);
                            break;
                    }
                }
            });

            signupViewModel.registrationState.observe(getViewLifecycleOwner(), new Observer<RegistrationState>() {
                @Override
                public void onChanged(RegistrationState registrationState) {
                    Log.i(TAG, "onChangedRegistration: " + registrationState);
                    switch (registrationState) {
                        case REGISTRATION_COMPLETED:
                            SaveSharedPreference.setLoginStatus(getContext(), true);
                            showWlecomeMessage();
                            break;
                        case COLLECT_USER_DATA:
                            navController.navigate(R.id.action_home_to_signup);
                            SaveSharedPreference.setLoginStatus(getContext(), false);
                            break;
                        case REGISTRED:
                            break;
                    }
                }
            });
        }

    }

    private void showWlecomeMessage() {
        Toast toast = Toast.makeText(getContext(), "Welcome Home! " + SaveSharedPreference.getUsername(getContext()), Toast.LENGTH_LONG);
        toast.show();
    }

    private void navigateToEditProfile(String username){
        HomeFragmentDirections.HomeToProfileAction action = HomeFragmentDirections.homeToProfileAction();
        action.setUsernameArg(username);
        navController.navigate(action);
    }

    @Override
    public void onPostClicked(int position) {
        User user = (User)items.get(position);
        if (!user.getUserName().equals(this.username)) {
            navigateToEditProfile(user.getUserName());
        }
    }
}
