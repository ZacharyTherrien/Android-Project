package com.example.project.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.assignment_1_note_project.R;
import com.example.notes.NoteApplication;

public class LoginDialogFragment extends DialogFragment {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = root.findViewById(R.id.login_Button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        registerButton = root.findViewById(R.id.register_Button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        usernameEditText = root.findViewById(R.id.username_EditText);
        passwordEditText = root.findViewById(R.id.password_EditText);

        return root;
    }

    private void register() {

        RegisterDialogFragment registerDialogFragment = new RegisterDialogFragment();
        registerDialogFragment.setCancelable(false);
        registerDialogFragment.show(getFragmentManager(), "register");

        dismiss();
    }

    private void login() {
        NoteApplication noteApplication = (NoteApplication) getActivity().getApplication();
        LoginManager loginManager = noteApplication.getLoginManager();
        loginManager.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
    }
}
