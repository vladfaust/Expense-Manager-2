package bl.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by romanismagilov on 13.06.15.
 */
public class DatabaseInstrument {

    // Private fields
    DBHelper dbHelper;
    SQLiteDatabase DB;
    // Constructor
    public DatabaseInstrument(Context context){
        dbHelper = new DBHelper(context);
        DB = dbHelper.getWritableDatabase();
        createDB();

        User.balance=readBalanceFromDB();
        changeBalanceBy(+193);
        changeBalanceBy(-32);

    }

    // Create DB table if not exists
    public void createDB(){
        // Transactions table
        DB.execSQL("CREATE TABLE IF NOT EXISTS Transactions" +
                "(" +
                "TID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "Amount INTEGER NOT NULL," +
                "Comment varchar(255)," +
                "SubCategory varchar(255)," +
                "Date datetime"+
                ")");
        // Category table
        DB.execSQL("CREATE TABLE IF NOT EXISTS Category" +
                "(" +
                "CID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "Name varchar(255)," +
                "Type varchar(10)," +
                "ColorID INTEGER"+
                ")");

        // User data
        DB.execSQL("CREATE TABLE IF NOT EXISTS User" +
                "(" +
                "UID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "Balance INTEGER"+
                ")");

        // Base categories
        DB.execSQL("INSERT INTO Category(Name, Type, ColorID) VALUES ('Grocery', 'spends', 1)");
        DB.execSQL("INSERT INTO Category(Name, Type, ColorID) VALUES ('Cafes', 'spends', 2)");
        DB.execSQL("INSERT INTO Category(Name, Type, ColorID) VALUES ('Transport', 'spends', 1)");
        DB.execSQL("INSERT INTO Category(Name, Type, ColorID) VALUES ('Salary', 'income', 1)");
        DB.execSQL("INSERT INTO User(Balance) VALUES (0)");

        Category.List = loadAllCategories();

        // Intermediate table to connect Categories and Transactions
        DB.execSQL("CREATE TABLE IF NOT EXISTS TransactionsCategory" +
                "(" +
                "CID INTEGER NOT NULL," +
                "TID INTEGER NOT NULL" +
                ")");

        // Testing transactions
        addTransaction("'test1'",1,"'Grocery'","'gift fo my ex'","'2015-06-11'");
        addTransaction("'test2'",350,"'Transport'","'subway'","'2015-06-10'");
        addTransaction("'test3'",10000,"'Salary'","'subway'","'2015-06-10'");

    }

    // Adding new transaction (by values)
    public void addTransaction(String comment, int amount, String category, String subCategory, String date){
        DB.execSQL("INSERT INTO Transactions (Amount,Comment,SubCategory,Date)\n" +
                "VALUES( "+amount+","+comment+","+subCategory+","+date+")");
        DB.execSQL("insert into TransactionsCategory (CID,TID) " +
                "values((select CID from Category where Name="+category+"), " +
                "(select MAX(TID) from Transactions))");
        Category test = Category.getCategoryByString(category);
        if (Category.getCategoryByString(category).getType().equals("income"))
            changeBalanceBy(amount);
        else
            changeBalanceBy(-1 * amount);
    }

    // Adding new transaction (by object)
    public void addTransaction(Transaction transaction, Category category){
        DB.execSQL("INSERT INTO Transactions (Amount,Comment,SubCategory,Date)\n" +
                "VALUES( "+transaction.getAmount()+","+transaction.getComment()+","+
                transaction.getSubCategory()+","+transaction.getDate()+")");
        DB.execSQL("insert into TransactionsCategory (CID,TID) " +
                "values((select CID from Category where Name="+category.getName()+"), " +
                "(select MAX(TID) from Transactions))");
        if (category.getType().equals("income"))
            changeBalanceBy(transaction.getAmount());
        else
            changeBalanceBy(-1 * transaction.getAmount());
    }

    // Editing of existing transaction without changing category (by id)
    public void editTransaction(int id, String comment, int amount, String subCategory){
        DB.execSQL("UPDATE Transactions SET Amount="+amount+", Comment="+comment+", SubCategory="+subCategory+")"+
        "WHERE TID="+id+";");
    }

    // Changing of transact category (by object)
    public void changeTransactionCategory(Transaction transaction, Category category){
        DB.execSQL("update TransactionsCategory set CID="+category.getCid()+" where TID="+transaction.getId()+"");
    }

    // Changing of transact category (by id)
    public void changeTransactionCategory(int cid, int tid){
        DB.execSQL("update TransactionsCategory set CID="+cid+" where TID="+tid+"");
    }

    //Load all categories from database to string array list
    public ArrayList<Category> loadAllCategories(){
        ArrayList<Category> list = new ArrayList<Category>();


        String query = String.format("SELECT * FROM Category");
        Cursor cursor  = dbHelper.getReadableDatabase().rawQuery(query, null);

        while (cursor.moveToNext()) {
            Category category = new Category();
            category.setCid(cursor.getInt(0));
            category.setName(cursor.getString(1));
            category.setType(cursor.getString(2));
            category.setColorID(cursor.getInt(3));
            list.add(category);
        }
        return list;
    }


    // Load all transactions from database to array list
    public ArrayList<Transaction> loadAllTransactions(){
        ArrayList<Transaction> list = new ArrayList<Transaction>();


        String query = String.format("SELECT * FROM Transactions inner join TransactionsCategory on Transactions.TID = TransactionsCategory.TID");
        Cursor cursor  = dbHelper.getReadableDatabase().rawQuery(query, null);
        while (cursor.moveToNext()) {
            Transaction transaction = new Transaction();
            transaction.setId(cursor.getInt(0));
            transaction.setAmount(cursor.getInt(1));
            transaction.setComment(cursor.getString(2));
            transaction.setCategory(Category.getCategoryByID(cursor.getInt(5)));
            transaction.setSubCategory(cursor.getString(3));
            transaction.setDate(cursor.getString(4));
            list.add(transaction);
        }
        return list;
    }

    // Get current balance (using all transactions)
//    public int currentBalance(ArrayList<Transaction> list){
//        int balance=0;
//        for (Transaction transaction: list){
//            if (transaction.category.getType().equals("income"))
//                balance+=transaction.getAmount();
//            else
//                balance-=transaction.getAmount();
//        }
//        return balance;
//    }

    public int readBalanceFromDB(){
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT Balance FROM User where User.UID=1", null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public void changeBalanceBy(int amount){
        User.balance=User.balance+amount;
        DB.execSQL("UPDATE User SET Balance="+User.balance+" where User.UID=1");
    }

    public void changeBalanceTo(int amount){
        User.balance=amount;
        DB.execSQL("UPDATE User SET Balance="+User.balance+" where User.UID=1");
    }

//
//    // Full editing of existing transaction (by object)
//    public void editTransaction(Transaction transaction, String comment, int amount, String category, String subCategory){
//        DB.execSQL("UPDATE Transactions SET Amount="+amount+", Comment="+comment+", Category="+category+", SubCategory="+subCategory+")"+
//                "WHERE TID="+transaction.getId()+";");
//    }
//
//    // Part editing of existing transaction (by id)
//    public void editTransaction(int id, String comment, int amount){
//        DB.execSQL("UPDATE Transactions SET Amount="+amount+", Comment="+comment+")"+
//                "WHERE TID="+id+";");
//    }
//    // Part editing of existing transaction (by object)
//    public void editTransaction(Transaction transaction, String comment, int amount){
//        DB.execSQL("UPDATE Transactions SET Amount="+amount+", Comment="+comment+")"+
//                "WHERE TID="+transaction.getId()+";");
//    }
//
//    // Deleting Transaction (by id)
//    public void deleteTransaction(int id){
//        DB.execSQL("DELETE * FROM Transactions WHERE TID="+id+";");
//    }
//
//    // Deleting Transaction (by object)
//    public void deleteTransaction(Transaction transaction){
//        DB.execSQL("DELETE * FROM Transactions WHERE TID="+transaction.getId()+";");
//    }
//

//


    
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
