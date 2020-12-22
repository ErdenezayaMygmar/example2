package MN.NUM.LAB4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String dbname = "UserInfo.db";
    public static final String tablename = "userinfo";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "username";
    public static final String COL_3 = "password";
    public static final String COL_4 = "age";
    public static final String COL_5 = "sex";
    public static final String COL_6 = "phone_number";
    public static final String COL_7 = "dateOfBirth";
    public DatabaseHelper( Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry = "CREATE TABLE " + tablename + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, " +
                "password TEXT, age INTEGER, sex TEXT, phone_number TEXT, dateOfBirth TEXT)";
        db.execSQL(qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tablename);
        onCreate(db);
    }

    public long addUser(String username, String password, int age, String sex, String phone_number, String dateOfBirth){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("age", age);
        contentValues.put("sex", sex);
        contentValues.put("phone_number", phone_number);
        contentValues.put("dateOfBirth", dateOfBirth);
        long result = db.insert(tablename, null, contentValues);

        return result;
    }

    public boolean checkUser(String username, String password){
        String[] cols = {COL_1};
        SQLiteDatabase udb = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " +COL_3 + "=?";
        String[] selectionArgs = {username, password};

        Cursor cursor = udb.query(tablename, cols, selection, selectionArgs,null, null, null);
        int cnt = cursor.getCount();
        if(cnt>0)
            return true;
        else return false;
    }

    public boolean checkUsername(String username){
        String[] columns = {COL_1};
        SQLiteDatabase myDb = getReadableDatabase();
        String selection = COL_2 + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = myDb.query(tablename,columns,selection,selectionArgs,null,null,null);
        int cnt = cursor.getCount();

        if(cnt>0)
            return false;
        else return true;
    }

    public Cursor checkUserCP(String username, String password){
        String[] cols = {COL_1};
        SQLiteDatabase udb = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " +COL_3 + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = udb.query(tablename, cols, selection, selectionArgs,null, null, null);
        return cursor;
    }

    public Cursor checkUsernameCP(String username){
        String[] columns = {COL_1};
        SQLiteDatabase myDb = getReadableDatabase();
        String selection = COL_2 + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = myDb.query(tablename, columns,selection,selectionArgs,null,null,null);
        return cursor;
    }

    public Cursor getUserInfo(String username){
        String[] columns = {COL_2,COL_4,COL_5,COL_6,COL_7};
        SQLiteDatabase myDb = getReadableDatabase();
        String selection = COL_2 + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = myDb.query(tablename, columns, selection, selectionArgs, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    public boolean update(String username, String age, String sex, String phone_number, String dateOfBirth){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("age", age);
        contentValues.put("sex", sex);
        contentValues.put("phone_number", phone_number);
        contentValues.put("dateOfBirth", dateOfBirth);
        int cnt =  db.update(tablename,contentValues,"username = ?",new String[]{username});
        if(cnt > 0)
            return true;
        else {
            return false;
        }
    }


    public boolean updatePassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        int cnt = db.update(tablename, contentValues, "username =?", new String[]{username});
        if(cnt > 0)
            return true;
        else {
            return false;
        }
    }
}
