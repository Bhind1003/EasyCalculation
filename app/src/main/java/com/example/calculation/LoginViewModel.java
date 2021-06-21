package com.example.calculation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private final MutableLiveData<String> email = new MutableLiveData<String>();
    private final MutableLiveData<Integer> key = new MutableLiveData<>(1);
    private final MutableLiveData<Boolean> isLogin = new MutableLiveData<Boolean>(false);

    public MutableLiveData<Integer> getKey() {
        return key;
    }

    public void setKey(int num) {
        key.setValue(num);
    }

    public MutableLiveData<Boolean> getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Boolean in) {
        isLogin.setValue(in);
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

}