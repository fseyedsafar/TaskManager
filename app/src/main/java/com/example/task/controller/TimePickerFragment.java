package com.example.task.controller;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;
import com.example.task.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment {

    public static final String ARG_TIME_PICKER_TIME = "argTimePickerTime";
    public static final String EXTRA_TASK_TIME = "extraTaskTime";
    private TimePicker mTimePicker;
    private String mTime;

    public TimePickerFragment() {
        // Required empty public constructor
    }

    public static TimePickerFragment newInstance(String time) {

        Bundle args = new Bundle();

        args.putSerializable(ARG_TIME_PICKER_TIME, time);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTime = (String) getArguments().getSerializable(ARG_TIME_PICKER_TIME);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.fragment_time_picker, null, false);

        mTimePicker = view.findViewById(R.id.time_picker_fragment);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult();
                    }
                })
                .create();
    }

    private void sendResult() {
        int hour = mTimePicker.getCurrentHour();
        int minute = mTimePicker.getCurrentMinute();
        String time = hour + " : " + minute;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TASK_TIME, time);

        Fragment fragment = getTargetFragment();
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}
