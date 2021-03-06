package com.example.calculation;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.calculation.databinding.FragmentQuestionBinding;
import com.example.calculation.util.NetUtils;
import com.example.calculation.util.SynNetUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {


    MyViewModel myViewModel;
    LoginViewModel loginViewModel;

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myViewModel = ViewModelProviders.of(requireActivity(), new SavedStateViewModelFactory(requireActivity().getApplication(), requireActivity())).get(MyViewModel.class);
        //myViewModel.generator();
        //myViewModel.getCurrentScore().setValue(0);
        final FragmentQuestionBinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        final StringBuilder builder = new StringBuilder();
        View.OnClickListener listener = v -> {
            switch (v.getId()) {
                case R.id.button0:
                    builder.append("0");
                    break;
                case R.id.button1:
                    builder.append("1");
                    break;
                case R.id.button2:
                    builder.append("2");
                    break;
                case R.id.button3:
                    builder.append("3");
                    break;
                case R.id.button4:
                    builder.append("4");
                    break;
                case R.id.button5:
                    builder.append("5");
                    break;
                case R.id.button6:
                    builder.append("6");
                    break;
                case R.id.button7:
                    builder.append("7");
                    break;
                case R.id.button8:
                    builder.append("8");
                    break;
                case R.id.button9:
                    builder.append("9");
                    break;
                case R.id.buttonClear:
                    builder.setLength(0);
                    break;
            }
            if (builder.length() == 0) {
                binding.textView9.setText(getString(R.string.input_indicator));
            } else {
                binding.textView9.setText(getString(R.string.input_indicator) + builder.toString());
            }

        };

        binding.button0.setOnClickListener(listener);
        binding.button1.setOnClickListener(listener);
        binding.button2.setOnClickListener(listener);
        binding.button3.setOnClickListener(listener);
        binding.button4.setOnClickListener(listener);
        binding.button5.setOnClickListener(listener);
        binding.button6.setOnClickListener(listener);
        binding.button7.setOnClickListener(listener);
        binding.button8.setOnClickListener(listener);
        binding.button9.setOnClickListener(listener);
        binding.buttonClear.setOnClickListener(listener);

        binding.buttonSubmit.setOnClickListener(v -> {
            if (builder.length() == 0) {
                builder.append("-1");
            }
            if (Integer.valueOf(builder.toString()).intValue() == myViewModel.getAnswer().getValue()) {
                myViewModel.answerCorrect();
                builder.setLength(0);
                binding.textView9.setText(R.string.answer_corrrect_message);
                //builder.append(getString(R.string.answer_corrrect_message));
            } else {
                NavController controller = Navigation.findNavController(v);
                uploadInfo();//??????????????????????????????
                if (myViewModel.win_flag) {
                    controller.navigate(R.id.action_questionFragment_to_winFragment);
                    myViewModel.win_flag = false;
                    myViewModel.save();
                } else {
                    controller.navigate(R.id.action_questionFragment_to_loseFragment);
                }
            }

        });
        return binding.getRoot();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_question, container, false);
    }

    public void uploadInfo() {
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        if (loginViewModel.getEmail().getValue() == null) {
            loginViewModel.setEmail("test@qq.com");
        }
        SynNetUtils.post(NetUtils.myIp + "record/addRecord", "{\n" +
                "  \"email\": \"" + loginViewModel.getEmail().getValue() + "\",\n" +
                "  \"score\": \"" + myViewModel.getCurrentScore().getValue() + "\"\n" +
                "}", response -> {
            Log.i("1414", "response=" + response);
        });
    }
}
