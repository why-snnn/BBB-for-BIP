package com.example.bbb.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> firstName = new MutableLiveData<>();
    private final MutableLiveData<String> lastName = new MutableLiveData<>();
    private final MutableLiveData<String> bio = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> telegram = new MutableLiveData<>();

    public DashboardViewModel(){ }

    public void init(String firstName, String lastName, String bio, String email, String telegram) {
        this.firstName.setValue(firstName);
        this.lastName.setValue(lastName);
        this.bio.setValue(bio);
        this.email.setValue(email);
        this.telegram.setValue(telegram);
    }

    public LiveData<String> getFirstName() {
        return firstName;
    }

    public LiveData<String> getLastName() {
        return lastName;
    }

    public LiveData<String> getBio() {
        return bio;
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public LiveData<String> getTg() {
        return telegram;
    }


}