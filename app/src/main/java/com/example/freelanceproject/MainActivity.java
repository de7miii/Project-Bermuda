package com.example.freelanceproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.freelanceproject.Adapters.PostsAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);

        final NavController navController = Navigation.findNavController(this, R.id.nav_frag_host);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.login_dest || destination.getId() == R.id.signup_dest || destination.getId() == R.id.editProfile_dest){
                    hideBottomNav();
                }else {
                    setupBottomNavMenu(navController);
                }
            }
        });


        // TODO: 9/7/2019 implement the launch screen and the login/signup screen (DONE)

        // TODO: 9/8/2019 fix the navigation logic and the bottomnavbar problem.

    }

    public void setupBottomNavMenu(NavController navController) {
        bottomNav.setVisibility(BottomNavigationView.VISIBLE);
        NavigationUI.setupWithNavController(bottomNav, navController);
        bottomNav.animate().alpha(1f).setDuration(1000);
    }

    public void hideBottomNav(){
        bottomNav.setVisibility(BottomNavigationView.GONE);
        bottomNav.animate().alpha(0f);
    }
}
