package bl.models;

/**
 * Created by romanismagilov on 16.06.15.
 */
public class User {
    static public int balance=0;
    static public int monthBudget=0;
    static public int monthSpends=0;
    static public String percentage;
    // Shortened link to singleton instance
    static DatabaseInstrument dbi;

    // Loading data from Database
    static public void refreshData(){
        dbi = DatabaseInstrument.instance;
        balance = dbi.getBalanceFromDB();
        monthSpends = dbi.getAllSpendsFrom(dbi.getFinancialMonthTransactions());
        monthBudget = dbi.getBudgetFromDB();
        percentage = (100*(float)monthSpends / (float)monthBudget)+"%";

    }
}
