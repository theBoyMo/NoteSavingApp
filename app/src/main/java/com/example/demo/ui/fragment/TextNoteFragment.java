package com.example.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.model.Note;

import io.realm.Realm;
import timber.log.Timber;

public class TextNoteFragment extends NoteFragment implements View.OnClickListener{


    private TextView mTitle;
    private TextView mDescription;

    public TextNoteFragment() {}

    public static TextNoteFragment newInstance() {
        return new TextNoteFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_note, container, false);

        mTitle = (EditText) view.findViewById(R.id.text_note_title);
        mDescription = (EditText) view.findViewById(R.id.text_note_description);
        Button saveButton = (Button) view.findViewById(R.id.save_note);
        saveButton.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        final Note textNote = new Note();

        // populate the object
        final Long id = setCustomId();
        textNote.setId(setCustomId());
        final String strTitle = mTitle.getText() != null ? mTitle.getText().toString() : "";
        textNote.setTitle(strTitle);
        final String strDescription = mDescription.getText() != null ? mDescription.getText().toString() : "";
        textNote.setDescription(strDescription);

        // save the object to the realm asynchronously
        mTransaction = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(textNote);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Timber.i("Success! id: %d, title: %s, description: %s", id, strTitle, strDescription);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Timber.e("Error writing object to realm, %s", error.getMessage());
            }
        });

        getActivity().finish();
    }



}