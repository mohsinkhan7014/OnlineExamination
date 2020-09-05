package nit.com.onlineexamination;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OLECDatabase extends SQLiteOpenHelper
{

    public static final String USER_DETAIL_TABLE = "userdetail";
    public static final String QUESTIONS_TABLE = "questions";

    public static synchronized OLECDatabase getInstance()
    {
        if ( s_oInstance == null )
            s_oInstance = new OLECDatabase();
        return s_oInstance;
    }
    public static final String DATABASE_NAME = "OLEC";
    public static final int DATABASE_VERSION = 1;// Update this
    private static OLECDatabase s_oInstance = null;

    public OLECDatabase()
    {
        super( AndroidContext.getContext(), DATABASE_NAME, null, DATABASE_VERSION );
        m_oDb = getWritableDatabase();
    }

    @Override
    public void onCreate( SQLiteDatabase db )
    {
        final String[] asCreateStmts = new String[]
                {
                        "create table " + USER_DETAIL_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, firstname TEXT , lastname TEXT , emailid TEXT , username TEXT , password TEXT , dateofbirth DATE )",
                        "create table " + QUESTIONS_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, technology TEXT , question TEXT , option1 TEXT , option2 TEXT , option3 TEXT , option4 TEXT , answer INTEGER, isChecked INTEGER )"

                };

        for ( String sStmt : asCreateStmts )
            db.execSQL( sStmt );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db,
                           int oldVersion,
                           int newVersion )
    {

        // System.out.println("test");
        Log.w( "upg", "Upgrading database from version " + oldVersion
                + " to " + newVersion + ", which will destroy all old data" );


    }

    public void dropDB()
    {
        release();
        AndroidContext.getContext().deleteDatabase( DATABASE_NAME );
    }

    public SQLiteDatabase getDb()
    {
        return m_oDb;
    }

    public static synchronized void release()
    {
        s_oInstance.m_oDb.close();
        s_oInstance = null;
    }





    public void CloseDB()

    {
        m_oDb.close();
    }
    public SQLiteDatabase m_oDb = null;
}
