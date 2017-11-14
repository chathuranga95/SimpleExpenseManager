package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

/**
 * Created by chathuranga on 11/14/2017.
 */

public class PersistentAccountDAO extends SQLiteOpenHelper implements AccountDAO {
    //version number to upgrade database version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "150600L.db";

    public PersistentAccountDAO(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableAccounts = "CREATE TABLE accounts (accNo text primary key, bankName text, accHolderName text, balance float)";

        db.execSQL(createTableAccounts);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
// Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS accounts");

        // Create tables again
        onCreate(db);
    }

    @Override
    public List<String> getAccountNumbersList() {
        ArrayList<String> accNumList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("select * from accounts", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    accNumList.add(cursor.getString(0));
                    cursor.moveToNext();
                }
            }
        } catch (SQLiteException ee) {

        }


        return accNumList;
    }

    @Override
    public List<Account> getAccountsList() {
        ArrayList<Account> accList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("select * from accounts", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    accList.add(new Account(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getFloat(3)));
                    cursor.moveToNext();
                }
            }
        } catch (SQLiteException ee) {

        }


        return accList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = this.getReadableDatabase();
        Account account = null;
        try {
            Cursor cursor = db.rawQuery("select * from account where accNo = '" + accountNo + "'", null);
            if (cursor.moveToFirst()) {
                account = new Account(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getFloat(3));
                return account;
            }
        } catch (SQLiteException ee) {
        }

        String msg = "Account " + accountNo + " is invalid.";
        throw new InvalidAccountException(msg);
    }

    @Override
    public void addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        //ContentValues values = new ContentValues();
        try {
            db.execSQL("insert into accounts values('" + account.getAccountNo() + "','" + account.getBankName() + "','" + account.getAccountHolderName() + "'," + account.getBalance() + ");");
        } catch (SQLiteException ee) {

        }
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = this.getWritableDatabase();
        //ContentValues values = new ContentValues();

        try {
            db.execSQL("delete from account where accNo = '" + accountNo + "'");
        } catch (SQLiteException ee) {

        }
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

        Account account = getAccount(accountNo);
        // specific implementation based on the transaction type
        switch (expenseType) {
            case EXPENSE:
                account.setBalance(account.getBalance() - amount);
                break;
            case INCOME:
                account.setBalance(account.getBalance() + amount);
                break;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            db.execSQL("update accounts set balance = '" + account.getBalance() + "' where accNo = '" + accountNo + "'");
        } catch (SQLiteException ee) {

        }
    }


}
