package com.example.project.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.project.ui.NoteApplication;
import com.example.project.R;

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
        NoteApplication application = (NoteApplication) getActivity().getApplication();
        LoginManager loginManager = application.getLoginManager();
        loginManager.setOnLoginListener(new OnLoginListener() {
            @Override
            public void onLogin(String uuid) {
                dismiss();
            }

            @Override
            public void onLogout() {

            }

            @Override
            public void onRegister(String uuid) {

            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), "Login error: " + message, Toast.LENGTH_LONG).show();
            }
        });
        loginManager.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
    }
}
