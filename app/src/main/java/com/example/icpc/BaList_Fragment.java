package com.example.icpc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BaList_Fragment extends Fragment {

    private static final String ARG_BOARD_TYPE = "board_type";
    private String boardType;

    public static BaList_Fragment newInstance(String boardType) {
        BaList_Fragment fragment = new BaList_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_BOARD_TYPE, boardType);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.contentRecyclerView);

        if (getArguments() != null) {
            boardType = getArguments().getString(ARG_BOARD_TYPE);
        }

        // 创建图标数据列表
        List<IconItem> iconList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            iconList.add(new IconItem(R.drawable.ic_launcher_background, boardType + " Icon " + (i + 1)));
        }

        // 创建适配器
        BaIconAdapter adapter = new BaIconAdapter(iconList);

        // 创建GridLayoutManager，设置每行显示3个图标
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
