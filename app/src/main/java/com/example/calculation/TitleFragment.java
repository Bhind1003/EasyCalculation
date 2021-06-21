package com.example.calculation;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.calculation.databinding.FragmentTitleBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class TitleFragment extends Fragment {


    public TitleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final MyViewModel myViewModel;
        myViewModel = ViewModelProviders.of(requireActivity(), new SavedStateViewModelFactory(requireActivity().getApplication(), requireActivity())).get(MyViewModel.class);
        FragmentTitleBinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        binding.button.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_titleFragment_to_questionFragment);
            myViewModel.getCurrentScore().setValue(0);
            myViewModel.generator();
        });

        return binding.getRoot();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_title, container, false);
    }
    private LoginViewModel mViewModel;
    @Override
    public void onStart() {
        super.onStart();
        mViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        TextView user=getView().findViewById(R.id.textUser);
        user.setOnClickListener(v->{
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_titleFragment_to_loginFragment);
        });
        TextView high = getView().findViewById(R.id.textViewHigh);
        high.setOnClickListener(v -> {
            mViewModel.setKey(2);
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_titleFragment_to_listRecordsFragment);
        });
        TextView history=getView().findViewById(R.id.textViewHistory);
        history.setOnClickListener(v -> {
            mViewModel.setKey(1);
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_titleFragment_to_listRecordsFragment);
        });
        if (mViewModel.getIsLogin().getValue()){
            user.setText(mViewModel.getEmail().getValue());
        }
    }
}
