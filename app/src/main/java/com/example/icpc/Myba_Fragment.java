package com.example.icpc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Myba_Fragment extends Fragment {
    private CardView cardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cardView.setRadius(8);//设置图片圆角的半径大小

        cardView.setCardElevation(8);//设置阴影部分大小

        cardView.setContentPadding(5, 5, 5, 5);//设置图片距离阴影大小

        return inflater.inflate(R.layout.fragment_myba, container, false);
    }
}