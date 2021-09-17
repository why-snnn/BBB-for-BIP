package com.example.bbb.ui.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bbb.EntryActivity;
import com.example.bbb.HttpHelper;
import com.example.bbb.MainMenuActivity;
import com.example.bbb.R;
import com.example.bbb.RegistrationActivity;
import com.example.bbb.databinding.FragmentDashboardBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class DashboardFragment extends Fragment {

    // private DashboardViewModel dashboardViewModel;
    private HttpHelper httpHelper;
    private FragmentDashboardBinding binding;
    final String[][] profileInfo = new String[1][1];

    TextView usernameText;
    EditText firstNameText;
    EditText lastNameText;
    EditText bioText;
    EditText emailText;
    EditText telegramText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        this.httpHelper = HttpHelper.getHttpHelper();

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        usernameText = root.findViewById(R.id.usernameText);
        firstNameText = root.findViewById(R.id.profileFirstName);
        lastNameText = root.findViewById(R.id.profileLastName);
        bioText = root.findViewById(R.id.profileBio);
        emailText = root.findViewById(R.id.profileEmail);
        telegramText = root.findViewById(R.id.profileTg);
        FloatingActionButton logoutFAB = root.findViewById(R.id.logoutBar);
        Button saveButton = root.findViewById(R.id.saveButton);
        saveButton.setVisibility(View.INVISIBLE);

        // dashboardViewModel = new DashboardViewModel(firsrName, lastName, bio, email, telegram);
        // dashboardViewModel.init("firstNameText", "lastName", "bio", "email", "telegram");

        final CountDownLatch latch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    profileInfo[0] =  httpHelper.HttpGetProfileInfo();
                    latch.countDown();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        firstNameText.setOnKeyListener((v, keyCode, event) -> {saveButton.setVisibility(View.VISIBLE); saveButton.setEnabled(true); return true; });
        lastNameText.setOnKeyListener((v, keyCode, event) -> { saveButton.setVisibility(View.VISIBLE); saveButton.setEnabled(true);return true; });
        bioText.setOnKeyListener((v, keyCode, event) -> {saveButton.setVisibility(View.VISIBLE); saveButton.setEnabled(true);return true; });
        emailText.setOnKeyListener((v, keyCode, event) -> {saveButton.setVisibility(View.VISIBLE); saveButton.setEnabled(true);return true; });
        telegramText.setOnKeyListener((v, keyCode, event) -> {saveButton.setVisibility(View.VISIBLE); saveButton.setEnabled(true);return true; });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameText.getText().toString();
                String lastName = lastNameText.getText().toString();
                String bio = bioText.getText().toString();
                String email = emailText.getText().toString();
                String telegramID = telegramText.getText().toString();

                final CountDownLatch latch = new CountDownLatch(1);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        httpHelper.HttpSetProfileInfo(firstName, lastName, bio, email, telegramID);
                        latch.countDown();
                        // startActivity(new Intent(EntryActivity.this, MainMenuActivity.class));
                    }
                }).start();
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                saveButton.setEnabled(false);
                saveButton.setVisibility(View.INVISIBLE);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });

        logoutFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CountDownLatch latch = new CountDownLatch(1);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        httpHelper.HttpLogout();
                        latch.countDown();
                    }
                }).start();
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(getActivity(), EntryActivity.class));
                getActivity().finish();
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        String username = "@" + profileInfo[0][0];
        usernameText.setText(username);

        firstNameText.setText(profileInfo[0][1]);
        lastNameText.setText(profileInfo[0][2]);
        bioText.setText(profileInfo[0][3]);
        emailText.setText(profileInfo[0][4]);
        telegramText.setText(profileInfo[0][5]);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}