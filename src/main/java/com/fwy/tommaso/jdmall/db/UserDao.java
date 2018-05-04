package com.fwy.tommaso.jdmall.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fwy.tommaso.jdmall.cons.DbConst;

/**
 * Created by Tommaso on 2017/12/30.
 */

public class UserDao {
    private DbOPenHelper mHelper;
    public UserDao(Context c){
        mHelper = new DbOPenHelper(c);
    }
    public boolean saveUser(String name,String pwd){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbConst._NAME,name);
        values.put(DbConst._PWD,pwd);
        long insertId = db.insert(DbConst.USER_TABLE,null,values);
        return insertId!=-1;
    }

    public void clearUsers(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(DbConst.USER_TABLE,null,null);
    }

    public UserInfo quiryLastUser(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(DbConst.USER_TABLE,new String[]{DbConst._NAME,DbConst._PWD},null,null,null,null,null);
        if (cursor.moveToFirst()){
            String name = cursor.getString(0);
            String pwd = cursor.getString(1);
            return new UserInfo(name,pwd);
        }
        return null;
    }

    public class UserInfo {
        public String name;
        public String pwd;

        public UserInfo(String name, String pwd) {
            this.name = name;
            this.pwd = pwd;

        }

        public String getName() {
            return name;
        }

        public String getPwd() {
            return pwd;
        }
    }
}


