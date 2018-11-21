package com.app.cbouix.sodapp.DataAccess.DataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.app.cbouix.sodapp.DataAccess.DataBase.DaoMaster;
import com.app.cbouix.sodapp.DataAccess.DataBase.DaoSession;

/**
 * Created by CBouix on 23/04/2017.
 */

public class DataBaseManager {

    private static final String DATABASE_NAME = "sodaApp-db";
    private DaoSession daoSession;
    private static DataBaseManager instance = null;
    protected DataBaseManager() {

    }
    public static DataBaseManager getInstance() {
        if(instance == null) {
            instance = new DataBaseManager();
        }
        return instance;
    }

    public void setupDatabase(Context c) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(c, DATABASE_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession()
    {
        return daoSession;
    }
}
