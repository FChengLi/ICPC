package com.example.icpc.fastlearning;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.icpc.R;

public class IntroduceFragment extends Fragment {

    private static final String ARG_DATA_ITEM = "data_item";
    private DataItem dataItem;

    public static IntroduceFragment newInstance(DataItem dataItem) {
        IntroduceFragment fragment = new IntroduceFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_DATA_ITEM, dataItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dataItem = getArguments().getParcelable(ARG_DATA_ITEM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_introduce, container, false);

        TextView title = view.findViewById(R.id.title);
        TextView content = view.findViewById(R.id.content);
        title.setText(dataItem.getTitle());
        content.setText(dataItem.getDescription());

        return view;
    }
}
