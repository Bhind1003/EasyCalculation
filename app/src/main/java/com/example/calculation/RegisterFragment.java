package com.example.calculation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.calculation.util.NetUtils;
import com.example.calculation.util.SynNetUtils;

public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LoginViewModel mViewModel;

    public RegisterFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        EditText emailName = getView().findViewById(R.id.emailName);
        EditText PersonName = getView().findViewById(R.id.PersonName);
        EditText Password1 = getView().findViewById(R.id.Password1);
        EditText Password2 = getView().findViewById(R.id.Password2);
        emailName.setText(mViewModel.getEmail().getValue());
        Button back = getView().findViewById(R.id.buttonBack);
        Button buttonAdd = getView().findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(v -> {
            String name = PersonName.getText().toString();
            String email = emailName.getText().toString();
            String password1 = Password1.getText().toString();
            String password2 = Password2.getText().toString();
            if (password1.compareTo(password2) == 0) {
                SynNetUtils.post(NetUtils.myIp + "user/addUser",
                        "{\n" +
                                "  \"email\": \"" + email + "\",\n" +
                                "  \"name\": \"" + name + "\",\n" +
                                "  \"password\": \"" + password1 + "\"\n" +
                                "}", response -> {
                            Log.d("1414", response);
                            switch (response) {
                                case "1":
                                    NavController controller = Navigation.findNavController(v);
                                    controller.navigate(R.id.action_registerFragment_to_loginFragment);
                                    mViewModel.setEmail(email);
                                    Toast.makeText(getContext(), "???????????????\n???????????????", Toast.LENGTH_SHORT).show();
                                    break;
                                case "0":
                                    Toast.makeText(getContext(), "???????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        });
            } else {
                Toast.makeText(getContext(), "?????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_registerFragment_to_loginFragment);
        });
    }

}