package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.Popup;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by chathuranga on 11/17/2017.
 */

public class PersistentTransactionDAO extends SQLiteOpenHelper implements TransactionDAO {

    //version number to upgrade database version
    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "150600L.db";

//    private final List<Transaction> transactions;

    public PersistentTransactionDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        Transaction transaction = new Transaction(date, accountNo, expenseType, amount);

        //insert record to table here
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL("insert into transactions values('" + transaction.getAccountNo() + "','" + transaction.getExpenseType() + "','" + transaction.getAmount() + "','" + transaction.getDate() + "');");
        } catch (SQLiteException ee) {
            Popup.printMsg(ee.toString());
        }
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> transactionList = new ArrayList<>();
        //get from database and assign


        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("select * from transactions", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    ExpenseType expenseType;
                    if (cursor.getString(cursor.getColumnIndex("expenseType")).equals("EXPENSE")) {
                        expenseType = ExpenseType.EXPENSE;
                    } else {
                        expenseType = ExpenseType.INCOME;
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                    try {
                        Date date = sdf.parse(cursor.getString(cursor.getColumnIndex("date")));
                        transactionList.add(new Transaction(date, cursor.getString(cursor.getColumnIndex("accNo")), expenseType, Double.parseDouble(cursor.getString(cursor.getColumnIndex("amount")))));
                    } catch (ParseException ex) {

                    }
                    cursor.moveToNext();
                }
            }
        } catch (SQLiteException ee) {

        }


        return transactionList;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> transactionList = getAllTransactionLogs();
        int size = transactionList.size();
        if (size <= limit) {
            return transactionList;
        }
        // return the last <code>limit</code> number of transaction logs
        return transactionList.subList(size - limit, size);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String createTableTransactions = "CREATE TABLE transactions (accNo text, expenseType varchar(8), amount double, date datetime,PRIMARY KEY (accNo, date))";
            db.execSQL(createTableTransactions);
            Popup.printMsg("table: transactions,  created");
        }
        catch (SQLiteException ee){
            Popup.printMsg("Table already exists");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed, all data will be gone!!!
        try{
            db.execSQL("DROP TABLE IF EXISTS transactions");
            onCreate(db);
        }
        catch (SQLiteException ee){

        }
        // Create tables again

    }
}

