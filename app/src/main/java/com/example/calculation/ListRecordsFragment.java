package com.example.calculation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.calculation.util.NetUtils;
import com.example.calculation.util.SynNetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListRecordsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListRecordsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListRecordsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListRecordsFragment.
     */
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
        TextView list = getView().findViewById(R.id.records);
        StringBuilder ans = new StringBuilder();//"时间\t\t用户邮箱\t\t用户名\t\t记录"
        ans.append("查询到的历史记录如下：\n");
        SynNetUtils.get(NetUtils.myIp + "record/listRecords?num=150", response -> {
            Log.v("1414", "ListRecordsFragment->onStart:response=" + response);
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    Log.v("1414", "jsonArray->jsonObject::" + jsonObject.getString("time")
//                            + jsonObject.getString("email") + jsonObject.getString("name")
//                            + jsonObject.getString("score"));
                    ans.append("在时间:").append(jsonObject.getString("time")).append("\n\t\t\t\t用户:")
                            .append(jsonObject.getString("name")).append("挑战的分数为:").append(jsonObject.getInt("score")).append("\n");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.setText(ans);
        });
    }
}