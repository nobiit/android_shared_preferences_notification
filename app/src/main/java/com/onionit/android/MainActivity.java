package com.onionit.android;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.onionit.android.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    protected ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Iconify.with(new FontAwesomeModule());

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        setSupportActionBar(activityMainBinding.toolbar);

        activityMainBinding.fab.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_support));

        activityMainBinding.fab.setOnClickListener(this.getFabOnClickListener());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.activityMainBinding = null;
    }

    protected View.OnClickListener getFabOnClickListener() {
        return view -> Snackbar.make(view, "Đây là dự án Demo môn lập trình Android của nhóm OnionIT", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}