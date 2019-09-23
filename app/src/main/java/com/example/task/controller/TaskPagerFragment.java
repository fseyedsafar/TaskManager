package com.example.task.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.example.task.R;
import com.example.task.model.PersonAndTask;
import com.example.task.repository.PersonAndTaskRepository;
import com.example.task.repository.TaskRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskPagerFragment extends Fragment {

    public static final String TAG_ADD_TASK_FRAGMENT = "tagAddTaskFragment";
    public static final String ARG_TASK_PAGER_FRAGMENT_ID = "argTaskPagerFragmentId";
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton mAddFlotingButton;
    private RecyclerView mTaskRecyclerView;
    private UUID mIDArguman;
    private List<PersonAndTask> mLoginAndTask;
    private List mLoginAndTaskRemove = new ArrayList();

    public TaskPagerFragment() {
        // Required empty public constructor
    }

    public static TaskPagerFragment newInstance(UUID id) {

        Bundle args = new Bundle();

        args.putSerializable(ARG_TASK_PAGER_FRAGMENT_ID, id);

        TaskPagerFragment fragment = new TaskPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIDArguman = (UUID) getArguments().getSerializable(ARG_TASK_PAGER_FRAGMENT_ID);
        mLoginAndTask = PersonAndTaskRepository.getInstance().getmPersonAndTask();

        for (int i = 0; i < mLoginAndTask.size() ; i++) {
            if (mLoginAndTask.get(i).getmID().equals(mIDArguman)) {
                TaskRepository.getInstance().setmTaskToDo(mLoginAndTask.get(i).getmTaskToDo());
                TaskRepository.getInstance().setmTaskDoing(mLoginAndTask.get(i).getmTaskDoing());
                TaskRepository.getInstance().setmTaskDone(mLoginAndTask.get(i).getmTaskDone());
            }
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_pager, container, false);

        initUI(view);

        mAddFlotingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTaskFragment addTaskFragment = AddTaskFragment.newInstance(mViewPager.getCurrentItem());
                addTaskFragment.show(getFragmentManager(), TAG_ADD_TASK_FRAGMENT);
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return TaskListFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                String state = "";
                switch (position) {
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
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.log_in_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_log_in: {
                PersonAndTaskRepository.getInstance().insert(new PersonAndTask(mIDArguman, TaskRepository.getInstance().getmTaskToDo(), TaskRepository.getInstance().getmTaskDoing(), TaskRepository.getInstance().getmTaskDone()));

                TaskRepository.getInstance().setmTaskToDo(mLoginAndTaskRemove);
                TaskRepository.getInstance().setmTaskDoing(mLoginAndTaskRemove);
                TaskRepository.getInstance().setmTaskDone(mLoginAndTaskRemove);

                getActivity().finish();
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private void initUI(View view) {
        mViewPager = view.findViewById(R.id.task_pager_fragment);
        mTabLayout = view.findViewById(R.id.tab_layout);
        mAddFlotingButton = view.findViewById(R.id.add_floating_button);
        mTaskRecyclerView = view.findViewById(R.id.task_recycler_view_fragment);
    }
}
