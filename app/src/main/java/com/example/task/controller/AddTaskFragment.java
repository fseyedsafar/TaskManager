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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.task.R;
import com.example.task.model.Task;
import com.example.task.repository.TaskRepository;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTaskFragment extends DialogFragment {

    public static final String TAG_DATE_PICKER_FRAGMENT = "TagDatePickerFragment";
    public static final String TAG_TIME_PICKER_FRAGMENT = "TagTimePickerFragment";
    public static final int REQUEST_CODE_DATE_PICKER = 0;
    public static final String ARG_CURRENT_PAGE_ADD_TASK = "argCurrentPageAddTask";
    public static final int REQUEST_CODE_TIME_PICKER = 1;
    public static final String BOUNDLE_ADD_TASK_FRAGMENT_DATE = "boundleAddTaskFragmentDate";
    private EditText mTitle;
    private EditText mDescription;
    private Button mDateButton;
    private Button mTimeButton;
    private Task mTask = new Task();
    private int currentPage;
    private Date mDate = new Date();
    private Date mTime;
    private String mTemp = "";
    private RadioGroup mRadioGroup;
    private RadioButton getmRadioButtonTask;
    private RadioButton mRadioButtonToDo;
    private RadioButton mRadioButtonDoing;
    private RadioButton mRadioButtonDone;


    public AddTaskFragment() {
        // Required empty public constructor
    }

    public static AddTaskFragment newInstance(int currentPage) {
        
        Bundle args = new Bundle();

        args.putSerializable(ARG_CURRENT_PAGE_ADD_TASK, currentPage);

        AddTaskFragment fragment = new AddTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            mTemp = (String) savedInstanceState.get(BOUNDLE_ADD_TASK_FRAGMENT_DATE);
        }

        currentPage = (int) getArguments().getSerializable(ARG_CURRENT_PAGE_ADD_TASK);

//        if (mTask.getmDate() == null) {
//            mTask.setmDate(new Date());
//        }
//        if (mTask.getmTime() == null) {
//            mTask.setmTime(new Date());
//        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(BOUNDLE_ADD_TASK_FRAGMENT_DATE, mDateButton.getText().toString());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.fragment_detail_task, null, false);

        initUI(view);

        if (!mTemp.equals("")){
            mDateButton.setText(mTemp);
        }

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mTask.getmDate());
                datePickerFragment.setTargetFragment(AddTaskFragment.this, REQUEST_CODE_DATE_PICKER);
                datePickerFragment.show(getFragmentManager(), TAG_DATE_PICKER_FRAGMENT);
            }
        });

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mTask.getmTime());
                timePickerFragment.setTargetFragment(AddTaskFragment.this, REQUEST_CODE_TIME_PICKER);
                timePickerFragment.show(getFragmentManager(), TAG_TIME_PICKER_FRAGMENT);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setPositiveButton(R.string.Save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        mTask = new Task(mTitle.getText().toString(), mDescription.getText().toString(),mDate ,getRadioButtonChecked(),setState(currentPage));
                        mTask.setmUserID(TaskPagerFragment.mUserID);
                        mTask.setmTitle(mTitle.getText().toString());
                        mTask.setmDescription(mDescription.getText().toString());
                        mTask.setmDate(mDate);
                        mTask.setmTime(mTime);
                        mTask.setmStateRadioButton(getRadioButtonChecked());
                        mTask.setmStateViewPager(TaskRepository.getInstance(getActivity()).setState(currentPage));
                        TaskRepository.getInstance(getActivity()).insert(mTask,getRadioButtonChecked() ,currentPage);

                        ((TaskListFragment) getTargetFragment()).notifyAdapter();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setView(view)
                .create();
    }

    private String getRadioButtonChecked(){
        String mStateRadioButton = "";
        int selectedItem = mRadioGroup.getCheckedRadioButtonId();
        getmRadioButtonTask = mRadioGroup.findViewById(selectedItem);

        if (selectedItem != -1) {
            mStateRadioButton = getmRadioButtonTask.getText().toString();
        }

        return mStateRadioButton;
    }

    private void initUI(View view) {
        mTitle = view.findViewById(R.id.title_editText);
        mDescription = view.findViewById(R.id.describtion_editText);
        mDateButton = view.findViewById(R.id.date_button);
        mTimeButton = view.findViewById(R.id.time_button);
        mRadioGroup = view.findViewById(R.id.radio_group);
        mRadioButtonToDo = view.findViewById(R.id.radioButton_ToDo);
        mRadioButtonDoing = view.findViewById(R.id.radioButton_Doing);
        mRadioButtonDone = view.findViewById(R.id.radioButton_Done);

        if (mDateButton.getText().toString().equals("Date")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = simpleDateFormat.format(new Date());
            mDateButton.setText(dateString);
        }
        if (mTimeButton.getText().toString().equals("Time")){
            SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm");
            String timeString = simpleTimeFormat.format(new Date());
            mTimeButton.setText(timeString);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null){
            return;
        }
        if (requestCode == REQUEST_CODE_DATE_PICKER && data != null){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_TASK_DATE);

//            if (data.equals(null)){
//                mDate = new Date();
//            }
//            else {
                mDate = date;
//            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = simpleDateFormat.format(mDate);
            mDateButton.setText(dateString);
        }

        if (requestCode == REQUEST_CODE_TIME_PICKER && data != null){
            Date time = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TASK_TIME);

//            if (data.equals(null)){
//                mTime = new Date();
//            }
//            else {
                mTime = time;

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
                String dateString = simpleDateFormat.format(mTime);
                mTimeButton.setText(dateString);

//                mTimeButton.setText(mTime.toString());
//            }
        }
    }

//    public String setState(int currentPage){
//        String state = "";
//        switch (currentPage){
//            case 0: {
//                state = "ToDo";
//                break;
//            }
//            case 1: {
//                state = "Doing";
//                break;
//            }
//            case 2: {
//                state = "Done";
//                break;
//            }
//        }
//        return state;
//    }
}
