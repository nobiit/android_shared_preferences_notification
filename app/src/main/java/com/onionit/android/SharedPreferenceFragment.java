package com.onionit.android;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.onionit.android.databinding.FragmentSharedPreferenceBinding;

public class SharedPreferenceFragment extends Fragment {
    protected FragmentSharedPreferenceBinding fragmentSharedPreferenceBinding;

    public SharedPreferenceFragment() {
        super(R.layout.fragment_shared_preference);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Activity activity = getActivity();
        if (activity != null) {
            activity.setTitle(getString(R.string.shared_preference_title));
        }

        if (view != null) {
            fragmentSharedPreferenceBinding = FragmentSharedPreferenceBinding.bind(view);

            fragmentSharedPreferenceBinding.sharedPreferenceName.addTextChangedListener(getEditTextOnTextChangedListener());
            fragmentSharedPreferenceBinding.sharedPreferenceKey.addTextChangedListener(getEditTextOnTextChangedListener());
            fragmentSharedPreferenceBinding.sharedPreferenceValue.addTextChangedListener(getEditTextOnTextChangedListener());
            fragmentSharedPreferenceBinding.readBtn.setOnClickListener(getReadBtnOnClickListener());
            fragmentSharedPreferenceBinding.writeBtn.setOnClickListener(getWriteBtnOnClickListener());
            fragmentSharedPreferenceBinding.deleteBtn.setOnClickListener(getDeleteBtnOnClickListener());
        }

        return view;
    }

    protected TextWatcher getEditTextOnTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (checkReadyForRead()) {
                    fragmentSharedPreferenceBinding.readBtn.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.buttonBackgroundColor, null));
                    fragmentSharedPreferenceBinding.readBtn.setEnabled(true);
                } else {
                    fragmentSharedPreferenceBinding.readBtn.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.buttonDisabledBackgroundColor, null));
                    fragmentSharedPreferenceBinding.readBtn.setEnabled(false);
                }
                if (checkReadyForWrite()) {
                    fragmentSharedPreferenceBinding.writeBtn.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.buttonBackgroundColor, null));
                    fragmentSharedPreferenceBinding.writeBtn.setEnabled(true);
                } else {
                    fragmentSharedPreferenceBinding.writeBtn.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.buttonDisabledBackgroundColor, null));
                    fragmentSharedPreferenceBinding.writeBtn.setEnabled(false);
                }
                if (checkReadyForDelete()) {
                    fragmentSharedPreferenceBinding.deleteBtn.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.buttonBackgroundColor, null));
                    fragmentSharedPreferenceBinding.deleteBtn.setEnabled(true);
                } else {
                    fragmentSharedPreferenceBinding.deleteBtn.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.buttonDisabledBackgroundColor, null));
                    fragmentSharedPreferenceBinding.deleteBtn.setEnabled(false);
                }
            }
        };
    }

    protected boolean checkReadyForReadWrite() {
        if (fragmentSharedPreferenceBinding.sharedPreferenceName.getText().toString().length() <= 0) {
            return false;
        }
        if (fragmentSharedPreferenceBinding.sharedPreferenceKey.getText().toString().length() <= 0) {
            return false;
        }
        return getSharedPreferences() != null;
    }

    protected boolean checkReadyForRead() {
        if (checkReadyForReadWrite()) {
            return getSharedPreferences().contains(fragmentSharedPreferenceBinding.sharedPreferenceKey.getText().toString());
        }
        return false;
    }

    protected boolean checkReadyForWrite() {
        if (checkReadyForReadWrite()) {
            if (fragmentSharedPreferenceBinding.sharedPreferenceValue.getText().toString().length() <= 0) {
                return false;
            }
            return fragmentSharedPreferenceBinding.sharedPreferenceValue.getText().toString().length() > 0;
        }
        return false;
    }

    protected boolean checkReadyForDelete() {
        return checkReadyForRead();
    }

    protected SharedPreferences getSharedPreferences() {
        Activity activity = getActivity();
        if (activity != null) {
            return activity.getSharedPreferences(String.valueOf(fragmentSharedPreferenceBinding.sharedPreferenceName.getText()), Context.MODE_PRIVATE);
        }
        return null;
    }

    protected View.OnClickListener getReadBtnOnClickListener() {
        return view -> {
            SharedPreferences sharedPreferences = getSharedPreferences();
            fragmentSharedPreferenceBinding.sharedPreferenceValue.setText(sharedPreferences.getString(fragmentSharedPreferenceBinding.sharedPreferenceKey.getText().toString(), ""));
        };
    }

    protected View.OnClickListener getWriteBtnOnClickListener() {
        return view -> {
            Activity activity = getActivity();
            if (activity != null) {
                SharedPreferences sharedPreferences = getSharedPreferences();
                sharedPreferences.edit().putString(fragmentSharedPreferenceBinding.sharedPreferenceKey.getText().toString(), fragmentSharedPreferenceBinding.sharedPreferenceValue.getText().toString()).apply();
            }
            getEditTextOnTextChangedListener().afterTextChanged(null);
        };
    }

    protected View.OnClickListener getDeleteBtnOnClickListener() {
        return view -> {
            Activity activity = getActivity();
            if (activity != null) {
                SharedPreferences sharedPreferences = getSharedPreferences();
                sharedPreferences.edit().remove(fragmentSharedPreferenceBinding.sharedPreferenceKey.getText().toString()).apply();
            }
            getEditTextOnTextChangedListener().afterTextChanged(null);
        };
    }
}