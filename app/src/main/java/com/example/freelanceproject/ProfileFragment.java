package com.example.freelanceproject;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.freelanceproject.Adapters.ProjectAdapter;
import com.example.freelanceproject.BusinessModel.Project;
import com.example.freelanceproject.BusinessModel.User;
import com.example.freelanceproject.Util.SaveSharedPreference;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private static final String TAG = ProfileFragment.class.getSimpleName();

    // TODO: 9/12/2019 Design the profile layout and set up the fragment to show user info and allow him to change it (DONE)
    // TODO: 9/16/2019 Allow user to edit their data

    private Toolbar mToolbar;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private ImageView mUserImage;
    private NavController navController;
    private RecyclerView recyclerView;
    private ProjectAdapter mProjectAdapter;

    private MainViewModel viewModel;
    private String username;
    private int acc_type;

    private ArrayList<Project> mProjectsList;
    private User mUser;

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

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            private int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0){
                    setHasOptionsMenu(true);
                }else {
                    setHasOptionsMenu(false);
                }
            }
        });

        viewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
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
        mCollapsingToolbar.setTitle(mUser.getName().trim());

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration
                = new AppBarConfiguration.Builder(R.id.profile_dest).build();
        /*
          this line causes the ui to lag and show the name of other fragments when navigating to them.
         */
//        NavigationUI.setupWithNavController(mCollapsingToolbar,mToolbar, navController, appBarConfiguration);

        if (SaveSharedPreference.getAccType(getContext()) == User.CLIENT_ACCOUNT){
            acc_type = User.CLIENT_ACCOUNT;
        }else {
            acc_type = User.FREELANCER_ACCOUNT;
        }

        Log.i(TAG, "onViewCreated: " + viewModel.getUser(username));
        mUser = viewModel.getUser(username) != null? (User) viewModel.getUser(username) : new User();
        mProjectsList = mUser.getPortfolio() != null? mUser.getPortfolio().getProjects() : new ArrayList<Project>();
        mUser.setImgPath("https://via.placeholder.com/350x250.png?text=Placeholder+Image");
        Glide.with(getContext()).load(mUser.getImgPath())
                .into(mUserImage);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_menu_item:
                navigateToEditProfile();
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
}
