package com.example.calculation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class loginViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private final MutableLiveData<String> email = new MutableLiveData<String>();
    private final MutableLiveData<Boolean> isLogin = new MutableLiveData<Boolean>(false);

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