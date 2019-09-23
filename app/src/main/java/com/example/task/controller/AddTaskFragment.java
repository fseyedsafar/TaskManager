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
import android.widget.CheckBox;
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
    public static final int REQUEST_CODE_TIME_PICKER = 0;
    public static final String BOUNDLE_ADD_TASK_FRAGMENT_DATE = "boundleAddTaskFragmentDate";
    private EditText mTitle;
    private EditText mDescription;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mState;
    private Button mCancel;
    private Button mSave;
    private Task mTask;
    private int currentPage;
    private Date mDate = new Date();
    private String mTime;
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
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(new Date());
                datePickerFragment.setTargetFragment(AddTaskFragment.this, REQUEST_CODE_DATE_PICKER);
                datePickerFragment.show(getFragmentManager(), TAG_DATE_PICKER_FRAGMENT);
            }
        });

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mTimeButton.getText().toString());
                timePickerFragment.setTargetFragment(AddTaskFragment.this, REQUEST_CODE_TIME_PICKER);
                timePickerFragment.show(getFragmentManager(), TAG_TIME_PICKER_FRAGMENT);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setPositiveButton(R.string.Save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mTask = new Task(mTitle.getText().toString(), mDescription.getText().toString(),mDate , mState.isChecked(),getRadioButtonChecked(),setState(currentPage));
                        TaskRepository.getInstance().insert(mTask,getRadioButtonChecked() ,currentPage);
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
        mState = view.findViewById(R.id.state_checkBox);

        if (mDateButton.getText().toString().equals("Date")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = simpleDateFormat.format(new Date());
            mDateButton.setText(dateString);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null){
            return;
        }
        if (requestCode == REQUEST_CODE_DATE_PICKER){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_TASK_DATE);

            if (data.equals(null)){
                mDate = new Date();
            }
            else {
                mDate = date;
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = simpleDateFormat.format(date);
            mDateButton.setText(dateString);
        }
    }

    private String setState(int currentPage){
        String state = "";
        switch (currentPage){
            case 0: {
                state = "ToDo";
                break;
            }
            case 1: {
                state = "Doing";
                break;
            }
            case 2: {
                state = "Done";
                break;
            }
        }
        return state;
    }
}
