package com.paxw.phonesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.paxw.phonesafe.utils.Constants;

/**
 * Created by lichuang on 2015/12/17.
 */
public class NotSafeNumberOpenDBHelper extends SQLiteOpenHelper{

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public NotSafeNumberOpenDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public NotSafeNumberOpenDBHelper(Context context){
        this(context, Constants.DBNAME, null, 1);
    }
    //数据库第一次创建的时候调用的方法。 适合做数据库表结构的初始化
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建一数据库表//创建数据库的表结构  主键_id 自增长  number黑名单号码  mode拦截模式  1电话拦截 2短信拦截 3全部拦截。
        db.execSQL("create table blacknumber (_id integer primary key autoincrement , number varchar(20), mode varchar(2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Called when the database has been opened.  The implementation
     * should check {@link SQLiteDatabase#isReadOnly} before updating the
     * database.
     * <p>
     * This method is called after the database connection has been configured
     * and after the database schema has been created, upgraded or downgraded as necessary.
     * If the database connection must be configured in some way before the schema
     * is created, upgraded, or downgraded, do it in {@link #onConfigure} instead.
     * </p>
     *
     * @param db The database.
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
