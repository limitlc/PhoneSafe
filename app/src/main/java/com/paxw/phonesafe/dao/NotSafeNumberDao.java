package com.paxw.phonesafe.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.paxw.phonesafe.bean.ContactsInfo;
import com.paxw.phonesafe.db.NotSafeNumberOpenDBHelper;
import com.paxw.phonesafe.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichuang on 2015/12/17.
 */
public class NotSafeNumberDao {
    private NotSafeNumberOpenDBHelper helper;

    public NotSafeNumberDao(Context context) {
        helper = new NotSafeNumberOpenDBHelper(context);

    }

    public void add(String number, String mode) {
        SQLiteDatabase database = helper.getWritableDatabase();
        database.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(Constants.DBPNUMBER, number);
        values.put(Constants.DBPMODE, mode);
        database.insert(Constants.DBTBLACKNUMBER, null, values);
        database.endTransaction();
        database.close();
    }

    public void delete(String number) {
        SQLiteDatabase database = helper.getWritableDatabase();
        database.beginTransaction();
        database.delete(Constants.DBTBLACKNUMBER, "number = ?", new String[]{number});
        database.endTransaction();
        database.close();

    }

    public void update(String number, String mode) {
        SQLiteDatabase database = helper.getWritableDatabase();
        database.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(Constants.DBPMODE, mode);
        database.update(Constants.DBTBLACKNUMBER, values, "number = ?", new String[]{number});
        database.endTransaction();
        database.close();

    }

    public List<ContactsInfo> findAll() {
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query(Constants.DBTBLACKNUMBER, new String[]{Constants.DBPNUMBER, Constants.DBPMODE}, null
                , null, null
                , null, null);
        List<ContactsInfo> list = new ArrayList<>();
        while (cursor.moveToNext()){
            ContactsInfo info = new ContactsInfo();
            info.setNumber(cursor.getString(0));
            info.setMode(cursor.getString(1));
            list.add(info);
        }
        cursor.close();
        database.close();
        return list;
    }


    public List<ContactsInfo> findPart(int startIndex){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor;
       // cursor = db.rawQuery("select number,mode  from blacknumber order by _id desc limit 20 offset ?", new String[]{String.valueOf(startIndex)});
        cursor = db.query(Constants.DBTBLACKNUMBER,new String[]{Constants.DBPNUMBER, Constants.DBPMODE},"offset = ?",new String[]{String.valueOf(startIndex)},null,null,"_id","20");
        List<ContactsInfo>  list = new ArrayList<>();
        while(cursor.moveToNext()){
            ContactsInfo info = new ContactsInfo();
            info.setNumber(cursor.getString(0));
            info.setMode(cursor.getString(1));
            list.add(info);
        }
        cursor.close();
        db.close();
        return list;
    }

    public int getTotalCount(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from "+Constants.DBTBLACKNUMBER,null);
        cursor.moveToNext();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }
    public boolean  find(String number) {
        boolean result = false;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(Constants.DBTBLACKNUMBER, null, "number=?", new String[]{number}, null, null, null);
        if(cursor.moveToNext()){
            result = true;
        }
        cursor.close();
        db.close();
        return result;
    }

    /**
     * 查询黑名单号码的拦截模式
     * @param number
     * @return  null代表不存在  1电话 2短信 3全部
     */
    public String findMode(String number){
        String mode = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("blacknumber", new String[]{"mode"}, "number=?", new String[]{number}, null, null, null);
        if(cursor.moveToNext()){
            mode = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return mode;
    }


}
