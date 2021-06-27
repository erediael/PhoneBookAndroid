package com.fmi.gvarbanov.phonebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fmi.gvarbanov.phonebook.internal.Constants;
import com.fmi.gvarbanov.phonebook.models.PhoneContact;

public class ViewActivity extends DbActivity {
    protected TextView viewContactName, viewPhoneNumber, viewAdditionalInfo, viewCategory;
    private PhoneContact phoneContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        setViewVariables();

        phoneContact = new PhoneContact(
                getIntent().getExtras().getInt(Constants.ContactId),
                getIntent().getExtras().getString(Constants.ContactName),
                getIntent().getExtras().getString(Constants.PhoneNumber),
                getIntent().getExtras().getString(Constants.AdditionalInfo),
                getIntent().getExtras().getString(Constants.Category)
        );

        viewContactName.setText(phoneContact.getContactName());
        viewPhoneNumber.setText(phoneContact.getPhoneNumber());
        viewAdditionalInfo.setText(phoneContact.getAdditionalInfo());
        viewCategory.setText(phoneContact.getCategory());
    }

    private void setViewVariables() {
        viewContactName = findViewById(R.id.viewContactName);
        viewPhoneNumber = findViewById(R.id.viewPhoneNumber);
        viewAdditionalInfo = findViewById(R.id.viewAdditionalInfo);
        viewCategory = findViewById(R.id.viewCategory);
    }

    public void editContact(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(Constants.ContactId, phoneContact.getId());
        intent.putExtra(Constants.ContactName, phoneContact.getContactName());
        intent.putExtra(Constants.PhoneNumber, phoneContact.getPhoneNumber());
        intent.putExtra(Constants.AdditionalInfo, phoneContact.getAdditionalInfo());
        intent.putExtra(Constants.Category, phoneContact.getCategory());
        startActivity(intent);
    }

    public void deleteContact(View view) {
        if (deleteById(phoneContact.getId())) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}