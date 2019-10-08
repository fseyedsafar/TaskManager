package com.example.task.controller;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;
import com.example.task.R;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment {

    public static final String ARG_TIME_PICKER_TIME = "argTimePickerTime";
    public static final String EXTRA_TASK_TIME = "extraTaskTime";
    private TimePicker mTimePicker;
    private Date mTime;

    public TimePickerFragment() {
        // Required empty public constructor
    }

    public static TimePickerFragment newInstance(Date mTime) {

        Bundle args = new Bundle();

        args.putSerializable(ARG_TIME_PICKER_TIME, mTime);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTime = (Date) getArguments().getSerializable(ARG_TIME_PICKER_TIME);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.fragment_time_picker, null, false);

        mTimePicker = view.findViewById(R.id.time_picker_fragment);

        initTimePicker();

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult();
                    }
                })
                .create();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initTimePicker(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTime);

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        mTimePicker.setHour(hour);
        mTimePicker.setMinute(minute);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void sendResult() {
        mTime.setHours(mTimePicker.getHour());
        mTime.setMinutes(mTimePicker.getMinute());

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TASK_TIME, mTime);

        Fragment fragment = getTargetFragment();
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}
