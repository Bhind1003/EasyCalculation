package com.example.calculation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.calculation.util.NetUtils;
import com.example.calculation.util.synNetUtils;

public class loginFragment extends Fragment {

    private loginViewModel mViewModel;

    public static loginFragment newInstance() {
        return new loginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(loginViewModel.class);
        // TODO: Use the ViewModel
        Button ok = getView().findViewById(R.id.button12);
        Button Register = getView().findViewById(R.id.button13);
        EditText email = getView().findViewById(R.id.email);
        EditText password = getView().findViewById(R.id.password);
        NavController controller = Navigation.findNavController(getView());

        ok.setOnClickListener(v -> {
            String text1 = email.getText().toString();
//            if (text1.trim().compareTo("")>0){
//                Toast.makeText(getContext(), "请输入邮箱！", Toast.LENGTH_SHORT).show();
//            }
            String text2 = password.getText().toString();
//            if (text2.trim().compareTo("")>0){
//                Toast.makeText(getContext(), "请输入密码！", Toast.LENGTH_SHORT).show();
//            }
            Log.d("1414", "input:" + text1 + "    " + text2);
            synNetUtils.post(NetUtils.myIp + "login",
                    "{\n" +
                            "  \"email\": \"" + text1 + "\",\n" +
                            "  \"password\": \"" + text2 + "\"\n" +
                            "}", response -> {
                        Log.d("1414", response);
                        switch (response) {
                            case "0":
                                Toast.makeText(getContext(), "密码错误！", Toast.LENGTH_SHORT).show();
                                break;
                            case "-1":
                                Toast.makeText(getContext(), "信息出错，该用户不存在！", Toast.LENGTH_SHORT).show();
                                break;
                            case "1":
                                Toast.makeText(getContext(), "登录成功！", Toast.LENGTH_SHORT).show();
                                controller.navigate(R.id.action_loginFragment_to_titleFragment);
                                break;
                        }
//                        Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                    });
        });
        Register.setOnClickListener(v -> {
            controller.navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }

}