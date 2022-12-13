package com.onionit.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.onionit.android.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    protected FragmentHomeBinding fragmentHomeBinding;
    protected FragmentManager fragmentManager;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Activity activity = getActivity();
        if (activity != null) {
            activity.setTitle(getString(R.string.app_name));
        }

        if (view != null) {
            fragmentHomeBinding = FragmentHomeBinding.bind(view);
            fragmentManager = getParentFragmentManager();

            fragmentHomeBinding.sharedPreferenceBtn.setOnClickListener(getSharedPreferenceBtnOnClickListener());
        }

        return view;
    }

    protected View.OnClickListener getSharedPreferenceBtnOnClickListener() {
        return view -> fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(R.id.fragmentView, SharedPreferenceFragment.class, null).setReorderingAllowed(true).addToBackStack(null).commit();
    }
}