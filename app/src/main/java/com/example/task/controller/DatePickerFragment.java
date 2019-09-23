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
import android.widget.DatePicker;
import com.example.task.R;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment {

    public static final String ARG_TASK_DATE = "argTaskDate";
    public static final String EXTRA_TASK_DATE = "extraTaskDate";
    private DatePicker mDatePicker;
    private Date mDate;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    public static DatePickerFragment newInstance(Date date) {
        
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_DATE, date);
        
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDate = (Date) getArguments().getSerializable(ARG_TASK_DATE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.fragment_date_picker, null, false);

        mDatePicker = view.findViewById(R.id.date_picker_fragment);

        initDatePicker();

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

    private void initDatePicker(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        mDatePicker.init(year, monthOfYear, dayOfMonth, null);
    }

    private void sendResult() {
        int year = mDatePicker.getYear();
        int monthOfYear = mDatePicker.getMonth();
        int dayOfMonth = mDatePicker.getDayOfMonth();

        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        Date date = gregorianCalendar.getTime();

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TASK_DATE, date);

        Fragment fragment = getTargetFragment();
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}
