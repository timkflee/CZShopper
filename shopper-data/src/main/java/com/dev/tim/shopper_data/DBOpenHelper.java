package com.dev.tim.shopper_data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dev.tim.shopper_data.greendao.DaoMaster;
import com.dev.tim.shopper_data.greendao.DaoSession;
import com.dev.tim.shopper_data.greendao.ItemDbDao;

import java.io.File;
import java.util.concurrent.Callable;

import de.greenrobot.dao.AbstractDao;

/**
 * Created by Tim on 12/28/2015.
 */
public class DBOpenHelper extends DaoMaster.DevOpenHelper {

    private static final String TAG = DBOpenHelper.class.getName();
    private static DBOpenHelper instance = null;


    private final Context context;
    private final String DATABASE_NAME;

    public static DBOpenHelper getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new DBOpenHelper(context);
        new Thread(new Runnable() {
            @Override
            public void run() {
                instance.doVacuum();
            }
        }).start();
    }

    private DBOpenHelper(Context context) {
        super(context, context.getResources().getString(R.string.shopper_db), null);
        this.context = context;
        DATABASE_NAME = context.getResources().getString(R.string.shopper_db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        android.util.Log.d(TAG, "Upgrading schema from version [" + oldVersion + "] to [" + newVersion + "]");
        DaoMaster.dropAllTables(db, true);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Downgrading schema from version [" + oldVersion + "] to [" + newVersion + "]");
        DaoMaster.dropAllTables(db, true);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.setMaximumSize(context.getResources().getInteger(R.integer.MAX_DATABASE_SIZE));
        super.onCreate(db);
    }

    public <V> V callInTx(Callable<V> callable) throws Exception {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            V result = callable.call();
            db.setTransactionSuccessful();
            return result;
        } finally {
            db.endTransaction();
        }
    }

    public void runInTx(Runnable runnable, AbstractDao dao) {
        SQLiteDatabase db = dao.getDatabase();
        db.beginTransaction();
        try {
            runnable.run();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() + ", runInTx");
        } finally {
            db.endTransaction();
        }
    }

    public DaoSession getSession() {
        SQLiteDatabase db = getWritableDatabase();
        DaoMaster master = new DaoMaster(db);
        return master.newSession();
    }

    public void logJournalState() {
        File journal = context.getDatabasePath(DATABASE_NAME + "-journal");
        Log.d(TAG, journal.getAbsolutePath() + (journal.exists() ? " exists" : "not exists"));
    }

    public void doVacuum() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("VACUUM");
    }

    public void clearAll() {
        getItemDbDao().deleteAll();
    }

    public ItemDbDao getItemDbDao() {
        return getSession().getItemDbDao();
    }
}
