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
import java.util.UUID;

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
    public static final String ARG_USER_ID_ADD_TASK = "argUserIdAddTask";
    private EditText mTitle;
    private EditText mDescription;
    private Button mDateButton;
    private Button mTimeButton;
    private RadioButton getmRadioButtonTask;
    private RadioButton mRadioButtonToDo;
    private RadioButton mRadioButtonDoing;
    private RadioButton mRadioButtonDone;
    private Task mTask = new Task();
    private int currentPage;
    private Date mDate = new Date();
    private Date mTime = new Date();
    private String mTemp = "";
    private RadioGroup mRadioGroup;
    private UUID mUserID;


    public AddTaskFragment() {
        // Required empty public constructor
    }

    public static AddTaskFragment newInstance(int currentPage, UUID userID) {
        
        Bundle args = new Bundle();

        args.putSerializable(ARG_CURRENT_PAGE_ADD_TASK, currentPage);
        args.putSerializable(ARG_USER_ID_ADD_TASK, userID);

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
        mUserID = (UUID) getArguments().getSerializable(ARG_USER_ID_ADD_TASK);
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

        initListener();

        return new AlertDialog.Builder(getActivity())
                .setPositiveButton(R.string.Save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mTask.setmUserID(mUserID);
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

    private void initListener() {
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

            mDate = date;
            mTask.setmDate(mDate);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = simpleDateFormat.format(mDate);
            mDateButton.setText(dateString);
        }

        if (requestCode == REQUEST_CODE_TIME_PICKER && data != null){
            Date time = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TASK_TIME);

                mTime = time;
                mTask.setmTime(mTime);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
                String dateString = simpleDateFormat.format(mTime);
                mTimeButton.setText(dateString);
        }
    }
}
