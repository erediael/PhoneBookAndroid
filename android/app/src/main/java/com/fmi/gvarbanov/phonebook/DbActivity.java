package com.fmi.gvarbanov.phonebook;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fmi.gvarbanov.phonebook.Utils.CommonUtils;
import com.fmi.gvarbanov.phonebook.events.OnFailure;
import com.fmi.gvarbanov.phonebook.events.OnSuccess;
import com.fmi.gvarbanov.phonebook.models.PhoneContact;

import java.util.ArrayList;
import java.util.List;

public class DbActivity extends AppCompatActivity {
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_CONTACT_NAME = "ContactName";
    private static final String COLUMN_PHONE_NUMBER = "PhoneNumber";
    private static final String COLUMN_ADDITIONAL_INFORMATION = "AdditionalInfo";
    private static final String COLUMN_CATEGORY = "Category";

    public List<PhoneContact> findAll() {
        String command = "SELECT * FROM PhoneBook";
        return getPhoneContacts(command,
                () -> Toast.makeText(this, "Successfully fetched all contacts", Toast.LENGTH_SHORT).show(),
                exception -> Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show(),
                null);
    }

    private ArrayList<PhoneContact> getPhoneContacts(String command, OnSuccess onSuccess, OnFailure onFailure, String[] args) {
        try (SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath() + "/phonebook", null)) {
            Cursor resultSet = database.rawQuery(command, args);
            resultSet.moveToFirst();

            ArrayList<PhoneContact> result = new ArrayList<>();

            while(!resultSet.isAfterLast()){
                PhoneContact newContact = new PhoneContact(
                        resultSet.getInt(resultSet.getColumnIndex(COLUMN_ID)),
                        resultSet.getString(resultSet.getColumnIndex(COLUMN_CONTACT_NAME)),
                        resultSet.getString(resultSet.getColumnIndex(COLUMN_PHONE_NUMBER)),
                        resultSet.getString(resultSet.getColumnIndex(COLUMN_ADDITIONAL_INFORMATION)),
                        resultSet.getString(resultSet.getColumnIndex(COLUMN_CATEGORY))
                );

                result.add(newContact);
                resultSet.moveToNext();
            }

            if (onSuccess != null) {
                onSuccess.execute();
            }
            return result;
        } catch (Exception ex) {
            if (onFailure != null) {
                onFailure.execute(ex);
            }
            return new ArrayList<>();
        }
    }

    public void InitializeDataBaseAndTables() {
        String command = "CREATE TABLE IF NOT EXISTS PhoneBook(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ContactName TEXT NOT NULL, " +
                "PhoneNumber TEXT NOT NULL, " +
                "AdditionalInfo TEXT NOT NULL, " +
                "Category TEXT NOT NULL, " +
                "unique(ContactName, PhoneNumber)" +
                ");";
        ExecuteNonQuery(command, null,
                exception -> Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public boolean updateContact(PhoneContact phoneContact) {
        if (CommonUtils.isNullOrWhiteSpace(phoneContact.getContactName()) || phoneContact.getContactName().length() < 3 || phoneContact.getContactName().length() > 30) {
            Toast.makeText(this, "Contact name must be between 3 and 30 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phoneContact.getPhoneNumber().length() != 10) {
            Toast.makeText(this, "Phone number must be exactly 10 numbers", Toast.LENGTH_SHORT).show();
            return false;
        }

        String command = "UPDATE PhoneBook SET ContactName = ?, PhoneNumber = ?, AdditionalInfo = ?, Category = ? WHERE Id = ?";
        Object[] args = new String[]{ phoneContact.getContactName(), phoneContact.getPhoneNumber(),
                phoneContact.getAdditionalInfo(), phoneContact.getCategory(),
                String.valueOf(phoneContact.getId()) };

        return ExecuteNonQuery(command,
                () -> Toast.makeText(this, "Successfully edited contact", Toast.LENGTH_SHORT).show(),
                exception -> Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show(),
                args);
    }

    public boolean deleteById(int Id) {
        String command = "DELETE FROM PhoneBook WHERE Id=?";

        Object[] args = new String[]{ String.valueOf(Id) };

        return ExecuteNonQuery(command,
                () -> Toast.makeText(this, "Successfully deleted contact", Toast.LENGTH_SHORT).show(),
                exception -> Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show(),
                args);
    }

    public void InsertContact(String contactName, String phoneNumber, String additionalInfo, String category) {
        if (CommonUtils.isNullOrWhiteSpace(contactName) || contactName.length() < 3 || contactName.length() > 30) {
            Toast.makeText(this, "Contact name must be between 3 and 30 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phoneNumber.length() != 10) {
            Toast.makeText(this, "Phone number must be exactly 10 numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        String command = "INSERT INTO PhoneBook(ContactName, PhoneNumber, AdditionalInfo, Category) values(?, ?, ?, ?);";

        ExecuteNonQuery(command,
                () -> Toast.makeText(this, "Successfully added contact", Toast.LENGTH_SHORT).show(),
                exception -> Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show(),
                contactName, phoneNumber, additionalInfo, category);
    }

    private boolean ExecuteNonQuery(String sqlCommand, OnSuccess onSuccess, OnFailure onFailure, Object... args) {
        try (SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath() + "/phonebook", null)) {
            if (args.length == 0) {
                database.execSQL(sqlCommand);
            } else {
                database.execSQL(sqlCommand, args);
            }

            if (onSuccess != null) {
                onSuccess.execute();
            }
            return true;
        } catch (Exception ex) {
            if (onFailure != null) {
                onFailure.execute(ex);
            }
            return false;
        }
    }
}
