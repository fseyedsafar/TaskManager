package com.example.task.controller;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.task.R;
import com.example.task.model.Task;
import com.example.task.repository.TaskRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskListFragment extends Fragment {

    public static final String ARG_CURRENT_PAGE_TASK_LIST = "argCurrentPageTaskList";
    public static final String TAG_EDIT_TASK_FRAGMENT = "tagEditTaskFragment";
    public static final int REQUEST_CODE_TASK_LIST_ADD = 1;
    public static final int REQUEST_CODE = 1;
    public static final String FRAGMENT_ADD_TASK = "fragmentAddTask";
    public static final int REQUEST_CODE_TASK_LIST_EDIT = 2;
    private RecyclerView mRecyclerView;
    private TaskListFragment.TaskAdapter mTaskAdapter;
    private List<Task> mTask;
    private int currentPage;
    private FloatingActionButton mAddFlotingButton;
    private ImageView mTaskImage;


    public static TaskListFragment newInstance(int currentPage) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CURRENT_PAGE_TASK_LIST, currentPage);

        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TaskListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentPage = (int) getArguments().getSerializable(ARG_CURRENT_PAGE_TASK_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        initUI(view);

        mTask = TaskRepository.getInstance().getTaskList(currentPage);

        mTaskAdapter = new TaskAdapter(mTask);

        if (mTaskAdapter.getItemCount() > 0)
            mTaskImage.setVisibility(View.GONE);

        mRecyclerView.setAdapter(mTaskAdapter);

        mAddFlotingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTaskFragment addTaskFragment = AddTaskFragment.newInstance(currentPage);
                addTaskFragment.setTargetFragment(TaskListFragment.this, REQUEST_CODE_TASK_LIST_ADD);
                addTaskFragment.show(getFragmentManager(), FRAGMENT_ADD_TASK);
            }
        });

        return view;
    }

    private void initUI(View view) {
        mRecyclerView = view.findViewById(R.id.task_recycler_view_fragment);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAddFlotingButton = view.findViewById(R.id.add_floating_button);
        mTaskImage = view.findViewById(R.id.task_image);
    }

    private class TaskHolder extends RecyclerView.ViewHolder{
        private TextView mTitleTextView;
        private TextView mDateTimeTextView;
        private TextView mPictureTextView;
        private Task mTask;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.title_text_view);
            mDateTimeTextView = itemView.findViewById(R.id.date_time_text_view);
            mPictureTextView = itemView.findViewById(R.id.text_view_picture);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditTaskFragment editTaskFragment = EditTaskFragment.newInstance(currentPage , mTask.getmID());
                    editTaskFragment.setTargetFragment(TaskListFragment.this, REQUEST_CODE_TASK_LIST_EDIT);
                    editTaskFragment.show(getFragmentManager(), TAG_EDIT_TASK_FRAGMENT);
                }
            });
        }

        public void bind(Task task){

            mTask = task;

            mTitleTextView.setText(mTask.getmTitle());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = simpleDateFormat.format(mTask.getmDate());
//            mDateTimeTextView.setText(dateString);

            SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm");
            String timeString = simpleTimeFormat.format(mTask.getmTime());
//            mDateTimeTextView.setText(dateString);

            mDateTimeTextView.setText(dateString + " " + timeString);

            if (!mTask.getmTitle().equals("")) {
                mPictureTextView.setText(String.valueOf(mTask.getmTitle().charAt(0)));
            }
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder>{
        private List<Task> mTask;

        public TaskAdapter(List<Task> mTask) {
            this.mTask = mTask;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.task_item, parent, false);
            TaskHolder taskHolder = new TaskHolder(view);
            return taskHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            holder.bind(mTask.get(position));
        }

        @Override
        public int getItemCount() {
            return mTask.size();
        }
    }

    public void notifyAdapter(){
        mTaskAdapter.notifyDataSetChanged();
        if (mTaskAdapter.getItemCount() > 0){
            mTaskImage.setVisibility(View.GONE);
        }
        if (mTaskAdapter.getItemCount() == 0){
            mTaskImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mTaskAdapter.notifyDataSetChanged();
    }
}
