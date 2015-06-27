package bl.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by romanismagilov on 13.06.15.
 */
public class DatabaseInstrument {

    // Private fields
    DBHelper dbHelper;
    SQLiteDatabase DB;
    String startOfFinancialMonth = "01";
    public static DatabaseInstrument instance;
    // Constructor
    public DatabaseInstrument(Context context){
        dbHelper = new DBHelper(context);
        DB = dbHelper.getWritableDatabase();
        createDB();
        instance=this;
        setBalanceChangedBy(+193);
        setBalanceChangedBy(-32);

        User.linkToSingleton();

    }

    // Create DB table if not exists
    public void createDB(){
        // Only for testing

        DB.execSQL("DROP TABLE IF EXISTS Transactions");
        DB.execSQL("DROP TABLE IF EXISTS TransactionsCategory");
        DB.execSQL("DROP TABLE IF EXISTS Category");
        DB.execSQL("DROP TABLE IF EXISTS User");
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
                "Balance INTEGER,"+
                "MonthBudget INTEGER"+
                ")");





        // Intermediate table to connect Categories and Transactions
        DB.execSQL("CREATE TABLE IF NOT EXISTS TransactionsCategory" +
                "(" +
                "CID INTEGER NOT NULL," +
                "TID INTEGER NOT NULL" +
                ")");



        // TESTING ONLY
        DB.execSQL("DELETE FROM Transactions;");
        DB.execSQL("DELETE FROM Category;");
        DB.execSQL("DELETE FROM TransactionsCategory;");
        DB.execSQL("DELETE FROM User;");

        // Base categories
        DB.execSQL("INSERT INTO Category(Name, Type, ColorID) VALUES ('Grocery', 'spends', 1)");
        DB.execSQL("INSERT INTO Category(Name, Type, ColorID) VALUES ('Cafes', 'spends', 2)");
        DB.execSQL("INSERT INTO Category(Name, Type, ColorID) VALUES ('Salary', 'income', 3)");
        DB.execSQL("INSERT INTO User(Balance, MonthBudget) VALUES (0, 10000)");

        Category.List = getAllCategories();

        // Testing transactions
        addTransaction("'test1'",1,Category.List.get(0),"'gift fo my ex'","'2015-06-11'");
        addTransaction("'test3'",10000,Category.List.get(1),"'subway'","'2015-06-10'");
        addTransaction("'test2'",350,Category.List.get(1),"'keka'","'2015-06-17'");
        addTransaction("'test4'",3,Category.List.get(0),"'gift fo my ex'","'2015-06-14'");
        addTransaction("'test5'",1030,Category.List.get(2),"'buhbhu'","'2015-06-15'");
        addTransaction("'test6'",150,Category.List.get(1),"'keka'","'2015-06-16'");


    }

    // Adding new transaction (by values)
    public void addTransaction(String comment, int amount, String category, String subCategory, String date){
        DB.execSQL("INSERT INTO Transactions (Amount,Comment,SubCategory,Date)\n" +
                "VALUES( "+amount+","+comment+","+subCategory+","+date+")");
        DB.execSQL("insert into TransactionsCategory (CID,TID) " +
                "values((select CID from Category where Name="+category+"), " +
                "(select MAX(TID) from Transactions))");
        if (Category.getCategoryByString(category)!=null){
            if (Category.getCategoryByString(category).getType().equals("income"))
                setBalanceChangedBy(amount);
            else
                setBalanceChangedBy(-1 * amount);
        }
    }

    // Adding new transaction (by values)
    public void addTransaction(String comment, int amount, Category category, String subCategory, String date){
        DB.execSQL("INSERT INTO Transactions (Amount,Comment,SubCategory,Date)\n" +
                "VALUES( "+amount+","+comment+","+subCategory+","+date+")");
        DB.execSQL("insert into TransactionsCategory (CID,TID) " +
                "values((select CID from Category where Name='"+category.getName()+"'), " +
                "(select MAX(TID) from Transactions))");
            if (category.getType().equals("income"))
                setBalanceChangedBy(amount);
            else
                setBalanceChangedBy(-1 * amount);

    }

    // Editing of existing transaction without changing category (by id)
    public void editTransaction(int id, String comment, int amount, String subCategory){
        DB.execSQL("UPDATE Transactions SET Amount="+amount+", Comment="+comment+", SubCategory="+subCategory+")"+
        "WHERE TID="+id+";");
    }

    // Changing of transact category (by object)
    public void setTransactionCategory(Transaction transaction, Category category){
        DB.execSQL("update TransactionsCategory set CID="+category.getCid()+" where TID="+transaction.getId()+"");
        transaction.category = category;
    }

    // Load all categories from database to string array list
    public ArrayList<Category> getAllCategories(){
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
        cursor.close();

        Category category = new Category();
        category.setCid(0);
        category.setName("");
        category.setType("");
        category.setColorID(0);
        list.add(category);

        return list;
    }


    // Load all transactions from database to array list
    public ArrayList<Transaction> getAllTransactions(){
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
        cursor.close();
        return list;
    }

    // Get all spends from selected transaction list
    public int getAllSpendsFrom(ArrayList<Transaction> list){
        int spends=0;
        for (Transaction transaction: list){
            if (transaction.category.getType().equals("spends"))
                spends+=transaction.getAmount();
        }
        return spends;
    }

    // Return all spends during selected transaction list in selected category
    public int getAllSpendsFrom(ArrayList<Transaction> list, Category category){
        int spends=0;
        for (Transaction transaction: list){
            if (transaction.category.getType().equals("spends")
                    &&transaction.category == category)
                spends+=transaction.getAmount();
        }
        return spends;
    }

    // loads transactions between date 1 and date 2
    public ArrayList<Transaction> getTransactions(int year1, int month1, int day1, int year2,
                                                  int month2, int day2){
        ArrayList<Transaction> list = new ArrayList<Transaction>();

        String query = String.format("SELECT * FROM Transactions inner join TransactionsCategory on Transactions.TID = TransactionsCategory.TID "+
                "WHERE([Date] BETWEEN '"+year1+"-"+month1+"-"+day1+"'" +
                " AND '"+year2+"-"+month2+"-"+day2+"')");
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
        cursor.close();

        return list;
    }

    // Return all transactions during current financial month
    public ArrayList<Transaction> getFinancialMonthTransactions(){
        ArrayList<Transaction> list = new ArrayList<Transaction>();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+2:00"));

        String startingMonth;
        if (cal.get(Calendar.DAY_OF_MONTH)<Integer.parseInt(startOfFinancialMonth))
            startingMonth = String.valueOf(cal.get(Calendar.MONTH));
        else
            startingMonth = String.valueOf(cal.get(Calendar.MONTH)+1);

        if (startingMonth.length()==1)
            startingMonth="0"+startingMonth;


        String finishingYear;
        String finishingMonth;
        if (startingMonth.equals(String.valueOf(12))){
            finishingYear = String.valueOf(cal.get(Calendar.YEAR)+1);
            finishingMonth = "1";
        }
        else{
            finishingYear = String.valueOf(cal.get(Calendar.YEAR));
            finishingMonth = String.valueOf(Integer.parseInt(startingMonth)+1);
        }

        // select * from incoming WHERE([date] BETWEEN '30.10.2012' AND '30.10.2012');
        String query = String.format("SELECT * FROM Transactions inner join TransactionsCategory on Transactions.TID = TransactionsCategory.TID "+
        "WHERE([Date] BETWEEN '"+cal.get(Calendar.YEAR)+"-"+startingMonth+"-"+startOfFinancialMonth+"'" +
                " AND '"+finishingYear+"-"+finishingMonth+"-"+startOfFinancialMonth+"')");
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
        cursor.close();

        return list;
    }

    public int getBalanceFromDB(){
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT Balance FROM User where User.UID=1", null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public void setBalanceChangedBy(int amount){
        DB.execSQL("UPDATE User SET Balance="+amount+" where User.UID=1");
    }

    public void setBalanceTo(int amount){
        DB.execSQL("UPDATE User SET Balance="+amount+" where User.UID=1");
    }

    public int getBudget(){
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT MonthBudget FROM User where User.UID=1", null);
        cursor.moveToFirst();
        return cursor.getInt(0);

    }

    // Set month budget to
    public void setBudget(int amount){
        DB.execSQL("UPDATE User SET MonthBudget="+amount+" where User.UID=1");
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
