package com.example.freelanceproject;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.example.freelanceproject.Adapters.PostsAdapter;
import com.example.freelanceproject.BusinessModel.Client;
import com.example.freelanceproject.BusinessModel.Freelancer;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    private static final String TAG = SearchFragment.class.getSimpleName();
    /**
     * Name changed to Rising depicting the new rising Users (Freelancers & Clients)
     * Future: to show the upcoming users and where searches take places
    **/
    // TODO: 9/13/2019 Implement the searsh logic and design the layout.(DONE)


    private Toolbar mToolbar;
    private NavController navController;

    private MainViewModel viewModel;
    private RecyclerView mRecyclerView;
    private PostsAdapter mPostsAdapter;

    private String searchQuery;
    private String searchText;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
        mPostsAdapter = new PostsAdapter(getContext());

        mPostsAdapter.setClientsList(viewModel.getClientsList());
        mPostsAdapter.setFreelancersList(viewModel.getFreelancersList());

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mToolbar = view.findViewById(R.id.search_toolbar);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Rising");
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE); // to be used if search dialog is implemented.
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                Log.i(TAG, "onQueryTextSubmit: " + searchQuery);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText;
                mPostsAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }
}
