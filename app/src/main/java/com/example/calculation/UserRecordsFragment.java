package com.example.calculation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.calculation.util.NetUtils;
import com.example.calculation.util.SynNetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserRecordsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserRecordsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LoginViewModel mViewModel;

    public UserRecordsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserRecordsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserRecordsFragment newInstance(String param1, String param2) {
        UserRecordsFragment fragment = new UserRecordsFragment();
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
        return inflater.inflate(R.layout.fragment_user_records, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        TextView list = getView().findViewById(R.id.userRecords);
        StringBuilder ans = new StringBuilder();
        ans.append("你过去的记录如下：\n\n");
        try {
            SynNetUtils.get(NetUtils.myIp + "record/listUserRecords?email=" + mViewModel.getEmail().getValue(), response -> {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ans.append(" 时间:").append(jsonObject.getString("time")).append("\n        用户:  '")
                                .append(jsonObject.getString("name")).append("'  挑战的分数记录:\t")
                                .append(jsonObject.getInt("score")).append("\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                list.setText(ans);
            });
        } catch (Exception e) {
            list.setText("查询失败！");
            e.printStackTrace();
        } finally {
            Toast.makeText(getContext(), "查询记录", Toast.LENGTH_SHORT).show();
        }
    }
}