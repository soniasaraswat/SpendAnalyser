package com.spendanalyzer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.spendanalyzer.model.Expenses;
import com.spendanalyzer.model.User;

import java.util.ArrayList;
import java.util.List;


public class DBconnection {


    DBinfo dBinfo;
    public DBconnection(Context context){
        dBinfo = new DBinfo(context);
    }

    public long dataInsert(int userId,int Money , int Spend, String Category, String Date){

        SQLiteDatabase sqLiteDatabase = dBinfo.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBinfo.COLUMN_USER_ID,userId);
        contentValues.put(DBinfo.money,Money);
        contentValues.put(DBinfo.spend,Spend);
        contentValues.put(DBinfo.category,Category);
        contentValues.put(DBinfo.date,Date);

        long id = sqLiteDatabase.insert(DBinfo.tableName,null,contentValues);
        return id;
    }

    public List<Expenses> viewMoney(int userID){
        Expenses expenses = null;
        List<Expenses> expensesList = new ArrayList<>();

        // selection criteria
        String selection = DBinfo.COLUMN_USER_ID + " = ?";

        // selection argument
        String[] selectionArgs = {String.valueOf(userID)};


        SQLiteDatabase sqLiteDatabase = dBinfo.getWritableDatabase();

        String[] columns = {DBinfo.UID, DBinfo.money, DBinfo.spend, DBinfo.category, DBinfo.date};

        Cursor cursor = sqLiteDatabase.query(DBinfo.tableName,columns,selection,selectionArgs,null,null,null);

        while(cursor.moveToNext()){
            int index0 = cursor.getColumnIndex(DBinfo.UID);
            int index1 = cursor.getColumnIndex(DBinfo.money);
            int index2 = cursor.getColumnIndex(DBinfo.spend);
            int index3 = cursor.getColumnIndex(DBinfo.category);
            int index4 = cursor.getColumnIndex(DBinfo.date);


            int Id = cursor.getInt(index0);
            int Moneye = cursor.getInt(index1);

            int spend=cursor.getInt(index2);


            String Category = cursor.getString(index3);
            String date = cursor.getString(index4);

            expenses = new Expenses(Id,Moneye,spend,Category,date);
            expensesList.add(expenses);
        }
        return expensesList;

    }

    public List<User> viewUserDetails(String emailID){

        List<User> userList = new ArrayList<>();
        User user= new User();
        // selection criteria
        String selection = DBinfo.COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {emailID};
        SQLiteDatabase sqLiteDatabase = dBinfo.getWritableDatabase();

        String[] columns = {DBinfo.COLUMN_USER_ID, DBinfo.COLUMN_USER_NAME, DBinfo.COLUMN_USER_EMAIL};

        Cursor cursor = sqLiteDatabase.query(DBinfo.TABLE_USER,columns,selection,selectionArgs,null,null,null);

        while(cursor.moveToNext()){
            int index0 = cursor.getColumnIndex(DBinfo.COLUMN_USER_ID);
            int index1 = cursor.getColumnIndex(DBinfo.COLUMN_USER_NAME);
            int index2 = cursor.getColumnIndex(DBinfo.COLUMN_USER_EMAIL);



            int Id = cursor.getInt(index0);
            String userName = cursor.getString(index1);

            String userEmail=cursor.getString(index2);
            user.setId(Id);
            user.setName(userName);
            user.setEmail(userEmail);


            userList.add(user);
        }


        return userList;
    }

    public ArrayList<String> searchCategory(String cate, int userID){

        // selection criteria
        String selection = DBinfo.COLUMN_USER_ID + " = ?"+" AND "+DBinfo.category+" =?" ;

        // selection argument
        String[] selectionArgs = {String.valueOf(userID),cate};

        SQLiteDatabase sqLiteDatabase = dBinfo.getWritableDatabase();

        String[] columns = {DBinfo.spend, DBinfo.category, DBinfo.date};

        Cursor cursor = sqLiteDatabase.query(DBinfo.tableName,columns, selection,selectionArgs,null,null,null);

        ArrayList<String> arrayList = new ArrayList<>();

        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex(DBinfo.spend);
            String Moneye = cursor.getString(index);
            arrayList.add(Moneye);
        }
        return arrayList;

    }

    public ArrayList<String> getMinus (int userID){
      //  String selection = DBinfo.COLUMN_USER_ID + " = ?" + " AND " + DBinfo.money + " NOT LIKE ' -%'";
        // selection criteria
        String selection = DBinfo.COLUMN_USER_ID + " = ?"/* + " AND " + DBinfo.spend + " LIKE ' -%'"*/;

        // selection argument
        String[] selectionArgs = {String.valueOf(userID),};

        SQLiteDatabase sqLiteDatabase = dBinfo.getWritableDatabase();

        String[] columns = {DBinfo.spend, DBinfo.category, DBinfo.date};

        Cursor cursor = sqLiteDatabase.query(DBinfo.tableName,columns, selection,selectionArgs,null,null,null);

        ArrayList<String> arrayList = new ArrayList<>();

        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex(DBinfo.spend);
            String Moneye = cursor.getString(index);
            arrayList.add(Moneye);
        }
        return arrayList;

    }

    public ArrayList<String> getPositive (int userID){

        // selection criteria

        String selection = DBinfo.COLUMN_USER_ID + " = ?" + " AND " + DBinfo.money + " NOT LIKE ' -%'";

        // selection argument
        String[] selectionArgs = {String.valueOf(userID)};



        SQLiteDatabase sqLiteDatabase = dBinfo.getWritableDatabase();

        String[] columns = {DBinfo.money, DBinfo.category};

        Cursor cursor = sqLiteDatabase.query(DBinfo.tableName,columns, selection,selectionArgs,null,null,null);

        ArrayList<String> arrayList = new ArrayList<>();

        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex(DBinfo.money);
            String Moneye = cursor.getString(index);
            arrayList.add(Moneye);
        }
        return arrayList;

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = dBinfo.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBinfo.COLUMN_USER_NAME, user.getName());
        values.put(DBinfo.COLUMN_USER_EMAIL, user.getEmail());
        values.put(DBinfo.COLUMN_USER_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(DBinfo.TABLE_USER, null, values);
        db.close();
    }



    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                DBinfo.COLUMN_USER_ID
        };
        SQLiteDatabase db = dBinfo.getReadableDatabase();

        // selection criteria
        String selection = DBinfo.COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(DBinfo.TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                DBinfo.COLUMN_USER_ID
        };
        SQLiteDatabase db = dBinfo.getReadableDatabase();
        // selection criteria
        String selection = DBinfo.COLUMN_USER_EMAIL + " = ?" + " AND " + DBinfo.COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(DBinfo.TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    static class DBinfo extends SQLiteOpenHelper {

        // User table name
        private static final String TABLE_USER = "user";

        // User Table Columns names
        private static final String COLUMN_USER_ID = "user_id";
        private static final String COLUMN_USER_NAME = "user_name";
        private static final String COLUMN_USER_EMAIL = "user_email";
        private static final String COLUMN_USER_PASSWORD = "user_password";

        //declaring my variables
        private static final String dataBase_Name = "expenseManager";
        private static final String tableName = "IncomeOutcome";
        private static final int dataBase_Version = 1;
        private static final String UID = "id";
        private static final String money = "Money";
        private static final String category = "Category";
        private static final String date = "Date";
        private static final String spend = "Spend";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+tableName;

        // create spend table sql query

        private static final String CREATE_TABLE = "CREATE TABLE "+tableName+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_USER_ID+" INTEGER, "+money+" INTEGER, "+spend+" INTEGER, "+category+" VARCHAR(255),"+date+" date);";



        // create table sql query
        private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";


        private Context context;
        public DBinfo(Context context) {
            super(context, dataBase_Name, null, dataBase_Version);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL(CREATE_TABLE);
                db.execSQL(CREATE_USER_TABLE);

            }catch (SQLException e){
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try{
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (SQLException e){
            }
        }
    }


}
