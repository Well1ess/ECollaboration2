package com.example.a29149.ecollaboration.model.platform;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a29149.ecollaboration.R;

public class PlatformMainFragment extends Fragment {

    public PlatformMainFragment() {
    }

    public static PlatformMainFragment newInstance(String param1, String param2) {
        PlatformMainFragment fragment = new PlatformMainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_platform_main, container, false);
    }

}
