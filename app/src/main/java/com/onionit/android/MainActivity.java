package com.onionit.android;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.onionit.android.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    protected ActivityMainBinding activityMainBinding;
    protected FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Iconify.with(new FontAwesomeModule());

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        fragmentManager = getSupportFragmentManager();

        setContentView(activityMainBinding.getRoot());
        setSupportActionBar(activityMainBinding.toolbar);

        activityMainBinding.fab.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_support));
        activityMainBinding.fab.setOnClickListener(this.getFabOnClickListener());

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().add(R.id.fragmentView, HomeFragment.class, null).commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.activityMainBinding = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.setTitle(getString(R.string.app_name));
    }

    protected View.OnClickListener getFabOnClickListener() {
        return view -> Snackbar.make(view, getString(R.string.fab_message), Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}