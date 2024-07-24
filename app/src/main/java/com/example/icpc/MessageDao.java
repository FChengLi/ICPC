package com.example.icpc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {

    private SQLiteDatabase db;
    private Message_DatabaseHelper dbHelper;

    public MessageDao(Context context) {
        dbHelper = new Message_DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void insert(Message message) {
        ContentValues values = new ContentValues();
        values.put(Message_DatabaseHelper.COLUMN_USER_ID, message.getUserId());
        values.put(Message_DatabaseHelper.COLUMN_TYPE, message.getType());
        values.put(Message_DatabaseHelper.COLUMN_MESSAGE_NAME, message.getMessageName());
        values.put(Message_DatabaseHelper.COLUMN_MESSAGE_TEXT, message.getMessageText());
        values.put(Message_DatabaseHelper.COLUMN_TIME, message.getTime());
        db.insert(Message_DatabaseHelper.TABLE_MESSAGES, null, values);
    }

    public List<Message> getMessagesByType(String userId, String type) {
        List<Message> messages = new ArrayList<>();
        Cursor cursor = db.query(Message_DatabaseHelper.TABLE_MESSAGES,
                null,
                Message_DatabaseHelper.COLUMN_USER_ID + "=? AND " + Message_DatabaseHelper.COLUMN_TYPE + "=?",
                new String[]{userId, type},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Message message = new Message(
                        cursor.getString(cursor.getColumnIndexOrThrow(Message_DatabaseHelper.COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Message_DatabaseHelper.COLUMN_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Message_DatabaseHelper.COLUMN_MESSAGE_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Message_DatabaseHelper.COLUMN_MESSAGE_TEXT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Message_DatabaseHelper.COLUMN_TIME))
                );
                message.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Message_DatabaseHelper.COLUMN_ID)));
                messages.add(message);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return messages;
    }
}
