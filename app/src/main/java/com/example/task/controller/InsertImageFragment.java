package com.example.task.controller;


import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.task.R;
import com.example.task.model.Task;
import com.example.task.repository.TaskRepository;
import com.example.task.utils.PictureUtils;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InsertImageFragment extends DialogFragment {

    public static final String ARG_TASK_INSERT_IMAGE_FRAGMENT = "argTaskInsertImageFragment";
    public static final int REQUEST_CODE_CAPTURE_IMAGE = 5;
    public static final String AUTHORITY_FILE_PROVIDER = "com.example.task.fileProvider";
    public static final String EXTRA_PHOTO_FILE_INSERT_IMAGE_FRAGMENT = "extraPhotoFileInsertImageFragment";
    private TextView mGalleryTextView;
    private TextView mCameraTextView;
    private Task mTask;
    private File mPhotoFile;
    private Uri mPhotoUri;

    public static InsertImageFragment newInstance(Task task) {

        Bundle args = new Bundle();

        args.putSerializable(ARG_TASK_INSERT_IMAGE_FRAGMENT, (Serializable) task);

        InsertImageFragment fragment = new InsertImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public InsertImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTask = (Task) getArguments().getSerializable(ARG_TASK_INSERT_IMAGE_FRAGMENT);
        mPhotoFile = TaskRepository.getInstance(getActivity()).getPhotoFile(mTask);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.fragment_insert_image, null, false);

        initUI(view);

        initListener();

        return new AlertDialog.Builder(getActivity())
                .setPositiveButton(android.R.string.cancel, null)
                .setView(view)
                .create();
    }

    private void initListener() {
        mGalleryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mCameraTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mPhotoFile == null){
                    return;
                }

                mPhotoUri = FileProvider.getUriForFile(getActivity(), AUTHORITY_FILE_PROVIDER, mPhotoFile);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
//                intent.putExtra(EXTRA_PHOTO_FILE_INSERT_IMAGE_FRAGMENT, mPhotoFile);

                List<ResolveInfo> cameraActivities = getActivity().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : cameraActivities) {
                    getActivity().grantUriPermission(resolveInfo.activityInfo.packageName, mPhotoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(intent, REQUEST_CODE_CAPTURE_IMAGE);
            }
        });
    }

    private void updatePhotoView(){

        if (mPhotoFile == null || !mPhotoFile.exists()){
//            mimage.setImageDrawble(null);
        } else {

            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getAbsolutePath(), getActivity());
        }
    }

    private void initUI(View view) {
        mGalleryTextView = view.findViewById(R.id.editText_gallery);
        mCameraTextView = view.findViewById(R.id.editText_camera);
    }
}
