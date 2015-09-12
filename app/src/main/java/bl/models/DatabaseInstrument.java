package bl.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by romanismagilov on 13.06.15.
 */
//TODO: SQL rows swapping (Category)
public class DatabaseInstrument {

    // Private fields
    DBHelper dbHelper;
    SQLiteDatabase DB;
    String startOfFinancialMonth = "01";
    boolean testing = true;
    public static DatabaseInstrument instance;

    // Constructor
    public DatabaseInstrument(Context context) {
        dbHelper = new DBHelper(context);
        DB = dbHelper.getWritableDatabase();
        createDB();
        instance = this;
        if (testing) {
            setBalanceChangedBy(+193);
            setBalanceChangedBy(-32);
        }

        User.linkToSingleton();

    }

    // Create DB table if not exists
    public void createDB() {
        // Only for testing
        if (testing) {
            DB.execSQL("DROP TABLE IF EXISTS Transactions");
            DB.execSQL("DROP TABLE IF EXISTS TransactionsCategory");
            DB.execSQL("DROP TABLE IF EXISTS Category");
            DB.execSQL("DROP TABLE IF EXISTS User");
        }
        // Transactions table
        DB.execSQL("CREATE TABLE IF NOT EXISTS Transactions" +
                "(" +
                "TID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "Amount REAL NOT NULL," +
                "Comment varchar(255)," +
                "SubCategory varchar(255)," +
                "Date datetime" +
                ")");
        // Category table
        DB.execSQL("CREATE TABLE IF NOT EXISTS Category" +
                "(" +
                "CID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "Name varchar(255)," +
                "Type varchar(10)," +
                "ColorID INTEGER" +
                ")");

        // User data
        DB.execSQL("CREATE TABLE IF NOT EXISTS User" +
                "(" +
                "UID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "Balance INTEGER," +
                "MonthBudget INTEGER" +
                ")");


        // Intermediate table to connect Categories and Transactions
        DB.execSQL("CREATE TABLE IF NOT EXISTS TransactionsCategory" +
                "(" +
                "CID INTEGER NOT NULL," +
                "TID INTEGER NOT NULL" +
                ")");


        if (testing) {
            // TESTING ONLY
            DB.execSQL("DELETE FROM Transactions;");
            DB.execSQL("DELETE FROM Category;");
            DB.execSQL("DELETE FROM TransactionsCategory;");
            DB.execSQL("DELETE FROM User;");
        }

        // Base categories
        DB.execSQL("INSERT INTO Category(Name, Type, ColorID) VALUES ('Grocery', 'spends', 1)");
        DB.execSQL("INSERT INTO Category(Name, Type, ColorID) VALUES ('Cafes', 'spends', 2)");
        DB.execSQL("INSERT INTO Category(Name, Type, ColorID) VALUES ('Salary', 'income', 3)");
        DB.execSQL("INSERT INTO Category(Name, Type, ColorID) VALUES ('Entertainment', 'spends', 1)");
        DB.execSQL("INSERT INTO Category(Name, Type, ColorID) VALUES ('Transport', 'spends', 1)");
        DB.execSQL("INSERT INTO Category(Name, Type, ColorID) VALUES ('Other', 'spends', 1)");
        DB.execSQL("INSERT INTO User(Balance, MonthBudget) VALUES (0, 10000)");

        Category.categoryArrayList = getAllCategories();

        if (testing) {
            // Testing transactions
            addTransaction("'test1'", 1, Category.categoryArrayList.get(0), "'gift fo my ex'", "'2015-06-11'");
            addTransaction("'test3'", 10000, Category.categoryArrayList.get(1), "'subway'", "'2015-06-10'");
            addTransaction("'test2'", 350, Category.categoryArrayList.get(1), "'keka'", "'2015-06-17'");
            addTransaction("'test4'", 3, Category.categoryArrayList.get(0), "'gift fo my ex'", "'2015-06-14'");
            addTransaction("'test5'", 1030, Category.categoryArrayList.get(2), "'buhbhu'", "'2015-06-15'");
            addTransaction("'test6'", 150, Category.categoryArrayList.get(1), "'keka'", "'2015-06-16'");
        }

    }

    public void getAllMonths(List<String> listOfMonths, List<List<String>> daysList, List<List<String>> monthDaysList) {
        ArrayList<Transaction> transactions = getAllTransactions();
        ArrayList<String> tempMonthList = new ArrayList<String>();
        for (Transaction transaction : transactions) {
            String[] temp = transaction.getDate().split("-");
            tempMonthList.add(getMonthByNum(temp[1]) + " " + temp[0]);
        }
        tempMonthList = new ArrayList<String>(new HashSet<String>(tempMonthList));
        for (String month : tempMonthList) {
            listOfMonths.add(month);
            ArrayList<String> selectedMonthDays = new ArrayList<>();
            for (Transaction transaction : transactions) {
                if (transaction.getDate().split("-")[0].equals(month.split(" ")[1]) &&
                        getMonthByNum(transaction.getDate().split("-")[1]).equals(month.split(" ")[0]))
                {
                    selectedMonthDays.add(transaction.getDate().split("-")[2] + " "
                            + getMonthByNum(transaction.getDate().split("-")[1]) + " "
                            + transaction.getDate().split("-")[0]);
                }
            }
            daysList.add(selectedMonthDays);
            monthDaysList.add(selectedMonthDays);
        }
    }

    public ArrayList<Transaction> getTransactionsByOneDay(String day){
        //11 June 2015 to 2015-06-11

        String monthStr;
        if (getNumByMonth(day.split(" ")[1])<10)
            monthStr = "0"+getNumByMonth(day.split(" ")[1]);
        else
            monthStr = String.valueOf(getNumByMonth(day.split(" ")[1]));

        ArrayList<Transaction> list = new ArrayList<Transaction>();
        day = day.split(" ")[2]+"-"+monthStr+"-"+day.split(" ")[0];
        String query = String.format("SELECT * FROM Transactions inner join TransactionsCategory on Transactions.TID = TransactionsCategory.TID " +
                "WHERE([Date] = '"+day+"')");
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
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

    String getMonthByNum(String val) {
        switch (val) {
            case "01":
                return "January";
            case "1":
                return "January";
            case "02":
                return "February";
            case "2":
                return "February";
            case "03":
                return "March";
            case "3":
                return "March";
            case "04":
                return "April";
            case "4":
                return "April";
            case "05":
                return "May";
            case "5":
                return "May";
            case "06":
                return "June";
            case "6":
                return "June";
            case "07":
                return "July";
            case "7":
                return "July";
            case "08":
                return "August";
            case "8":
                return "August";
            case "09":
                return "September";
            case "9":
                return "September";
            case "10":
                return "October";
            case "11":
                return "November";
            case "12":
                return "December";
            default:
                return "Unknown";
        }
    }

    public int getNumByMonth(String val) {
        switch (val) {
            case "January":
                return 1;
            case "February":
                return 2;
            case "March":
                return 3;
            case "April":
                return 4;
            case "May":
                return 5;
            case "June":
                return 6;
            case "July":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "October":
                return 10;
            case "November":
                return 11;
            case "December":
                return 12;
            default:
                return 0;
        }
    }

    //region Categories
    // Load all categories from database to string array list
    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> list = new ArrayList<Category>();


        String query = String.format("SELECT * FROM Category");
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);

        while (cursor.moveToNext()) {
            Category category = new Category();
            category.setCid(cursor.getInt(0));
            category.setName(cursor.getString(1));
            category.setType(cursor.getString(2));
            category.setColorID(cursor.getInt(3));
            list.add(category);
        }
        cursor.close();

        // Adding all subcategories

        for (Transaction transaction : getAllTransactions())
            for (Category cat : list)                 // not to be doubled
                if (transaction.getCategory().getName().equals(cat.getName())
                        && !cat.containsSelectedSubcategory(transaction.subCategory))
                    cat.subCategoryList.add(transaction.subCategory);

        // Adding empty category (needed.)
        Category category = new Category();
        category.setCid(0);
        category.setName("");
        category.setType("");
        category.setColorID(0);
        list.add(category);

        return list;
    }

    public int getSummaryForSubcategoryInCurrentMonth(Category category, String subcategory) {
        return category.getSummaryForSubcategory(subcategory, getFinancialMonthTransactions());
    }

    public int getSummaryForSelectedPeriod(Category category, String subcategory, int year1, int month1, int day1, int year2,
                                           int month2, int day2) {
        return category.getSummaryForSubcategory(subcategory, getTransactions(year1, month1, day1, year2, month2, day2));
    }

    //endregion

    //region Transactions
    // Get all spends from selected transaction list
    public int getAllSpendsFrom(ArrayList<Transaction> list) {
        int spends = 0;
        for (Transaction transaction : list) {
            if (transaction.category.getType().equals("spends"))
                spends += transaction.getAmount();
        }
        return spends;
    }

    // Deleting Transaction (by id)
    public void deleteTransaction(int id) {
        DB.execSQL("DELETE * FROM Transactions WHERE TID=" + id + ";");
    }

    // Deleting Transaction (by object)
    public void deleteTransaction(Transaction transaction) {
        DB.execSQL("DELETE * FROM Transactions WHERE TID=" + transaction.getId() + ";");
    }


    // Return all spends during selected transaction list in selected category
    public int getAllSpendsFrom(ArrayList<Transaction> list, Category category) {
        int spends = 0;
        for (Transaction transaction : list) {
            if (transaction.category.getType().equals("spends")
                    && transaction.category == category)
                spends += transaction.getAmount();
        }
        return spends;
    }

    // Load all transactions from database to array list
    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> list = new ArrayList<Transaction>();


        String query = String.format("SELECT * FROM Transactions inner join TransactionsCategory on Transactions.TID = TransactionsCategory.TID");
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
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

    // loads transactions between date 1 and date 2
    public ArrayList<Transaction> getTransactions(int year1, int month1, int day1, int year2,
                                                  int month2, int day2) {
        ArrayList<Transaction> list = new ArrayList<Transaction>();

        String month1Str;
        if (month1<10)
            month1Str = "0"+month1;
        else
            month1Str = String.valueOf(month1);

        String month2Str;
        if (month2<10)
            month2Str = "0"+month2;
        else
            month2Str = String.valueOf(month2);

        String query = String.format("SELECT * FROM Transactions inner join TransactionsCategory on Transactions.TID = TransactionsCategory.TID " +
                "WHERE([Date] BETWEEN '" + year1 + "-" + month1Str + "-" + day1 + "'" +
                " AND '" + year2 + "-" + month2Str + "-" + day2 + "')");
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
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
    public ArrayList<Transaction> getFinancialMonthTransactions() {
        ArrayList<Transaction> list = new ArrayList<Transaction>();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+2:00"));

        String startingMonth;
        if (cal.get(Calendar.DAY_OF_MONTH) < Integer.parseInt(startOfFinancialMonth))
            startingMonth = String.valueOf(cal.get(Calendar.MONTH));
        else
            startingMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);

        if (startingMonth.length() == 1)
            startingMonth = "0" + startingMonth;


        String finishingYear;
        String finishingMonth;
        if (startingMonth.equals(String.valueOf(12))) {
            finishingYear = String.valueOf(cal.get(Calendar.YEAR) + 1);
            finishingMonth = "1";
        } else {
            finishingYear = String.valueOf(cal.get(Calendar.YEAR));
            finishingMonth = String.valueOf(Integer.parseInt(startingMonth) + 1);
        }

        // select * from incoming WHERE([date] BETWEEN '30.10.2012' AND '30.10.2012');
        String query = String.format("SELECT * FROM Transactions inner join TransactionsCategory on Transactions.TID = TransactionsCategory.TID " +
                "WHERE([Date] BETWEEN '" + cal.get(Calendar.YEAR) + "-" + startingMonth + "-" + startOfFinancialMonth + "'" +
                " AND '" + finishingYear + "-" + finishingMonth + "-" + startOfFinancialMonth + "')");
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
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

    // Editing of existing transaction without changing category (by id)
    public void editTransaction(int id, String comment, int amount, String subCategory) {
        DB.execSQL("UPDATE Transactions SET Amount=" + amount + ", Comment=" + comment + ", SubCategory=" + subCategory + ")" +
                "WHERE TID=" + id + ";");
    }

    // Changing of transact category (by object)
    public void setTransactionCategory(Transaction transaction, Category category) {
        DB.execSQL("update TransactionsCategory set CID=" + category.getCid() + " where TID=" + transaction.getId() + "");
        transaction.category = category;
    }


    // Adding new transaction (by values)
    public void addTransaction(String comment, int amount, String category, String subCategory, String date) {
        DB.execSQL("INSERT INTO Transactions (Amount,Comment,SubCategory,Date)\n" +
                "VALUES( " + amount + "," + comment + "," + subCategory + "," + date + ")");
        DB.execSQL("insert into TransactionsCategory (CID,TID) " +
                "values((select CID from Category where Name=" + category + "), " +
                "(select MAX(TID) from Transactions))");
        if (Category.getCategoryByString(category) != null) {
            if (Category.getCategoryByString(category).getType().equals("income"))
                setBalanceChangedBy(amount);
            else
                setBalanceChangedBy(-1 * amount);
        }
    }

    // Adding new transaction (by values)
    public void addTransaction(String comment, float amount, Category category, String subCategory, String date) {
        DB.execSQL("INSERT INTO Transactions (Amount,Comment,SubCategory,Date)\n" +
                "VALUES( " + amount + "," + comment + "," + subCategory + "," + date + ")");
        DB.execSQL("insert into TransactionsCategory (CID,TID) " +
                "values((select CID from Category where Name='" + category.getName() + "'), " +
                "(select MAX(TID) from Transactions))");
        if (category.getType().equals("income"))
            setBalanceChangedBy(amount);
        else
            setBalanceChangedBy(-1 * amount);
        // Category.getCategoryByString(category.getName()).subCategoryList.add(subCategory);

    }


    //endregion

    //region Balance
    public int getBalanceFromDB() {
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT Balance FROM User where User.UID=1", null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public void setBalanceChangedBy(float amount) {
        DB.execSQL("UPDATE User SET Balance=" + amount + " where User.UID=1");
    }

    public void setBalanceTo(float amount) {
        DB.execSQL("UPDATE User SET Balance=" + amount + " where User.UID=1");
    }
    //endregion

    //region Budget
    // get month budget from DB
    public int getBudget() {
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT MonthBudget FROM User where User.UID=1", null);
        cursor.moveToFirst();
        return cursor.getInt(0);

    }

    // Set month budget to
    public void setBudget(int amount) {
        DB.execSQL("UPDATE User SET MonthBudget=" + amount + " where User.UID=1");
    }
    //endregion


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
