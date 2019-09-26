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

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.freelanceproject.BusinessModel.User;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {
    private static final String TAG = EditProfileFragment.class.getSimpleName();

    private MainViewModel viewModel;
    private String username;
    private User mUser;

    private Toolbar mToolbar;

    private ImageView userImageView;
    private EditText nameTextInput, emailTextInput, birthYearTextInput, descriptionTextInput;

    private String newName, newEmail, newDescription, newBirthYear;

    private NavController mNavController;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        mToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Edit Profile");
        setHasOptionsMenu(true);

        userImageView = view.findViewById(R.id.userImageView);
        nameTextInput = view.findViewById(R.id.nameTextInput);
        nameTextInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
        emailTextInput = view.findViewById(R.id.emailTextInput);
        emailTextInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
        descriptionTextInput = view.findViewById(R.id.descriptionTextInput);
        descriptionTextInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
        birthYearTextInput = view.findViewById(R.id.birthYearTextInput);
        birthYearTextInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = Navigation.findNavController(view);
        if (mUser.getImgPath() != null)
            Glide.with(getContext()).load(mUser.getImgPath())
                    .into(userImageView);
        if (mUser.getName() != null)
            nameTextInput.setText(mUser.getName());
        if (mUser.getEmail() != null)
            emailTextInput.setText(mUser.getEmail());
        if (mUser.getDescription() != null)
            descriptionTextInput.setText(mUser.getDescription());
        if (mUser.getAge() != 0)
            birthYearTextInput.setText(Integer.toString(calculateBirthYear(mUser.getAge())));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
        username = EditProfileFragmentArgs.fromBundle(getArguments()).getUserArg();
        Log.i(TAG, "onCreate: " + username);
        mUser = (User) viewModel.getUser(username);
        Log.i(TAG, "onCreate: " + mUser.getImgPath());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nameTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0){
                    newName = s.toString().trim();
                    Log.i(TAG, "onTextChanged: " + newName);
                }else {
                    nameTextInput.setError("Name cannot be left empty");
                    newName = null;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "nameTextInput afterTextChanged: " + s.toString().trim());
            }
        });
        emailTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()!= 0 ){
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()){
                        newEmail = s.toString().trim();
                        Log.i(TAG, "onTextChanged: " + newEmail);
                    }else{
                        emailTextInput.setError("Enter a valid email");
                    }
                }else {
                    emailTextInput.setError("Email cannot be left empty");
                    newEmail = null;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "emailTextInput afterTextChanged: " + s.toString().trim());
            }
        });
        descriptionTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0){
                    newDescription = s.toString().trim();
                    Log.i(TAG, "onTextChanged: " + newDescription);
                }else {
                    newDescription = null;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "descriptionTextInput afterTextChanged: " + s.toString().trim());
            }
        });
        birthYearTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0){
                    newBirthYear = s.toString().trim();
                    Log.i(TAG, "onTextChanged: " + newBirthYear);
                }else {
                    newBirthYear = null;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "birthYearTextInput afterTextChanged: " + s.toString().trim());
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_menu_item:
                if (saveNewUserData()){
                    mNavController.navigate(R.id.editProfileDoneAction);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean saveNewUserData(){
        // TODO: 9/17/2019 implement the editing logic
        if (isDataTheSame()){
            return true;
        }
        return false;
    }

    private int calculateBirthYear(int age){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) - age;
    }
    private int calculateAge(int year){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) - year;
    }

    private boolean isDataTheSame(){
        return nameTextInput.getText()!=null
                && emailTextInput.getText()!=null
                && descriptionTextInput.getText()!=null
                && birthYearTextInput.getText()!=null
                && newName == null
                && newEmail == null
                && newDescription == null
                && newBirthYear == null
                && nameTextInput.getText().length() != 0
                && emailTextInput.getText().length() != 0
                && descriptionTextInput.getText().length() != 0
                && birthYearTextInput.getText().length()!= 0;
    }
    private boolean isNameChanged(){
        return nameTextInput.getText() != null
                && newName != null
                && newName.equals(nameTextInput.getText().toString())
                && nameTextInput.getText().length() != 0;
    }
    private boolean isNameEmpty(){
        return isNameChanged() && nameTextInput.getText().length() == 0 && newName == null;
    }
    private boolean isEmailChanged(){
        return emailTextInput.getText() != null
                && newEmail != null
                && newEmail.equals(emailTextInput.getText().toString())
                && emailTextInput.getText().length() != 0;
    }
    private boolean isEmailEmpty(){
        return isEmailChanged() && emailTextInput.getText().length() == 0 && newEmail == null;
    }
    private boolean isDescriptionChanged(){
        return descriptionTextInput.getText() != null
                && newDescription != null
                && newDescription.equals(descriptionTextInput.getText().toString())
                && descriptionTextInput.getText().length() != 0;
    }
    private boolean isDesctiptionEmpty(){
        return isDescriptionChanged() && descriptionTextInput.getText().length() == 0 && newDescription == null;
    }
    private boolean isBirthYearChanged(){
        return birthYearTextInput.getText() != null
                && newBirthYear != null
                && newBirthYear.equals(birthYearTextInput.getText().toString())
                && birthYearTextInput.getText().length()!= 0;
    }
    private boolean isBirthYearEmpty(){
        return isBirthYearChanged() && birthYearTextInput.getText().length() == 0 && newBirthYear == null;
    }
}
