package com.example.freelanceproject;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.freelanceproject.Adapters.ProjectAdapter;
import com.example.freelanceproject.BusinessModel.Client;
import com.example.freelanceproject.BusinessModel.Freelancer;
import com.example.freelanceproject.BusinessModel.Project;
import com.example.freelanceproject.BusinessModel.User;
import com.example.freelanceproject.Util.SaveSharedPreference;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private static final String TAG = ProfileFragment.class.getSimpleName();

    // TODO: 9/12/2019 Design the profile layout and set up the fragment to show user info and allow him to change it (DONE)
    // TODO: 9/16/2019 Allow user to edit their data (DONE)
    // TODO: 9/29/2019 Add on click listeners and allow one's to see other users profile (DONE)

    private Toolbar mToolbar;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private ImageView mUserImage;
    private NavController navController;
    private RecyclerView recyclerView;
    private ProjectAdapter mProjectAdapter;

    private MaterialTextView userSkills, userCred, userMinRate, userMaxRate, ratesSeparator;
    private FloatingActionButton newProjectFab;

    private MainViewModel viewModel;
    private LoginViewModel loginViewModel;
    private String username;
    private int acc_type;

    private ArrayList<Project> mProjectsList;
    private User mUser;

    private Menu mMenu;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mToolbar = view.findViewById(R.id.toolbar);
        mCollapsingToolbar = view.findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbar.setTitleEnabled(true);
        mAppBarLayout = view.findViewById(R.id.container);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        mUserImage = view.findViewById(R.id.user_image);
        userSkills = view.findViewById(R.id.skills_text_view);
        userCred = view.findViewById(R.id.cred_score_text_view);
        userMinRate = view.findViewById(R.id.min_rates_text_view);
        ratesSeparator = view.findViewById(R.id.rates_separator);
        userMaxRate = view.findViewById(R.id.max_rates_text_view);
        newProjectFab = view.findViewById(R.id.profile_new_project_fab);
        setHasOptionsMenu(true);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            private int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0){
                    if (hasOptionsMenu()) {
                        MenuItem editProfileItem = mMenu.findItem(R.id.edit_menu_edit_profile_item);
                        editProfileItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    }
                }else {
                    if (hasOptionsMenu()) {
                        MenuItem editProfileItem = mMenu.findItem(R.id.edit_menu_edit_profile_item);
                        editProfileItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                    }
                }
            }
        });

        viewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
        loginViewModel = ViewModelProviders.of(requireActivity()).get(LoginViewModel.class);
        username = SaveSharedPreference.getUsername(getContext());

        mProjectAdapter = new ProjectAdapter(getContext());

        recyclerView = view.findViewById(R.id.profile_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProjectAdapter.setProjectsList(mProjectsList);
        recyclerView.setAdapter(mProjectAdapter);
        mCollapsingToolbar.setTitle(mUser.getName().trim().toUpperCase());

        // TODO: 9/28/2019 Create a new destination to create a new project when the user clicks on the fab
        newProjectFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Add new Project", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
//        AppBarConfiguration appBarConfiguration
//                = new AppBarConfiguration.Builder(R.id.profile_dest).build();
        /*
          this line causes the ui to lag and show the name of other fragments when navigating to them.
         */
//        NavigationUI.setupWithNavController(mCollapsingToolbar,mToolbar, navController, appBarConfiguration);

        if (SaveSharedPreference.getAccType(getContext()) == User.CLIENT_ACCOUNT){
            acc_type = User.CLIENT_ACCOUNT;
        }else {
            acc_type = User.FREELANCER_ACCOUNT;
        }
        Log.i(TAG, "onViewCreated: " + ProfileFragmentArgs.fromBundle(getArguments()).getUsernameArg());
        if (ProfileFragmentArgs.fromBundle(getArguments()).getUsernameArg() != null){
            mUser = (User)viewModel.getUser(ProfileFragmentArgs.fromBundle(getArguments()).getUsernameArg());
            acc_type = mUser.getAccType();
            setHasOptionsMenu(false);
            newProjectFab.setVisibility(View.GONE);
        }else{
            Log.i(TAG, "onViewCreated: " + viewModel.getUser(username));
            if (viewModel.getUser(username) != null){
                switch (acc_type){
                    case Client.CLIENT_ACCOUNT:
                        mUser = (Client)viewModel.getUser(username);
                        break;
                    case Client.FREELANCER_ACCOUNT:
                        mUser = (Freelancer)viewModel.getUser(username);
                        break;
                }
            }
        }

        mProjectsList = mUser.getPortfolio() != null? mUser.getPortfolio().getProjects() : new ArrayList<Project>();


        mUser.setImgPath("https://via.placeholder.com/350x250.png?text=Placeholder+Image");
        Glide.with(getContext()).load(mUser.getImgPath())
                .into(mUserImage);
        userCred.setText("CreadScore: " + Integer.toString(mUser.getCredScore()));
        if (acc_type == Client.FREELANCER_ACCOUNT){
            userSkills.setText(getSkillsString(((Freelancer)mUser).getSkills()));
            userMinRate.setText(mUser.getRatesMin() + Currency.getInstance(Locale.US).getSymbol());
            userMaxRate.setText(mUser.getRatesMax() + Currency.getInstance(Locale.US).getSymbol());
        }else{
            ratesSeparator.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.edit, menu);
        mMenu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_menu_edit_profile_item:
                navigateToEditProfile();
                return true;
            case R.id.edit_menu_log_out_item:
                loginViewModel.logOut(getContext());
                navController.popBackStack(R.id.home_dest, false);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void navigateToEditProfile(){
        ProfileFragmentDirections.EditProfileAction action = ProfileFragmentDirections.editProfileAction();
        action.setUserArg(username);
        navController.navigate(action);
    }

    private String getSkillsString(ArrayList<String> skills){
        StringBuilder stringBuilder = new StringBuilder();
        for (String s :
                skills) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }
}
