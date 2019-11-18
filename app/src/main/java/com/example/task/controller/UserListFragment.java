package com.example.task.controller;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.task.R;
import com.example.task.model.User;
import com.example.task.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserListFragment extends DialogFragment {

    public static final String TAG_USER_LIST_FRAGMENT = "tagUserListFragment";
    public static final int REQUEST_CODE_USER_LIST_FRAGMENT = 6;
    private RecyclerView mRecyclerView;
    private UserAdapter mUserAdapter;
    private List<User> mUserList;

    public UserListFragment() {
        // Required empty public constructor
    }

    public static UserListFragment newInstance() {

        Bundle args = new Bundle();

        UserListFragment fragment = new UserListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.fragment_user_list, null, false);

        initRecycler(view);

        return new AlertDialog.Builder(getActivity())
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((TaskListFragment)getTargetFragment()).notifyAdapter();
                    }
                })
                .setView(view)
                .create();
    }

    private void initRecycler(View view) {
        mRecyclerView = view.findViewById(R.id.user_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUserList = UserRepository.getInstance(getActivity()).getUserList();
        mUserAdapter = new UserAdapter(mUserList);
        mRecyclerView.setAdapter(mUserAdapter);
    }

    private class UserHolder extends RecyclerView.ViewHolder{

        private TextView mID;
        private TextView mUser;
        private TextView mPass;
        private TextView mDate;
        private TextView mTaskCount;
        private Button mDelete;
        private User mThisUser;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
                mID = itemView.findViewById(R.id.text_view_user_id);
                mUser = itemView.findViewById(R.id.text_view_user_user);
                mPass = itemView.findViewById(R.id.text_view_user_pass);
                mDate = itemView.findViewById(R.id.text_view_user_date);
                mTaskCount = itemView.findViewById(R.id.text_view_user_count);
                mDelete = itemView.findViewById(R.id.delete_button);

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DeleteFragment deleteFragment = DeleteFragment.newInstance(mThisUser.getmID());
                    deleteFragment.setTargetFragment(UserListFragment.this, REQUEST_CODE_USER_LIST_FRAGMENT);
                    deleteFragment.show(getFragmentManager(), TAG_USER_LIST_FRAGMENT);
                }
            });
        }

        public void bind(User user){

            this.mThisUser = user;

            mID.setText(user.getmID().toString());
            mUser.setText(user.getmUser());
            mPass.setText(user.getmPass());
            mDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(user.getmDate()));
            String s = String.valueOf(user.getmTaskCount());
            mTaskCount.setText(s);
        }
    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder>{

        private List<User> mUsersList;

        public UserAdapter(List<User> mUsers) {
            this.mUsersList = mUsers;
        }

        public void setmUsersList(List<User> mUsersList) {
            this.mUsersList = mUsersList;
        }

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.user_item, parent, false);
            UserHolder userHolder = new UserHolder(view);
            return userHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, int position) {
            holder.bind(mUserList.get(position));
        }

        @Override
        public int getItemCount() {
            return mUsersList.size();
        }
    }

    public void notifyAdapter(){
        mUserAdapter.setmUsersList(UserRepository.getInstance(getActivity()).getUserList());
        mUserAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

        mUserAdapter.setmUsersList(UserRepository.getInstance(getActivity()).getUserList());
        mUserAdapter.notifyDataSetChanged();
    }
}
