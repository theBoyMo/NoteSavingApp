package com.example.demo.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.R;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

public class VideoNoteFragment extends NoteFragment implements
        View.OnClickListener, View.OnLongClickListener{


    public interface NoteFragmentContract {
        // TODO - launch VideoListActivity
        //      - save video note to realm

        void selectVideo();
        void playVideo();
        void saveVideoNote();
    }

    private NoteFragmentContract mActivity;
    private TextView mTitle;
    private ImageView mThumbnail;

    public VideoNoteFragment() {}

    public static VideoNoteFragment newInstance() {
        return new VideoNoteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_note, container, false);

        mTitle = (EditText) view.findViewById(R.id.text_note_title);
        mThumbnail = (ImageView) view.findViewById(R.id.video_note_thumbnail);
        mThumbnail.setOnClickListener(this);
        mThumbnail.setOnLongClickListener(this);
        Button saveButton = (Button) view.findViewById(R.id.save_note);
        saveButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        // TODO fetch video from local store, save note to realm
        switch (view.getId()) {
            case R.id.video_note_thumbnail:
                mActivity.playVideo();
                break;
            case R.id.save_note:
                mActivity.saveVideoNote(); // save note to realm
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        // allow the user to select/change the video selection
        // use start activityForResult so as to get back the video/thumbnail path/uri & mimeType
        if (v.getId() == R.id.video_note_thumbnail) {
            mActivity.selectVideo();
        }
        return false;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            mActivity = (NoteFragmentContract) activity;
        } catch (ClassCastException e) {
            Timber.e("%s does not implement contract interface, error: %s", activity.getClass().getSimpleName(), e.getMessage());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    public void updateImageView(String thumbnailPath) {
        // update the ImageView with the selected video's thumbnail
        Picasso.with(getActivity())
                .load(thumbnailPath)
                .fit().centerCrop()
                .placeholder(R.drawable.action_video)
                .into(mThumbnail);
    }


}
