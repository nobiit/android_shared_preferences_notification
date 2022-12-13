package com.onionit.android;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.onionit.android.databinding.FragmentNotificationBinding;

import java.util.Random;

public class NotificationFragment extends Fragment {
    protected static String CHANNEL_ID = NotificationFragment.class.getName();

    protected FragmentNotificationBinding fragmentNotificationBinding;
    protected NotificationManager notificationManager;

    public NotificationFragment() {
        super(R.layout.fragment_notification);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Activity activity = getActivity();
        if (activity != null) {
            notificationManager = activity.getSystemService(NotificationManager.class);

            activity.setTitle(getString(R.string.notification_title));
        }

        if (view != null) {
            fragmentNotificationBinding = FragmentNotificationBinding.bind(view);

            fragmentNotificationBinding.notificationId.addTextChangedListener(getEditTextOnTextChangedListener());
            fragmentNotificationBinding.notificationTitle.addTextChangedListener(getEditTextOnTextChangedListener());
            fragmentNotificationBinding.notificationMessage.addTextChangedListener(getEditTextOnTextChangedListener());
            fragmentNotificationBinding.randomNotificationIdBtn.setOnClickListener(getRandomNotificationIdBtnOnClickListener());
            fragmentNotificationBinding.createBtn.setOnClickListener(getCreateBtnOnClickListener());

            createNotificationChannel();
        }

        return view;
    }

    protected void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, getString(R.string.notification_title), NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
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
                if (checkReadyForCreate()) {
                    fragmentNotificationBinding.createBtn.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.buttonBackgroundColor, null));
                    fragmentNotificationBinding.createBtn.setEnabled(true);
                } else {
                    fragmentNotificationBinding.createBtn.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.buttonDisabledBackgroundColor, null));
                    fragmentNotificationBinding.createBtn.setEnabled(false);
                }
            }
        };
    }

    protected boolean checkReadyForCreate() {
        if (fragmentNotificationBinding.notificationId.getText().toString().length() <= 0) {
            return false;
        }
        if (fragmentNotificationBinding.notificationTitle.getText().toString().length() <= 0) {
            return false;
        }
        if (fragmentNotificationBinding.notificationMessage.getText().toString().length() <= 0) {
            return false;
        }
        return notificationManager != null;
    }

    protected View.OnClickListener getRandomNotificationIdBtnOnClickListener() {
        return view -> {
            int notificationId;
            do {
                notificationId = new Random().nextInt();
            } while (notificationId <= 0);
            fragmentNotificationBinding.notificationId.setText(String.valueOf(notificationId), TextView.BufferType.EDITABLE);
        };
    }

    protected View.OnClickListener getCreateBtnOnClickListener() {
        return view -> {
            Activity activity = getActivity();
            if (activity != null) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, CHANNEL_ID).setSmallIcon(R.drawable.icon_app).setContentTitle(fragmentNotificationBinding.notificationTitle.getText()).setContentText(fragmentNotificationBinding.notificationMessage.getText());
                Notification notification = builder.build();
                notificationManager.notify(Integer.decode(fragmentNotificationBinding.notificationId.getText().toString()), notification);
            }
        };
    }
}