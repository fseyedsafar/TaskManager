package com.example.task.controller;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import com.example.task.R;
import com.example.task.model.Task;
import com.example.task.repository.TaskRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskListFragment extends Fragment {

    public static final String ARG_CURRENT_PAGE_TASK_LIST = "argCurrentPageTaskList";
    public static final String TAG_EDIT_TASK_FRAGMENT = "tagEditTaskFragment";
    public static final int REQUEST_CODE_TASK_LIST_ADD = 1;
//    public static final int REQUEST_CODE = 1;
    public static final String FRAGMENT_ADD_TASK = "fragmentAddTask";
    public static final int REQUEST_CODE_TASK_LIST_EDIT = 2;
    public static final String ARG_USER_ID_TASK_LIST = "argUserIDTaskList";
    private RecyclerView mRecyclerView;
    private TextView mDateTimeTextView;
    private ImageView mTaskImage;
    private FloatingActionButton mAddFlotingButton;
    private TaskListFragment.TaskAdapter mTaskAdapter;
    static List<Task> mTask;
    private int currentPage;
    private UUID mUserID;

    public static TaskListFragment newInstance(int currentPage, UUID userID) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CURRENT_PAGE_TASK_LIST, currentPage);
        args.putSerializable(ARG_USER_ID_TASK_LIST, userID);

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
        mUserID = (UUID) getArguments().getSerializable(ARG_USER_ID_TASK_LIST);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        initUI(view);

        initRecyclerView();

        initListener();

        return view;
    }

    private void initRecyclerView() {
        mTask = TaskRepository.getInstance(getActivity()).getTaskListFromDB(TaskRepository.getInstance(getActivity()).getState(currentPage, mUserID), mUserID);
        mTaskAdapter = new TaskAdapter(mTask);

        if (mTaskAdapter.getItemCount() > 0)
            mTaskImage.setVisibility(View.GONE);

        mRecyclerView.setAdapter(mTaskAdapter);
    }

    private void initListener() {
        mAddFlotingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTaskFragment addTaskFragment = AddTaskFragment.newInstance(currentPage, mUserID);
                addTaskFragment.setTargetFragment(TaskListFragment.this, REQUEST_CODE_TASK_LIST_ADD);
                addTaskFragment.show(getFragmentManager(), FRAGMENT_ADD_TASK);
            }
        });
    }

    private void initUI(View view) {
        mRecyclerView = view.findViewById(R.id.task_recycler_view_fragment);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAddFlotingButton = view.findViewById(R.id.add_floating_button);
        mTaskImage = view.findViewById(R.id.task_image);
    }

    private class TaskHolder extends RecyclerView.ViewHolder{
        private TextView mTitleTextView;
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

            SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm");
            String timeString = simpleTimeFormat.format(mTask.getmTime());

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

        public void setmTask(List<Task> mTask) {
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
        mTask = TaskRepository.getInstance(getActivity()).getTaskListFromDB(TaskRepository.getInstance(getActivity()).getState(currentPage, mUserID), mUserID);
        mTaskAdapter.setmTask(mTask);
        mTaskAdapter.notifyDataSetChanged();
        ((TaskPagerFragment)getTargetFragment()).notifyAdapter();
        if (mTaskAdapter.getItemCount() > 0){
            mTaskImage.setVisibility(View.GONE);
        }
        if (mTaskAdapter.getItemCount() == 0){
            mTaskImage.setVisibility(View.VISIBLE);
        }
    }

    public void updateSearch(List<Task> searchList){
        List<Task> searchListForAdapter = searchList;
        mTaskAdapter.setmTask(searchListForAdapter);
        mTaskAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

        //notifyAdapter();
        mTaskAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.log_in_menu, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_task).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                List<Task> searchList = TaskRepository.getInstance(getActivity()).getAllTask(new String[]{mUserID.toString()});
                List<Task> newArray = new ArrayList<>();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                for (Task task : searchList) {
                    if (task.getmTitle().contains(s) || task.getmDescription().contains(s) || simpleDateFormat.format(task.getmDate()).contains(s) || task.getmTime().toString().contains(s)){
                        newArray.add(task);
                    }
                }
                mTaskImage.setVisibility(View.GONE);
                updateSearch(newArray);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                notifyAdapter();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_log_in: {
                getActivity().finish();
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
