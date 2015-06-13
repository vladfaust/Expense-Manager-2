package com.app.expencemanager.ui.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by romanismagilov on 13.06.15.
 */
public class DatabaseInstrument {

    // Private fields
    DBHelper dbHelper;
    SQLiteDatabase DB = dbHelper.getWritableDatabase();

    // Constructor
    public DatabaseInstrument(Context context){
        dbHelper = new DBHelper(context);
        createDB();
    }

    // Create DB table if not exists
    public void createDB(){
        DB.execSQL("CREATE TABLE IF NOT EXISTS Transactions" +
                "(" +
                "ID int NOT NULL AUTO_INCREMENT," +
                "Amount int NOT NULL," +
                "Comment varchar(255)," +
                "Category varchar(255)," +
                "SubCategory varchar(255)," +
                "PRIMARY KEY (ID)" +
                ")");
    }

    // Adding new transaction (by values)
    public void addTransaction(String comment, int amount, String category, String subCategory){
        DB.execSQL("INSERT INTO Transactions (Amount,Comment,Category,SubCategory)\n" +
                "VALUES( "+amount+","+comment+","+category+","+subCategory+")");
    }

    // Adding new transaction (by object)
    public void addTransaction(Transaction transaction){
        DB.execSQL("INSERT INTO Transactions (Amount,Comment,Category,SubCategory)\n" +
                "VALUES( "+transaction.getAmount()+","+transaction.getComment()+","+
                transaction.getCategory()+","+transaction.getSubCategory()+")");
    }

    // Full editing of existing transaction (by id)
    public void editTransaction(int id, String comment, int amount, String category, String subCategory){
        DB.execSQL("UPDATE Transactions SET Amount="+amount+", Comment="+comment+", Category="+category+", SubCategory="+subCategory+")"+
        "WHERE ID="+id+";");
    }

    // Full editing of existing transaction (by object)
    public void editTransaction(Transaction transaction, String comment, int amount, String category, String subCategory){
        DB.execSQL("UPDATE Transactions SET Amount="+amount+", Comment="+comment+", Category="+category+", SubCategory="+subCategory+")"+
                "WHERE ID="+transaction.getId()+";");
    }

    // Part editing of existing transaction (by id)
    public void editTransaction(int id, String comment, int amount){
        DB.execSQL("UPDATE Transactions SET Amount="+amount+", Comment="+comment+")"+
                "WHERE ID="+id+";");
    }
    // Part editing of existing transaction (by object)
    public void editTransaction(Transaction transaction, String comment, int amount){
        DB.execSQL("UPDATE Transactions SET Amount="+amount+", Comment="+comment+")"+
                "WHERE ID="+transaction.getId()+";");
    }

    // Deleting Transaction (by id)
    public void deleteTransaction(int id){
        DB.execSQL("DELETE * FROM Transactions WHERE ID="+id+";");
    }

    // Deleting Transaction (by object)
    public void deleteTransaction(Transaction transaction){
        DB.execSQL("DELETE * FROM Transactions WHERE ID="+transaction.getId()+";");
    }


    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
