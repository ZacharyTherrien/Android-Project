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

import com.example.project.R;
import com.example.project.ui.TaskApplication;

public class RegisterDialogFragment extends DialogFragment {

    private Button createButton;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText passwordCheckEditText;
    private Button cancelButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);

        usernameEditText = root.findViewById(R.id.username_EditText);
        passwordEditText = root.findViewById(R.id.password_EditText);
        passwordCheckEditText = root.findViewById(R.id.passwordCheck_EditText);

        createButton = root.findViewById(R.id.create_Button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

        cancelButton = root.findViewById(R.id.cancel_Button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        return root;
    }

    private void createAccount() {
        TaskApplication taskApplication = (TaskApplication) getActivity().getApplication();
        LoginManager loginManager = taskApplication.getLoginManager();
        loginManager.register(usernameEditText.getText().toString(),
                              passwordEditText.getText().toString(),
                              passwordCheckEditText.getText().toString());
        dismiss();
    }

    private void cancel() {
        LoginDialogFragment dialogFragment = new LoginDialogFragment();
        dialogFragment.setCancelable(false);
        dialogFragment.show(getFragmentManager(), "login");
        dismiss();
    }
}
