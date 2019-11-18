package com.example.task.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.task.R;

import java.util.UUID;

public class TaskPagerActivity extends AppCompatActivity {

    public static final String EXTRA_USER_ID_TASK_PAGER_ACTIVITY = "extraUserIdTaskPagerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_pager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.container_task_fragment);
        if (fragment == null) {
            fragmentManager.beginTransaction().add(R.id.container_task_fragment, TaskPagerFragment.newInstance((UUID) getIntent().getSerializableExtra(LoginFragment.EXTRA_LOGIN_FRAGMENT_ID))).commit();
        }
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, TaskPagerActivity.class);

//        intent.putExtra(EXTRA_USER_ID_TASK_PAGER_ACTIVITY, mUserID);

        return intent;
    }
}
