package com.example.calculation;

import android.os.Bundle;
import android.util.Log;
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

public class ListRecordsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LoginViewModel mViewModel;

    public ListRecordsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ListRecordsFragment newInstance(String param1, String param2) {
        ListRecordsFragment fragment = new ListRecordsFragment();
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
        return inflater.inflate(R.layout.fragment_list_records, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        TextView list = getView().findViewById(R.id.records);
        StringBuilder ans = new StringBuilder();//"时间\t\t用户邮箱\t\t用户名\t\t记录"
        ans.append("查询到的记录如下：\n\n");
        try {
            SynNetUtils.get(NetUtils.myIp + "record/listRecords?num=" + mViewModel.getKey().getValue(), response -> {
                Log.v("1414", "ListRecordsFragment->onStart:response=" + response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ans.append(" 时间:").append(jsonObject.getString("time")).append("\n        用户:  '")
                                .append(jsonObject.getString("name")).append("'  挑战的分数为:\t")
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