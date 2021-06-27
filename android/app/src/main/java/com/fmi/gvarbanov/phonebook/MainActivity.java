package com.fmi.gvarbanov.phonebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fmi.gvarbanov.phonebook.internal.Constants;
import com.fmi.gvarbanov.phonebook.models.PhoneContact;

import java.util.List;

public class MainActivity extends DbActivity {
    protected ListView contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitializeDataBaseAndTables();
        contacts = findViewById(R.id.contacts);

        List<PhoneContact> contactsFound = findAll();

        ArrayAdapter<PhoneContact> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, contactsFound);

        contacts.setAdapter(arrayAdapter);
        contacts.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, ViewActivity.class);
            PhoneContact phoneContact = arrayAdapter.getItem(position);
            intent.putExtra(Constants.ContactId, phoneContact.getId());
            intent.putExtra(Constants.ContactName, phoneContact.getContactName());
            intent.putExtra(Constants.PhoneNumber, phoneContact.getPhoneNumber());
            intent.putExtra(Constants.AdditionalInfo, phoneContact.getAdditionalInfo());
            intent.putExtra(Constants.Category, phoneContact.getCategory());
            startActivity(intent);
        });
    }

    public void launchCreateActivity(View view) {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }
}