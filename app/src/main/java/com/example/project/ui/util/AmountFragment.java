package com.example.project.ui.util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.project.R;

public class AmountFragment extends DialogFragment {

    EditText amountEditText;
    Spinner amountsSpinner;
    Button enterButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_amount, container, false);

        amountEditText = root.findViewById(R.id.amount_EditText);
        amountsSpinner = root.findViewById(R.id.amounts_Spinner);
        enterButton = root.findViewById(R.id.enter_Button);

        //Create an ArrayAdapter using the string array and a default spinner layout from the spinner.
        ArrayAdapter<CharSequence> sortsAdapter = ArrayAdapter.createFromResource(getContext(), R.array.amounts,
                android.R.layout.simple_spinner_item);
        //Specify the layout to use when the list of choices appears.
        sortsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the spinner.
        amountsSpinner.setAdapter(sortsAdapter);

        //Set the enter button's event listener.
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendTime();
            }
        });

        return root;
    }

    public int SendTime(){

        return 0;
    }

}
