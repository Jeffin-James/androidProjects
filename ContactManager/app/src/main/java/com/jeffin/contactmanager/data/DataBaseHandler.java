package com.jeffin.contactmanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.jeffin.contactmanager.R;
import com.jeffin.contactmanager.model.Contact;
import com.jeffin.contactmanager.util.ContactManager;

import java.util.StringTokenizer;

public class DataBaseHandler extends SQLiteOpenHelper {
    public DataBaseHandler(Context context) {
        super(context, ContactManager.NAME, null, ContactManager.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE "+ContactManager.TABLE_NAME+"("+ContactManager.ID+"INTEGER PRIMARY KEY,"+ContactManager.NAME+"TEXT,"+ContactManager.PHONE_NUMBER+"TEXT"+")";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE = String.valueOf(R.string.drop_table);
        sqLiteDatabase.execSQL(DROP_TABLE,new String[]{ContactManager.DATABASE_NAME});//to drop the table

        onCreate(sqLiteDatabase);
    }
    public void addContact(Contact contact){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContactManager.NAME,contact.getName());
        values.put(ContactManager.PHONE_NUMBER,contact.getPhoneNumber());
        database.insert(ContactManager.TABLE_NAME,null,values);
        database.close();
    }
    public Contact getContact(int id){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(ContactManager.TABLE_NAME,new String[]{ContactManager.ID,ContactManager.NAME,ContactManager.PHONE_NUMBER},
                ContactManager.ID+"=?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        Contact contacts = new Contact();
        contacts.setId(Integer.parseInt(cursor.getString(0)));
        contacts.setName(cursor.getString(1));
        contacts.setPhoneNumber(cursor.getString(2));
        return contacts;
    }
}
