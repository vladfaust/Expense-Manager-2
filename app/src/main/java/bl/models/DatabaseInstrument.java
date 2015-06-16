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
    SQLiteDatabase DB = dbHelper.getWritableDatabase();
    // Constructor
    public DatabaseInstrument(Context context){
        dbHelper = new DBHelper(context);
        createDB();
        Category.List = loadAllCategories();
        User.balance=currentBalance(loadAllTransactions());

    }

    // Create DB table if not exists
    public void createDB(){
        // Transactions table
        DB.execSQL("CREATE TABLE IF NOT EXISTS Transactions" +
                "(" +
                "TID int NOT NULL AUTO_INCREMENT," +
                "Amount int NOT NULL," +
                "Comment varchar(255)," +
                "SubCategory varchar(255)," +
                "Date datetime,"+
                "PRIMARY KEY (TID)" +
                ")");
        // Category table
        DB.execSQL("CREATE TABLE IF NOT EXISTS Category" +
                "(" +
                "CID int NOT NULL AUTO_INCREMENT," +
                "Name varchar(255)," +
                "Type varchar(10)," +
                "PRIMARY KEY (CID)" +
                ")");
        // Intermediate table to connect Categories and Transactions
        DB.execSQL("CREATE TABLE IF NOT EXISTS TransactionsCategory" +
                "(" +
                "CID int NOT NULL," +
                "TID int NOT NULL" +
                ")");
    }

    // Adding new transaction (by values)
    public void addTransaction(String comment, int amount, String category, String subCategory, String date){
        DB.execSQL("INSERT INTO Transactions (Amount,Comment,SubCategory,Date)\n" +
                "VALUES( "+amount+","+comment+","+subCategory+","+date+")");
        DB.execSQL("insert into TransactionCategory (CID,TID) " +
                "values((select CID from Category where Name='"+category+"'), " +
                "(select MAX(TID) from Transaction))");
        User.balance=currentBalance(loadAllTransactions());
    }

    // Adding new transaction (by object)
    public void addTransaction(Transaction transaction, Category category){
        DB.execSQL("INSERT INTO Transactions (Amount,Comment,SubCategory,Date)\n" +
                "VALUES( "+transaction.getAmount()+","+transaction.getComment()+","+
                transaction.getSubCategory()+","+transaction.getDate()+")");
        DB.execSQL("insert into TransactionCategory (CID,TID) " +
                "values((select CID from Category where Name='"+category.getName()+"'), " +
                "(select MAX(TID) from Transaction))");
        User.balance=currentBalance(loadAllTransactions());
    }

    // Editing of existing transaction without changing category (by id)
    public void editTransaction(int id, String comment, int amount, String subCategory){
        DB.execSQL("UPDATE Transactions SET Amount="+amount+", Comment="+comment+", SubCategory="+subCategory+")"+
        "WHERE TID="+id+";");
    }

    // Changing of transact category (by object)
    public void changeTransactionCategory(Transaction transaction, Category category){
        DB.execSQL("update TransactionCategory set CID="+category.getCid()+" where TID="+transaction.getId()+"");
    }

    // Changing of transact category (by id)
    public void changeTransactionCategory(int cid, int tid){
        DB.execSQL("update TransactionCategory set CID="+cid+" where TID="+tid+"");
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
            list.add(category);
        }
        return list;
    }


    // Load all transactions from database to array list
    public ArrayList<Transaction> loadAllTransactions(){
        ArrayList<Transaction> list = new ArrayList<Transaction>();


        String query = String.format("SELECT * FROM Transactions");
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

    // Get current balance
    public int currentBalance(ArrayList<Transaction> list){
        int balance=0;
        for (Transaction transaction: list){
            if (transaction.category.getType()=="income")
                balance+=transaction.getAmount();
            else
                balance-=transaction.getAmount();
        }
        return balance;
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
