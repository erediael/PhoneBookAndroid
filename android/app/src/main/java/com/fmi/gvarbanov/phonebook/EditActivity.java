package com.fmi.gvarbanov.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.fmi.gvarbanov.phonebook.internal.Constants;
import com.fmi.gvarbanov.phonebook.models.PhoneContact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EditActivity extends DbActivity {
    protected EditText contactName, phoneNumber, additionalInfo;
    protected Spinner category;
    protected Button editContactBtn;
    private PhoneContact phoneContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        phoneContact = new PhoneContact(
                getIntent().getExtras().getInt(Constants.ContactId),
                getIntent().getExtras().getString(Constants.ContactName),
                getIntent().getExtras().getString(Constants.PhoneNumber),
                getIntent().getExtras().getString(Constants.AdditionalInfo),
                getIntent().getExtras().getString(Constants.Category)
        );

        setViewVariables();
    }

    private void setViewVariables() {
        contactName = findViewById(R.id.contactNameEt);
        contactName.setText(phoneContact.getContactName());
        phoneNumber = findViewById(R.id.phoneNumberEt);
        phoneNumber.setText(phoneContact.getPhoneNumber());

        additionalInfo = findViewById(R.id.additionalInfoEt);
        additionalInfo.setText(phoneContact.getAdditionalInfo());

        category = findViewById(R.id.categoryS);
        category.setAdapter(getAdapter());
        editContactBtn = findViewById(R.id.editContactBtn);
    }

    private ArrayAdapter<String> getAdapter() {
        Set<String> arraySpinnerItems = new LinkedHashSet<>();
        arraySpinnerItems.add(phoneContact.getCategory());
        arraySpinnerItems.add("");
        arraySpinnerItems.add("Favourites");
        arraySpinnerItems.add("Family");
        arraySpinnerItems.add("Friends");
        arraySpinnerItems.add("Colleagues");
        arraySpinnerItems.add("Other");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new ArrayList<>(arraySpinnerItems));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }

    public void editContact(View view) {
        PhoneContact newPhoneContact = new PhoneContact(
                phoneContact.getId(),
                contactName.getText().toString(),
                phoneNumber.getText().toString(),
                additionalInfo.getText().toString(),
                category.getSelectedItem().toString()
        );

        if (updateContact(newPhoneContact)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}