package com.example.finalexam_wmp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "EnrollmentApp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_STUDENT = "Student";
    private static final String TABLE_SUBJECT = "Subject";
    private static final String TABLE_ENROLLMENT = "Enrollment";

    private static final String COLUMN_STUDENT_ID = "id";
    private static final String COLUMN_STUDENT_NAME = "name";
    private static final String COLUMN_STUDENT_EMAIL = "email";
    private static final String COLUMN_STUDENT_PASSWORD = "password";

    private static final String COLUMN_SUBJECT_ID = "id";
    private static final String COLUMN_SUBJECT_NAME = "name";
    private static final String COLUMN_SUBJECT_CREDITS = "credits";

    private static final String COLUMN_ENROLLMENT_ID = "id";
    private static final String COLUMN_ENROLLMENT_STUDENT_ID = "student_id";
    private static final String COLUMN_ENROLLMENT_SUBJECT_ID = "subject_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_STUDENT + " (" +
                COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STUDENT_NAME + " TEXT, " +
                COLUMN_STUDENT_EMAIL + " TEXT UNIQUE, " +
                COLUMN_STUDENT_PASSWORD + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_SUBJECT + " (" +
                COLUMN_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SUBJECT_NAME + " TEXT, " +
                COLUMN_SUBJECT_CREDITS + " INTEGER)");

        db.execSQL("CREATE TABLE " + TABLE_ENROLLMENT + " (" +
                COLUMN_ENROLLMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ENROLLMENT_STUDENT_ID + " INTEGER, " +
                COLUMN_ENROLLMENT_SUBJECT_ID + " INTEGER, " +
                "FOREIGN KEY (" + COLUMN_ENROLLMENT_STUDENT_ID + ") REFERENCES " + TABLE_STUDENT + "(" + COLUMN_STUDENT_ID + "), " +
                "FOREIGN KEY (" + COLUMN_ENROLLMENT_SUBJECT_ID + ") REFERENCES " + TABLE_SUBJECT + "(" + COLUMN_SUBJECT_ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENROLLMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);
    }

    public long addStudent(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("password", password);
        return db.insert("Student", null, values);
    }

    public boolean authenticateStudent(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Student WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});
        boolean isAuthenticated = cursor.getCount() > 0;
        cursor.close();
        return isAuthenticated;
    }

}

