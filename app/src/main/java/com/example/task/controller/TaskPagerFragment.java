package com.example.task.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.example.task.R;
import com.google.android.material.tabs.TabLayout;
import java.util.UUID;

public class TaskPagerFragment extends Fragment {

    public static final String ARG_TASK_PAGER_FRAGMENT_ID = "argTaskPagerFragmentId";
    public static final int REQUEST_CODE_TARGET_TASK_PAGER = 4;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private RecyclerView mTaskRecyclerView;
    private UUID mUserID;
    private int mCurrentPage;
    private FragmentPagerAdapter adapter;

    public static TaskPagerFragment newInstance(UUID userID) {

        Bundle args = new Bundle();

        args.putSerializable(ARG_TASK_PAGER_FRAGMENT_ID, userID);

        TaskPagerFragment fragment = new TaskPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserID = (UUID) getArguments().getSerializable(ARG_TASK_PAGER_FRAGMENT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_pager, container, false);

        initUI(view);

        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.setAdapter(adapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {

            @Override
            public int getItemPosition(@NonNull Object object) {
                return POSITION_NONE;
            }

            @Override
            public Fragment getItem(int position) {
                mCurrentPage = position;
                TaskListFragment taskListFragment = TaskListFragment.newInstance(position, mUserID);
                taskListFragment.setTargetFragment(TaskPagerFragment.this, REQUEST_CODE_TARGET_TASK_PAGER);
                return taskListFragment;
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

    private void initUI(View view) {
        mViewPager = view.findViewById(R.id.task_pager_fragment);
        mTabLayout = view.findViewById(R.id.tab_layout);
        mTaskRecyclerView = view.findViewById(R.id.task_recycler_view_fragment);
    }

    public void notifyAdapter(){
       if (adapter != null)
        adapter.notifyDataSetChanged();
    }
}
