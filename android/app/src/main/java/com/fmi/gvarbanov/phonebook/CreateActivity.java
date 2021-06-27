package com.fmi.gvarbanov.phonebook;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateActivity extends DbActivity {
    protected EditText contactName, phoneNumber, additionalInfo;
    protected Spinner category;
    protected Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        setViewVariables();
    }

    public void saveContact(View view) {
        InsertContact(
            contactName.getText().toString(),
            phoneNumber.getText().toString(),
            additionalInfo.getText().toString(),
            category.getSelectedItem().toString());
    }

    private void setViewVariables() {
        contactName = findViewById(R.id.contactName);
        phoneNumber = findViewById(R.id.phoneNumber);
        additionalInfo = findViewById(R.id.additionalInfo);
        category = findViewById(R.id.category);
        category.setAdapter(getAdapter());
        saveButton = findViewById(R.id.saveButton);
    }

    private ArrayAdapter<String> getAdapter() {
        String[] arraySpinner = new String[] {
                "", "Favourites", "Family", "Friends", "Colleagues", "Other"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                arraySpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }
}