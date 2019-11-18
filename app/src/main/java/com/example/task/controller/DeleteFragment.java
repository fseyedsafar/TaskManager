package com.example.task.controller;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.example.task.R;
import com.example.task.model.Task;
import com.example.task.repository.TaskRepository;
import com.example.task.repository.UserRepository;

import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteFragment extends DialogFragment {

    public static final String ARG_USER_ID_DELETE_FRAGMENT = "argUserIdDeletefragment";
    private UUID mUserID;

    public static DeleteFragment newInstance(UUID mUserID) {

        Bundle args = new Bundle();

        args.putSerializable(ARG_USER_ID_DELETE_FRAGMENT, mUserID);

        DeleteFragment fragment = new DeleteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DeleteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserID = (UUID) getArguments().getSerializable(ARG_USER_ID_DELETE_FRAGMENT);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.fragment_delete, null, false);

        return new AlertDialog.Builder(getActivity())
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        List<Task> allUserTask = TaskRepository.getInstance(getActivity()).getAllUserTask(new String[]{mUserID.toString()});

                        for (Task task : allUserTask) {
                            if (task.getmUserID().equals(mUserID)) {
                                TaskRepository.getInstance(getActivity()).delete(task.getmID());
                            }
                        }

                        UserRepository.getInstance(getActivity()).delete(mUserID);
                        ((UserListFragment)getTargetFragment()).notifyAdapter();
                    }
                })
                .setNegativeButton("No", null)
                .setView(view)
                .create();
    }
}
