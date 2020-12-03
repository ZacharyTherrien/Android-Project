package com.example.project.ui.util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.project.R;

public class AmountFragment extends DialogFragment {

    public interface OnAmountEnteredListener {
        void onAmountEntered(int amount, String unit);
    }

    //Views
    EditText amountEditText;
    Spinner amountsSpinner;
    Button enterButton;

    //Fields
    String unitChoice;

    public void setOnAmountenteredListener(OnAmountEnteredListener onAmountenteredListener) {
        this.onAmountenteredListener = onAmountenteredListener;
    }

    OnAmountEnteredListener onAmountenteredListener;

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

        amountsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unitChoice = getResources().getStringArray(R.array.amounts)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Set the enter button's event listener.
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendTime();
            }
        });

        return root;
    }

    public void SendTime(){
        if(!TryParse(amountEditText.getText().toString())){
            DisplayError("Invalid Value.", "The value entered was not a number or was too much.");
        }
        else{
            int time = Integer.parseInt(amountEditText.getText().toString());
            if(time < 0){
                DisplayError("Invalid number", "The value cannot be negative.");
            }
            else{
                onAmountenteredListener.onAmountEntered(time, unitChoice);
                dismiss();
            }
        }
    }

    private boolean TryParse(String value){
        try{
            Integer.parseInt(value);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }

    private void DisplayError(String title, String msg){
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(msg + " Please try again.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }
}
