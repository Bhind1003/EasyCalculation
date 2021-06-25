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
import com.example.calculation.util.SynNetUtils;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        // TODO: Use the ViewModel
        Button Login = getView().findViewById(R.id.button12);
        Button Register = getView().findViewById(R.id.button13);
        Button Enter = getView().findViewById(R.id.enter);
        EditText email = getView().findViewById(R.id.email);
        EditText password = getView().findViewById(R.id.password);
        email.setText(mViewModel.getEmail().getValue());
        NavController controller = Navigation.findNavController(getView());

        Login.setOnClickListener(v -> {
            String text1 = email.getText().toString();
            String text2 = password.getText().toString();
            Log.d("1414", "input:" + text1 + "    " + text2);
            SynNetUtils.post(NetUtils.myIp + "login",
                    "{\n" +
                            "  \"email\": \"" + text1 + "\",\n" +
                            "  \"password\": \"" + text2 + "\"\n" +
                            "}", response -> {
                        Log.d("1414", "SynNetUtils.post->response=" + response);
                        switch (response) {
                            case "0":
                                Toast.makeText(getContext(), "密码错误！", Toast.LENGTH_SHORT).show();
                                break;
                            case "-1":
                                Toast.makeText(getContext(), "信息出错，该用户不存在,请先注册！", Toast.LENGTH_SHORT).show();
                                break;
                            case "1":
                                Toast.makeText(getContext(), "登录成功！开始你的训练！", Toast.LENGTH_SHORT).show();
                                mViewModel.setIsLogin(true);
                                mViewModel.setEmail(text1);
                                controller.navigate(R.id.action_loginFragment_to_titleFragment);
                                break;
                            default:
                                Toast.makeText(getContext(), "信息出错，未连接到服务器！", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        Register.setOnClickListener(v -> {
            mViewModel.setEmail(email.getText().toString());
            controller.navigate(R.id.action_loginFragment_to_registerFragment);
        });
        Enter.setOnClickListener(v -> {
            controller.navigate(R.id.action_loginFragment_to_titleFragment);
        });
    }
}