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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task.R;
import com.example.task.model.Person;
import com.example.task.repository.PersonRepository;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends DialogFragment {

    public static final String ARG_SIGN_UP_FRAGMENT_USER = "argSignUpFragmentUser";
    public static final String ARG_SIGN_UP_FRAGMENT_PASS = "argSignUpFragmentPass";
    public static final String EXTRA_SIGN_UP_FRAGMENT_USER = "extraSignUpFragmentUser";
    public static final String EXTRA_SIGN_UP_FRAGMENT_PASS = "extraSignUpFragmentPass";
    private EditText mUserEditText;
    private EditText mPassEditText;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance(String user, String pass) {
        
        Bundle args = new Bundle();

        args.putSerializable(ARG_SIGN_UP_FRAGMENT_USER, user);
        args.putSerializable(ARG_SIGN_UP_FRAGMENT_PASS, pass);
        
        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.fragment_sign_up, null, false);

        initUI(view);

        mUserEditText.setText((String) getArguments().getSerializable(ARG_SIGN_UP_FRAGMENT_USER));
        mPassEditText.setText((String) getArguments().getSerializable(ARG_SIGN_UP_FRAGMENT_PASS));

        return new AlertDialog.Builder(getActivity())
                .setPositiveButton(R.string.Save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!(mUserEditText.getText().toString().equals("")) && !(mPassEditText.getText().toString().equals(""))) {
                            PersonRepository.getInstance().insert(new Person(mUserEditText.getText().toString(), mPassEditText.getText().toString()));
                        }

                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_SIGN_UP_FRAGMENT_USER, mUserEditText.getText().toString());
                        intent.putExtra(EXTRA_SIGN_UP_FRAGMENT_PASS, mPassEditText.getText().toString());

                        Fragment fragment = getTargetFragment();
                        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setView(view)
                .create();
    }

    private void initUI(View view) {
        mUserEditText = view.findViewById(R.id.textUser_signUp_fragment);
        mPassEditText = view.findViewById(R.id.textPass_signUp_fragment);
    }
}
