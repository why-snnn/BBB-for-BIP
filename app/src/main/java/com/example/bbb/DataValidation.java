package com.example.bbb;

public class DataValidation {

    private boolean isNameValid(String name){
        return !name.equals("")
                && name.length() <= 150
                && !name.contains("*"); // TODO переделать критерии
    }

    private boolean isPasswordValid(String password){
        return !password.equals("")
                && password.length() <= 150
                && password.length() >= 8;
    }

    public boolean isCredentialsValid(String name, String password){
        return isNameValid(name) && isPasswordValid(password);
    }
}
