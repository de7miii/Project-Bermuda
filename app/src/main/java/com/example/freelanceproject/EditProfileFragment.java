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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.freelanceproject.BusinessModel.User;
import com.example.freelanceproject.Util.SaveSharedPreference;

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
    private EditText nameTextInput, emailTextInput, birthYearTextInput, descriptionTextInput, minRateTextInput, maxRateTextInput;
    private TextView payRatesText;

    private String newName, newEmail, newDescription, newBirthYear, newMinRate, newMaxRate;

    private NavController mNavController;

    private int accType;

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
        payRatesText = view.findViewById(R.id.payRateText);
        minRateTextInput = view.findViewById(R.id.minRateTextInput);
        maxRateTextInput = view.findViewById(R.id.maxRateTextInput);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accType = SaveSharedPreference.getAccType(getContext());
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
        if (accType == User.CLIENT_ACCOUNT){
            payRatesText.setVisibility(View.GONE);
            minRateTextInput.setVisibility(View.GONE);
            maxRateTextInput.setVisibility(View.GONE);
        }else{
            minRateTextInput.setText(Integer.toString(mUser.getRatesMin()));
            maxRateTextInput.setText(Integer.toString(mUser.getRatesMax()));
        }
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
                if (s.toString().length() != 0){
                    newName = s.toString().trim();
                }else {
                    nameTextInput.setError("Name cannot be left empty");
                    newName = null;
                }
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
                if (s.toString().length() != 0) {
                    newEmail = s.toString().trim();
                }else {
                    newEmail = null;
                }
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
                if (s.toString().length() != 0) {
                    newDescription = s.toString().trim();
                }else {
                    newDescription = null;
                }
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
                if (s.toString().length() != 0) {
                    newBirthYear = s.toString().trim();
                }else{
                    newBirthYear = null;
                }
            }
        });
        minRateTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    newMinRate = s.toString().trim();
                    Log.i(TAG, "onTextChanged: " + newMinRate);
                }else{
                    newMinRate = null;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() != 0) {
                    newMinRate = s.toString().trim();
                }else{
                    newMinRate = null;
                }
            }
        });
        maxRateTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    newMaxRate = s.toString().trim();
                    Log.i(TAG, "onTextChanged: " + newMaxRate);
                }else{
                    newMaxRate = null;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() != 0) {
                    newMaxRate = s.toString().trim();
                }else{
                    newMaxRate = null;
                }
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
        }else {
            if (accType == User.FREELANCER_ACCOUNT) {
                return editName()
                        && editEmail()
                        && editDescription()
                        && editBirthyear()
                        && editMinRate()
                        && editMaxRate();
            }else{
                return editName()
                        && editEmail()
                        && editDescription()
                        && editBirthyear();
            }
        }
    }

    private int calculateBirthYear(int age){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) - age;
    }
    private int calculateAge(String year){
        if (year != null) {
            Calendar calendar = Calendar.getInstance();
            return calendar.get(Calendar.YEAR) - Integer.parseInt(year);
        }else{
            return mUser.getAge();
        }
    }

    private boolean isDataTheSame(){
        return isNameChanged()
                && isEmailChanged()
                && isDescriptionChanged()
                && isBirthYearChanged();
    }
    private boolean isNameChanged(){
        return nameTextInput.getText() != null
                && newName != null
                && newName.equals(nameTextInput.getText().toString())
                && nameTextInput.getText().length() != 0;
    }
    private boolean isEmailChanged(){
        return emailTextInput.getText() != null
                && newEmail != null
                && newEmail.equals(emailTextInput.getText().toString())
                && emailTextInput.getText().length() != 0;
    }
    private boolean isDescriptionChanged(){
        return descriptionTextInput.getText() != null
                && newDescription != null
                && newDescription.equals(descriptionTextInput.getText().toString())
                && descriptionTextInput.getText().length() != 0;
    }
    private boolean isBirthYearChanged(){
        return birthYearTextInput.getText() != null
                && newBirthYear != null
                && newBirthYear.equals(birthYearTextInput.getText().toString())
                && birthYearTextInput.getText().length()!= 0;
    }

    private boolean editName(){
        if (newName!= null){
            mUser.setName(newName);
            return true;
        }else{
            return nameTextInput.getText().toString().length() != 0;
        }
    }

    private boolean editEmail(){
        if (newEmail != null) {
            mUser.setEmail(newEmail);
            return true;
        }else if (emailTextInput.getText().toString().length() != 0){
            if (mUser.getEmail().equals(emailTextInput.getText().toString().trim())){
                return true;
            }else{
                mUser.setEmail(emailTextInput.getText().toString().trim());
                return true;
            }
        }else{
            return false;
        }
    }

    private boolean editDescription(){
        if (newDescription != null) {
            mUser.setDescription(newDescription);
            return true;
        }else if (descriptionTextInput.getText().toString().length() != 0){
            if (mUser.getDescription().equals(descriptionTextInput.getText().toString().trim())){
                return true;
            }else{
                mUser.setDescription(descriptionTextInput.getText().toString().trim());
                return true;
            }
        }else {
//            descriptionTextInput.setError("Description cannot be empty");
            return true;
        }
    }

    private boolean editBirthyear(){
        if (newBirthYear != null) {
            mUser.setAge(calculateAge(newBirthYear));
            return true;
        } else if (birthYearTextInput.getText().toString().length() != 0) {
            if (calculateBirthYear(mUser.getAge()) == Integer.parseInt(birthYearTextInput.getText().toString().trim())){
                return true;
            }else{
                mUser.setAge(calculateAge(birthYearTextInput.getText().toString().trim()));
                return true;
            }
        }else{
//            birthYearTextInput.setError("Birth year cannot be empty");
            return true;
        }
    }

    private boolean editMinRate(){
        if (newMinRate != null) {
            mUser.setRatesMin(Integer.parseInt(newMinRate));
            return true;
        } else if (minRateTextInput.getText().toString().length() != 0) {
            if (Integer.parseInt(minRateTextInput.getText().toString().trim()) == mUser.getRatesMin()){
                return true;
            }else{
                mUser.setRatesMin(Integer.parseInt(minRateTextInput.getText().toString().trim()));
                return true;
            }
        }else{
            minRateTextInput.setError("Minimum Rate cannot be empty");
            return false;
        }
    }

    private boolean editMaxRate(){
        if (newMaxRate != null) {
            mUser.setRatesMax(Integer.parseInt(newMaxRate));
            return true;
        } else if (maxRateTextInput.getText().toString().length() != 0) {
            if (Integer.parseInt(maxRateTextInput.getText().toString().trim()) == mUser.getRatesMax()){
                return true;
            }else{
                mUser.setRatesMax(Integer.parseInt(maxRateTextInput.getText().toString().trim()));
                return true;
            }
        }else{
            maxRateTextInput.setError("Minimum Rate cannot be empty");
            return false;
        }
    }
}
