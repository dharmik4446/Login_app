package com.example.loginapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, "Userdata", null, 1) {

    companion object {
        const val TABLE_NAME = "Userdatalist"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_MAIL = "mail"
        const val COLUMN_PASSWORD = "password"
//        const val COLUMN_HOBBY="hobby"
//        const val COLUMN_GENDER="genderstore"
        const val COLUMN_CITY="selectedCity"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME ($COLUMN_USERNAME TEXT , $COLUMN_MAIL TEXT PRIMARY KEY UNIQUE, $COLUMN_PASSWORD TEXT,$COLUMN_CITY TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun saveuserdata(
        userName: String, emailValidator: String, enterdpassword: String
    ): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(COLUMN_USERNAME, userName)
        cv.put(COLUMN_MAIL, emailValidator)
        cv.put(COLUMN_PASSWORD, enterdpassword)
//        cv.put(COLUMN_CITY, selectedCity)
//        cv.put(COLUMN_HOBBY,hobby)
//        cv.put(COLUMN_GENDER,genderstore)
        db.insert(TABLE_NAME, null, cv)
        return true
    }

    fun getText(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }
    fun updatePassword(emailValidator: String,newPassword: String):
            Boolean {
        val cv = ContentValues()
        val db = this.writableDatabase
        cv.put(COLUMN_MAIL,emailValidator)
        cv.put(COLUMN_PASSWORD,newPassword)
        val rowsAffected = db.update(TABLE_NAME, cv, "$COLUMN_MAIL = ?", arrayOf(emailValidator))
        return rowsAffected>0
    }

    fun delete():Int {
        val db=this.writableDatabase
        return db.delete(TABLE_NAME,null,null)
    }

    fun saveCity(city:String): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(COLUMN_CITY, city)
        // Add other relevant columns and values

        db.insert(TABLE_NAME, null, cv)
        return true
    }


}



