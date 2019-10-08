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
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditTaskFragment extends DialogFragment {


    public static final String TAG_DATE_PICKER_FRAGMENT = "TagDatePickerFragment";
    public static final int REQUEST_CODE_DATE_PICKER = 0;
    public static final int REQUEST_CODE_TIME_PICKER = 1;
    public static final String ARG_CURRENT_PAGE_EDIT_TEXT = "argCurrentPageEditText";
    public static final String ARG_ID_EDIT_TASK = "argIdEditTask";
    public static final String BOUNDLE_EDIT_TASK_FRAGMENT_DATE = "boundleEditTaskFragmentDate";
    public static final String TAG_TIME_PICKER_FRAGMENT = "tagTimePickerFragment";
    private EditText mTitle;
    private EditText mDescription;
    private Button mDateButton;
    private Button mTimeButton;
    private RadioGroup mRadioGroup;
    private RadioButton getmRadioButtonTask;
    private RadioButton mRadioButtonToDo;
    private RadioButton mRadioButtonDoing;
    private RadioButton mRadioButtonDone;
    private UUID id;
    private int currentPage;
    private Task mTask;
    private Date mDate;
    private Date mTime;
    private String mTemp = "";
    private String mStateRadioButton;


    public EditTaskFragment() {
        // Required empty public constructor
    }

    public static EditTaskFragment newInstance(int currentPage, UUID id) {

        Bundle args = new Bundle();

        args.putSerializable(ARG_CURRENT_PAGE_EDIT_TEXT, currentPage);
        args.putSerializable(ARG_ID_EDIT_TASK, id);

        EditTaskFragment fragment = new EditTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initValue(savedInstanceState);
    }

    private void initValue(@Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null){
            mTemp = (String) savedInstanceState.get(BOUNDLE_EDIT_TASK_FRAGMENT_DATE);
        }

        currentPage = (int) getArguments().getSerializable(ARG_CURRENT_PAGE_EDIT_TEXT);
        id = (UUID) getArguments().getSerializable(ARG_ID_EDIT_TASK);
        mTask = TaskRepository.getInstance(getActivity()).getTask(id, currentPage);
        mDate = mTask.getmDate();
        mTime = mTask.getmTime();
        mStateRadioButton = mTask.getmStateRadioButton();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.fragment_detail_task, null , false);

        initUI(view);

        setUI();

        initListener();

        return new AlertDialog.Builder(getActivity())
                .setNegativeButton(R.string.Edit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        initUpdateTask();
                        ((TaskListFragment) getTargetFragment()).notifyAdapter();
                    }
                })
                .setPositiveButton(android.R.string.cancel, null)
                .setNeutralButton(R.string.Delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TaskRepository.getInstance(getActivity()).delete(mTask, currentPage, mTask.getmID());
                        ((TaskListFragment) getTargetFragment()).notifyAdapter();
                    }
                })
                .setView(view)
                .create();
    }

    private void initListener() {
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mTask.getmDate());
                datePickerFragment.setTargetFragment(EditTaskFragment.this, REQUEST_CODE_DATE_PICKER);
                datePickerFragment.show(getFragmentManager(), TAG_DATE_PICKER_FRAGMENT);
            }
        });

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mTask.getmTime());
                timePickerFragment.setTargetFragment(EditTaskFragment.this, REQUEST_CODE_TIME_PICKER);
                timePickerFragment.show(getFragmentManager(), TAG_TIME_PICKER_FRAGMENT);
            }
        });
    }

    private void initUpdateTask() {
        mTask.setmTitle(mTitle.getText().toString());
        mTask.setmDescription(mDescription.getText().toString());
        mTask.setmDate(mDate);
        mTask.setmTime(mTime);
        mTask.setmStateRadioButton(getRadioButtonChecked());
        mTask.setmStateViewPager(TaskRepository.getInstance(getActivity()).setState(currentPage));
        TaskRepository.getInstance(getActivity()).update(mTask, currentPage, mTask.getmID());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(BOUNDLE_EDIT_TASK_FRAGMENT_DATE, mDateButton.getText().toString());
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
    }

    private void setUI(){
        mTitle.setText(mTask.getmTitle());
        mDescription.setText(mTask.getmDescription());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = simpleDateFormat.format(mTask.getmDate());
        mDateButton.setText(dateString);

        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm");
        String timeString = simpleTimeFormat.format(mTask.getmTime());
        mTimeButton.setText(timeString);

        switch (mStateRadioButton){
            case "ToDo":{
                mRadioButtonToDo.setChecked(true);
                break;
            }
            case "Doing":{
                mRadioButtonDoing.setChecked(true);
                break;
            }
            case "Done":{
                mRadioButtonDone.setChecked(true);
                break;
            }
            default:
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

            mDate = date;
            mTask.setmDate(mDate);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = simpleDateFormat.format(date);
            mDateButton.setText(dateString);
        }
        if (requestCode == REQUEST_CODE_TIME_PICKER){
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TASK_TIME);

                mTime = date;
                mTask.setmTime(mTime);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
                String dateString = simpleDateFormat.format(mTime);
                mTimeButton.setText(dateString);
        }
    }
}
