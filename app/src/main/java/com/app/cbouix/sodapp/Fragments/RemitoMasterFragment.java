package com.app.cbouix.sodapp.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.app.cbouix.sodapp.Activities.RemitoActivity;

/**
 * Created by CBouix on 09/04/2017.
 */

public class RemitoMasterFragment extends Fragment{

    protected RemitoActivity _activity = (RemitoActivity) getActivity();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        _activity = (RemitoActivity) getActivity();
        super.onCreate(savedInstanceState);
    }
}
